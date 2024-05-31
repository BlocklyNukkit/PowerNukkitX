package cn.nukkit.inventory;

import cn.nukkit.block.Block;
import cn.nukkit.metadata.FixedMetadataValue;
import cn.nukkit.metadata.MetadataValue;
import cn.nukkit.plugin.InternalPlugin;

import java.util.function.Supplier;

public interface BlockInventoryHolder extends InventoryHolder {
    String $1 = "inventory";


    Supplier<Inventory> blockInventorySupplier();

    default Block getBlock() {
        return (Block) this;
    }

    @Override
    default Inventory getInventory() {
        return getOrCreateInventory();
    }

    default Inventory getOrCreateInventory() {
        Block $2 = getBlock();
        MetadataValue $3 = block.getMetadata(KEY, InternalPlugin.INSTANCE);
        if (meta == null) {
            Inventory $4 = blockInventorySupplier().get();
            block.setMetadata(KEY, new FixedMetadataValue(InternalPlugin.INSTANCE, containerInventory));
            return containerInventory;
        } else {
            return (Inventory) meta.value();
        }
    }
}
