package cn.nukkit.block.impl;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockEntityHolder;
import cn.nukkit.block.BlockFlowable;
import cn.nukkit.block.BlockID;
import cn.nukkit.block.property.BlockProperties;
import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.block.state.BlockState;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityEndPortal;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.player.Player;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

public class BlockEndPortal extends BlockFlowable implements BlockEntityHolder<BlockEntityEndPortal> {

    private static final BlockState STATE_OBSIDIAN = BlockState.of(OBSIDIAN);

    public BlockEndPortal() {
        this(0);
    }

    public BlockEndPortal(int meta) {
        super(0);
    }

    @Override
    public String getName() {
        return "End Portal Block";
    }

    @Override
    public int getId() {
        return END_PORTAL;
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    @NotNull @Override
    public Class<? extends BlockEntityEndPortal> getBlockEntityClass() {
        return BlockEntityEndPortal.class;
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    @NotNull @Override
    public String getBlockEntityType() {
        return BlockEntity.END_PORTAL;
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
        return BlockEntityHolder.setBlockAndCreateEntity(this) != null;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @NotNull @Override
    public BlockProperties getProperties() {
        return CommonBlockProperties.EMPTY_PROPERTIES;
    }

    @Override
    public boolean canPassThrough() {
        return false;
    }

    @Override
    public boolean isBreakable(Item item) {
        return false;
    }

    @Override
    public double getHardness() {
        return -1;
    }

    @Override
    public double getResistance() {
        return 18000000;
    }

    @Override
    public int getLightLevel() {
        return 15;
    }

    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @PowerNukkitDifference(since = "1.2.1.0-PN", info = "NukkitX returns null")
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean canBeFlowedInto() {
        return false;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(BlockID.AIR));
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBePulled() {
        return false;
    }

    @Override
    public double getMaxY() {
        return y() + (12.0 / 16.0);
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public static void spawnObsidianPlatform(Position position) {
        Level level = position.getLevel();
        int x = position.getFloorX();
        int y = position.getFloorY();
        int z = position.getFloorZ();

        for (int blockX = x - 2; blockX <= x + 2; blockX++) {
            for (int blockZ = z - 2; blockZ <= z + 2; blockZ++) {
                level.setBlockStateAt(blockX, y - 1, blockZ, STATE_OBSIDIAN);
                for (int blockY = y; blockY <= y + 3; blockY++) {
                    level.setBlockStateAt(blockX, blockY, blockZ, BlockState.AIR);
                }
            }
        }
    }
}