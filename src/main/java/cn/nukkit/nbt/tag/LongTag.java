package cn.nukkit.nbt.tag;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.nbt.stream.NBTInputStream;
import cn.nukkit.nbt.stream.NBTOutputStream;
import java.io.IOException;

public class LongTag extends NumberTag<Long> {
    public long data;

    @PowerNukkitXOnly
    @Since("1.19.60-r1")
    public LongTag(long data) {
        super("");
        this.data = data;
    }

    public LongTag(String name) {
        super(name);
    }

    public LongTag(String name, long data) {
        super(name);
        this.data = data;
    }

    @Override
    public Long getData() {
        return data;
    }

    @Override
    public void setData(Long data) {
        this.data = data == null ? 0 : data;
    }

    @Override
    void write(NBTOutputStream dos) throws IOException {
        dos.writeLong(data);
    }

    @Override
    void load(NBTInputStream dis) throws IOException {
        data = dis.readLong();
    }

    @Override
    public Long parseValue() {
        return this.data;
    }

    @Override
    public byte getId() {
        return TAG_Long;
    }

    @Override
    public String toString() {
        return "LongTag " + this.getName() + " (data:" + data + ")";
    }

    @Override
    public String toSNBT() {
        return data + "L";
    }

    @Override
    public String toSNBT(int space) {
        return data + "L";
    }

    @Override
    public Tag copy() {
        return new LongTag(getName(), data);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            LongTag o = (LongTag) obj;
            return data == o.data;
        }
        return false;
    }
}
