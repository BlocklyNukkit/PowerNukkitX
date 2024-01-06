package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockTuffDoubleSlab extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties(TUFF_DOUBLE_SLAB, CommonBlockProperties.MINECRAFT_VERTICAL_HALF);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockTuffDoubleSlab() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockTuffDoubleSlab(BlockState blockstate) {
        super(blockstate);
    }
}