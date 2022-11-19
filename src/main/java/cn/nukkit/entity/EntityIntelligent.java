package cn.nukkit.entity;

import cn.nukkit.Server;
import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.entity.ai.behaviorgroup.EmptyBehaviorGroup;
import cn.nukkit.entity.ai.behaviorgroup.IBehaviorGroup;
import cn.nukkit.entity.ai.controller.WalkController;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;
import cn.nukkit.entity.ai.memory.IMemoryStorage;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code EntityIntelligent}抽象了一个具有行为组{@link IBehaviorGroup}（也就是具有AI）的实体
 */
@PowerNukkitXOnly
@Since("1.6.0.0-PNX")
@Getter
@Setter
public abstract class EntityIntelligent extends EntityPhysical {

    public static final IBehaviorGroup EMPTY_BEHAVIOR_GROUP = new EmptyBehaviorGroup();

    /**
     * 是否为活跃实体，如果实体不活跃，就应当降低AI运行频率
     */
    protected boolean isActive = true;

    public EntityIntelligent(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        var storage = getMemoryStorage();
        if (storage != null) {
            getMemoryStorage().put(CoreMemoryTypes.ENTITY_SPAWN_TIME, Server.getInstance().getTick());
        }
    }

    @Override
    protected void initEntity() {
        super.initEntity();
    }

    /**
     * 返回此实体持有的行为组{@link IBehaviorGroup} <br/>
     * 默认实现只会返回一个空行为{@link EmptyBehaviorGroup}常量，若你想让实体具有AI，你需要覆写此方法
     *
     * @return 此实体持有的行为组
     */

    @PowerNukkitXOnly
    public IBehaviorGroup getBehaviorGroup() {
        return EMPTY_BEHAVIOR_GROUP;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (!this.isImmobile()) {
            var behaviorGroup = getBehaviorGroup();
            behaviorGroup.tickRunningCoreBehaviors(this);
            behaviorGroup.tickRunningBehaviors(this);
            behaviorGroup.applyController(this);
        }
        return super.onUpdate(currentTick);
    }

    /**
     * 我们将行为组运行循环的部分工作并行化以提高性能
     */
    @Override
    public void asyncPrepare(int currentTick) {
        if (needsRecalcMovement) { // 每次要重新计算实体运动时，都重新计算一次是否活跃
            isActive = level.isHighLightChunk(getChunkX(), getChunkZ());
        }
        if (!this.isImmobile()) { // immobile会禁用实体AI
            var behaviorGroup = getBehaviorGroup();
            if (behaviorGroup == null) return;
            if (needsRecalcMovement) {
                behaviorGroup.collectSensorData(this);
                behaviorGroup.evaluateCoreBehaviors(this);
                behaviorGroup.evaluateBehaviors(this);
                behaviorGroup.updateRoute(this);
            }
        }
        super.asyncPrepare(currentTick);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        var result = super.attack(source);
        var storage = getMemoryStorage();
        if (storage != null) {
            storage.put(CoreMemoryTypes.BE_ATTACKED_EVENT, source);
            storage.put(CoreMemoryTypes.LAST_BE_ATTACKED_TIME, Server.getInstance().getTick());
        }
        return result;
    }

    public IMemoryStorage getMemoryStorage() {
        return getBehaviorGroup().getMemoryStorage();
    }

    /**
     * 返回实体最大的跳跃高度，返回值会用在移动处理上
     *
     * @return 实体最大跳跃高度
     * @see WalkController
     */
    public float getJumpingHeight() {
        return 1.0f;
    }

    public boolean hasMoveDirection() {
        return getMoveDirectionStart() != null && getMoveDirectionEnd() != null;
    }

    @Override
    public boolean enableHeadYaw() {
        return true;
    }

    public Vector3 getLookTarget() {
        return getMemoryStorage().get(CoreMemoryTypes.LOOK_TARGET);
    }

    public void setLookTarget(Vector3 lookTarget) {
        getMemoryStorage().put(CoreMemoryTypes.LOOK_TARGET, lookTarget);
    }

    public Vector3 getMoveTarget() {
        return getMemoryStorage().get(CoreMemoryTypes.MOVE_TARGET);
    }

    public void setMoveTarget(Vector3 moveTarget) {
        getMemoryStorage().put(CoreMemoryTypes.MOVE_TARGET, moveTarget);
    }

    public Vector3 getMoveDirectionStart() {
        return getMemoryStorage().get(CoreMemoryTypes.MOVE_DIRECTION_START);
    }

    public void setMoveDirectionStart(Vector3 moveDirectionStart) {
        getMemoryStorage().put(CoreMemoryTypes.MOVE_DIRECTION_START, moveDirectionStart);
    }

    public Vector3 getMoveDirectionEnd() {
        return getMemoryStorage().get(CoreMemoryTypes.MOVE_DIRECTION_END);
    }

    public void setMoveDirectionEnd(Vector3 moveDirectionEnd) {
        getMemoryStorage().put(CoreMemoryTypes.MOVE_DIRECTION_END, moveDirectionEnd);
    }

    public boolean isShouldUpdateMoveDirection() {
        return getMemoryStorage().get(CoreMemoryTypes.SHOULD_UPDATE_MOVE_DIRECTION);
    }

    public void setShouldUpdateMoveDirection(boolean shouldUpdateMoveDirection) {
        getMemoryStorage().put(CoreMemoryTypes.SHOULD_UPDATE_MOVE_DIRECTION, shouldUpdateMoveDirection);
    }

    public boolean isEnablePitch() {
        return getMemoryStorage().get(CoreMemoryTypes.ENABLE_PITCH);
    }

    public void setEnablePitch(boolean enablePitch) {
        getMemoryStorage().put(CoreMemoryTypes.ENABLE_PITCH, enablePitch);
    }

    //暂时不使用
//    @PowerNukkitXOnly
//    @Since("1.19.40-r4")
//    public boolean isEnableYaw() {
//        return getMemoryStorage().get(CoreMemoryTypes.ENABLE_YAW);
//    }
//
//    @PowerNukkitXOnly
//    @Since("1.19.40-r4")
//    public void setEnableYaw(boolean enableYaw) {
//        getMemoryStorage().put(CoreMemoryTypes.ENABLE_YAW, enableYaw);
//    }
//
//    @PowerNukkitXOnly
//    @Since("1.19.40-r4")
//    public boolean isEnableHeadYaw() {
//        return getMemoryStorage().get(CoreMemoryTypes.ENABLE_HEAD_YAW);
//    }
//
//    @PowerNukkitXOnly
//    @Since("1.19.40-r4")
//    public void setEnableHeadYaw(boolean enableHeadYaw) {
//        getMemoryStorage().put(CoreMemoryTypes.ENABLE_HEAD_YAW, enableHeadYaw);
//    }
}
