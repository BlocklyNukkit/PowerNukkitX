package cn.nukkit.event.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.math.Vector3;
import lombok.Getter;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EntityMotionEvent extends EntityEvent implements Cancellable {
    @Getter
    private static final HandlerList handlers = new HandlerList();

    private final Vector3 motion;

    public EntityMotionEvent(Entity entity, Vector3 motion) {
        this.entity = entity;
        this.motion = motion;
    }

    @Deprecated
    public Vector3 getVector() {
        return this.motion;
    }

    public Vector3 getMotion() {
        return this.motion;
    }
}
