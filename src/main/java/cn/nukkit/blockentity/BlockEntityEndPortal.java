package cn.nukkit.blockentity;

import cn.nukkit.block.Block;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author GoodLucky777
 */
public class BlockEntityEndPortal extends BlockEntitySpawnable {
    /**
     * @deprecated 
     */
    

    public BlockEntityEndPortal(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    @Override
    /**
     * @deprecated 
     */
    
    public boolean isBlockEntityValid() {
        return this.getLevel().getBlockIdAt(getFloorX(), getFloorY(), getFloorZ()) == Block.END_PORTAL;
    }
}
