package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.BooleanBlockProperty;
import cn.nukkit.item.ItemTool;

public class BlockSculkCatalyst extends BlockSolid{

    public static final BooleanBlockProperty BLOOM = new BooleanBlockProperty("bloom",false);
    public static final BlockProperties PROPERTIES = new BlockProperties(BLOOM);

    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getName() {
        return "Sculk Catalyst";
    }

    @Override
    public int getId() {
        return SCULK_CATALYST;
    }

    @PowerNukkitOnly
    @Override
    public boolean canBePulled() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public int getLightLevel() {
        return 6;
    }

    @Override
    public double getResistance() {
        return 3;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_HOE;
    }
}
