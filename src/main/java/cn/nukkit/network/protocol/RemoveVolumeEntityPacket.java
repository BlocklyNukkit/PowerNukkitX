package cn.nukkit.network.protocol;


public class RemoveVolumeEntityPacket extends DataPacket {


    public static final int NETWORK_ID = ProtocolInfo.REMOVE_VOLUME_ENTITY_PACKET;

    private long id;
    /**
     * @since v503
     */
    private int dimension;


    public RemoveVolumeEntityPacket() {
        // Does nothing
    }

    @Override
    public int pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        id = getUnsignedVarInt();
    }

    @Override
    public void encode() {
        reset();
        putUnsignedVarInt(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
