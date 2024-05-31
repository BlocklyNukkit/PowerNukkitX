package cn.nukkit.block;

import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

public class BlockCrackedNetherBricks extends BlockSolid {
    public static final BlockProperties $1 = new BlockProperties(CRACKED_NETHER_BRICKS);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockCrackedNetherBricks() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockCrackedNetherBricks(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Cracked Nether Bricks";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getHardness() {
        return 2;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getResistance() {
        return 6;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canHarvestWithHand() {
        return false;
    }
}