package cn.nukkit.utils;

import cn.powernukkitx.libdeflate.LibdeflateCompressor;

import java.io.Closeable;


public final class PNXLibDeflater extends LibdeflateCompressor implements Closeable, AutoCloseable {
    public PNXLibDeflater() {
        this(6);
    }

    public PNXLibDeflater(int level) {
        super(level);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        if (!closed) {
            close();
        }
    }
}
