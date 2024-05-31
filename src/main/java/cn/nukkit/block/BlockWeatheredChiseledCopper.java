package cn.nukkit.block;

import org.jetbrains.annotations.NotNull;

public class BlockWeatheredChiseledCopper extends Block {
    public static final BlockProperties $1 = new BlockProperties(WEATHERED_CHISELED_COPPER);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockWeatheredChiseledCopper() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockWeatheredChiseledCopper(BlockState blockstate) {
        super(blockstate);
    }
}