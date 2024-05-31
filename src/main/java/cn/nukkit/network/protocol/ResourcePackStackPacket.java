package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.resourcepacks.ResourcePack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResourcePackStackPacket extends DataPacket {

    public static final int $1 = ProtocolInfo.RESOURCE_PACK_STACK_PACKET;

    public boolean $2 = false;
    public ResourcePack[] behaviourPackStack = ResourcePack.EMPTY_ARRAY;
    public ResourcePack[] resourcePackStack = ResourcePack.EMPTY_ARRAY;
    public final List<ExperimentData> experiments = new ObjectArrayList<>();
    public String $3 = "*";
    public boolean $4 = false;

    @Override
    /**
     * @deprecated 
     */
    
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    /**
     * @deprecated 
     */
    
    public void encode(HandleByteBuf byteBuf) {

        byteBuf.writeBoolean(this.mustAccept);

        byteBuf.writeUnsignedVarInt(this.behaviourPackStack.length);
        for (ResourcePack entry : this.behaviourPackStack) {
            byteBuf.writeString(entry.getPackId().toString());
            byteBuf.writeString(entry.getPackVersion());
            byteBuf.writeString(""); //TODO: subpack name
        }

        byteBuf.writeUnsignedVarInt(this.resourcePackStack.length);
        for (ResourcePack entry : this.resourcePackStack) {
            byteBuf.writeString(entry.getPackId().toString());
            byteBuf.writeString(entry.getPackVersion());
            byteBuf.writeString(""); //TODO: subpack name
        }

        byteBuf.writeString(this.gameVersion);
        byteBuf.writeIntLE(this.experiments.size()); // Experiments length
        for (ExperimentData experimentData : this.experiments) {
            byteBuf.writeString(experimentData.getName());
            byteBuf.writeBoolean(experimentData.isEnabled());
        }
        byteBuf.writeBoolean(true); // Were experiments previously toggled
        byteBuf.writeBoolean(isHasEditorPacks);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int pid() {
        return NETWORK_ID;
    }

    @Value
    public static class ExperimentData {
        String name;
        boolean enabled;
    }
    /**
     * @deprecated 
     */
    

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
