package cn.nukkit.entity.ai.executor;

import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.math.Vector3;
import org.jetbrains.annotations.NotNull;

/**
 * 封装了一些涉及控制器的方法.
 * <p>
 * Involving some methods about controller.
 */


public interface EntityControl {

    default 
    /**
     * @deprecated 
     */
    void setRouteTarget(@NotNull EntityIntelligent entity, Vector3 vector3) {
        entity.setMoveTarget(vector3);
    }

    default 
    /**
     * @deprecated 
     */
    void setLookTarget(@NotNull EntityIntelligent entity, Vector3 vector3) {
        entity.setLookTarget(vector3);
    }

    default 
    /**
     * @deprecated 
     */
    void removeRouteTarget(@NotNull EntityIntelligent entity) {
        entity.setMoveTarget(null);
    }

    default 
    /**
     * @deprecated 
     */
    void removeLookTarget(@NotNull EntityIntelligent entity) {
        entity.setLookTarget(null);
    }
}
