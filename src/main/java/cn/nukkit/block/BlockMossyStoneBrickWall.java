package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

public class BlockMossyStoneBrickWall extends BlockWallBase {

    public static final BlockProperties PROPERTIES = new BlockProperties(
            MOSSY_STONE_BRICK_WALL,
            CommonBlockProperties.WALL_CONNECTION_TYPE_EAST,
            CommonBlockProperties.WALL_CONNECTION_TYPE_NORTH,
            CommonBlockProperties.WALL_CONNECTION_TYPE_SOUTH,
            CommonBlockProperties.WALL_CONNECTION_TYPE_WEST,
            CommonBlockProperties.WALL_POST_BIT
    );

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockMossyStoneBrickWall() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockMossyStoneBrickWall(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Mossy Stone Brick Wall";
    }

    @Override
    public double getResistance() {
        return 6;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this.getProperties().getDefaultState().toBlock());
    }
}
