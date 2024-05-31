package cn.nukkit.network.process.handler;

import cn.nukkit.network.connection.BedrockSession;
import cn.nukkit.network.process.SessionState;
import cn.nukkit.network.protocol.ClientToServerHandshakePacket;

public class HandshakePacketHandler extends BedrockSessionPacketHandler {
    /**
     * @deprecated 
     */
    
    public HandshakePacketHandler(BedrockSession session) {
        super(session);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void handle(ClientToServerHandshakePacket pk) {
        session.getMachine().fire(SessionState.RESOURCE_PACK);
    }
}
