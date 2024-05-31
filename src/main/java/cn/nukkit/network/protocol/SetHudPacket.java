package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.hud.HudElement;
import cn.nukkit.network.protocol.types.hud.HudVisibility;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SetHudPacket extends DataPacket {

    public static final int $1 = ProtocolInfo.SET_HUD;

    public final Set<HudElement> elements = new ObjectOpenHashSet<>();
    public HudVisibility visibility;

    @Override
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
        this.elements.clear();
        byteBuf.readArray(this.elements, value -> HudElement.values()[byteBuf.readUnsignedVarInt()]);
        this.visibility = HudVisibility.values()[byteBuf.readByte()];
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeArray(this.elements, (buf, element) -> byteBuf.writeUnsignedVarInt(element.ordinal()));
        byteBuf.writeByte((byte) this.visibility.ordinal());
    }
    /**
     * @deprecated 
     */
    

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
