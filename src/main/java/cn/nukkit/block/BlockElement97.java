package cn.nukkit.block;

import org.jetbrains.annotations.NotNull;

public class BlockElement97 extends Block {
    public static final BlockProperties $1 = new BlockProperties("minecraft:element_97");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockElement97() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockElement97(BlockState blockstate) {
        super(blockstate);
    }
}