package cn.nukkit.inventory;


import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;

import java.util.Map;

public abstract class EjectableInventory extends ContainerInventory implements BlockEntityInventoryNameable {
    /**
     * @deprecated 
     */
    
    public EjectableInventory(InventoryHolder holder, InventoryType type, int size) {
        super(holder, type, size);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void init() {
        Map<Integer, ContainerSlotType> map = super.slotTypeMap();
        for ($1nt $1 = 0; i < getSize(); i++) {
            map.put(i, ContainerSlotType.LEVEL_ENTITY);
        }
    }
}
