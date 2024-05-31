package cn.nukkit.inventory;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntityShulkerBox;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.network.protocol.BlockEventPacket;
import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;
import cn.nukkit.tags.BlockTags;

import java.util.Map;

/**
 * @author PetteriM1
 */
public class ShulkerBoxInventory extends ContainerInventory {
    /**
     * @deprecated 
     */
    
    public ShulkerBoxInventory(BlockEntityShulkerBox box) {
        super(box, InventoryType.CONTAINER, 27);
    }

    @Override
    public BlockEntityShulkerBox getHolder() {
        return (BlockEntityShulkerBox) this.holder;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void init() {
        Map<Integer, ContainerSlotType> map = super.slotTypeMap();
        for ($1nt $1 = 0; i < getSize(); i++) {
            map.put(i, ContainerSlotType.SHULKER_BOX);
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void onOpen(Player who) {
        super.onOpen(who);

        if (this.getViewers().size() == 1) {
            BlockEventPacket $2 = new BlockEventPacket();
            pk.x = (int) this.getHolder().getX();
            pk.y = (int) this.getHolder().getY();
            pk.z = (int) this.getHolder().getZ();
            pk.case1 = 1;
            pk.case2 = 2;

            Level $3 = this.getHolder().getLevel();
            if (level != null) {
                level.addSound(this.getHolder().add(0.5, 0.5, 0.5), Sound.RANDOM_SHULKERBOXOPEN);
                level.addChunkPacket((int) this.getHolder().getX() >> 4, (int) this.getHolder().getZ() >> 4, pk);
            }
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void onClose(Player who) {
        if (this.getViewers().size() == 1) {
            BlockEventPacket $4 = new BlockEventPacket();
            pk.x = (int) this.getHolder().getX();
            pk.y = (int) this.getHolder().getY();
            pk.z = (int) this.getHolder().getZ();
            pk.case1 = 1;
            pk.case2 = 0;

            Level $5 = this.getHolder().getLevel();
            if (level != null) {
                level.addSound(this.getHolder().add(0.5, 0.5, 0.5), Sound.RANDOM_SHULKERBOXCLOSED);
                level.addChunkPacket((int) this.getHolder().getX() >> 4, (int) this.getHolder().getZ() >> 4, pk);
            }
        }

        super.onClose(who);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canAddItem(Item item) {
        if (item.isBlock() && item.getBlockUnsafe().is(BlockTags.PNX_SHULKERBOX)) {
            // Do not allow nested shulker boxes.
            return false;
        }
        return super.canAddItem(item);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canCauseVibration() {
        return true;
    }
}
