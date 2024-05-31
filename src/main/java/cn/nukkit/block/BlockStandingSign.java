package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemOakSign;
import cn.nukkit.level.Level;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.CompassRoseDirection;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static cn.nukkit.block.property.CommonBlockProperties.FACING_DIRECTION;
import static cn.nukkit.block.property.CommonBlockProperties.GROUND_SIGN_DIRECTION;

/**
 * Alias post sign
 */
@Slf4j
public class BlockStandingSign extends BlockSignBase implements BlockEntityHolder<BlockEntitySign> {
    public static final BlockProperties $1 = new BlockProperties(STANDING_SIGN, GROUND_SIGN_DIRECTION);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockStandingSign() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockStandingSign(BlockState blockstate) {
        super(blockstate);
    }

    
    /**
     * @deprecated 
     */
    protected String getStandingSignId() {
        return getId();
    }
    /**
     * @deprecated 
     */
    

    public String getWallSignId() {
        return WALL_SIGN;
    }

    @Override
    @NotNull
    public Class<? extends BlockEntitySign> getBlockEntityClass() {
        return BlockEntitySign.class;
    }

    @Override
    @NotNull
    /**
     * @deprecated 
     */
    
    public String getBlockEntityType() {
        return BlockEntity.SIGN;
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (down().isAir()) {
                getLevel().useBreakOn(this);
                return Level.BLOCK_UPDATE_NORMAL;
            }
        }
        return 0;
    }

    @Override
    public Item toItem() {
        return new ItemOakSign();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        if (face == BlockFace.DOWN) {
            return false;
        }
        if (player != null && !player.isSneaking() && target instanceof BlockSignBase) {
            return false;
        }

        Block $2 = level.getBlock(this, 0);
        Block $3 = level.getBlock(this, 1);

        CompoundTag $4 = new CompoundTag();

        if (face == BlockFace.UP) {
            CompassRoseDirection $5 = CompassRoseDirection.from((int) Math.floor((((player != null ? player.yaw : 0) + 180) * 16 / 360) + 0.5) & 0x0f);
            Block $6 = Block.get(getStandingSignId());
            post.setPropertyValue(GROUND_SIGN_DIRECTION, direction.getIndex());
            getLevel().setBlock(block, post, true);
        } else {
            Block $7 = Block.get(getWallSignId());
            wall.setPropertyValue(FACING_DIRECTION, face.getIndex());
            getLevel().setBlock(block, wall, true);
        }
        if (item.hasCustomBlockData()) {
            for (var e : item.getCustomBlockData().getEntrySet()) {
                nbt.put(e.getKey(), e.getValue());
            }
        }

        try {
            createBlockEntity(nbt);
            if (player != null) {
                player.openSignEditor(this, true);
            }
            return true;
        } catch (Exception e) {
            log.warn("Failed to create block entity {} at {}", getBlockEntityType(), getLocation(), e);
            level.setBlock(layer0, 0, layer0, true);
            level.setBlock(layer1, 0, layer1, true);
            return false;
        }
    }
}