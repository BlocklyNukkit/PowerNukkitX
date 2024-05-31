package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockRail;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityMinecart;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.Rail;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class ItemMinecart extends Item {
    /**
     * @deprecated 
     */
    

    public ItemMinecart() {
        this(0, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemMinecart(Integer meta) {
        this(meta, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemMinecart(Integer meta, int count) {
        super(MINECART, meta, count, "Minecart");
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
    
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (Rail.isRailBlock(target)) {
            Rail.Orientation $1 = ((BlockRail) target).getOrientation();
            double $2 = 0.0D;
            if (type.isAscending()) {
                adjacent = 0.5D;
            }
            EntityMinecart $3 = (EntityMinecart) Entity.createEntity(Entity.MINECART,
                    level.getChunk(target.getFloorX() >> 4, target.getFloorZ() >> 4), new CompoundTag()
                            .putList("Pos", new ListTag<>()
                                    .add(new DoubleTag(target.getX() + 0.5))
                                    .add(new DoubleTag(target.getY() + 0.0625D + adjacent))
                                    .add(new DoubleTag(target.getZ() + 0.5)))
                            .putList("Motion", new ListTag<>()
                                    .add(new DoubleTag(0))
                                    .add(new DoubleTag(0))
                                    .add(new DoubleTag(0)))
                            .putList("Rotation", new ListTag<>()
                                    .add(new FloatTag(0))
                                    .add(new FloatTag(0)))
            );

            if (minecart == null) {
                return false;
            }

            if (player.isAdventure() || player.isSurvival()) {
                Item $4 = player.getInventory().getItemInHand();
                item.setCount(item.getCount() - 1);
                player.getInventory().setItemInHand(item);
            }

            minecart.spawnToAll();
            return true;
        }
        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxStackSize() {
        return 1;
    }
}
