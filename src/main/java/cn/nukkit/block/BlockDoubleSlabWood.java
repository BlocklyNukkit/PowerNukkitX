package cn.nukkit.block;

import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.value.WoodType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

/**
 * @author xtypr
 * @since 2015/12/2
 */

public class BlockDoubleSlabWood extends BlockDoubleSlabBase {

    public BlockDoubleSlabWood() {
        this(0);
    }

    public BlockDoubleSlabWood(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public int getId() {
        return DOUBLE_WOOD_SLAB;
    }


    @NotNull
    @Override
    public BlockProperties getProperties() {
        return BlockSlabWood.PROPERTIES;
    }


    public WoodType getWoodType() {
        return getPropertyValue(WoodType.PROPERTY);
    }


    public void setWoodType(WoodType type) {
        setPropertyValue(WoodType.PROPERTY, type);
    }


    @Override
    public String getSlabName() {
        return getWoodType().getEnglishName();
    }

    @Override
    public String getName() {
        return "Double " + getSlabName() + " Wood Slab";
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }


    @Override
    public int getSingleSlabId() {
        return WOOD_SLAB;
    }

    //TODO Adjust or remove this when merging https://github.com/PowerNukkit/PowerNukkit/pull/370
    @Override

    protected boolean isCorrectTool(Item item) {
        return true;
    }

}
