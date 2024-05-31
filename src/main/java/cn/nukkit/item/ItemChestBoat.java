package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFlowingWater;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityChestBoat;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

public class ItemChestBoat extends Item {
    /**
     * @deprecated 
     */
    
    public ItemChestBoat() {
        this(0, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemChestBoat(Integer meta) {
        this(meta, 1);
    }

    //legacy chest boat , have aux
    /**
     * @deprecated 
     */
    
    public ItemChestBoat(Integer meta, int count) {
        super(CHEST_BOAT, meta, count);
        adjustName();
    }

    //chest boat item after split aux
    /**
     * @deprecated 
     */
    
    public ItemChestBoat(String id) {
        super(id);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setDamage(int meta) {
        super.setDamage(meta);
        adjustName();
    }

    
    /**
     * @deprecated 
     */
    private void adjustName() {
        switch (getDamage()) {
            case 0 -> name = "Oak Chest Boat";
            case 1 -> name = "Spruce Chest Boat";
            case 2 -> name = "Birch Chest Boat";
            case 3 -> name = "Jungle Chest Boat";
            case 4 -> name = "Acacia Chest Boat";
            case 5 -> name = "Dark Oak Chest Boat";
            case 6 -> name = "Mangrove Chest Boat";
            case 7 -> name = "Bamboo Chest Raft";
            case 8 -> name = "Cherry Chest Boat";
            default -> name = "Chest Boat";
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canBeActivated() {
        return true;
    }
    /**
     * @deprecated 
     */
    

    public int getBoatId() {
        return this.meta;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (face != BlockFace.UP || block instanceof BlockFlowingWater) return false;
        EntityChestBoat $1 = (EntityChestBoat) Entity.createEntity(Entity.CHEST_BOAT,
                level.getChunk(block.getFloorX() >> 4, block.getFloorZ() >> 4), new CompoundTag()
                        .putList("Pos", new ListTag<DoubleTag>()
                                .add(new DoubleTag(block.getX() + 0.5))
                                .add(new DoubleTag(block.getY() - (target instanceof BlockFlowingWater ? 0.375 : 0)))
                                .add(new DoubleTag(block.getZ() + 0.5)))
                        .putList("Motion", new ListTag<DoubleTag>()
                                .add(new DoubleTag(0))
                                .add(new DoubleTag(0))
                                .add(new DoubleTag(0)))
                        .putList("Rotation", new ListTag<FloatTag>()
                                .add(new FloatTag((float) ((player.yaw + 90f) % 360)))
                                .add(new FloatTag(0)))
                        .putInt("Variant", getBoatId())
        );

        if (boat == null) {
            return false;
        }

        if (player.isSurvival() || player.isAdventure()) {
            Item $2 = player.getInventory().getItemInHand();
            item.setCount(item.getCount() - 1);
            player.getInventory().setItemInHand(item);
        }

        boat.spawnToAll();
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxStackSize() {
        return 1;
    }
}
