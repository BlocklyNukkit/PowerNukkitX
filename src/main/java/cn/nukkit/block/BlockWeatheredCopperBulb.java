package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockWeatheredCopperBulb extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties(WEATHERED_COPPER_BULB, CommonBlockProperties.LIT, CommonBlockProperties.POWERED_BIT);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWeatheredCopperBulb() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWeatheredCopperBulb(BlockState blockstate) {
        super(blockstate);
    }
}