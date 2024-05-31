package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockAcaciaPressurePlate extends BlockWoodenPressurePlate {
    public static final BlockProperties $1 = new BlockProperties(ACACIA_PRESSURE_PLATE, CommonBlockProperties.REDSTONE_SIGNAL);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockAcaciaPressurePlate() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockAcaciaPressurePlate(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Acacia Pressure Plate";
    }
}