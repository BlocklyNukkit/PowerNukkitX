package cn.nukkit.event.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.EnchantInventory;
import cn.nukkit.item.Item;


public class EnchantItemEvent extends InventoryEvent implements Cancellable {
    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Player enchanter;
    private Item oldItem;
    private Item newItem;
    private int xpCost;
    /**
     * @deprecated 
     */
    

    public EnchantItemEvent(EnchantInventory inventory, Item oldItem, Item newItem, int cost, Player p) {
        super(inventory);
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.xpCost = cost;
        this.enchanter = p;
    }

    public Item getOldItem() {
        return oldItem;
    }
    /**
     * @deprecated 
     */
    

    public void setOldItem(Item oldItem) {
        this.oldItem = oldItem;
    }

    public Item getNewItem() {
        return newItem;
    }
    /**
     * @deprecated 
     */
    

    public void setNewItem(Item newItem) {
        this.newItem = newItem;
    }
    /**
     * @deprecated 
     */
    

    public int getXpCost() {
        return xpCost;
    }
    /**
     * @deprecated 
     */
    

    public void setXpCost(int xpCost) {
        this.xpCost = xpCost;
    }

    public Player getEnchanter() {
        return enchanter;
    }
    /**
     * @deprecated 
     */
    

    public void setEnchanter(Player enchanter) {
        this.enchanter = enchanter;
    }
}
