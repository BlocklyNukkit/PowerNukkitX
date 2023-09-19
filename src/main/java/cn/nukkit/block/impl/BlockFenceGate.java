package cn.nukkit.block.impl;

import static cn.nukkit.block.property.CommonBlockProperties.DIRECTION;
import static cn.nukkit.block.property.CommonBlockProperties.OPEN;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockTransparentMeta;
import cn.nukkit.block.BlockWallBase;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.BooleanBlockProperty;
import cn.nukkit.event.block.BlockRedstoneEvent;
import cn.nukkit.event.block.DoorToggleEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.level.vibration.VibrationEvent;
import cn.nukkit.level.vibration.VibrationType;
import cn.nukkit.math.BlockFace;
import cn.nukkit.player.AdventureSettings;
import cn.nukkit.player.Player;
import cn.nukkit.utils.Faceable;
import cn.nukkit.utils.RedstoneComponent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * @author xtypr
 * @since 2015/11/23
 */
@PowerNukkitDifference(info = "Implements RedstoneComponent.", since = "1.4.0.0-PN")
public class BlockFenceGate extends BlockTransparentMeta implements RedstoneComponent, Faceable {
    // Contains a list of positions of fence gates, which have been opened by hand (by a player).
    // It is used to detect on redstone update, if the gate should be closed if redstone is off on the update,
    // previously the gate always closed, when placing an unpowered redstone at the gate, this fixes it
    // and gives the vanilla behavior; no idea how to make this better :d
    private static final List<Location> manualOverrides = new ArrayList<>();

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public static final BooleanBlockProperty IN_WALL = new BooleanBlockProperty("in_wall_bit", false);

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public static final BlockProperties PROPERTIES = new BlockProperties(DIRECTION, OPEN, IN_WALL);

    public BlockFenceGate() {
        this(0);
    }

    public BlockFenceGate(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return FENCE_GATE;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getName() {
        return "Oak Fence Gate";
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @PowerNukkitOnly
    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    private static final double[] offMinX = new double[2];
    private static final double[] offMinZ = new double[2];
    private static final double[] offMaxX = new double[2];
    private static final double[] offMaxZ = new double[2];

    static {
        offMinX[0] = 0;
        offMinZ[0] = 0.375;
        offMaxX[0] = 1;
        offMaxZ[0] = 0.625;

        offMinX[1] = 0.375;
        offMinZ[1] = 0;
        offMaxX[1] = 0.625;
        offMaxZ[1] = 1;
    }

    private int getOffsetIndex() {
        switch (getBlockFace()) {
            case SOUTH:
            case NORTH:
                return 0;
            default:
                return 1;
        }
    }

    @Override
    public double getMinX() {
        return this.x() + offMinX[getOffsetIndex()];
    }

    @Override
    public double getMinZ() {
        return this.z() + offMinZ[getOffsetIndex()];
    }

    @Override
    public double getMaxX() {
        return this.x() + offMaxX[getOffsetIndex()];
    }

    @Override
    public double getMaxZ() {
        return this.z() + offMaxZ[getOffsetIndex()];
    }

    @PowerNukkitDifference(
            info = "InWall property is now properly set, returns false if setBlock fails",
            since = "1.4.0.0-PN")
    @PowerNukkitDifference(info = "Open door if redstone signal is detected.", since = "1.4.0.0-PN")
    @Override
    public boolean place(
            @NotNull Item item,
            @NotNull Block block,
            @NotNull Block target,
            @NotNull BlockFace face,
            double fx,
            double fy,
            double fz,
            Player player) {
        BlockFace direction = player.getDirection();
        setBlockFace(direction);

        if (getSide(direction.rotateY()) instanceof BlockWallBase
                || getSide(direction.rotateYCCW()) instanceof BlockWallBase) {
            setInWall(true);
        }

        if (!this.getLevel().setBlock(block, this, true, true)) {
            return false;
        }

        if (getLevel().getServer().isRedstoneEnabled() && !this.isOpen() && this.isGettingPower()) {
            this.setOpen(null, true);
        }

        return true;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player) {
        return toggle(player);
    }

    @PowerNukkitDifference(info = "Just call the #setOpen() method.", since = "1.4.0.0-PN")
    public boolean toggle(Player player) {
        if (!player.getAdventureSettings().get(AdventureSettings.Type.DOORS_AND_SWITCHED)) return false;
        return this.setOpen(player, !this.isOpen());
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean setOpen(@Nullable Player player, boolean open) {
        if (open == this.isOpen()) {
            return false;
        }

        DoorToggleEvent event = new DoorToggleEvent(this, player);
        event.call();

        if (event.isCancelled()) {
            return false;
        }

        player = event.getPlayer();

        BlockFace direction;

        BlockFace originDirection = getBlockFace();

        if (player != null) {
            double yaw = player.yaw();
            double rotation = (yaw - 90) % 360;

            if (rotation < 0) {
                rotation += 360.0;
            }

            if (originDirection.getAxis() == BlockFace.Axis.Z) {
                if (rotation >= 0 && rotation < 180) {
                    direction = BlockFace.NORTH;
                } else {
                    direction = BlockFace.SOUTH;
                }
            } else {
                if (rotation >= 90 && rotation < 270) {
                    direction = BlockFace.EAST;
                } else {
                    direction = BlockFace.WEST;
                }
            }
        } else {
            if (originDirection.getAxis() == BlockFace.Axis.Z) {
                direction = BlockFace.SOUTH;
            } else {
                direction = BlockFace.WEST;
            }
        }

        setBlockFace(direction);
        toggleBooleanProperty(OPEN);
        this.getLevel().setBlock(this, this, false, false);

        if (player != null) {
            this.setManualOverride(this.isGettingPower() || isOpen());
        }

        playOpenCloseSound();

        var source = this.clone().add(0.5, 0.5, 0.5);
        VibrationEvent vibrationEvent = open
                ? new VibrationEvent(player != null ? player : this, source, VibrationType.BLOCK_OPEN)
                : new VibrationEvent(player != null ? player : this, source, VibrationType.BLOCK_CLOSE);
        this.getLevel().getVibrationManager().callVibrationEvent(vibrationEvent);
        return true;
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void playOpenCloseSound() {
        if (this.isOpen()) {
            this.playOpenSound();
        } else {
            this.playCloseSound();
        }
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void playOpenSound() {
        getLevel().addSound(this, Sound.RANDOM_DOOR_OPEN);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void playCloseSound() {
        getLevel().addSound(this, Sound.RANDOM_DOOR_CLOSE);
    }

    public boolean isOpen() {
        return getBooleanValue(OPEN);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setOpen(boolean open) {
        setBooleanValue(OPEN, open);
    }

    @PowerNukkitDifference(info = "Will connect to walls correctly", since = "1.4.0.0-PN")
    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            BlockFace face = getBlockFace();
            boolean touchingWall = getSide(face.rotateY()) instanceof BlockWallBase
                    || getSide(face.rotateYCCW()) instanceof BlockWallBase;
            if (touchingWall != isInWall()) {
                this.setInWall(touchingWall);
                getLevel().setBlock(this, this, true);
                return type;
            }
        } else if (type == Level.BLOCK_UPDATE_REDSTONE
                && this.getLevel().getServer().isRedstoneEnabled()) {
            this.onRedstoneUpdate();
            return type;
        }

        return 0;
    }

    @PowerNukkitDifference(info = "Checking if the door was opened/closed manually.", since = "1.4.0.0-PN")
    private void onRedstoneUpdate() {
        if ((this.isOpen() != this.isGettingPower()) && !this.getManualOverride()) {
            if (this.isOpen() != this.isGettingPower()) {
                new BlockRedstoneEvent(this, this.isOpen() ? 15 : 0, this.isOpen() ? 0 : 15).call();

                this.setOpen(null, this.isGettingPower());
            }
        } else if (this.getManualOverride() && (this.isGettingPower() == this.isOpen())) {
            this.setManualOverride(false);
        }
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setManualOverride(boolean val) {
        if (val) {
            manualOverrides.add(this.getLocation());
        } else {
            manualOverrides.remove(this.getLocation());
        }
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean getManualOverride() {
        return manualOverrides.contains(this.getLocation());
    }

    @Override
    public boolean onBreak(Item item) {
        this.setManualOverride(false);
        return super.onBreak(item);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public boolean isInWall() {
        return getBooleanValue(IN_WALL);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public void setInWall(boolean inWall) {
        setBooleanValue(IN_WALL, inWall);
    }

    @Override
    public BlockFace getBlockFace() {
        return getPropertyValue(DIRECTION);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    @Override
    public void setBlockFace(BlockFace face) {
        setPropertyValue(DIRECTION, face);
    }
}