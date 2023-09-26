package cn.nukkit.block.impl;

import static cn.nukkit.block.property.CommonBlockProperties.POWERED;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.BlockTransparentMeta;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.BooleanBlockProperty;
import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemString;
import cn.nukkit.level.Level;
import cn.nukkit.level.vibration.VibrationEvent;
import cn.nukkit.level.vibration.VibrationType;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.player.Player;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * @author CreeperFace
 */
public class BlockTripWire extends BlockTransparentMeta {
    @Deprecated(since = "1.20.0-r2", forRemoval = true)
    @DeprecationDetails(since = "1.20.0-r2", reason = "replace to CommonBlockProperties")
    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final BooleanBlockProperty ATTACHED = new BooleanBlockProperty("attached_bit", false);

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final BooleanBlockProperty DISARMED = new BooleanBlockProperty("disarmed_bit", false);

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final BooleanBlockProperty SUSPENDED = new BooleanBlockProperty("suspended_bit", false);

    @PowerNukkitOnly
    @Since("1.5.0.0-PN")
    public static final BlockProperties PROPERTIES =
            new BlockProperties(POWERED, SUSPENDED, CommonBlockProperties.ATTACHED, DISARMED);

    public BlockTripWire(int meta) {
        super(meta);
    }

    public BlockTripWire() {
        this(0);
    }

    @Override
    public int getId() {
        return TRIP_WIRE;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getName() {
        return "Tripwire";
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @PowerNukkitOnly
    @Override
    public int getWaterloggingLevel() {
        return 2;
    }

    @Override
    public boolean canBeFlowedInto() {
        return false;
    }

    @Override
    public double getResistance() {
        return 0;
    }

    @Override
    public double getHardness() {
        return 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public Item toItem() {
        return new ItemString();
    }

    public boolean isPowered() {
        return (this.getDamage() & 1) > 0;
    }

    public boolean isAttached() {
        return (this.getDamage() & 4) > 0;
    }

    public boolean isDisarmed() {
        return (this.getDamage() & 8) > 0;
    }

    public void setPowered(boolean value) {
        if (value ^ this.isPowered()) {
            this.setDamage(this.getDamage() ^ 0x01);
        }
    }

    public void setAttached(boolean value) {
        if (value ^ this.isAttached()) {
            this.setDamage(this.getDamage() ^ 0x04);
        }
    }

    public void setDisarmed(boolean value) {
        if (value ^ this.isDisarmed()) {
            this.setDamage(this.getDamage() ^ 0x08);
        }
    }

    @PowerNukkitDifference(info = "Trigger observer.", since = "1.4.0.0-PN")
    @Override
    public void onEntityCollide(Entity entity) {
        if (!this.getLevel().getServer().isRedstoneEnabled()) {
            return;
        }

        if (!entity.doesTriggerPressurePlate()) {
            return;
        }

        boolean powered = this.isPowered();

        if (!powered) {
            this.setPowered(true);
            this.getLevel().setBlock(this, this, true, false);
            this.updateHook(false);

            this.getLevel().scheduleUpdate(this, 10);
            this.getLevel().updateComparatorOutputLevelSelective(this, true);
        }
    }

    private void updateHook(boolean scheduleUpdate) {
        if (!this.getLevel().getServer().isRedstoneEnabled()) {
            return;
        }

        for (BlockFace side : new BlockFace[] {BlockFace.SOUTH, BlockFace.WEST}) {
            for (int i = 1; i < 42; ++i) {
                Block block = this.getSide(side, i);

                if (block instanceof BlockTripWireHook) {
                    BlockTripWireHook hook = (BlockTripWireHook) block;

                    if (hook.getFacing() == side.getOpposite()) {
                        hook.calculateState(false, true, i, this);
                    }

                    /*if(scheduleUpdate) {
                        this.level.scheduleUpdate(hook, 10);
                    }*/
                    break;
                }

                if (block.getId() != Block.TRIP_WIRE) {
                    break;
                }
            }
        }
    }

    @PowerNukkitDifference(info = "Trigger observer.", since = "1.4.0.0-PN")
    @Override
    public int onUpdate(int type) {
        if (!this.getLevel().getServer().isRedstoneEnabled()) {
            return 0;
        }

        if (type == Level.BLOCK_UPDATE_SCHEDULED) {
            if (!isPowered()) {
                return type;
            }

            boolean found = false;
            for (Entity entity : this.getLevel().getCollidingEntities(this.getCollisionBoundingBox())) {
                if (!entity.doesTriggerPressurePlate()) {
                    continue;
                }

                found = true;
            }

            if (found) {
                this.getLevel().scheduleUpdate(this, 10);
            } else {
                this.setPowered(false);
                this.getLevel().setBlock(this, this, true, false);
                this.updateHook(false);

                this.getLevel().updateComparatorOutputLevelSelective(this, true);
            }
            return type;
        }

        return 0;
    }

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
        this.getLevel().setBlock(this, this, true, true);
        this.updateHook(false);

        return true;
    }

    @Override
    public boolean onBreak(Item item) {
        if (item.getId() == Item.SHEARS) {
            this.setDisarmed(true);
            this.getLevel().setBlock(this, this, true, false);
            this.updateHook(false);
            this.getLevel().setBlock(this, Block.get(BlockID.AIR), true, true);
            // todo: initiator should be a entity
            getLevel()
                    .getVibrationManager()
                    .callVibrationEvent(new VibrationEvent(this, this.add(0.5, 0.5, 0.5), VibrationType.SHEAR));
        } else {
            this.setPowered(true);
            this.getLevel().setBlock(this, Block.get(BlockID.AIR), true, true);
            this.updateHook(true);
        }

        return true;
    }

    @Override
    public double getMaxY() {
        return this.y() + 0.5;
    }

    @Override
    protected AxisAlignedBB recalculateCollisionBoundingBox() {
        return this;
    }
}