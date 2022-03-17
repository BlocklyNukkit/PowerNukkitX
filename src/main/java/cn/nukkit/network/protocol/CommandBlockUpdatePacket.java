package cn.nukkit.network.protocol;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.math.BlockVector3;
import lombok.ToString;

@ToString
public class CommandBlockUpdatePacket extends DataPacket {

    public boolean isBlock;
    public int x;
    public int y;
    public int z;
    public int commandBlockMode;
    public boolean isRedstoneMode;
    public boolean isConditional;
    public long minecartEid;
    public String command;
    public String lastOutput;
    public String name;
    public boolean shouldTrackOutput;
    @PowerNukkitOnly
    @Since("1.6.0.0-PNX")
    public int tickDelay;
    @PowerNukkitOnly
    @Since("1.6.0.0-PNX")
    public boolean executingOnFirstTick;

    @Override
    public byte pid() {
        return ProtocolInfo.COMMAND_BLOCK_UPDATE_PACKET;
    }

    @Override
    @PowerNukkitDifference
    public void decode() {
        this.isBlock = this.getBoolean();
        if (this.isBlock) {
            BlockVector3 v = this.getBlockVector3();
            this.x = v.x;
            this.y = v.y;
            this.z = v.z;
            this.commandBlockMode = (int) this.getUnsignedVarInt();
            this.isRedstoneMode = this.getBoolean();
            this.isConditional = this.getBoolean();
        } else {
            this.minecartEid = this.getEntityRuntimeId();
        }
        this.command = this.getString();
        this.lastOutput = this.getString();
        this.name = this.getString();
        this.shouldTrackOutput = this.getBoolean();
        this.tickDelay = this.getLInt();
        this.executingOnFirstTick = this.getBoolean();
    }

    @Override
    @PowerNukkitDifference
    public void encode() {
        this.reset();
        this.putBoolean(this.isBlock);
        if (this.isBlock) {
            this.putBlockVector3(this.x, this.y, this.z);
            this.putUnsignedVarInt(this.commandBlockMode);
            this.putBoolean(this.isRedstoneMode);
            this.putBoolean(this.isConditional);
        } else {
            this.putEntityRuntimeId(this.minecartEid);
        }
        this.putString(this.command);
        this.putString(this.lastOutput);
        this.putString(this.name);
        this.putBoolean(this.shouldTrackOutput);
        this.putLInt(this.tickDelay);
        this.putBoolean(this.executingOnFirstTick);
    }
}
