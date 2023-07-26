package cn.nukkit.level.format.generic;

import cn.nukkit.Server;
import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.PowerNukkitXDifference;
import cn.nukkit.api.Since;
import cn.nukkit.level.GameRules;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.StringTag;
import cn.nukkit.utils.ChunkException;
import cn.nukkit.utils.LevelException;
import cn.nukkit.utils.Utils;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.*;
import java.lang.ref.WeakReference;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@Log4j2
public abstract class BaseLevelProvider implements LevelProvider {
    protected final String path;
    protected final AtomicReference<BaseRegionLoader> lastRegion = new AtomicReference<>();
    protected final Long2ObjectMap<BaseRegionLoader> regions = new Long2ObjectOpenHashMap<>();

    @PowerNukkitXDifference(since = "1.19.20-r3", info = "同步开销甚至远大于装拆箱开销")
    protected final ConcurrentMap<Long, BaseFullChunk> chunks = new ConcurrentHashMap<>();

    @PowerNukkitXDifference(since = "1.19.20-r3", info = "允许多线程彻底地并发获取区块")
    private final ThreadLocal<WeakReference<BaseFullChunk>> lastChunk = new ThreadLocal<>();

    protected Level level;
    protected CompoundTag levelData;
    // protected final Long2ObjectMap<BaseFullChunk> chunks = new Long2ObjectOpenHashMap<>();
    private Vector3 spawn;
    // private final AtomicReference<BaseFullChunk> lastChunk = new AtomicReference<>();

    @PowerNukkitDifference(since = "1.4.0.0-PN", info = "Fixed resource leak")
    public BaseLevelProvider(Level level, String path) throws IOException {
        this.level = level;
        this.path = path;
        File filePath = new File(this.path);
        if (!filePath.exists() && !filePath.mkdirs()) {
            throw new LevelException("Could not create the directory " + filePath);
        }

        CompoundTag levelData;
        File levelDatFile = new File(getPath(), "level.dat");
        try (FileInputStream fos = new FileInputStream(levelDatFile);
                BufferedInputStream input = new BufferedInputStream(fos)) {
            levelData = NBTIO.readCompressed(input, ByteOrder.BIG_ENDIAN);
        } catch (Exception e) {
            log.fatal(
                    "Failed to load the level.dat file at {}, attempting to load level.dat_old instead!",
                    levelDatFile.getAbsolutePath(),
                    e);
            try {
                File old = new File(getPath(), "level.dat_old");
                if (!old.isFile()) {
                    log.fatal("The file {} does not exists!", old.getAbsolutePath());
                    FileNotFoundException ex =
                            new FileNotFoundException("The file " + old.getAbsolutePath() + " does not exists!");
                    ex.addSuppressed(e);
                    throw ex;
                }
                try (FileInputStream fos = new FileInputStream(old);
                        BufferedInputStream input = new BufferedInputStream(fos)) {
                    levelData = NBTIO.readCompressed(input, ByteOrder.BIG_ENDIAN);
                } catch (Exception e2) {
                    log.fatal("Failed to load the level.dat_old file at {}", levelDatFile.getAbsolutePath());
                    e2.addSuppressed(e);
                    throw e2;
                }
            } catch (Exception e2) {
                LevelException ex = new LevelException(
                        "Could not load the level.dat and the level.dat_old files. You might need to restore them from a backup!",
                        e);
                ex.addSuppressed(e2);
                throw ex;
            }
        }

        if (levelData.get("Data") instanceof CompoundTag) {
            this.levelData = levelData.getCompound("Data");
        } else {
            throw new LevelException("Invalid level.dat");
        }

        if (!this.levelData.contains("generatorName")) {
            this.levelData.putString(
                    "generatorName",
                    Generator.getGenerator("DEFAULT").getSimpleName().toLowerCase());
        }

        if (!this.levelData.contains("generatorOptions")) {
            this.levelData.putString("generatorOptions", "");
        }

        this.levelData.putList(new ListTag<>("ServerBrand").add(new StringTag("", "MCPositron")));

        this.spawn = new Vector3(
                this.levelData.getInt("SpawnX"), this.levelData.getInt("SpawnY"), this.levelData.getInt("SpawnZ"));
    }

    @PowerNukkitOnly
    @Since("1.4.0.0-PN")
    public BaseLevelProvider(Level level, String path, CompoundTag levelData, Vector3 spawn) {
        this.level = level;
        this.path = path;
        this.levelData = levelData;
        this.spawn = spawn;
    }

    protected static int getRegionIndexX(int chunkX) {
        return chunkX >> 5;
    }

    protected static int getRegionIndexZ(int chunkZ) {
        return chunkZ >> 5;
    }

    public abstract BaseFullChunk loadChunk(long index, int chunkX, int chunkZ, boolean create);

    public int size() {
        return this.chunks.size();
    }

    @Override
    public void unloadChunks() {
        var iter = chunks.values().iterator();
        while (iter.hasNext()) {
            iter.next().unload(true, false);
            iter.remove();
        }
    }

    @Override
    public String getGenerator() {
        return this.levelData.getString("generatorName");
    }

    @Override
    public Map<String, Object> getGeneratorOptions() {
        return new HashMap<String, Object>() {
            {
                put("preset", levelData.getString("generatorOptions"));
            }
        };
    }

    @Override
    public Map<Long, BaseFullChunk> getLoadedChunks() {
        return ImmutableMap.copyOf(chunks);
    }

    @Override
    public boolean isChunkLoaded(int X, int Z) {
        return isChunkLoaded(Level.chunkHash(X, Z));
    }

    public void putChunk(long index, BaseFullChunk chunk) {
        chunks.put(index, chunk);
    }

    @Override
    public boolean isChunkLoaded(long hash) {
        return this.chunks.containsKey(hash);
    }

    public BaseRegionLoader getRegion(int x, int z) {
        long index = Level.chunkHash(x, z);
        synchronized (regions) {
            return this.regions.get(index);
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    public Server getServer() {
        return this.level.getServer();
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public String getName() {
        return this.levelData.getString("LevelName");
    }

    @Override
    public boolean isRaining() {
        return this.levelData.getBoolean("raining");
    }

    @Override
    public void setRaining(boolean raining) {
        this.levelData.putBoolean("raining", raining);
    }

    @Override
    public int getRainTime() {
        return this.levelData.getInt("rainTime");
    }

    @Override
    public void setRainTime(int rainTime) {
        this.levelData.putInt("rainTime", rainTime);
    }

    @Override
    public boolean isThundering() {
        return this.levelData.getBoolean("thundering");
    }

    @Override
    public void setThundering(boolean thundering) {
        this.levelData.putBoolean("thundering", thundering);
    }

    @Override
    public int getThunderTime() {
        return this.levelData.getInt("thunderTime");
    }

    @Override
    public void setThunderTime(int thunderTime) {
        this.levelData.putInt("thunderTime", thunderTime);
    }

    @Override
    public long getCurrentTick() {
        return this.levelData.getLong("Time");
    }

    @Override
    public void setCurrentTick(long currentTick) {
        this.levelData.putLong("Time", currentTick);
    }

    @Override
    public long getTime() {
        return this.levelData.getLong("DayTime");
    }

    @Override
    public void setTime(long value) {
        this.levelData.putLong("DayTime", value);
    }

    @Override
    public long getSeed() {
        return this.levelData.getLong("RandomSeed");
    }

    @Override
    public void setSeed(long value) {
        this.levelData.putLong("RandomSeed", value);
    }

    @Override
    public Vector3 getSpawn() {
        return spawn;
    }

    @Override
    public void setSpawn(Vector3 pos) {
        this.levelData.putInt("SpawnX", (int) pos.x);
        this.levelData.putInt("SpawnY", (int) pos.y);
        this.levelData.putInt("SpawnZ", (int) pos.z);
        spawn = pos;
    }

    @Override
    public GameRules getGamerules() {
        GameRules rules = GameRules.getDefault();

        try {
            if (this.levelData.contains("GameRules")) rules.readNBT(this.levelData.getCompound("GameRules"));
        } catch (Throwable ignore) {
            log.warn("Failed to load game rules for level: " + getName() + ", fall back to the default");
            rules = GameRules.getDefault();
        }

        return rules;
    }

    @Override
    public void setGameRules(GameRules rules) {
        this.levelData.putCompound("GameRules", rules.writeNBT());
    }

    @Override
    public void doGarbageCollection() {
        int limit = (int) (System.currentTimeMillis() - 50);
        synchronized (regions) {
            if (regions.isEmpty()) {
                return;
            }

            ObjectIterator<BaseRegionLoader> iter = regions.values().iterator();
            while (iter.hasNext()) {
                BaseRegionLoader loader = iter.next();

                if (loader.lastUsed <= limit) {
                    try {
                        loader.close();
                    } catch (IOException e) {
                        throw new RuntimeException("Unable to close RegionLoader", e);
                    }
                    lastRegion.set(null);
                    iter.remove();
                }
            }
        }
    }

    @Override
    public void saveChunks() {
        for (BaseFullChunk chunk : this.chunks.values()) {
            if (chunk.getChanges() != 0) {
                chunk.setChanged(false);
                this.saveChunk(chunk.getX(), chunk.getZ());
            }
        }
    }

    public CompoundTag getLevelData() {
        return levelData;
    }

    @PowerNukkitDifference(since = "1.4.0.0-PN", info = "Fixed resource leak")
    @Override
    public void saveLevelData() {
        File levelDataFile = new File(getPath(), "level.dat");
        try {
            Utils.safeWrite(levelDataFile, file -> {
                try (FileOutputStream fos = new FileOutputStream(file);
                        BufferedOutputStream out = new BufferedOutputStream(fos)) {
                    NBTIO.writeGZIPCompressed(new CompoundTag().putCompound("Data", this.levelData), out);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (IOException e) {
            log.fatal("Failed to save the level.dat file at {}", levelDataFile.getAbsolutePath(), e);
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void updateLevelName(String name) {
        if (!this.getName().equals(name)) {
            this.levelData.putString("LevelName", name);
        }
    }

    @Override
    public boolean loadChunk(int chunkX, int chunkZ) {
        return this.loadChunk(chunkX, chunkZ, false);
    }

    @Override
    public boolean loadChunk(int chunkX, int chunkZ, boolean create) {
        long index = Level.chunkHash(chunkX, chunkZ);
        if (this.chunks.containsKey(index)) {
            return true;
        }
        return loadChunk(index, chunkX, chunkZ, create) != null;
    }

    @Override
    public boolean unloadChunk(int X, int Z) {
        return this.unloadChunk(X, Z, true);
    }

    @Override
    public boolean unloadChunk(int X, int Z, boolean safe) {
        long index = Level.chunkHash(X, Z);
        BaseFullChunk chunk = this.chunks.get(index);
        if (chunk != null && chunk.unload(false, safe)) {
            lastChunk.set(null);
            this.chunks.remove(index, chunk);
            return true;
        }
        return false;
    }

    @Override
    public BaseFullChunk getChunk(int chunkX, int chunkZ) {
        return this.getChunk(chunkX, chunkZ, false);
    }

    @Nullable protected final BaseFullChunk getThreadLastChunk() {
        var ref = lastChunk.get();
        if (ref == null) {
            return null;
        }
        return ref.get();
    }

    @Override
    public BaseFullChunk getLoadedChunk(int chunkX, int chunkZ) {
        var tmp = getThreadLastChunk();
        if (tmp != null && tmp.getX() == chunkX && tmp.getZ() == chunkZ) {
            return tmp;
        }
        long index = Level.chunkHash(chunkX, chunkZ);
        lastChunk.set(new WeakReference<>(tmp = chunks.get(index)));
        return tmp;
    }

    @Override
    public BaseFullChunk getLoadedChunk(long hash) {
        var tmp = getThreadLastChunk();
        if (tmp != null && tmp.getIndex() == hash) {
            return tmp;
        }
        lastChunk.set(new WeakReference<>(tmp = chunks.get(hash)));
        return tmp;
    }

    @Override
    public BaseFullChunk getChunk(int chunkX, int chunkZ, boolean create) {
        var tmp = getThreadLastChunk();
        if (tmp != null && tmp.getX() == chunkX && tmp.getZ() == chunkZ) {
            return tmp;
        }
        long index = Level.chunkHash(chunkX, chunkZ);
        lastChunk.set(new WeakReference<>(tmp = chunks.get(index)));
        if (tmp != null) {
            return tmp;
        } else {
            tmp = this.loadChunk(index, chunkX, chunkZ, create);
            lastChunk.set(new WeakReference<>(tmp));
            return tmp;
        }
    }

    @Override
    public void setChunk(int chunkX, int chunkZ, FullChunk chunk) {
        if (!(chunk instanceof BaseFullChunk)) {
            throw new ChunkException("Invalid Chunk class");
        }
        chunk.setProvider(this);
        chunk.setPosition(chunkX, chunkZ);
        long index = Level.chunkHash(chunkX, chunkZ);
        if (this.chunks.containsKey(index) && !this.chunks.get(index).equals(chunk)) {
            this.unloadChunk(chunkX, chunkZ, false);
        }
        this.chunks.put(index, (BaseFullChunk) chunk);
    }

    @Override
    public boolean isChunkPopulated(int chunkX, int chunkZ) {
        BaseFullChunk chunk = this.getChunk(chunkX, chunkZ);
        return chunk != null && chunk.isPopulated();
    }

    @Override
    public synchronized void close() {
        this.unloadChunks();
        synchronized (regions) {
            ObjectIterator<BaseRegionLoader> iter = this.regions.values().iterator();

            while (iter.hasNext()) {
                try {
                    iter.next().close();
                } catch (IOException e) {
                    throw new RuntimeException("Unable to close RegionLoader", e);
                }
                lastRegion.set(null);
                iter.remove();
            }
        }
        this.level = null;
    }

    @Override
    public boolean isChunkGenerated(int chunkX, int chunkZ) {
        BaseRegionLoader region = this.getRegion(chunkX >> 5, chunkZ >> 5);
        return region != null
                && region.chunkExists(chunkX - region.getX() * 32, chunkZ - region.getZ() * 32)
                && this.getChunk(chunkX - region.getX() * 32, chunkZ - region.getZ() * 32, true)
                        .isGenerated();
    }
}
