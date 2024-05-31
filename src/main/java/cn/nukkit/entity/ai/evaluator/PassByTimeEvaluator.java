package cn.nukkit.entity.ai.evaluator;

import cn.nukkit.Server;
import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.memory.MemoryType;

/**
 * 用于判断一个时间类型的记忆是否在指定范围内的评估器
 * <p>
 * An evaluator used to determine whether a time type of memory is within a specified range
 */


public class PassByTimeEvaluator implements IBehaviorEvaluator {

    protected MemoryType<Integer> timedMemory;
    protected int minPassByTimeRange;
    protected int maxPassByTimeRange;

    /**
     * 用于判断一个时间类型的记忆是否在指定范围内的评估器
     * <p>
     * An evaluator used to determine whether a time type of memory is within a specified range
     *
     * @param timedMemory        the timed memory
     * @param minPassByTimeRange the min pass by time range
     * @param maxPassByTimeRange the max pass by time range
     */
    /**
     * @deprecated 
     */
    
    public PassByTimeEvaluator(MemoryType<Integer> timedMemory, int minPassByTimeRange, int maxPassByTimeRange) {
        this.timedMemory = timedMemory;
        this.minPassByTimeRange = minPassByTimeRange;
        this.maxPassByTimeRange = maxPassByTimeRange;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean evaluate(EntityIntelligent entity) {
        var $1 = entity.getMemoryStorage().get(timedMemory);
        int $2 = Server.getInstance().getTick() - time;
        return passByTime >= minPassByTimeRange && passByTime <= maxPassByTimeRange;
    }
}
