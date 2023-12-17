package cn.nukkit.item;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;


public class ItemDoorMangrove extends Item{
    public ItemDoorMangrove() {
        this(0, 1);
    }

    public ItemDoorMangrove(Integer meta) {
        this(meta, 1);
    }

    public ItemDoorMangrove(Integer meta, int count) {
        super(ITEM_MANGROVE_DOOR, 0, count, "Mangrove Door");
        this.block = Block.get(BlockID.MANGROVE_DOOR);
    }
}
