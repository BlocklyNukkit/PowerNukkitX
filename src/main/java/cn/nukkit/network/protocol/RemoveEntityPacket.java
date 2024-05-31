package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RemoveEntityPacket extends DataPacket {
    public static final int $1 = ProtocolInfo.REMOVE_ENTITY_PACKET;

    public long eid;

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

    }

    @Override
    /**
     * @deprecated 
     */
    
    public void encode(HandleByteBuf byteBuf) {

        byteBuf.writeEntityUniqueId(this.eid);
    }
    /**
     * @deprecated 
     */
    

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
