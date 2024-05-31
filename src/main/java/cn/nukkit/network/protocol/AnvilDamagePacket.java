package cn.nukkit.network.protocol;

import cn.nukkit.math.BlockVector3;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnvilDamagePacket extends DataPacket {
    public static final int $1 = ProtocolInfo.ANVIL_DAMAGE_PACKET;
    public int damage;
    public int x;
    public int y;
    public int z;

    @Override
    /**
     * @deprecated 
     */
    
    public int pid() {
        return ProtocolInfo.ANVIL_DAMAGE_PACKET;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void decode(HandleByteBuf byteBuf) {
        this.damage = byteBuf.readByte();
        BlockVector3 $2 = byteBuf.readBlockVector3();
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void encode(HandleByteBuf byteBuf) {

    }
    /**
     * @deprecated 
     */
    

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
