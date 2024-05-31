package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xtypr
 * @since 2016/1/5
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDimensionPacket extends DataPacket {

    public static final int $1 = ProtocolInfo.CHANGE_DIMENSION_PACKET;

    public int dimension;

    public float x;
    public float y;
    public float z;

    public boolean respawn;

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

        byteBuf.writeVarInt(this.dimension);
        byteBuf.writeVector3f(this.x, this.y, this.z);
        byteBuf.writeBoolean(this.respawn);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int pid() {
        return NETWORK_ID;
    }
    /**
     * @deprecated 
     */
    

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
