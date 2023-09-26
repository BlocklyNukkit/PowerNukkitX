package cn.nukkit.block.impl;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.BlockDoubleSlabBase;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.value.WoodType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import org.jetbrains.annotations.NotNull;

/**
 * @author xtypr
 * @since 2015/12/2
 */
@PowerNukkitDifference(info = "Extends BlockDoubleSlabBase only in PowerNukkit")
public class BlockDoubleSlabWood extends BlockDoubleSlabBase {

    public BlockDoubleSlabWood() {
        this(0);
    }

    public BlockDoubleSlabWood(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return DOUBLE_WOOD_SLAB;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return BlockSlabWood.PROPERTIES;
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public WoodType getWoodType() {
        return getPropertyValue(WoodType.PROPERTY);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setWoodType(WoodType type) {
        setPropertyValue(WoodType.PROPERTY, type);
    }

    @PowerNukkitOnly
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

    @PowerNukkitOnly
    @Override
    public int getSingleSlabId() {
        return WOOD_SLAB;
    }

    // TODO Adjust or remove this when merging https://github.com/PowerNukkit/PowerNukkit/pull/370
    @Override
    @PowerNukkitOnly
    protected boolean isCorrectTool(Item item) {
        return true;
    }
}