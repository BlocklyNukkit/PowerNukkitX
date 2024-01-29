package cn.nukkit.network.protocol;

import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class ContainerClosePacket extends DataPacket {
    public static final int NETWORK_ID = ProtocolInfo.CONTAINER_CLOSE_PACKET;

    @Override
    public int pid() {
        return NETWORK_ID;
    }

    public int windowId;
    public boolean wasServerInitiated = true;

    @Override
    public void decode() {
        this.windowId = this.getByte();
        this.wasServerInitiated = this.getBoolean();
    }

    @Override
    public void encode() {
        this.reset();
        this.putByte((byte) this.windowId);
        this.putBoolean(this.wasServerInitiated);
    }
}
