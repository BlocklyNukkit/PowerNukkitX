package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;

public class PlayerDropItemEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item drop;
    /**
     * @deprecated 
     */
    

    public PlayerDropItemEvent(Player player, Item drop) {
        this.player = player;
        this.drop = drop;
    }

    public Item getItem() {
        return this.drop;
    }
}
