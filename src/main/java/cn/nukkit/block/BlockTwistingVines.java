package cn.nukkit.block;

import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import static cn.nukkit.block.property.CommonBlockProperties.TWISTING_VINES_AGE;

public class BlockTwistingVines extends BlockVinesNether {
    public static final BlockProperties $1 = new BlockProperties(TWISTING_VINES, TWISTING_VINES_AGE);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockTwistingVines() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockTwistingVines(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Twisting Vines";
    }

    @Override
    @NotNull public BlockFace getGrowthDirection() {
        return BlockFace.UP;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getVineAge() {
        return getPropertyValue(TWISTING_VINES_AGE);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setVineAge(int vineAge) {
        setPropertyValue(TWISTING_VINES_AGE, vineAge);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxVineAge() {
        return TWISTING_VINES_AGE.getMax();
    }
}