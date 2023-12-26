package cn.nukkit.block;

import cn.nukkit.block.state.BlockProperties;
import cn.nukkit.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement104 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_104");

    @Override
    public @NotNull BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement104() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement104(BlockState blockstate) {
        super(blockstate);
    }
}