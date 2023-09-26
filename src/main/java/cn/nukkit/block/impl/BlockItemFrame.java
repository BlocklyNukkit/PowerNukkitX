package cn.nukkit.block.impl;

import static cn.nukkit.block.property.CommonBlockProperties.FACING_DIRECTION;
import static cn.nukkit.math.BlockFace.AxisDirection.POSITIVE;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.*;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.BooleanBlockProperty;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityItemFrame;
import cn.nukkit.event.block.ItemFrameUseEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemItemFrame;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.network.protocol.LevelEventPacket;
import cn.nukkit.player.Player;
import cn.nukkit.utils.Faceable;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * @author Pub4Game
 * @since 03.07.2016
 */
@PowerNukkitDifference(since = "1.4.0.0-PN", info = "Implements BlockEntityHolder and Faceable only in PowerNukkit")
public class BlockItemFrame extends BlockTransparentMeta implements BlockEntityHolder<BlockEntityItemFrame>, Faceable {
    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public static final BooleanBlockProperty HAS_MAP = new BooleanBlockProperty("item_frame_map_bit", false);

    @PowerNukkitOnly
    @Since("1.5.2.0-PN")
    public static final BooleanBlockProperty HAS_PHOTO = new BooleanBlockProperty("item_frame_photo_bit", false);

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public static final BlockProperties PROPERTIES = new BlockProperties(FACING_DIRECTION, HAS_MAP, HAS_PHOTO);

    public BlockItemFrame() {
        this(0);
    }

    public BlockItemFrame(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return ITEM_FRAME_BLOCK;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockFace getBlockFace() {
        return getPropertyValue(FACING_DIRECTION);
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Override
    public void setBlockFace(@NotNull BlockFace face) {
        setPropertyValue(FACING_DIRECTION, face);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean isStoringMap() {
        return getBooleanValue(HAS_MAP);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setStoringMap(boolean map) {
        setBooleanValue(HAS_MAP, map);
    }

    @PowerNukkitOnly
    @Since("1.5.2.0-PN")
    public boolean isStoringPhoto() {
        return getBooleanValue(HAS_PHOTO);
    }

    @PowerNukkitOnly
    @Since("1.5.2.0-PN")
    public void setStoringPhoto(boolean hasPhoto) {
        setBooleanValue(HAS_PHOTO, hasPhoto);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    @NotNull @Override
    public String getBlockEntityType() {
        return BlockEntity.ITEM_FRAME;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public Class<? extends BlockEntityItemFrame> getBlockEntityClass() {
        return BlockEntityItemFrame.class;
    }

    @Override
    public String getName() {
        return "Item Frame";
    }

    @PowerNukkitDifference(info = "Allow to stay in walls", since = "1.3.0.0-PN")
    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            Block support = this.getSideAtLayer(0, getFacing().getOpposite());
            if (!support.isSolid() && support.getId() != COBBLE_WALL) {
                this.getLevel().useBreakOn(this);
                return type;
            }
        }

        return 0;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @PowerNukkitOnly
    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Override
    public int onTouch(@Nullable Player player, Action action) {
        onUpdate(Level.BLOCK_UPDATE_TOUCH);
        if (player != null && action == Action.LEFT_CLICK_BLOCK) {
            return getOrCreateBlockEntity().dropItem(player) ? 1 : 0;
        }
        return 0;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player) {
        BlockEntityItemFrame itemFrame = getOrCreateBlockEntity();
        if (itemFrame.getItem().isNull()) {
            Item itemOnFrame = item.clone();
            ItemFrameUseEvent event =
                    new ItemFrameUseEvent(player, this, itemFrame, itemOnFrame, ItemFrameUseEvent.Action.PUT);
            event.call();
            if (event.isCancelled()) return false;
            if (player != null && !player.isCreative()) {
                itemOnFrame.setCount(itemOnFrame.getCount() - 1);
                player.getInventory().setItemInHand(itemOnFrame);
            }
            itemOnFrame.setCount(1);
            itemFrame.setItem(itemOnFrame);
            if (itemOnFrame.getId() == ItemID.MAP) {
                setStoringMap(true);
                this.getLevel().setBlock(this, this, true);
            }
            this.getLevel().addLevelEvent(this, LevelEventPacket.EVENT_SOUND_ITEM_FRAME_ITEM_ADDED);
        } else {
            ItemFrameUseEvent event =
                    new ItemFrameUseEvent(player, this, itemFrame, null, ItemFrameUseEvent.Action.ROTATION);
            event.call();
            if (event.isCancelled()) return false;
            itemFrame.setItemRotation((itemFrame.getItemRotation() + 1) % 8);
            if (isStoringMap()) {
                setStoringMap(false);
                this.getLevel().setBlock(this, this, true);
            }
            this.getLevel().addLevelEvent(this, LevelEventPacket.EVENT_SOUND_ITEM_FRAME_ITEM_ROTATED);
        }
        return true;
    }

    @PowerNukkitDifference(info = "Allow to place on walls", since = "1.3.0.0-PN")
    @Override
    public boolean place(
            @NotNull Item item,
            @NotNull Block block,
            @NotNull Block target,
            @NotNull BlockFace face,
            double fx,
            double fy,
            double fz,
            @Nullable Player player) {
        if ((!(target.isSolid() || target instanceof BlockWall) && !target.equals(block)
                || (block.isSolid() && !block.canBeReplaced()))) {
            return false;
        }

        if (target.equals(block) && block.canBeReplaced()) {
            face = BlockFace.UP;
            target = block.down();
            if (!target.isSolid() && !(target instanceof BlockWall)) {
                return false;
            }
        }

        setBlockFace(face);
        setStoringMap(item.getId() == ItemID.MAP);
        CompoundTag nbt = new CompoundTag().putByte("ItemRotation", 0).putFloat("ItemDropChance", 1.0f);
        if (item.hasCustomBlockData()) {
            for (Tag aTag : item.getCustomBlockData().getAllTags()) {
                nbt.put(aTag.getName(), aTag);
            }
        }
        BlockEntityItemFrame frame = BlockEntityHolder.setBlockAndCreateEntity(this, true, true, nbt);
        if (frame == null) {
            return false;
        }

        this.getLevel().addSound(this, Sound.BLOCK_ITEMFRAME_PLACE);
        return true;
    }

    @Override
    public boolean onBreak(Item item) {
        this.getLevel().setBlock(this, layer, Block.get(BlockID.AIR), true, true);
        this.getLevel().addLevelEvent(this, LevelEventPacket.EVENT_SOUND_ITEM_FRAME_REMOVED);
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        BlockEntityItemFrame itemFrame = getBlockEntity();
        if (itemFrame != null && ThreadLocalRandom.current().nextFloat() <= itemFrame.getItemDropChance()) {
            return new Item[] {toItem(), itemFrame.getItem().clone()};
        } else {
            return new Item[] {toItem()};
        }
    }

    @Override
    public Item toItem() {
        return new ItemItemFrame();
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride() {
        BlockEntityItemFrame blockEntity = getBlockEntity();

        if (blockEntity != null) {
            return blockEntity.getAnalogOutput();
        }

        return super.getComparatorInputOverride();
    }

    public BlockFace getFacing() {
        return getBlockFace();
    }

    @Override
    public double getHardness() {
        return 0.25;
    }

    @Override
    @PowerNukkitOnly
    public boolean breaksWhenMoved() {
        return true;
    }

    @Override
    @PowerNukkitOnly
    public boolean sticksToPiston() {
        return false;
    }

    @PowerNukkitDifference(since = "1.3.0.0-PN", info = "Will calculate the correct AABB")
    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        double[][] aabb = {
            {2.0 / 16, 14.0 / 16},
            {2.0 / 16, 14.0 / 16},
            {2.0 / 16, 14.0 / 16}
        };

        BlockFace facing = getFacing();
        if (facing.getAxisDirection() == POSITIVE) {
            int axis = facing.getAxis().ordinal();
            aabb[axis][0] = 0;
            aabb[axis][1] = 1.0 / 16;
        }

        return new SimpleAxisAlignedBB(
                aabb[0][0] + x(),
                aabb[1][0] + y(),
                aabb[2][0] + z(),
                aabb[0][1] + x(),
                aabb[1][1] + y(),
                aabb[2][1] + z());
    }
}