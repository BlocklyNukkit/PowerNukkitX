package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityDecoratedPot;
import cn.nukkit.item.Item;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.Faceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class BlockDecoratedPot extends BlockTransparent implements Faceable, BlockEntityHolder<BlockEntityDecoratedPot>{
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:decorated_pot", CommonBlockProperties.DIRECTION);

    public BlockDecoratedPot() {
        super(PROPERTIES.getDefaultState());
    }

    public BlockDecoratedPot(BlockState blockState) {
        super(blockState);
    }

    public String getName() {
        return "Decorated Pot";
    }

    @Override
    public @NotNull BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("id", BlockEntity.DECORATED_POT);
        nbt.putByte("isMovable", 1);

        if (item.getNamedTag() != null) {
            Map<String, Tag> customData = item.getNamedTag().getTags();
            for (Map.Entry<String, Tag> tag : customData.entrySet()) {
                nbt.put(tag.getKey(), tag.getValue());
            }
        }

        nbt.putInt("x", (int) this.x);
        nbt.putInt("y", (int) this.y);
        nbt.putInt("z", (int) this.y);

        this.setBlockFace(player.getDirection().getOpposite());
        return BlockEntityHolder.setBlockAndCreateEntity(this, true, true, nbt) != null;
    }

    @NotNull
    @Override
    public Class<? extends BlockEntityDecoratedPot> getBlockEntityClass() {
        return BlockEntityDecoratedPot.class;
    }

    @NotNull
    @Override
    public String getBlockEntityType() {
        return BlockEntity.DECORATED_POT;
    }

    @Override
    public void setBlockFace(BlockFace face) {
        if(face != null) {
            setPropertyValue(CommonBlockProperties.DIRECTION, face.getHorizontalIndex());
        } else {
            setPropertyValue(CommonBlockProperties.DIRECTION, BlockFace.SOUTH.getHorizontalIndex());
        }
    }

    @Override
    public BlockFace getBlockFace() {
        return BlockFace.fromHorizontalIndex(getPropertyValue(CommonBlockProperties.DIRECTION));
    }
}