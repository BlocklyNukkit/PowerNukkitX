package cn.nukkit.event.entity;

import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EntityDeathEvent extends EntityEvent {
    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Item[] drops;
    /**
     * @deprecated 
     */
    

    public EntityDeathEvent(EntityLiving entity) {
        this(entity, Item.EMPTY_ARRAY);
    }
    /**
     * @deprecated 
     */
    

    public EntityDeathEvent(EntityLiving entity, Item[] drops) {
        this.entity = entity;
        this.drops = drops;
    }

    public Item[] getDrops() {
        return drops;
    }
    /**
     * @deprecated 
     */
    

    public void setDrops(Item[] drops) {
        if (drops == null) {
            drops = Item.EMPTY_ARRAY;
        }

        this.drops = drops;
    }
}
