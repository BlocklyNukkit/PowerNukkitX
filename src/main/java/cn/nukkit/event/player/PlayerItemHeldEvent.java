package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class PlayerItemHeldEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item item;
    private final int hotbarSlot;
    /**
     * @deprecated 
     */
    

    public PlayerItemHeldEvent(Player player, Item item, int hotbarSlot) {
        this.player = player;
        this.item = item;
        this.hotbarSlot = hotbarSlot;
    }
    /**
     * @deprecated 
     */
    

    public int getSlot() {
        return this.hotbarSlot;
    }

    public Item getItem() {
        return item;
    }

}
