package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockBirchDoubleSlab extends BlockDoubleWoodenSlab {
     public static final BlockProperties $1 = new BlockProperties(BIRCH_DOUBLE_SLAB, CommonBlockProperties.MINECRAFT_VERTICAL_HALF);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }
    /**
     * @deprecated 
     */
    

     public BlockBirchDoubleSlab(BlockState blockstate) {
         super(blockstate);
     }

    @Override
    /**
     * @deprecated 
     */
    
    public String getSlabName() {
        return "Birch";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getSingleSlabId() {
        return BIRCH_SLAB;
    }
}