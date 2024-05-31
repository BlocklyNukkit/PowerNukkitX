package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.TrimMaterial;
import cn.nukkit.network.protocol.types.TrimPattern;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
@NoArgsConstructor
public class TrimDataPacket extends DataPacket {
    public static final int $1 = ProtocolInfo.TRIM_DATA;
    public final List<TrimPattern> patterns = new ObjectArrayList<>();
    public final List<TrimMaterial> materials = new ObjectArrayList<>();
    /**
     * @deprecated 
     */
    

    public int pid() {
        return NETWORK_ID;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void decode(HandleByteBuf byteBuf) {
        int $2 = byteBuf.readUnsignedVarInt();
        for ($3nt $1 = 0; i < length1; i++) {
            patterns.add(new TrimPattern(byteBuf.readString(), byteBuf.readString()));
        }
        int $4 = byteBuf.readUnsignedVarInt();
        for ($5nt $2 = 0; i < length2; i++) {
            materials.add(new TrimMaterial(byteBuf.readString(), byteBuf.readString(), byteBuf.readString()));
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(patterns.size());
        patterns.forEach(p -> {
            byteBuf.writeString(p.itemName());
            byteBuf.writeString(p.patternId());
        });
        byteBuf.writeUnsignedVarInt(materials.size());
        materials.forEach(m -> {
            byteBuf.writeString(m.materialId());
            byteBuf.writeString(m.color());
            byteBuf.writeString(m.itemName());
        });
    }
    /**
     * @deprecated 
     */
    

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
