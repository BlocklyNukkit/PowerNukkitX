package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.inventory.SpecialWindowId;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.InventoryContentPacket;
import cn.nukkit.network.protocol.types.inventory.FullContainerName;
import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;

public class ItemBundle extends Item {

    private Integer bundle_id;

    public ItemBundle() {
        this(BUNDLE);
    }

    public ItemBundle(String id) {
        super(id);
        saveNBT();
    }

    public void saveNBT() {
        CompoundTag compoundTag = getOrCreateNamedTag();
        compoundTag.putInt("bundle_weight", 10);
        ListTag<CompoundTag> items = new ListTag<>();
        CompoundTag tag1 = new CompoundTag();
        tag1.putByte("Count", 10);
        tag1.putShort("Damage", 0);
        tag1.putString("Name", ItemID.DIAMOND);
        tag1.putByte("Slot", 0);
        tag1.putByte("WasPickedUp", 0);
        items.add(tag1);
        CompoundTag tag2 = new CompoundTag();
        tag2.putByte("Count", 29);
        tag2.putShort("Damage", 0);
        tag2.putString("Name", ItemID.DIAMOND);
        tag2.putByte("Slot", 1);
        tag2.putByte("WasPickedUp", 0);
        items.add(tag2);
        for(int i = 2; i < 64; i++) {
            CompoundTag tag = new CompoundTag();
            tag.putByte("Count", 0);
            tag.putShort("Damage", 0);
            tag.putString("Name", "");
            tag.putByte("Slot", i);
            tag.putByte("WasPickedUp", 0);
            items.add(tag);
        }

        compoundTag.putList("storage_item_component_content", items);
        setNamedTag(compoundTag);
    }

    public void loadNBT() {
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        this.saveNBT();
        player.getInventory().sendSlot(0, player);
        player.sendMessage(toString());
        InventoryContentPacket pk = new InventoryContentPacket();
        pk.slots = new Item[1];
        pk.slots[0] = Item.get(Item.RED_DYE);
        pk.storageItem = this;
        pk.inventoryId = SpecialWindowId.CONTAINER_ID_REGISTRY.getId();
        pk.fullContainerName = new FullContainerName(ContainerSlotType.DYNAMIC_CONTAINER, bundle_id);
        player.dataPacket(pk);

        return super.onActivate(level, player, block, target, face, fx, fy, fz);
    }

}

