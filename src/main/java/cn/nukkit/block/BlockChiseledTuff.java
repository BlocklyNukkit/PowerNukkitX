package cn.nukkit.block;

import org.jetbrains.annotations.NotNull;

public class BlockChiseledTuff extends BlockSolid {
    public static final BlockProperties PROPERTIES = new BlockProperties(CHISELED_TUFF);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockChiseledTuff() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockChiseledTuff(BlockState blockstate) {
        super(blockstate);
    }
}