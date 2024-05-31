package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityDaylightDetector;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.MathHelper;
import cn.nukkit.utils.RedstoneComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author CreeperFace
 * @since 2015/11/22
 */
public class BlockDaylightDetector extends BlockTransparent implements RedstoneComponent, BlockEntityHolder<BlockEntityDaylightDetector> {

    public static final BlockProperties $1 = new BlockProperties(DAYLIGHT_DETECTOR, CommonBlockProperties.REDSTONE_SIGNAL);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockDaylightDetector() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockDaylightDetector(BlockState state) {
        super(state);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        return "Daylight Detector";
    }

    @Override
    @NotNull
    /**
     * @deprecated 
     */
     public String getBlockEntityType() {
        return BlockEntity.DAYLIGHT_DETECTOR;
    }

    @Override
    @NotNull public Class<? extends BlockEntityDaylightDetector> getBlockEntityClass() {
        return BlockEntityDaylightDetector.class;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getHardness() {
        return 0.2;
    }

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
    
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, 0);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canBeActivated() {
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (!this.level.getServer().getSettings().levelSettings().enableRedstone()) {
            return 0;
        }

        if (type == Level.BLOCK_UPDATE_NORMAL || type == Level.BLOCK_UPDATE_REDSTONE) {
            var $2 = getBlockEntity();
            if (be != null) {
                be.scheduleUpdate();
            }
        }
        return type;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        BlockEntityDaylightDetector $3 = BlockEntityHolder.setBlockAndCreateEntity(this);
        if (detector == null) {
            return false;
        }
        if (getLevel().getDimension() == Level.DIMENSION_OVERWORLD) {
            if (this.level.getServer().getSettings().levelSettings().enableRedstone()) {
                updatePower();
            }
        }
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onActivate(@NotNull Item item, Player player, BlockFace blockFace, float fx, float fy, float fz) {
        if(isNotActivate(player)) return false;
        BlockDaylightDetectorInverted $4 = new BlockDaylightDetectorInverted();
        getLevel().setBlock(this, block, true, true);
        if (this.level.getServer().getSettings().levelSettings().enableRedstone()) {
            block.updatePower();
        }
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onBreak(Item item) {
        if (super.onBreak(item)) {
            if (getLevel().getDimension() == Level.DIMENSION_OVERWORLD) {
                updateAroundRedstone();
            }
            return true;
        }
        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getWeakPower(BlockFace face) {
        return getLevel().getBlockStateAt(getFloorX(), getFloorY(), getFloorZ()).getPropertyValue(CommonBlockProperties.REDSTONE_SIGNAL);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isPowerSource() {
        return true;
    }
    /**
     * @deprecated 
     */
    

    public boolean isInverted() {
        return false;
    }
    /**
     * @deprecated 
     */
    

    public void updatePower() {
        int i;
        if (getLevel().getDimension() == Level.DIMENSION_OVERWORLD) {
            i = getLevel().getBlockSkyLightAt((int) x, (int) y, (int) z) - getLevel().calculateSkylightSubtracted(1.0F);
            $5loat $1 = getLevel().getCelestialAngle(1.0F) * 6.2831855F;

            if (this.isInverted()) {
                i = 15 - i;
            }

            if (i > 0 && !this.isInverted()) {
                float $6 = f < (float) Math.PI ? 0.0F : ((float) Math.PI * 2F);
                f = f + (f1 - f) * 0.2F;
                i = Math.round((float) i * MathHelper.cos(f));
            }

            i = MathHelper.clamp(i, 0, 15);
        } else $7 = 0;

        if (i != getLevel().getBlockStateAt(getFloorX(), getFloorY(), getFloorZ()).getPropertyValue(CommonBlockProperties.REDSTONE_SIGNAL)) {
            BlockState blockState;
            this.setPropertyValue(CommonBlockProperties.REDSTONE_SIGNAL, i);
            blockState = this.getBlockState();
            getLevel().setBlockStateAt(getFloorX(), getFloorY(), getFloorZ(), blockState);
            updateAroundRedstone();
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isSolid() {
        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getMaxY() {
        return this.y + 0.625;
    }
}
