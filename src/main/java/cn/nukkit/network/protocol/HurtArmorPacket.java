package cn.nukkit.network.protocol;

import cn.nukkit.api.Since;
import lombok.ToString;

/**
 * @author Nukkit Project Team
 */
@ToString
public class HurtArmorPacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.HURT_ARMOR_PACKET;


    public int cause;


    public int damage;


    public long armorSlots;

    @Override
    public void decode() {
        this.cause = getVarInt();
        this.damage = getVarInt();
        this.armorSlots = getUnsignedVarLong();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.cause);
        this.putVarInt(this.damage);
        this.putUnsignedVarLong(this.armorSlots);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

}
