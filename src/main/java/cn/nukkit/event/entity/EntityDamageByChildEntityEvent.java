package cn.nukkit.event.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.HandlerList;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EntityDamageByChildEntityEvent extends EntityDamageByEntityEvent {
    private final Entity childEntity;
    /**
     * @deprecated 
     */
    

    public EntityDamageByChildEntityEvent(Entity damager, Entity childEntity, Entity entity, DamageCause cause, float damage) {
        super(damager, entity, cause, damage);
        this.childEntity = childEntity;
    }

    public Entity getChild() {
        return childEntity;
    }
}
