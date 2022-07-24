package cn.nukkit.entity.ai.behaviorgroup;

import cn.nukkit.Server;
import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.behavior.IBehavior;
import cn.nukkit.entity.ai.controller.IController;
import cn.nukkit.entity.ai.memory.*;
import cn.nukkit.entity.ai.route.RouteFindingManager;
import cn.nukkit.entity.ai.route.SimpleRouteFinder;
import cn.nukkit.entity.ai.sensor.ISensor;
import cn.nukkit.math.Vector3;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@PowerNukkitXOnly
@Since("1.6.0.0-PNX")
@Getter
public class BehaviorGroup implements IBehaviorGroup {

    /**
     * 全部行为
     */
    protected final Set<IBehavior> behaviors = new HashSet<>();
    /**
     * 传感器
     */
    protected final Set<ISensor> sensors = new HashSet<>();
    /**
     * 控制器
     */
    protected final Set<IController> controllers = new HashSet<>();
    /**
     * 正在运行的行为
     */
    protected final Set<IBehavior> runningBehaviors = new HashSet<>();
    /**
     * 记忆存储器
     */
    protected final IMemoryStorage memoryStorage = new MemoryStorage();
    /**
     * 寻路器(非异步，因为没必要，生物AI本身就是并行的)
     */
    protected SimpleRouteFinder routeFinder;
    /**
     * 寻路任务
     */
    protected RouteFindingManager.RouteFindingTask routeFindingTask;

    /**
     * 决定多少gt更新一次路径
     */
    //todo: 根据tps动态调整计算速率
    protected int routeUpdateCycle = 20;//gt

    /**
     * 记录距离上次路径更新过去的gt数
     */
    protected int currentRouteUpdateTick = 0;//gt

    public BehaviorGroup(Set<IBehavior> behaviors, Set<ISensor> sensors, Set<IController> controllers, SimpleRouteFinder routeFinder) {
        this.behaviors.addAll(behaviors);
        this.sensors.addAll(sensors);
        this.controllers.addAll(controllers);
        this.routeFinder = routeFinder;
    }

    public void addBehavior(IBehavior behavior) {
        this.behaviors.add(behavior);
    }

    public void addSensor(ISensor sensor) {
        this.sensors.add(sensor);
    }

    public void addController(IController controller) {
        this.controllers.add(controller);
    }

    /**
     * 运行并刷新正在运行的行为
     */
    public void tickRunningBehaviors(EntityIntelligent entity) {
        Set<IBehavior> removed = new HashSet<>();
        for (var behavior : runningBehaviors) {
            if (!behavior.execute(entity)) {
                removed.add(behavior);
                behavior.onStop(entity);
            }
        }
        runningBehaviors.removeAll(removed);
    }

    public void collectSensorData(EntityIntelligent entity) {
        for (ISensor sensor : sensors) {
            sensor.sense(entity);
        }
    }

    /**
     * 评估所有行为
     *
     * @param entity 评估的实体对象
     */
    public void evaluateBehaviors(EntityIntelligent entity) {
        //存储评估成功的行为（未过滤优先级）
        var evalSucceed = new HashSet<IBehavior>();
        int highestPriority = Integer.MIN_VALUE;
        for (IBehavior behavior : behaviors) {
            //若已经在运行了，就不需要评估了
            if (runningBehaviors.contains(behavior)) continue;
            if (behavior.evaluate(entity)) {
                evalSucceed.add(behavior);
                if (behavior.getPriority() > highestPriority) {
                    highestPriority = behavior.getPriority();
                }
            }
        }
        //如果没有评估结果，则返回空
        if (evalSucceed.isEmpty()) return;
        //过滤掉低优先级的行为
        final var finalHighestPriority = highestPriority;
        evalSucceed.removeIf(entry -> entry.getPriority() != finalHighestPriority);
        if (evalSucceed.isEmpty()) return;
        //当前运行的行为的优先级（优先级必定都是一样的，所以说不需要比较得出）
        var currentHighestPriority = runningBehaviors.isEmpty() ? Integer.MIN_VALUE : runningBehaviors.iterator().next().getPriority();
        //result的行为优先级
        int resultHighestPriority = evalSucceed.iterator().next().getPriority();
        if (resultHighestPriority < currentHighestPriority){
            //如果result的优先级低于当前运行的行为，则不执行
            return;
        } else if (resultHighestPriority > currentHighestPriority) {
            //如果result的优先级比当前运行的行为的优先级高，则替换当前运行的所有行为
            interruptAllRunningBehaviors(entity);
            runningBehaviors.addAll(evalSucceed);
        }
        //如果result的优先级和当前运行的行为的优先级一样，则添加result的行为
        else addToRunningBehaviors(entity, evalSucceed);
    }

    @Override
    public void applyController(EntityIntelligent entity) {
        for (IController controller : controllers) {
            controller.control(entity);
        }
    }

    @Override
    public void updateRoute(EntityIntelligent entity) {
        currentRouteUpdateTick++;
        //到达更新周期时，开始重新计算新路径
        if (currentRouteUpdateTick >= routeUpdateCycle) {
            Vector3 target = getRouteTarget();
            //若有路径目标，则计算新路径
            if (target != null && (routeFindingTask == null || routeFindingTask.getFinished() || Server.getInstance().getNextTick() - routeFindingTask.getStartTime() > 8)) {
                    //clone防止寻路器潜在的修改
                    RouteFindingManager.getInstance().submit(routeFindingTask = new RouteFindingManager.RouteFindingTask(routeFinder, task -> {
                        updateMoveDirection(entity);
                        setMemoryData(NeedUpdateMoveDirectionMemory.class,false);
                        currentRouteUpdateTick = 0;
                    })
                    .setStart(entity.clone())
                    .setTarget(target));
            } else {
                //没有路径目标，则清除路径信息
                clearMemory(MoveDirectionMemory.class);
            }
        }
        //若不能再移动了，则清除路径信息
        var reachableTarget = routeFinder.getReachableTarget();
        if (reachableTarget != null && entity.floor().equals(reachableTarget.floor())) {
            clearMemory(MoveTargetMemory.class);
            clearMemory(MoveDirectionMemory.class);
        }
        if (needUpdateMoveDirection()) {
            if (routeFinder.hasNext()) {
                //若有新的移动方向，则更新
                updateMoveDirection(entity);
                setMemoryData(NeedUpdateMoveDirectionMemory.class,false);
            }
        }
    }

    @Nullable
    protected Vector3 getRouteTarget() {
        return memoryStorage.get(MoveTargetMemory.class).getData();
    }

    protected boolean needUpdateMoveDirection() {
        return memoryStorage.checkData(NeedUpdateMoveDirectionMemory.class,true);
    }

    protected void clearMemory(Class<? extends IMemory<?>> clazz) {
        memoryStorage.clear(clazz);
    }

    protected <T> void setMemoryData(Class<? extends IMemory<T>> clazz, T data) {
        memoryStorage.get(clazz).setData(data);
    }

    protected void updateMoveDirection(EntityIntelligent entity) {
        MoveDirectionMemory directionMemory = memoryStorage.get(MoveDirectionMemory.class);
        Vector3 end;
        if (directionMemory != null) {
            end = directionMemory.getEnd();
        } else {
            end = entity.clone();
        }
        var next = routeFinder.next();
        if (next != null) {
            directionMemory.setStart(end);
            directionMemory.setEnd(next.getVector3());
            directionMemory.updateDirection();
        }
    }

    /**
     * 添加评估成功后的行为到{@link BehaviorGroup#runningBehaviors}
     *
     * @param entity    评估的实体
     * @param behaviors 要添加的行为
     */
    protected void addToRunningBehaviors(EntityIntelligent entity, @NotNull Set<IBehavior> behaviors) {
        behaviors.forEach((behavior) -> {
            behavior.onStart(entity);
            runningBehaviors.add(behavior);
        });
    }

    /**
     * 中断所有正在运行的行为
     */
    protected void interruptAllRunningBehaviors(EntityIntelligent entity) {
        for (IBehavior behavior : runningBehaviors) {
            behavior.onInterrupt(entity);
        }
        runningBehaviors.clear();
    }

    /**
     * 获取指定Set<IBehavior>内的最高优先级
     *
     * @param behaviors 行为组
     * @return int 最高优先级
     */
    protected int getHighestPriority(@NotNull Set<IBehavior> behaviors) {
        int highestPriority = Integer.MIN_VALUE;
        for (IBehavior behavior : behaviors) {
            if (behavior.getPriority() > highestPriority) {
                highestPriority = behavior.getPriority();
            }
        }
        return highestPriority;
    }
}
