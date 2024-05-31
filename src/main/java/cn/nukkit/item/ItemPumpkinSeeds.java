package cn.nukkit.item;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;

public class ItemPumpkinSeeds extends Item {
    /**
     * @deprecated 
     */
    
    public ItemPumpkinSeeds() {
        this(0, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemPumpkinSeeds(Integer meta) {
        this(meta, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemPumpkinSeeds(Integer meta, int count) {
        super(PUMPKIN_SEEDS, 0, count, "Pumpkin Seeds");
        this.block = Block.get(BlockID.PUMPKIN_STEM);
    }
}