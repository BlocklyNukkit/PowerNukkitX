package cn.nukkit.block;

import cn.nukkit.block.property.enums.StoneType;
import org.jetbrains.annotations.NotNull;

public class BlockAndesite extends BlockStone {
    public static final BlockProperties PROPERTIES = new BlockProperties(ANDESITE);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockAndesite() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockAndesite(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public StoneType stoneType() {
        return StoneType.ANDESITE;
    }
}