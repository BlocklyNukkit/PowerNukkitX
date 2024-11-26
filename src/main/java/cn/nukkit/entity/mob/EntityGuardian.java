package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.entity.EntitySwimmable;
import cn.nukkit.entity.ai.behavior.Behavior;
import cn.nukkit.entity.ai.behaviorgroup.BehaviorGroup;
import cn.nukkit.entity.ai.behaviorgroup.IBehaviorGroup;
import cn.nukkit.entity.ai.controller.DiveController;
import cn.nukkit.entity.ai.controller.LookController;
import cn.nukkit.entity.ai.controller.SpaceMoveController;
import cn.nukkit.entity.ai.executor.SpaceRandomRoamExecutor;
import cn.nukkit.entity.ai.route.finder.impl.SimpleSpaceAStarRouteFinder;
import cn.nukkit.entity.ai.route.posevaluator.SwimmingPosEvaluator;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntityGuardian extends EntityMob implements EntitySwimmable {

    @Override
    @NotNull public String getIdentifier() {
        return GUARDIAN;
    }

    public EntityGuardian(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return new BehaviorGroup(
                this.tickSpread,
                Set.of(),
                Set.of(
                        new Behavior(
                                new SpaceRandomRoamExecutor(0.36f, 12, 1, 80, false, -1, false, 10),
                                entity -> true, 1)
                ),
                Set.of(),
                Set.of(new SpaceMoveController(), new LookController(true, true), new DiveController()),
                new SimpleSpaceAStarRouteFinder(new SwimmingPosEvaluator(), this),
                this
        );
    }

    @Override
    public void initEntity() {
        this.setMaxHealth(30);
        super.initEntity();
    }

    @Override
    public String getOriginalName() {
        return "Guardian";
    }

    @Override
    public float getWidth() {
        return 0.85f;
    }

    @Override
    public float getHeight() {
        return 0.85f;
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public Item[] getDrops() {
        return new Item[]{Item.get(Item.PRISMARINE_SHARD, 0, Utils.rand(0, 2))};
    }

    @Override
    public Integer getExperienceDrops() {
        return 10;
    }
}
