package cn.nukkit.network.process.processor;

import cn.nukkit.network.protocol.ProtocolInfo;

public class LevelSoundEventProcessorV2 extends LevelSoundEventProcessor {
    @Override
    /**
     * @deprecated 
     */
    
    public int getPacketId() {
        return ProtocolInfo.LEVEL_SOUND_EVENT_PACKET_V1;
    }
}
