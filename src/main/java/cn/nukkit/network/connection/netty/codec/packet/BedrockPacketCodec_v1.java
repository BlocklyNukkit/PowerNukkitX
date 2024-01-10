package cn.nukkit.network.connection.netty.codec.packet;

import cn.nukkit.network.connection.netty.BedrockPacketWrapper;
import cn.nukkit.network.protocol.DataPacket;
import io.netty.buffer.ByteBuf;

public class BedrockPacketCodec_v1 extends BedrockPacketCodec {

    @Override
    public void encodeHeader(ByteBuf buf, DataPacket msg) {
        buf.writeByte(msg.pid() & 0xff);
    }

    @Override
    public void decodeHeader(ByteBuf buf, BedrockPacketWrapper msg) {
        msg.setPacketId(buf.readUnsignedByte());
    }
}
