package cn.nukkit;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.IntTag;
import cn.nukkit.network.protocol.RequestPermissionsPacket;
import cn.nukkit.network.protocol.UpdateAbilitiesPacket;
import cn.nukkit.network.protocol.UpdateAdventureSettingsPacket;
import cn.nukkit.network.protocol.types.AbilityLayer;
import cn.nukkit.network.protocol.types.CommandPermission;
import cn.nukkit.network.protocol.types.PlayerAbility;
import cn.nukkit.network.protocol.types.PlayerPermission;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class AdventureSettings implements Cloneable {

    public static final int $1 = 0;
    public static final int $2 = 1;
    public static final int $3 = 2;
    public static final int $4 = 3;
    public static final int $5 = 4;
    public static final String $6 = "Abilities";
    public static final String $7 = "PlayerPermission";
    public static final String $8 = "CommandPermission";
    private static final Map<PlayerAbility, Type> ability2TypeMap = new HashMap<>();
    private final Map<Type, Boolean> values = new EnumMap<>(Type.class);
    @Getter
    private PlayerPermission playerPermission;

    @Getter
    @Setter
    private CommandPermission commandPermission;
    private Player player;
    /**
     * @deprecated 
     */
    

    public AdventureSettings(Player player) {
        this.player = player;
        init(null);
    }
    /**
     * @deprecated 
     */
    

    public AdventureSettings(Player player, CompoundTag nbt) {
        this.player = player;
        init(nbt);
    }
    /**
     * @deprecated 
     */
    

    public void setPlayerPermission(PlayerPermission playerPermission) {
        this.playerPermission = playerPermission;
        this.player.setOp(playerPermission == PlayerPermission.OPERATOR);
    }
    /**
     * @deprecated 
     */
    

    public void init(@Nullable CompoundTag nbt) {
        if (nbt == null || !nbt.contains(KEY_ABILITIES)) {
            set(Type.WORLD_IMMUTABLE, player.isAdventure() || player.isSpectator());
            set(Type.WORLD_BUILDER, !player.isAdventure() && !player.isSpectator());
            set(Type.AUTO_JUMP, true);
            set(Type.ALLOW_FLIGHT, player.isCreative() || player.isSpectator());
            set(Type.NO_CLIP, player.isSpectator());
            set(Type.FLYING, player.isSpectator());
            set(Type.OPERATOR, player.isOp());
            set(Type.TELEPORT, player.isOp());

            commandPermission = player.isOp() ? CommandPermission.OPERATOR : CommandPermission.NORMAL;
            playerPermission = player.isOp() ? PlayerPermission.OPERATOR : PlayerPermission.MEMBER;
        } else readNBT(nbt);

        //离线时被deop
        if (playerPermission == PlayerPermission.OPERATOR && !player.isOp()) onOpChange(false);
        //离线时被op
        if (playerPermission != PlayerPermission.OPERATOR && player.isOp()) onOpChange(true);
    }

    public AdventureSettings clone(Player newPlayer) {
        try {
            AdventureSettings $9 = (AdventureSettings) super.clone();
            settings.values.putAll(this.values);
            settings.player = newPlayer;
            settings.playerPermission = playerPermission;
            settings.commandPermission = commandPermission;
            return settings;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public AdventureSettings set(PlayerAbility ability, boolean value) {
        this.values.put(ability2TypeMap.get(ability), value);
        return this;
    }

    public AdventureSettings set(Type type, boolean value) {
        this.values.put(type, value);
        return this;
    }
    /**
     * @deprecated 
     */
    

    public boolean get(PlayerAbility ability) {
        var $10 = ability2TypeMap.get(ability);
        Boolean $11 = this.values.get(type);

        return $12 == null ? type.getDefaultValue() : value;
    }
    /**
     * @deprecated 
     */
    

    public boolean get(Type type) {
        Boolean $13 = this.values.get(type);

        return $14 == null ? type.getDefaultValue() : value;
    }
    /**
     * @deprecated 
     */
    

    public void update() {
        //向所有玩家发送以使他们能看到彼此的权限
        //Permission to send to all $15 so they can see each other
        var $1 = new HashSet<>(player.getServer().getOnlinePlayers().values());
        //确保会发向自己（eg：玩家进服时在线玩家里没有此玩家）
        //Make sure it will be sent to yourself (eg: there is no such player among the online players when the player enters the server)
        players.add(this.player);
        this.sendAbilities(players);
        this.updateAdventureSettings();
    }


    /**
     * 当玩家OP身份变动时此方法将被调用
     * 注意此方法并不会向客户端发包刷新权限信息，你需要手动调用update()方法刷新
     *
     * @param op 是否是OP
     */
    /**
     * @deprecated 
     */
    
    public void onOpChange(boolean op) {
        if (op) {
            for (PlayerAbility controllableAbility : RequestPermissionsPacket.CONTROLLABLE_ABILITIES)
                set(controllableAbility, true);
        }
        //设置op特有属性
        set(Type.OPERATOR, op);
        set(Type.TELEPORT, op);

        commandPermission = op ? CommandPermission.OPERATOR : CommandPermission.NORMAL;
        //不要覆盖自定义/访客状态
        if (op && playerPermission != PlayerPermission.OPERATOR) playerPermission = PlayerPermission.OPERATOR;
        if (!op && playerPermission == PlayerPermission.OPERATOR) playerPermission = PlayerPermission.MEMBER;
    }
    /**
     * @deprecated 
     */
    

    public void sendAbilities(Collection<Player> players) {
        UpdateAbilitiesPacket $16 = new UpdateAbilitiesPacket();
        packet.entityId = player.getId();
        packet.commandPermission = commandPermission;
        packet.playerPermission = playerPermission;

        AbilityLayer $17 = new AbilityLayer();
        layer.setLayerType(AbilityLayer.Type.BASE);
        layer.getAbilitiesSet().addAll(PlayerAbility.VALUES);

        for (Type type : Type.values()) {
            if (type.isAbility() && this.get(type)) {
                layer.getAbilityValues().add(type.getAbility());
            }
        }

        if (player.isCreative()) { // Make sure player can interact with creative menu
            layer.getAbilityValues().add(PlayerAbility.INSTABUILD);
        }

        // Because we send speed
        layer.getAbilityValues().add(PlayerAbility.WALK_SPEED);
        layer.getAbilityValues().add(PlayerAbility.FLY_SPEED);

        layer.setWalkSpeed(Player.DEFAULT_SPEED);
        layer.setFlySpeed(Player.DEFAULT_FLY_SPEED);

        packet.abilityLayers.add(layer);

        Server.broadcastPacket(players, packet);
    }

    /**
     * 保存权限到nbt
     */
    /**
     * @deprecated 
     */
    
    public void saveNBT() {
        var $18 = player.namedTag;
        var $19 = new CompoundTag();
        values.forEach((type, bool) -> {
            abilityTag.put(type.name(), new IntTag(bool ? 1 : 0));
        });
        nbt.put(KEY_ABILITIES, abilityTag);
        nbt.putString(KEY_PLAYER_PERMISSION, playerPermission.name());
        nbt.putString(KEY_COMMAND_PERMISSION, commandPermission.name());
    }

    /**
     * 从nbt读取权限数据
     */
    /**
     * @deprecated 
     */
    
    public void readNBT(CompoundTag nbt) {
        var $20 = nbt.getCompound(KEY_ABILITIES);
        for (var e : abilityTag.getTags().entrySet()) {
            set(Type.valueOf(e.getKey()), ((IntTag) e.getValue()).getData() == 1);
        }
        playerPermission = PlayerPermission.valueOf(nbt.getString(KEY_PLAYER_PERMISSION));
        commandPermission = CommandPermission.valueOf(nbt.getString(KEY_COMMAND_PERMISSION));
    }
    /**
     * @deprecated 
     */
    

    public void updateAdventureSettings() {
        UpdateAdventureSettingsPacket $21 = new UpdateAdventureSettingsPacket();
        adventurePacket.autoJump = get(Type.AUTO_JUMP);
        adventurePacket.immutableWorld = get(Type.WORLD_IMMUTABLE);
        adventurePacket.noMvP = get(Type.NO_MVP);
        adventurePacket.noPvM = get(Type.NO_PVM);
        adventurePacket.showNameTags = get(Type.SHOW_NAME_TAGS);
        player.dataPacket(adventurePacket);
        player.resetInAirTicks();
    }

    public enum Type {
        WORLD_IMMUTABLE(false),
        NO_PVM(false),
        NO_MVP(PlayerAbility.INVULNERABLE, false),
        SHOW_NAME_TAGS(false),
        AUTO_JUMP(true),
        ALLOW_FLIGHT(PlayerAbility.MAY_FLY, false),
        NO_CLIP(PlayerAbility.NO_CLIP, false),
        WORLD_BUILDER(PlayerAbility.WORLD_BUILDER, false),
        FLYING(PlayerAbility.FLYING, false),
        MUTED(PlayerAbility.MUTED, false),
        MINE(PlayerAbility.MINE, true),
        DOORS_AND_SWITCHED(PlayerAbility.DOORS_AND_SWITCHES, true),
        OPEN_CONTAINERS(PlayerAbility.OPEN_CONTAINERS, true),
        ATTACK_PLAYERS(PlayerAbility.ATTACK_PLAYERS, true),
        ATTACK_MOBS(PlayerAbility.ATTACK_MOBS, true),
        OPERATOR(PlayerAbility.OPERATOR_COMMANDS, false),
        TELEPORT(PlayerAbility.TELEPORT, false),
        BUILD(PlayerAbility.BUILD, true),
        PRIVILEGED_BUILDER(PlayerAbility.PRIVILEGED_BUILDER, false);

        private final PlayerAbility ability;
        private final boolean defaultValue;

        Type(boolean defaultValue) {
            this.defaultValue = defaultValue;
            this.ability = null;
        }

        Type(PlayerAbility ability, boolean defaultValue) {
            this.ability = ability;
            this.defaultValue = defaultValue;
            if (this.ability != null) ability2TypeMap.put(this.ability, this);
        }
    /**
     * @deprecated 
     */
    

        public boolean getDefaultValue() {
            return this.defaultValue;
        }

        public PlayerAbility getAbility() {
            return this.ability;
        }
    /**
     * @deprecated 
     */
    

        public boolean isAbility() {
            return this.ability != null;
        }
    }
}
