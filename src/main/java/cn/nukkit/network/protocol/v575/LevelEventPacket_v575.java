package cn.nukkit.network.protocol.v575;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
@Deprecated(since = "1.19.70-r1")
public class LevelEventPacket_v575 extends DataPacket {
    public static final byte NETWORK_ID = ProtocolInfo.LEVEL_EVENT_PACKET;

    public static final int EVENT_SOUND_CLICK = 1000;
    public static final int EVENT_SOUND_CLICK_FAIL = 1001;
    public static final int EVENT_SOUND_SHOOT = 1002;
    public static final int EVENT_SOUND_DOOR = 1003;
    public static final int EVENT_SOUND_FIZZ = 1004;
    public static final int EVENT_SOUND_TNT = 1005;
    public static final int EVENT_SOUND_PLAY_RECORDING = 1006;
    public static final int EVENT_SOUND_GHAST = 1007;
    public static final int EVENT_SOUND_GHAST_SHOOT = 1008;
    public static final int EVENT_SOUND_BLAZE_SHOOT = 1009;
    public static final int EVENT_SOUND_DOOR_BUMP = 1010;
    public static final int EVENT_SOUND_DOOR_CRASH = 1012;

    public static final int EVENT_SOUND_ZOMBIE_INFECTED = 1016;
    public static final int EVENT_SOUND_ZOMBIE_CONVERTED = 1017;
    public static final int EVENT_SOUND_ENDERMAN_TELEPORT = 1018;

    public static final int EVENT_SOUND_ANVIL_BREAK = 1020;
    public static final int EVENT_SOUND_ANVIL_USE = 1021;
    public static final int EVENT_SOUND_ANVIL_FALL = 1022;

    public static final int EVENT_SOUND_ITEM_DROP = 1030;
    public static final int EVENT_SOUND_ITEM_THROWN = 1031;
    public static final int EVENT_SOUND_PORTAL = 1032;

    public static final int EVENT_SOUND_ITEM_FRAME_ITEM_ADDED = 1040;
    public static final int EVENT_SOUND_ITEM_FRAME_REMOVED = 1041;
    public static final int EVENT_SOUND_ITEM_FRAME_PLACED = 1042;
    public static final int EVENT_SOUND_ITEM_FRAME_ITEM_REMOVED = 1043;
    public static final int EVENT_SOUND_ITEM_FRAME_ITEM_ROTATED = 1044;

    public static final int EVENT_SOUND_CAMERA_TAKE_PICTURE = 1050;
    public static final int EVENT_SOUND_EXPERIENCE_ORB = 1051;
    public static final int EVENT_SOUND_TOTEM = 1052;

    public static final int EVENT_SOUND_ARMOR_STAND_BREAK = 1060;
    public static final int EVENT_SOUND_ARMOR_STAND_HIT = 1061;
    public static final int EVENT_SOUND_ARMOR_STAND_FALL = 1062;
    public static final int EVENT_SOUND_ARMOR_STAND_PLACE = 1063;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_SOUND_POINTED_DRIPSTONE_LAND = 1064;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_SOUND_DYE_USED = 1065;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_SOUND_INK_SACE_USED = 1066;

    public static final int EVENT_GUARDIAN_CURSE = 2006;
    public static final int EVENT_PARTICLE_DEATH_SMOKE = 2007;
    public static final int EVENT_PARTICLE_BLOCK_FORCE_FIELD = 2008;
    public static final int EVENT_PARTICLE_PROJECTILE_HIT = 2009;
    public static final int EVENT_PARTICLE_DRAGON_EGG_TELEPORT = 2010;
    public static final int EVENT_PARTICLE_CROP_EATEN = 2011;
    public static final int EVENT_PARTICLE_CRIT = 2012;
    public static final int EVENT_PARTICLE_ENDERMAN_TELEPORT = 2013;
    public static final int EVENT_PARTICLE_PUNCH_BLOCK = 2014;
    public static final int EVENT_PARTICLE_BUBBLES = 2015;
    public static final int EVENT_PARTICLE_EVAPORATE = 2016;
    public static final int EVENT_PARTICLE_DESTROY_ARMOR_STAND = 2017;
    public static final int EVENT_PARTICLE_BREAKING_EGG = 2018;
    public static final int EVENT_PARTICLE_DESTROY_EGG = 2019;
    public static final int EVENT_PARTICLE_EVAPORATE_WATER = 2020;
    public static final int EVENT_PARTICLE_DESTROY_BLOCK_NO_SOUND = 2021;
    public static final int EVENT_PARTICLE_KNOCKBACK_ROAR = 2022;
    public static final int EVENT_PARTICLE_TELEPORT_TRAIL = 2023;
    public static final int EVENT_PARTICLE_POINT_CLOUD = 2024;
    public static final int EVENT_PARTICLE_EXPLOSION = 2025;
    public static final int EVENT_PARTICLE_BLOCK_EXPLOSION = 2026;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_VIBRATION_SIGNAL = 2027;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_DRIPSTONE_DRIP = 2028;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_FIZZ_EFFECT = 2029;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_WAX_ON = 2030;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_WAX_OFF = 2031;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_SCRAPE = 2032;

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final int EVENT_PARTICLE_ELECTRIC_SPARK = 2033;

    public static final int EVENT_PARTICLE_TURTLE_EGG = 2034;
    public static final int EVENT_PARTICLE_SCULK_SHRIEK = 2035;
    public static final int EVENT_PARTICLE_SCULK_CATALYST_BLOOM = 2036;
    public static final int EVENT_PARTICLE_SCULK_CHARGE = 2037;
    public static final int EVENT_PARTICLE_SCULK_CHARGE_POP = 2038;
    public static final int EVENT_PARTICLE_SONIC_EXPLOSION = 2039;

    public static final int EVENT_START_RAIN = 3001;
    public static final int EVENT_START_THUNDER = 3002;
    public static final int EVENT_STOP_RAIN = 3003;
    public static final int EVENT_STOP_THUNDER = 3004;
    public static final int EVENT_GLOBAL_PAUSE = 3005;
    public static final int EVENT_SIM_TIME_STEP = 3006;
    public static final int EVENT_SIM_TIME_SCALE = 3007;

    public static final int EVENT_SOUND_BUTTON_CLICK = 3500;
    public static final int EVENT_SOUND_EXPLODE = 3501;
    public static final int EVENT_CAULDRON_DYE_ARMOR = 3502;
    public static final int EVENT_CAULDRON_CLEAN_ARMOR = 3503;
    public static final int EVENT_CAULDRON_FILL_POTION = 3504;
    public static final int EVENT_CAULDRON_TAKE_POTION = 3505;
    public static final int EVENT_SOUND_SPLASH = 3506;
    public static final int EVENT_CAULDRON_TAKE_WATER = 3507;
    public static final int EVENT_CAULDRON_ADD_DYE = 3508;
    public static final int EVENT_CAULDRON_CLEAN_BANNER = 3509;
    public static final int EVENT_CAULDRON_FLUSH = 3510;
    public static final int EVENT_AGENT_SPAWN_EFFECT = 3511;
    public static final int EVENT_CAULDRON_FILL_LAVA = 3512;
    public static final int EVENT_CAULDRON_TAKE_LAVA = 3513;
    public static final int EVENT_CAULDRON_FILL_POWDER_SNOW = 3514;
    public static final int EVENT_CAULDRON_TAKE_POWDER_SNOW = 3515;

    public static final int EVENT_BLOCK_START_BREAK = 3600;
    public static final int EVENT_BLOCK_STOP_BREAK = 3601;
    public static final int EVENT_BLOCK_UPDATE_BREAK = 3602;

    public static final int EVENT_SET_DATA = 4000;

    public static final int EVENT_PLAYERS_SLEEPING = 9800;
    public static final int EVENT_SLEEPING_PLAYERS = 9801;

    public static final int EVENT_JUMP_PREVENTED = 9810;

    public static final int EVENT_ADD_PARTICLE_MASK = 0x4000;

    public int evid;
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public int data = 0;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        this.evid = this.getVarInt();
        Vector3f v = this.getVector3f();
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.data = this.getVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.evid);
        this.putVector3f(this.x, this.y, this.z);
        this.putVarInt(this.data);
    }
}
