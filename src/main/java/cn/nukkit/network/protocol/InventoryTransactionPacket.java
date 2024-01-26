package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.LegacySetItemSlotData;
import cn.nukkit.network.protocol.types.inventorytransaction.NetworkInventoryAction;
import cn.nukkit.network.protocol.types.inventorytransaction.ReleaseItemData;
import cn.nukkit.network.protocol.types.inventorytransaction.TransactionData;
import cn.nukkit.network.protocol.types.inventorytransaction.UseItemData;
import cn.nukkit.network.protocol.types.inventorytransaction.UseItemOnEntityData;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.ToString;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;

@ToString()
public class InventoryTransactionPacket extends DataPacket {
    //InventoryTransactionType 0-5
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MISMATCH = 1;
    public static final int TYPE_USE_ITEM = 2;
    public static final int TYPE_USE_ITEM_ON_ENTITY = 3;
    public static final int TYPE_RELEASE_ITEM = 4;

    public static final int USE_ITEM_ACTION_CLICK_BLOCK = 0;
    public static final int USE_ITEM_ACTION_CLICK_AIR = 1;
    public static final int USE_ITEM_ACTION_BREAK_BLOCK = 2;

    public static final int RELEASE_ITEM_ACTION_RELEASE = 0; //bow shoot
    public static final int RELEASE_ITEM_ACTION_CONSUME = 1; //eat food, drink potion

    public static final int USE_ITEM_ON_ENTITY_ACTION_INTERACT = 0;
    public static final int USE_ITEM_ON_ENTITY_ACTION_ATTACK = 1;


    public static final int ACTION_MAGIC_SLOT_DROP_ITEM = 0;
    public static final int ACTION_MAGIC_SLOT_PICKUP_ITEM = 1;

    public static final int ACTION_MAGIC_SLOT_CREATIVE_DELETE_ITEM = 0;
    public static final int ACTION_MAGIC_SLOT_CREATIVE_CREATE_ITEM = 1;

    public int transactionType;
    public NetworkInventoryAction[] actions;
    public TransactionData transactionData;
    public final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();

    public int legacyRequestId;

    /**
     * NOTE: THESE FIELDS DO NOT EXIST IN THE PROTOCOL, it's merely used for convenience for us to easily
     * determine whether we're doing a crafting or enchanting transaction.
     */
    public boolean isCraftingPart = false;
    public boolean isEnchantingPart = false;
    public boolean isRepairItemPart = false;
    public boolean isTradeItemPart = false;

    @Override
    public int pid() {
        return ProtocolInfo.INVENTORY_TRANSACTION_PACKET;
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.legacyRequestId);
        this.putUnsignedVarInt(this.transactionType);

        //slots array
        if (legacyRequestId != 0) {
            this.putUnsignedVarInt(this.legacySlots.size());
            for (var slot : legacySlots) {
                this.putByte((byte) slot.getContainerId());
                this.putByteArray(slot.getSlots());
            }
        }

        this.putUnsignedVarInt(this.actions.length);
        for (NetworkInventoryAction action : this.actions) {
            action.write(this);
        }

        switch (this.transactionType) {
            case TYPE_NORMAL:
            case TYPE_MISMATCH:
                break;
            case TYPE_USE_ITEM:
                UseItemData useItemData = (UseItemData) this.transactionData;

                this.putUnsignedVarInt(useItemData.actionType);
                this.putBlockVector3(useItemData.blockPos);
                this.putBlockFace(useItemData.face);
                this.putVarInt(useItemData.hotbarSlot);
                this.putSlot(useItemData.itemInHand);
                this.putVector3f(useItemData.playerPos.asVector3f());
                this.putVector3f(useItemData.clickPos);
                this.putUnsignedVarInt(useItemData.blockRuntimeId);
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                UseItemOnEntityData useItemOnEntityData = (UseItemOnEntityData) this.transactionData;

                this.putEntityRuntimeId(useItemOnEntityData.entityRuntimeId);
                this.putUnsignedVarInt(useItemOnEntityData.actionType);
                this.putVarInt(useItemOnEntityData.hotbarSlot);
                this.putSlot(useItemOnEntityData.itemInHand);
                this.putVector3f(useItemOnEntityData.playerPos.asVector3f());
                this.putVector3f(useItemOnEntityData.clickPos.asVector3f());
                break;
            case TYPE_RELEASE_ITEM:
                ReleaseItemData releaseItemData = (ReleaseItemData) this.transactionData;

                this.putUnsignedVarInt(releaseItemData.actionType);
                this.putVarInt(releaseItemData.hotbarSlot);
                this.putSlot(releaseItemData.itemInHand);
                this.putVector3f(releaseItemData.headRot.asVector3f());
                break;
            default:
                throw new RuntimeException("Unknown transaction type " + this.transactionType);
        }
    }

    @Override
    public void decode() {
        this.legacyRequestId = this.getVarInt();
        if (legacyRequestId != 0) {
            int length = (int) this.getUnsignedVarInt();
            for (int i = 0; i < length; i++) {
                byte containerId = (byte) this.getByte();
                byte[] slots = this.getByteArray();
                this.legacySlots.add(new LegacySetItemSlotData(containerId, slots));
            }
        }
        //InventoryTransactionType
        this.transactionType = (int) this.getUnsignedVarInt();

        int length = (int) this.getUnsignedVarInt();
        Collection<NetworkInventoryAction> actions = new ArrayDeque<>();
        for (int i = 0; i < length; i++) {
            actions.add(new NetworkInventoryAction().read(this));
        }
        this.actions = actions.toArray(NetworkInventoryAction.EMPTY_ARRAY);

        switch (this.transactionType) {
            case TYPE_NORMAL:
            case TYPE_MISMATCH:
                //Regular ComplexInventoryTransaction doesn't read any extra data
                break;
            case TYPE_USE_ITEM:
                UseItemData itemData = new UseItemData();

                itemData.actionType = (int) this.getUnsignedVarInt();
                itemData.blockPos = this.getBlockVector3();
                itemData.face = this.getBlockFace();
                itemData.hotbarSlot = this.getVarInt();
                itemData.itemInHand = this.getSlot();
                itemData.playerPos = this.getVector3f().asVector3();
                itemData.clickPos = this.getVector3f();
                itemData.blockRuntimeId = (int) this.getUnsignedVarInt();

                this.transactionData = itemData;
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                UseItemOnEntityData useItemOnEntityData = new UseItemOnEntityData();

                useItemOnEntityData.entityRuntimeId = this.getEntityRuntimeId();
                useItemOnEntityData.actionType = (int) this.getUnsignedVarInt();
                useItemOnEntityData.hotbarSlot = this.getVarInt();
                useItemOnEntityData.itemInHand = this.getSlot();
                useItemOnEntityData.playerPos = this.getVector3f().asVector3();
                useItemOnEntityData.clickPos = this.getVector3f().asVector3();

                this.transactionData = useItemOnEntityData;
                break;
            case TYPE_RELEASE_ITEM:
                ReleaseItemData releaseItemData = new ReleaseItemData();

                releaseItemData.actionType = (int) getUnsignedVarInt();
                releaseItemData.hotbarSlot = getVarInt();
                releaseItemData.itemInHand = getSlot();
                releaseItemData.headRot = this.getVector3f().asVector3();

                this.transactionData = releaseItemData;
                break;
            default:
                throw new RuntimeException("Unknown transaction type " + this.transactionType);
        }
    }
}
