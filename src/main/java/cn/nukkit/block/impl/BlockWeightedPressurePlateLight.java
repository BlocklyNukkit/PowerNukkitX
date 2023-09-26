package cn.nukkit.block.impl;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.block.BlockPressurePlateBase;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.NukkitMath;

/**
 * @author CreeperFace
 */
public class BlockWeightedPressurePlateLight extends BlockPressurePlateBase {

    public BlockWeightedPressurePlateLight() {
        this(0);
    }

    public BlockWeightedPressurePlateLight(int meta) {
        super(meta);
        this.onPitch = 0.90000004f;
        this.offPitch = 0.75f;
    }

    @Override
    public int getId() {
        return LIGHT_WEIGHTED_PRESSURE_PLATE;
    }

    @Override
    public String getName() {
        return "Weighted Pressure Plate (Light)";
    }

    @Override
    public double getHardness() {
        return 0.5D;
    }

    @Override
    public double getResistance() {
        return 2.5D;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    @PowerNukkitOnly
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    protected int computeRedstoneStrength() {
        int count =
                Math.min(this.getLevel().getCollidingEntities(getCollisionBoundingBox()).length, this.getMaxWeight());

        if (count > 0) {
            float f = (float) Math.min(this.getMaxWeight(), count) / (float) this.getMaxWeight();
            return NukkitMath.ceilFloat(f * 15.0F);
        } else {
            return 0;
        }
    }

    public int getMaxWeight() {
        return 15;
    }
}