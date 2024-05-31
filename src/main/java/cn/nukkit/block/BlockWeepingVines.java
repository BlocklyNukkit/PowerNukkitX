package cn.nukkit.block;

import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import static cn.nukkit.block.property.CommonBlockProperties.WEEPING_VINES_AGE;

public class BlockWeepingVines extends BlockVinesNether {
    public static final BlockProperties $1 = new BlockProperties(WEEPING_VINES, WEEPING_VINES_AGE);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockWeepingVines() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockWeepingVines(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Weeping Vines";
    }

    @Override
    @NotNull public BlockFace getGrowthDirection() {
        return BlockFace.DOWN;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getVineAge() {
        return getPropertyValue(WEEPING_VINES_AGE);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setVineAge(int vineAge) {
        setPropertyValue(WEEPING_VINES_AGE, vineAge);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxVineAge() {
        return WEEPING_VINES_AGE.getMax();
    }
}