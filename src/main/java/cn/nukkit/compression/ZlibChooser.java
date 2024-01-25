package cn.nukkit.compression;

import cn.nukkit.Server;
import cn.nukkit.lang.BaseLang;
import cn.nukkit.network.connection.netty.codec.compression.ZlibCompressionCodec;
import cn.nukkit.utils.TextFormat;
import cn.powernukkitx.libdeflate.Libdeflate;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.zip.Deflater;

@Slf4j
public abstract class ZlibChooser {
    private static final int MAX_INFLATE_LEN = 1024 * 1024 * 10;
    private static ZlibProvider[] providers;
    private static ZlibProvider provider;

    static {
        providers = new ZlibProvider[4];
        providers[2] = new ZlibThreadLocal();
        provider = providers[2];
    }

    public static void setProvider(int providerIndex) {
        var lang = Server.getInstance() == null ? new BaseLang("eng") : Server.getInstance().getLanguage();
        switch (providerIndex) {
            case 0:
                if (providers[providerIndex] == null)
                    providers[providerIndex] = new ZlibOriginal();
                break;
            case 1:
                if (providers[providerIndex] == null)
                    providers[providerIndex] = new ZlibSingleThreadLowMem();
                break;
            case 2:
                if (providers[providerIndex] == null)
                    providers[providerIndex] = new ZlibThreadLocal();
                if (Libdeflate.isAvailable())
                    log.info(TextFormat.WHITE + lang.tr("nukkit.zlib.acceleration-can-enable"));
                break;
            case 3:
                if (Libdeflate.isAvailable()) {
                    ZlibCompressionCodec.libDeflateAvailable = true;
                    if (providers[providerIndex] == null) {
                        providers[providerIndex] = new LibDeflateThreadLocal();
                    }
                } else {
                    log.warn(lang.tr("nukkit.zlib.unavailable"));
                    providerIndex = 2;
                    if (providers[providerIndex] == null)
                        providers[providerIndex] = new ZlibThreadLocal();
                }
                break;
            default:
                throw new UnsupportedOperationException("Invalid provider: " + providerIndex);
        }
        if (providerIndex < 2) {
            log.warn(lang.tr("nukkit.zlib.affect-performance"));
        }
        if (providerIndex == 3) {
            log.warn(lang.tr("nukkit.zlib.acceleration-experimental"));
        }
        provider = providers[providerIndex];
        log.info(lang.tr("nukkit.zlib.selected") + ": {} ({})", providerIndex, provider.getClass().getCanonicalName());
    }


    public static byte[] deflate(byte[] data) throws IOException {
        return deflate(data, Deflater.DEFAULT_COMPRESSION);
    }


    public static byte[] deflate(byte[] data, int level) throws IOException {
        return provider.deflate(data, level);
    }

    public static byte[] inflate(byte[] data) throws IOException {
        return inflate(data, MAX_INFLATE_LEN);
    }

    public static byte[] inflate(byte[] data, int maxSize) throws IOException {
        return provider.inflate(data, maxSize);
    }
}
