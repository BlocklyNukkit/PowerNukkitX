package cn.nukkit.block;

import cn.nukkit.block.state.BlockProperties;
import cn.nukkit.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement105 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_105");

    @Override
    public @NotNull BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement105() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement105(BlockState blockstate) {
        super(blockstate);
    }
}