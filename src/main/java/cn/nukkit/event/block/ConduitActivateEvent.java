package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.HandlerList;


public class ConduitActivateEvent extends BlockEvent {

    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }
    /**
     * @deprecated 
     */
    

    public ConduitActivateEvent(Block block) {
        super(block);
    }

}
