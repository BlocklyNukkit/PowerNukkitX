package cn.nukkit.network.protocol;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import lombok.Getter;

@Since("1.19.70-r1")
@PowerNukkitXOnly
@Getter
public class UnlockedRecipesPacket extends DataPacket {
    public boolean unlockedNotification;
    public final List<String> unlockedRecipes = new ObjectArrayList<>();

    @Override
    public byte pid() {
        return ProtocolInfo.UNLOCKED_RECIPES_PACKET;
    }

    @Override
    public void decode() {
        this.unlockedNotification = this.getBoolean();
        int count = (int) this.getUnsignedVarInt();
        for (int i = 0; i < count; i++) {
            this.unlockedRecipes.add(this.getString());
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putBoolean(this.unlockedNotification);
        this.putUnsignedVarInt(this.unlockedRecipes.size());
        for (String recipe : this.unlockedRecipes) {
            this.putString(recipe);
        }
    }
}
