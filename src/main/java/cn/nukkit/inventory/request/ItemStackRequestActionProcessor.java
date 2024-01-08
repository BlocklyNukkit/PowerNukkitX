package cn.nukkit.inventory.request;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.types.itemstack.request.action.ItemStackRequestAction;
import cn.nukkit.network.protocol.types.itemstack.request.action.ItemStackRequestActionType;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponse;
import cn.nukkit.network.protocol.types.itemstack.response.ItemStackResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ItemStackRequestActionProcessor<T extends ItemStackRequestAction> {
    ItemStackRequestActionType getType();

    Function<Integer, ItemStackResponse> E = (id) -> new ItemStackResponse(ItemStackResponseStatus.ERROR, id, List.of());

    ItemStackResponse handle(T action, Player player, int currentActionIndex, ItemStackRequestAction[] actions, Map<String, Object> context);

    default boolean failToValidateStackNetworkId(int expectedSNID, int clientSNID) {
        //若客户端发来的stackNetworkId小于0，说明客户端保证数据无误并要求遵从服务端的数据
        //这通常发生在当一个ItemStackRequest中有多个action时且多个action有相同的source/destination container
        //第一个action检查完id后后面的action就不需要重复检查了
        return clientSNID > 0 && expectedSNID != clientSNID;
    }
}