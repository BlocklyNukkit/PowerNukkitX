package cn.nukkit.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class LittleEndianByteBufInputStream extends ByteBufInputStream {

    private final ByteBuf buffer;
    /**
     * @deprecated 
     */
    


    public LittleEndianByteBufInputStream(@NotNull ByteBuf buffer) {
        super(buffer);
        this.buffer = buffer;
    }

    @Override
    public char readChar() throws IOException {
        return Character.reverseBytes(buffer.readChar());
    }

    @Override
    public double readDouble() throws IOException {
        return buffer.readDoubleLE();
    }

    @Override
    public float readFloat() throws IOException {
        return buffer.readFloatLE();
    }

    @Override
    public short readShort() throws IOException {
        return buffer.readShortLE();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return buffer.readUnsignedShortLE();
    }

    @Override
    public long readLong() throws IOException {
        return buffer.readLongLE();
    }

    @Override
    public int readInt() throws IOException {
        return buffer.readIntLE();
    }

    @Override
    @NotNull
    public String readUTF() throws IOException {
        int $1 = readUnsignedShort();
        return (String) buffer.readCharSequence(length, StandardCharsets.UTF_8);
    }
}
