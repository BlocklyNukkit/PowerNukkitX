package cn.nukkit.block;

import cn.nukkit.block.state.BlockProperties;
import cn.nukkit.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement92 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_92");

    @Override
    public @NotNull BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement92() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement92(BlockState blockstate) {
        super(blockstate);
    }
}