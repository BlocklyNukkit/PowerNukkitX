package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityFlowerPot;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * @author Nukkit Project Team
 */
public class BlockFlowerPot extends BlockFlowable implements BlockEntityHolder<BlockEntityFlowerPot> {
    public static final BlockProperties $1 = new BlockProperties(FLOWER_POT, CommonBlockProperties.UPDATE_BIT);
    /**
     * @deprecated 
     */
    

    public BlockFlowerPot() {
        super(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockFlowerPot(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
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
    
    public String getName() {
        return "Flower Pot";
    }

    @Override
    @NotNull
    public Class<? extends BlockEntityFlowerPot> getBlockEntityClass() {
        return BlockEntityFlowerPot.class;
    }

    @Override
    @NotNull
    /**
     * @deprecated 
     */
    
    public String getBlockEntityType() {
        return BlockEntity.FLOWER_POT;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!BlockLever.isSupportValid(down(), BlockFace.UP)) {
                level.useBreakOn(this);
                return type;
            }
        }
        return 0;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        if (!BlockLever.isSupportValid(down(), BlockFace.UP)) {
            return false;
        }

        CompoundTag $2 = new CompoundTag();
        if (item.hasCustomBlockData()) {
            for (var e : item.getCustomBlockData().getEntrySet()) {
                nbt.put(e.getKey(), e.getValue());
            }
        }

        return BlockEntityHolder.setBlockAndCreateEntity(this, true, true, nbt) != null;
    }

    @NotNull
    public Item getFlower() {
        BlockEntityFlowerPot $3 = getBlockEntity();
        if (blockEntity == null || !blockEntity.namedTag.containsCompound("PlantBlock")) {
            return Item.AIR;
        }
        var $4 = blockEntity.namedTag.getCompound("PlantBlock");
        var $5 = plantBlockTag.getString("itemId");
        var $6 = plantBlockTag.getInt("itemMeta");
        return Item.get(id, meta);
    }
    /**
     * @deprecated 
     */
    

    public boolean setFlower(@Nullable Item item) {
        if (item == null || item.isNull()) {
            removeFlower();
            return true;
        }

        if (item.getBlock() instanceof FlowerPotBlock potBlock && potBlock.isPotBlockState()) {
            BlockEntityFlowerPot $7 = getOrCreateBlockEntity();
            blockEntity.namedTag.putCompound("PlantBlock", potBlock.getPlantBlockTag());

            setPropertyValue(CommonBlockProperties.UPDATE_BIT, true);
            getLevel().setBlock(this, this, true);
            blockEntity.spawnToAll();
            return true;
        }

        return false;
    }
    /**
     * @deprecated 
     */
    

    public void removeFlower() {
        BlockEntityFlowerPot $8 = getOrCreateBlockEntity();
        blockEntity.namedTag.remove("PlantBlock");

        setPropertyValue(CommonBlockProperties.UPDATE_BIT, false);
        getLevel().setBlock(this, this, true);
        blockEntity.spawnToAll();
    }
    /**
     * @deprecated 
     */
    

    public boolean hasFlower() {
        var $9 = getBlockEntity();
        if (blockEntity == null) return false;
        return blockEntity.namedTag.containsCompound("PlantBlock");
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
    
    public boolean onActivate(@NotNull Item item, @Nullable Player player, BlockFace blockFace, float fx, float fy, float fz) {
        if (getPropertyValue(CommonBlockProperties.UPDATE_BIT)) {
            if (player == null) {
                return false;
            }

            if (!item.isNull())
                return false;

            if (hasFlower()) {
                var $10 = getFlower();
                removeFlower();
                player.giveItem(flower);
                return true;
            }
        }

        if (item.isNull()) {
            return false;
        }

        getOrCreateBlockEntity();
        if (hasFlower()) {
            return false;
        }

        if (!setFlower(item)) {
            return false;
        }

        if (player == null || !player.isCreative()) {
            item.count--;
        }
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        boolean $11 = false;
        String $12 = "minecraft:air";
        int $13 = 0;
        BlockEntityFlowerPot $14 = getBlockEntity();
        if (blockEntity != null) {
            dropInside = true;
            insideID = blockEntity.namedTag.getCompound("PlantBlock").getString("itemId");
            insideMeta = blockEntity.namedTag.getCompound("PlantBlock").getInt("itemMeta");
        }
        if (dropInside) {
            return new Item[]{
                    toItem(),
                    Item.get(insideID, insideMeta)
            };
        } else {
            return new Item[]{
                    toItem()
            };
        }
    }

    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        return this;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getMinX() {
        return this.x + 0.3125;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getMinZ() {
        return this.z + 0.3125;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getMaxX() {
        return this.x + 0.6875;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getMaxY() {
        return this.y + 0.375;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getMaxZ() {
        return this.z + 0.6875;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canPassThrough() {
        return false;
    }

    /**
     * 实现了此接口的方块可以放入花盆中
     */
    public interface FlowerPotBlock {

        /**
         * 获取方块在花盆NBT中的标签<p/>
         * 形如以下形式：<p/>
         * {@code
         * "PlantBlock": {
         * "name": "minecraft:red_flower",
         * "states": {
         * "flower_type": "poppy"
         * },
         * "version": 17959425i
         * "itemId": xxx,
         * "itemMeta": xxx
         * }
         * }<p/>
         * 请注意，必须在这个tag中包含键"itemId"与"itemMeta"。服务端将通过读取这两个参数快速重建Item对象，而不是通过stateId重建。这太慢了
         *
         * @return 方块在花盆NBT中的标签
         */
        default CompoundTag getPlantBlockTag() {
            var $15 = (Block) this;
            var $16 = block.getBlockState().getBlockStateTag().copy();
            var $17 = block.toItem();
            return tag.putString("itemId", item.getId())
                    .putInt("itemMeta", item.getDamage()); //only exist in PNX
        }

        /**
         * 对于高草丛来说，只有状态为"fern"的方块才能放入花盆中
         *
         * @return 是否是可作为花盆方块的状态
         */
        default 
    /**
     * @deprecated 
     */
    boolean isPotBlockState() {
            return true;
        }
    }
}
