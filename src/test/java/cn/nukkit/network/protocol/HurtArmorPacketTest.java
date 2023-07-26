package cn.nukkit.network.protocol;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HurtArmorPacketTest {
    HurtArmorPacket packet;

    @BeforeEach
    void setUp() {
        packet = new HurtArmorPacket();
    }

    @Test
    void encodeDecode() {
        packet.cause = 1;
        packet.damage = 2;
        packet.armorSlots = 3;
        packet.encode();
        packet.getVarInt();
        packet.decode();
        assertEquals(1, packet.cause);
        assertEquals(2, packet.damage);
        assertEquals(3, packet.armorSlots);
    }
}
