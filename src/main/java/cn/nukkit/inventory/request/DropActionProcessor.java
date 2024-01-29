package cn.nukkit.inventory.request;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.types.itemstack.request.action.DropAction;
import cn.nukkit.network.protocol.types.itemstack.request.action.ItemStackRequestActionType;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponse;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponseContainer;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponseSlot;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * Allay Project 2023/9/23
 *
 * @author daoge_cmd
 */
@Slf4j
public class DropActionProcessor implements ItemStackRequestActionProcessor<DropAction> {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.DROP;
    }

    @Override
    public ItemStackResponse handle(DropAction action, Player player, ItemStackRequestContext context) {
        Inventory inventory = NetworkMapping.getInventory(player, action.getSource().getContainer());
        var count = action.getCount();
        var slot = inventory.fromNetworkSlot(action.getSource().getSlot());
        var item = inventory.getItem(slot);
        if (failToValidateStackNetworkId(item.getNetId(), action.getSource().getStackNetworkId())) {
            log.warn("mismatch stack network id!");
            return context.error();
        }
        if (item.isNull()) {
            log.warn("cannot throw an air!");
            return context.error();
        }
        if (item.getCount() < count) {
            log.warn("cannot throw more items than the current amount!");
            return context.error();
        }
        Item drop = inventory.getItem(slot);
        drop.setCount(count);
        player.dropItem(drop);
        item = inventory.getItem(slot);
        return context.success(List.of(
                new ItemStackResponseContainer(
                        inventory.getSlotType(slot),
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        inventory.toNetworkSlot(slot),
                                        inventory.toNetworkSlot(slot),
                                        item.getCount(),
                                        item.getNetId(),
                                        item.getCustomName(),
                                        item.getDamage()
                                )
                        )
                )
        ));
    }
}
