package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.utils.version.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString(exclude = "data")
@NoArgsConstructor
@AllArgsConstructor
public class ResourcePackChunkDataPacket
 extends AbstractResourcePackDataPacket {

    public static final int $1 = ProtocolInfo.RESOURCE_PACK_CHUNK_DATA_PACKET;

    public UUID packId;
    private Version packVersion;
    public int chunkIndex;
    public long progress;
    public byte[] data;

    @Override
    /**
     * @deprecated 
     */
    
    public void decode(HandleByteBuf byteBuf) {
        decodePackInfo(byteBuf);
        this.chunkIndex = byteBuf.readIntLE();
        this.progress = byteBuf.readLongLE();
        this.data = byteBuf.readByteArray();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void encode(HandleByteBuf byteBuf) {
        encodePackInfo(byteBuf);
        byteBuf.writeIntLE(this.chunkIndex);
        byteBuf.writeLongLE(this.progress);
        byteBuf.writeByteArray(this.data);
    }

    @Override
    public Version getPackVersion() {
        return packVersion;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setPackVersion(Version packVersion) {
        this.packVersion = packVersion;
    }

    @Override
    public UUID getPackId() {
        return packId;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setPackId(UUID packId) {
        this.packId = packId;
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
