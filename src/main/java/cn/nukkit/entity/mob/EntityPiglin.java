package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityWalkable;
import cn.nukkit.entity.ai.behavior.Behavior;
import cn.nukkit.entity.ai.behaviorgroup.BehaviorGroup;
import cn.nukkit.entity.ai.behaviorgroup.IBehaviorGroup;
import cn.nukkit.entity.ai.controller.LookController;
import cn.nukkit.entity.ai.controller.WalkController;
import cn.nukkit.entity.ai.evaluator.EntityCheckEvaluator;
import cn.nukkit.entity.ai.executor.FlatRandomRoamExecutor;
import cn.nukkit.entity.ai.executor.MeleeAttackExecutor;
import cn.nukkit.entity.ai.executor.VarCopyExecutor;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;
import cn.nukkit.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import cn.nukkit.entity.ai.route.posevaluator.WalkingPosEvaluator;
import cn.nukkit.entity.ai.sensor.NearestPlayerSensor;
import cn.nukkit.entity.ai.sensor.NearestTargetEntitySensor;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.experimental.Wither;
import org.apache.logging.log4j.core.Core;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Erik Miller | EinBexiii
 */

public class EntityPiglin extends EntityMob implements EntityWalkable {
    @Override
    @NotNull public String getIdentifier() {
        return PIGLIN;
    }

    public EntityPiglin(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return new BehaviorGroup(
                this.tickSpread,
                Set.of(new Behavior(new VarCopyExecutor(), none(), 20)),
                Set.of(
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, false, 30), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                entity -> getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER) instanceof Player player && Arrays.stream(player.getInventory().getArmorContents()).anyMatch(item -> item instanceof ItemArmor armor && armor.getTier() == ItemArmor.TIER_GOLD)), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                ),
                Set.of(
                        new NearestPlayerSensor(40, 0, 20),
                        new NearestTargetEntitySensor<>(0, 15, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget)
                ),
                Set.of(new WalkController(), new LookController(true, true)),
                new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this),
                this
        );
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(16);
        this.diffHandDamage = new float[]{2.5f, 3f, 4.5f};
        super.initEntity();
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public String getOriginalName() {
        return "Piglin";
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return !this.isBaby()/*TODO: Should this check player's golden armor?*/;
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return switch (entity.getIdentifier().toString()) {
            case WITHER_SKELETON, WITHER -> true;
            case HOGLIN -> entity instanceof EntityHoglin hoglin && !hoglin.isBaby();
            default -> false;
        };
    }
}
