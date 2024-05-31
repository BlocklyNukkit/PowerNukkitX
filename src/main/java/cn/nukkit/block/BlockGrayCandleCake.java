package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockGrayCandleCake extends BlockCandleCake {
    public static final BlockProperties $1 = new BlockProperties(GRAY_CANDLE_CAKE, CommonBlockProperties.LIT);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockGrayCandleCake() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockGrayCandleCake(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public BlockCandle toCandleForm() {
        return new BlockGrayCandle();
    }
}