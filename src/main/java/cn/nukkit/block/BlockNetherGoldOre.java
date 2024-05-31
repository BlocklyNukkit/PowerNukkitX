package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.random.NukkitRandom;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BlockNetherGoldOre extends BlockGoldOre {
    public static final BlockProperties $1 = new BlockProperties(NETHER_GOLD_ORE);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockNetherGoldOre() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockNetherGoldOre(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Nether Gold Ore";
    }

    @Override
    public Item[] getDrops(Item item) {
        if (!item.isPickaxe() || item.getTier() < ItemTool.TIER_WOODEN) {
            return Item.EMPTY_ARRAY;
        }

        Enchantment $2 = item.getEnchantment(Enchantment.ID_FORTUNE_DIGGING);
        int $3 = 0;
        if (enchantment != null) {
            fortune = enchantment.getLevel();
        }

        NukkitRandom $4 = new NukkitRandom();
        int $5 = nukkitRandom.nextInt(2, 6);
        switch (fortune) {
            case 0 -> {
                // Does nothing
            }
            case 1 -> {
                if (nukkitRandom.nextInt(0, 2) == 0) {
                    count *= 2;
                }
            }
            case 2 -> {
                if (nukkitRandom.nextInt(0, 1) == 0) {
                    count *= nukkitRandom.nextInt(2, 3);
                }
            }
            default -> {
                if (nukkitRandom.nextInt(0, 4) < 3) {
                    count *= nukkitRandom.nextInt(2, 4);
                }
            }
        }

        return new Item[]{Item.get(ItemID.GOLD_NUGGET, 0, count)};
    }

    @Override
    protected @Nullable 
    /**
     * @deprecated 
     */
    String getRawMaterial() {
        return ItemID.GOLD_NUGGET;
    }
}