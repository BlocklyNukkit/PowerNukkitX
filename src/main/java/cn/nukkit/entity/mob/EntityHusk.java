package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.ai.behavior.Behavior;
import cn.nukkit.entity.ai.behaviorgroup.BehaviorGroup;
import cn.nukkit.entity.ai.behaviorgroup.IBehaviorGroup;
import cn.nukkit.entity.ai.controller.LookController;
import cn.nukkit.entity.ai.controller.WalkController;
import cn.nukkit.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import cn.nukkit.entity.ai.executor.FlatRandomRoamExecutor;
import cn.nukkit.entity.ai.executor.HuskAttackExecutor;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;
import cn.nukkit.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import cn.nukkit.entity.ai.route.posevaluator.WalkingPosEvaluator;
import cn.nukkit.entity.ai.sensor.NearestPlayerSensor;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.LevelSoundEventPacket;

import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntityHusk extends EntityZombie {

    public static final int NETWORK_ID = 47;

    public EntityHusk(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return new BehaviorGroup(
                this.tickSpread,
                Set.of(),
                Set.of(
                        new Behavior(new HuskAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 10), all(
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                entity -> !entity.getMemoryStorage().notEmpty(CoreMemoryTypes.ATTACK_TARGET) || !(entity.getMemoryStorage().get(CoreMemoryTypes.ATTACK_TARGET) instanceof Player player) || player.isSurvival()
                        ), 3, 1),
                        new Behavior(new HuskAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, false, 10), all(
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                entity -> {
                                    if (entity.getMemoryStorage().isEmpty(CoreMemoryTypes.NEAREST_PLAYER))
                                        return true;
                                    Player player = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
                                    return player.isSurvival();
                                }
                        ), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), (entity -> true), 1, 1)
                ),
                Set.of(new NearestPlayerSensor(40, 0, 20)),
                Set.of(new WalkController(), new LookController(true, true)),
                new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this),
                this
        );
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(20);
        super.initEntity();
        this.setDataProperty(new IntEntityData(Entity.DATA_AMBIENT_SOUND_INTERVAL, Entity.DATA_AMBIENT_SOUND_INTERVAL_RANGE));
        this.setDataProperty(new IntEntityData(Entity.DATA_AMBIENT_SOUND_EVENT_NAME, LevelSoundEventPacket.SOUND_AMBIENT));
        if (this.isBaby()) {
            this.setDataProperty(new IntEntityData(Entity.DATA_AMBIENT_SOUND_EVENT_NAME, LevelSoundEventPacket.SOUND_AMBIENT_BABY));
        }
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @PowerNukkitOnly
    @Since("1.5.1.0-PN")
    @Override
    public String getOriginalName() {
        return "Husk";
    }

    @PowerNukkitOnly
    @Override
    public boolean isUndead() {
        return true;
    }

    @PowerNukkitOnly
    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }
}
