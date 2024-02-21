package cn.nukkit.network.protocol;

import lombok.ToString;

@ToString
public class SetLastHurtByPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.SET_LAST_HURT_BY_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        //TODO
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
