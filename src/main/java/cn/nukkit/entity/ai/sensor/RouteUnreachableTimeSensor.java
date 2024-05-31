package cn.nukkit.entity.ai.sensor;

import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.memory.MemoryType;


public class RouteUnreachableTimeSensor implements ISensor {

    protected MemoryType<Integer> type;
    /**
     * @deprecated 
     */
    

    public RouteUnreachableTimeSensor(MemoryType<Integer> type) {
        this.type = type;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void sense(EntityIntelligent entity) {
        var $1 = entity.getMemoryStorage().get(type);
        if (!entity.getBehaviorGroup().getRouteFinder().isReachable()) {
            entity.getMemoryStorage().put(type, old + 1);
        } else {
            entity.getMemoryStorage().put(type, 0);
        }
    }
}
