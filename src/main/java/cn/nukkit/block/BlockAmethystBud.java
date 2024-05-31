package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.Faceable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;

import static cn.nukkit.block.property.CommonBlockProperties.MINECRAFT_BLOCK_FACE;

public abstract class BlockAmethystBud extends BlockTransparent implements Faceable {
    /**
     * @deprecated 
     */
    
    public BlockAmethystBud(BlockState blockState) {
        super(blockState);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return getNamePrefix() + " Amethyst Bud";
    }

    protected abstract String getNamePrefix();

    @Override
    /**
     * @deprecated 
     */
    
    public int getWaterloggingLevel() {
        return 1;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getHardness() {
        return 1.5;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getResistance() {
        return 1.5;
    }

    @Override
    public abstract int getLightLevel();

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
    
    public int getToolTier() {
        return ItemTool.TIER_IRON;
    }

    @Override
    public Item[] getDrops(Item item) {
        return Item.EMPTY_ARRAY;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isSolid() {
        return false;
    }

    @Override
    public BlockFace getBlockFace() {
        return getPropertyValue(MINECRAFT_BLOCK_FACE);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setBlockFace(BlockFace face) {
        setPropertyValue(MINECRAFT_BLOCK_FACE, face);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        if (!target.isSolid()) {
            return false;
        }

        setBlockFace(face);
        this.level.setBlock(block, this, true, true);
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onBreak(Item item) {
        if (item.isPickaxe()) {
            Arrays.stream(this.getDrops(item)).forEach(item1 -> this.level.dropItem(this.add(0.5,0,0.5), item1));
            this.getLevel().setBlock(this, layer, Block.get(BlockID.AIR), true, true);
        } else {
            this.getLevel().setBlock(this, layer, Block.get(BlockID.AIR), true, true);
        }

        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if ((this.getSide(this.getBlockFace().getOpposite()).isAir())) {
            this.onBreak(Item.get(ItemID.DIAMOND_PICKAXE));
        }

        return 0;
    }
}
