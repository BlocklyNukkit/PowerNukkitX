package cn.nukkit.block;

import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.IntBlockProperty;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import org.jetbrains.annotations.NotNull;


public class BlockCaveVinesBodyWithBerries extends BlockCaveVines {
    public static final IntBlockProperty AGE_PROPERTY = new IntBlockProperty("growing_plant_age", false, 25, 0);
    public static final BlockProperties PROPERTIES = new BlockProperties(AGE_PROPERTY);

    @Override
    public String getName() {
        return "Cave Vines Body With Berries";
    }

    @Override
    public int getId() {
        return CAVE_VINES_BODY_WITH_BERRIES;
    }


    @NotNull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public int getLightLevel() {
        return 14;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{Item.get(ItemID.GLOW_BERRIES)};
    }

}
