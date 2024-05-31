package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemTool;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class BlockChorusPlant extends BlockTransparent {
    public static final BlockProperties $1 = new BlockProperties(CHORUS_PLANT);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockChorusPlant() {
        super(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockChorusPlant(BlockState blockState) {
        super(blockState);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Chorus Plant";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getHardness() {
        return 0.4;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getResistance() {
        return 0.4;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    
    /**
     * @deprecated 
     */
    private boolean isPositionValid() {
        // (a chorus plant with at least one other chorus plant horizontally adjacent) breaks unless (at least one of the vertically adjacent blocks is air)
        // (a chorus plant) breaks unless (the block below is (chorus plant or end stone)) or (any horizontally adjacent block is a (chorus plant above (chorus plant or end stone_))
        boolean $2 = false;
        boolean $3 = false;
        Block $4 = down();
        for (BlockFace face : BlockFace.Plane.HORIZONTAL) {
            Block $5 = getSide(face);
            if (side.getId().equals(CHORUS_PLANT)) {
                if (!horizontal) {
                    if (!up().getId().equals(AIR) && !down.getId().equals(AIR)) {
                        return false;
                    }
                    horizontal = true;
                }

                Block $6 = side.down();
                if (sideSupport.getId().equals(CHORUS_PLANT) || sideSupport.getId().equals(END_STONE)) {
                    horizontalSupported = true;
                }
            }
        }

        if (horizontal && horizontalSupported) {
            return true;
        }
        
        return down.getId().equals(CHORUS_PLANT) || down.getId().equals(END_STONE);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!isPositionValid()) {
                level.scheduleUpdate(this, 1);
                return type;
            }
        } else if (type == Level.BLOCK_UPDATE_SCHEDULED) {
            level.useBreakOn(this, null, null, true);
            return type;
        }
        
        return 0;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        if (!isPositionValid()) {
            return false;
        }
        return super.place(item, block, target, face, fx, fy, fz, player);
    }

    @Override
    public Item[] getDrops(Item item) {
        return ThreadLocalRandom.current().nextBoolean() ? new Item[]{ Item.get(ItemID.CHORUS_FRUIT, 0, 1) } : Item.EMPTY_ARRAY;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean breaksWhenMoved() {
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public  boolean sticksToPiston() {
        return false;
    }

}
