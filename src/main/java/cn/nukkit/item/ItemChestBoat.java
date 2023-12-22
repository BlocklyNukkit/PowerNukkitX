package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockWater;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityChestBoat;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;

public class ItemChestBoat extends Item {
    public ItemChestBoat() {
        this(0, 1);
    }

    public ItemChestBoat(Integer meta) {
        this(meta, 1);
    }

    public ItemChestBoat(Integer meta, int count) {
        this(CHEST_BOAT, meta, count);
    }

    protected ItemChestBoat(String id, Integer meta, int count) {
        super(id, meta, count);
        adjustName();
    }

    public ItemChestBoat(String id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    private void adjustName() {
        switch (getAux()) {
            case 0:
                name = "Oak Chest Boat";
                return;
            case 1:
                name = "Spruce Chest Boat";
                return;
            case 2:
                name = "Birch Chest Boat";
                return;
            case 3:
                name = "Jungle Chest Boat";
                return;
            case 4:
                name = "Acacia Chest Boat";
                return;
            case 5:
                name = "Dark Oak Chest Boat";
                return;
            case 6:
                name = "Mangrove Chest Boat";
                return;
            case 7:
                name = "Bamboo Chest Raft";
                return;
            case 8:
                name = "Cherry Chest Boat";
                return;
            default:
                name = "Chest Boat";
        }
    }


    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (face != BlockFace.UP || block instanceof BlockWater) return false;
        EntityChestBoat boat = (EntityChestBoat) Entity.createEntity("ChestBoat",
                level.getChunk(block.getFloorX() >> 4, block.getFloorZ() >> 4), new CompoundTag("")
                        .putList(new ListTag<DoubleTag>("Pos")
                                .add(new DoubleTag("", block.getX() + 0.5))
                                .add(new DoubleTag("", block.getY() - (target instanceof BlockWater ? 0.375 : 0)))
                                .add(new DoubleTag("", block.getZ() + 0.5)))
                        .putList(new ListTag<DoubleTag>("Motion")
                                .add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0))
                                .add(new DoubleTag("", 0)))
                        .putList(new ListTag<FloatTag>("Rotation")
                                .add(new FloatTag("", (float) ((player.yaw + 90f) % 360)))
                                .add(new FloatTag("", 0)))
                        .putInt("Variant", getBoatId())
        );

        if (boat == null) {
            return false;
        }

        if (player.isSurvival() || player.isAdventure()) {
            Item item = player.getInventory().getItemInHand();
            item.setCount(item.getCount() - 1);
            player.getInventory().setItemInHand(item);
        }

        boat.spawnToAll();
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
