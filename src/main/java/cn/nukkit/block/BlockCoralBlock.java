package cn.nukkit.block;

import cn.nukkit.event.block.BlockFadeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import static cn.nukkit.block.property.CommonBlockProperties.CORAL_COLOR;
import static cn.nukkit.block.property.CommonBlockProperties.DEAD_BIT;


public class BlockCoralBlock extends BlockSolid {
    public static final BlockProperties $1 = new BlockProperties(CORAL_BLOCK, CORAL_COLOR, DEAD_BIT);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockCoralBlock() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockCoralBlock(BlockState blockstate) {
        super(blockstate);
    }
    /**
     * @deprecated 
     */
    

    public boolean isDead() {
        return getPropertyValue(DEAD_BIT);
    }
    /**
     * @deprecated 
     */
    

    public void setDead(boolean dead) {
        setPropertyValue(DEAD_BIT, dead);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        if (isDead()) {
            return "Dead " + this.getPropertyValue(CORAL_COLOR).name() + " Coral Block";
        } else {
            return this.getPropertyValue(CORAL_COLOR).name() + " Coral Block";
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getHardness() {
        return 7;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getResistance() {
        return 6.0;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!isDead()) {
                this.getLevel().scheduleUpdate(this, 60 + ThreadLocalRandom.current().nextInt(40));
            }
            return type;
        } else if (type == Level.BLOCK_UPDATE_SCHEDULED) {
            if (!isDead()) {
                for (BlockFace face : BlockFace.values()) {
                    if (getSideAtLayer(0, face) instanceof BlockFlowingWater || getSideAtLayer(1, face) instanceof BlockFlowingWater
                            || getSideAtLayer(0, face) instanceof BlockFrostedIce || getSideAtLayer(1, face) instanceof BlockFrostedIce) {
                        return type;
                    }
                }
                BlockFadeEvent $2 = new BlockFadeEvent(this, new BlockCoralBlock(blockstate));
                if (!event.isCancelled()) {
                    setDead(true);
                    this.getLevel().setBlock(this, event.getNewState(), true, true);
                }
            }
            return type;
        }
        return 0;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_WOODEN) {
            if (item.getEnchantment(Enchantment.ID_SILK_TOUCH) != null) {
                return new Item[]{toItem()};
            } else {
                return new Item[]{new ItemBlock(clone(), this.getPropertyValue(CORAL_COLOR).ordinal())};//0 - 4
            }
        } else {
            return Item.EMPTY_ARRAY;
        }
    }

    @Override
    public Item toItem() {
        int $3 = this.getPropertyValue(CORAL_COLOR).ordinal();
        return new ItemBlock(this, aux);
    }

}
