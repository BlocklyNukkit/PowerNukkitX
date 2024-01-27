package cn.nukkit.inventory.request;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;
import cn.nukkit.network.protocol.types.itemstack.request.action.ItemStackRequestActionType;
import cn.nukkit.network.protocol.types.itemstack.request.action.SwapAction;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponse;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponseContainer;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponseSlot;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * Allay Project 2023/10/7
 *
 * @author daoge_cmd
 */
@Slf4j
public class SwapActionProcessor implements ItemStackRequestActionProcessor<SwapAction> {
    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.SWAP;
    }

    @Override
    public ItemStackResponse handle(SwapAction action, Player player, ItemStackRequestContext context) {
        ContainerSlotType sourceSlotType = action.getSource().getContainer();
        ContainerSlotType destinationSlotType = action.getDestination().getContainer();
        Inventory source = NetworkMapping.getInventory(player, sourceSlotType);
        Inventory destination = NetworkMapping.getInventory(player, destinationSlotType);

        var sourceSlot = source.fromNetworkSlot(action.getSource().getSlot());
        var destinationSlot = destination.fromNetworkSlot(action.getDestination().getSlot());
        var sourceItem = source.getItem(sourceSlot);
        var destinationItem = destination.getItem(destinationSlot);
        if (failToValidateStackNetworkId(sourceItem.getNetId(), action.getSource().getStackNetworkId())) {
            log.warn("mismatch stack network id!");
            return context.error();
        }
        if (failToValidateStackNetworkId(destinationItem.getNetId(), action.getDestination().getStackNetworkId())) {
            log.warn("mismatch stack network id!");
            return context.error();
        }
        source.setItem(sourceSlot, destinationItem);
        destination.setItem(destinationSlot, sourceItem);
        return context.success(List.of(
                new ItemStackResponseContainer(
                        source.getSlotType(sourceSlot),
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        source.toNetworkSlot(sourceSlot),
                                        source.toNetworkSlot(sourceSlot),
                                        destinationItem.getCount(),
                                        destinationItem.getNetId(),
                                        destinationItem.getCustomName(),
                                        destinationItem.getDamage()
                                )
                        )
                ),
                new ItemStackResponseContainer(
                        destination.getSlotType(destinationSlot),
                        Collections.singletonList(
                                new ItemStackResponseSlot(
                                        destination.toNetworkSlot(destinationSlot),
                                        destination.toNetworkSlot(destinationSlot),
                                        sourceItem.getCount(),
                                        sourceItem.getNetId(),
                                        sourceItem.getCustomName(),
                                        sourceItem.getDamage()
                                )
                        )
                )
        ));
    }
}
