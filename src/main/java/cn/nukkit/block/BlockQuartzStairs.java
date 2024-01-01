package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

public class BlockQuartzStairs extends BlockStairs {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:quartz_stairs", CommonBlockProperties.UPSIDE_DOWN_BIT, CommonBlockProperties.WEIRDO_DIRECTION);

    @Override
    public @NotNull BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockQuartzStairs() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockQuartzStairs(BlockState blockstate) {
        super(blockstate);
    }


    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public double getResistance() {
        return 4;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override

    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public String getName() {
        return "Quartz Stairs";
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}