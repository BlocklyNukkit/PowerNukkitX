package cn.nukkit.level.format;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.state.BlockRegistry;
import cn.nukkit.block.state.BlockState;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.DimensionData;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.NumberTag;
import cn.nukkit.utils.collection.nb.Long2ObjectNonBlockingMap;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Allay Project 12/16/2023
 *
 * @author Cool_Loong
 */
@Slf4j
public class Chunk implements IChunk {
    protected final Long2ObjectNonBlockingMap<Entity> entities;
    protected final Long2ObjectNonBlockingMap<BlockEntity> tiles;//block entity id -> block entity
    protected final Long2ObjectNonBlockingMap<BlockEntity> tileList;//block entity position hash index -> block entity
    protected final ChunkSection[] sections;
    protected final short[] heightMap;//256 size Values start at 0 and are 0-384 for the Overworld range
    protected final CompoundTag extraData;
    protected volatile ChunkState chunkState;
    protected volatile long changes;
    private int x;
    private int z;
    private long hash;
    protected boolean isInit;
    protected LevelProvider provider;
    //delay load block entity and entity
    protected List<CompoundTag> blockEntityNBT;
    protected List<CompoundTag> entityNBT;

    private Chunk(
            final int chunkX,
            final int chunkZ,
            final LevelProvider levelProvider
    ) {
        this.chunkState = ChunkState.NEW;
        this.x = chunkX;
        this.z = chunkZ;
        this.provider = levelProvider;
        this.sections = new ChunkSection[levelProvider.getDimensionData().getChunkSectionCount()];
        this.heightMap = new short[256];
        this.entities = new Long2ObjectNonBlockingMap<>();
        this.tiles = new Long2ObjectNonBlockingMap<>();
        this.tileList = new Long2ObjectNonBlockingMap<>();
        this.entityNBT = new ArrayList<>();
        this.blockEntityNBT = new ArrayList<>();
        this.extraData = new CompoundTag();
    }

    private Chunk(
            final ChunkState state,
            final int chunkX,
            final int chunkZ,
            final LevelProvider levelProvider,
            final ChunkSection[] sections,
            final short[] heightMap,
            final List<CompoundTag> entityNBT,
            final List<CompoundTag> blockEntityNBT,
            final CompoundTag extraData
    ) {
        this.chunkState = state;
        this.x = chunkX;
        this.z = chunkZ;
        this.provider = levelProvider;
        this.sections = sections;
        this.heightMap = heightMap;
        this.entities = new Long2ObjectNonBlockingMap<>();
        this.tiles = new Long2ObjectNonBlockingMap<>();
        this.tileList = new Long2ObjectNonBlockingMap<>();
        this.entityNBT = entityNBT;
        this.blockEntityNBT = blockEntityNBT;
        this.extraData = extraData;
    }

    @Override
    public boolean isSectionEmpty(int fY) {
        return this.sections[fY - getDimensionData().getMinSectionY()].isEmpty();
    }

    @Override
    public ChunkSection getSection(int fY) {
        return this.sections[fY - getDimensionData().getMinSectionY()];
    }

    @Override
    public boolean setSection(int fY, ChunkSection section) {
        this.sections[fY - getDimensionData().getMinSectionY()] = section;
        setChanged();
        return true;
    }

    @Override
    public ChunkSection[] getSections() {
        return this.sections;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public long getIndex() {
        return this.hash;
    }

    @Override
    public LevelProvider getProvider() {
        return provider;
    }

    @Override
    public BlockState getBlockState(int x, int y, int z, int layer) {
        return getSection(y >> 4).getBlockState(x, y & 0x0f, z, layer);
    }

    @Override
    public BlockState getAndSetBlockState(int x, int y, int z, BlockState blockstate, int layer) {
        try {
            setChanged();
            return getOrCreateSection(y >> 4).getAndSetBlockState(x, y & 0x0f, z, blockstate, layer);
        } finally {
            removeInvalidTile(x, y, z);
        }
    }

    @Override
    public void setBlockState(int x, int y, int z, BlockState blockstate, int layer) {
        try {
            setChanged();
            getOrCreateSection(y >> 4).setBlockState(x, y & 0x0f, z, blockstate, layer);
        } finally {
            removeInvalidTile(x, y, z);
        }
    }

    @Override
    public int getSkyLight(int x, int y, int z) {
        return getSection(y).getSkyLight(x, y & 0x0f, z);
    }

    @Override
    public void setBlockSkyLight(int x, int y, int z, int level) {
        setChanged();
        getOrCreateSection(y >> 4).setSkyLight(x, y & 0x0f, z, (byte) level);
    }

    @Override
    public int getBlockLight(int x, int y, int z) {
        return getSection(y).getBlockLight(x, y & 0x0f, z);
    }

    @Override
    public void setBlockLight(int x, int y, int z, int level) {
        setChanged();
        getOrCreateSection(y >> 4).setBlockLight(x, y & 0x0f, z, (byte) level);
    }

    @Override
    public int getHighestBlockAt(int x, int z, boolean cache) {
        if (cache) {
            return this.getHeightMap(x, z);
        }
        for (int y = getDimensionData().getMaxHeight(); y >= getDimensionData().getMinHeight(); --y) {
            if (getBlockState(x, y, z) != BlockAir.PROPERTIES.getBlockState()) {
                this.setHeightMap(x, z, y);
                return y;
            }
        }
        return getDimensionData().getMinHeight();
    }

    @Override
    public int getHeightMap(int x, int z) {
        return this.heightMap[(z << 4) | x];
    }

    @Override
    public void setHeightMap(int x, int z, int value) {
        //基岩版3d-data保存heightMap是以0为索引保存的，所以这里需要减去世界最小值，详情查看
        //Bedrock Edition 3d-data saves the height map start from index of 0, so need to subtract the world minimum height here, see for details:
        //https://github.com/bedrock-dev/bedrock-level/blob/main/src/include/data_3d.h#L115
        this.heightMap[(z << 4) | x] = (short) (value - getDimensionData().getMinHeight());
    }

    @Override
    public void recalculateHeightMap() {
        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) {
                recalculateHeightMapColumn(x, z);
            }
        }
    }

    @Override
    public int recalculateHeightMapColumn(int chunkX, int chunkZ) {
        int max = getHighestBlockAt(x, z, false);
        int y;
        for (y = max; y >= 0; --y) {
            BlockState blockState = getBlockState(x, y, z);
            Block block = BlockRegistry.get(blockState);
            if (block.getLightFilter() > 1 || block.diffusesSkyLight()) {
                break;
            }
        }

        setHeightMap(x, z, y + 1);
        return y + 1;
    }

    @Override
    public void populateSkyLight() {
        // basic light calculation
        for (int z = 0; z < 16; ++z) {
            for (int x = 0; x < 16; ++x) { // iterating over all columns in chunk
                int top = this.getHeightMap(x, z) - 1; // top-most block

                int y;

                for (y = getDimensionData().getMaxHeight(); y > top; --y) {
                    // all the blocks above & including the top-most block in a column are exposed to sun and
                    // thus have a skylight value of 15
                    this.setBlockSkyLight(x, y, z, 15);
                }

                int nextLight = 15; // light value that will be applied starting with the next block
                int nextDecrease = 0; // decrease that that will be applied starting with the next block

                // TODO: remove nextLight & nextDecrease, use only light & decrease variables
                for (y = top; y >= 0; --y) { // going under the top-most block
                    nextLight -= nextDecrease;
                    int light = nextLight; // this light value will be applied for this block. The following checks are all about the next blocks

                    if (light < 0) {
                        light = 0;
                    }

                    this.setBlockSkyLight(x, y, z, light);

                    if (light == 0) { // skipping block checks, because everything under a block that has a skylight value
                        // of 0 also has a skylight value of 0
                        continue;
                    }

                    // START of checks for the next block
                    BlockState blockState = getBlockState(x, y, z);
                    Block block = BlockRegistry.get(blockState);

                    if (!block.isTransparent()) { // if we encounter an opaque block, all the blocks under it will
                        // have a skylight value of 0 (the block itself has a value of 15, if it's a top-most block)
                        nextLight = 0;
                    } else if (block.diffusesSkyLight()) {
                        nextDecrease += 1; // skylight value decreases by one for each block under a block
                        // that diffuses skylight. The block itself has a value of 15 (if it's a top-most block)
                    } else {
                        nextDecrease -= block.getLightFilter(); // blocks under a light filtering block will have a skylight value
                        // decreased by the lightFilter value of that block. The block itself
                        // has a value of 15 (if it's a top-most block)
                    }
                    // END of checks for the next block
                }
            }
        }
    }

    @Override
    public int getBiomeId(int x, int y, int z) {
        return getSection(y >> 4).getBiomeId(x, y & 0x0f, z);
    }

    @Override
    public void setBiomeId(int x, int y, int z, int biomeId) {
        try {
            setChanged();
            getOrCreateSection(y >> 4).setBiomeId(x, y & 0x0f, z, biomeId);
        } finally {
            removeInvalidTile(x, y, z);
        }
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return Biome.getBiome(getSection(y >> 4).getBiomeId(x, y & 0x0f, z));
    }

    @Override
    public void setBiome(int x, int y, int z, Biome biome) {
        try {
            setChanged();
            getOrCreateSection(y >> 4).setBiomeId(x, y & 0x0f, z, biome.getId());
        } finally {
            removeInvalidTile(x, y, z);
        }
    }

    @Override
    public boolean isLightPopulated() {
        return extraData.getBoolean("LightPopulated");
    }

    @Override
    public void setLightPopulated(boolean value) {
        extraData.putBoolean("LightPopulated", value);
    }

    @Override
    public void setLightPopulated() {
        extraData.putBoolean("LightPopulated", true);
    }

    @Override
    public ChunkState getChunkState() {
        return this.chunkState;
    }

    @Override
    public void setChunkState(ChunkState chunkState) {
        this.chunkState = chunkState;
    }

    @Override
    public void addEntity(Entity entity) {
        this.entities.put(entity.getId(), entity);
        if (!(entity instanceof Player) && this.isInit) {
            this.setChanged();
        }
    }

    @Override
    public void removeEntity(Entity entity) {
        if (this.entities != null) {
            this.entities.remove(entity.getId());
            if (!(entity instanceof Player) && this.isInit) {
                this.setChanged();
            }
        }
    }

    @Override
    public void addBlockEntity(BlockEntity blockEntity) {
        this.tiles.put(blockEntity.getId(), blockEntity);
        int index = ((blockEntity.getFloorZ() & 0x0f) << 16) | ((blockEntity.getFloorX() & 0x0f) << 12) | (ensureY(blockEntity.getFloorY()) + 64);
        BlockEntity entity = this.tileList.get(index);
        if (this.tileList.containsKey(index) && !entity.equals(blockEntity)) {
            this.tiles.remove(entity.getId());
            entity.close();
        }
        this.tileList.put(index, blockEntity);
        if (this.isInit) {
            this.setChanged();
        }
    }

    @Override
    public void removeBlockEntity(BlockEntity blockEntity) {
        if (this.tiles != null) {
            this.tiles.remove(blockEntity.getId());
            int index = ((blockEntity.getFloorZ() & 0x0f) << 16) | ((blockEntity.getFloorX() & 0x0f) << 12) | (ensureY(blockEntity.getFloorY()) + 64);
            this.tileList.remove(index);
            if (this.isInit) {
                this.setChanged();
            }
        }
    }

    @Override
    public Map<Long, Entity> getEntities() {
        return entities;
    }

    @Override
    public Map<Long, BlockEntity> getBlockEntities() {
        return tiles;
    }

    @Override
    public BlockEntity getTile(int x, int y, int z) {
        return this.tileList.get(((long) z << 16) | ((long) x << 12) | (y + 64));
    }

    @Override
    public boolean isLoaded() {
        return this.getProvider() != null && this.getProvider().isChunkLoaded(this.getX(), this.getZ());
    }

    @Override
    public boolean load() throws IOException {
        return this.load(true);
    }

    @Override
    public boolean load(boolean generate) throws IOException {
        return this.getProvider() != null && this.getProvider().getChunk(this.getX(), this.getZ(), true) != null;
    }

    @Override
    public boolean unload() {
        return this.unload(true, true);
    }

    @Override
    public boolean unload(boolean save) {
        return this.unload(save, true);
    }

    @Override
    public boolean unload(boolean save, boolean safe) {
        LevelProvider provider = this.getProvider();
        if (provider == null) {
            return true;
        }
        if (save && this.changes != 0) {
            provider.saveChunk(this.getX(), this.getZ());
        }
        if (safe) {
            for (Entity entity : this.getEntities().values()) {
                if (entity instanceof Player) {
                    return false;
                }
            }
        }
        for (Entity entity : new ArrayList<>(this.getEntities().values())) {
            if (entity instanceof Player) {
                continue;
            }
            entity.close();
        }

        for (BlockEntity blockEntity : new ArrayList<>(this.getBlockEntities().values())) {
            blockEntity.close();
        }
        this.provider = null;
        return true;
    }

    @Override
    public void initChunk() {
        if (this.getProvider() != null && !this.isInit) {
            boolean changed = false;
            if (this.entityNBT != null) {
                for (CompoundTag nbt : entityNBT) {
                    if (!nbt.contains("id")) {
                        this.setChanged();
                        continue;
                    }
                    ListTag pos = nbt.getList("Pos");
                    if ((((NumberTag) pos.get(0)).getData().intValue() >> 4) != this.getX() || ((((NumberTag) pos.get(2)).getData().intValue() >> 4) != this.getZ())) {
                        changed = true;
                        continue;
                    }
                    Entity entity = Entity.createEntity(nbt.getString("id"), this, nbt);
                    if (entity != null) {
                        changed = true;
                    }
                }
                this.entityNBT = null;
            }

            if (this.blockEntityNBT != null) {
                for (CompoundTag nbt : blockEntityNBT) {
                    if (nbt != null) {
                        if (!nbt.contains("id")) {
                            changed = true;
                            continue;
                        }
                        if ((nbt.getInt("x") >> 4) != this.getX() || ((nbt.getInt("z") >> 4) != this.getZ())) {
                            changed = true;
                            continue;
                        }
                        BlockEntity blockEntity = BlockEntity.createBlockEntity(nbt.getString("id"), this, nbt);
                        if (blockEntity == null) {
                            changed = true;
                        }
                    }
                }
                this.blockEntityNBT = null;
            }

            if (changed) {
                this.setChanged();
            }

            this.isInit = true;
        }
    }

    @Override
    public short[] getHeightMapArray() {
        return heightMap;
    }

    @Override
    public CompoundTag getExtraData() {
        return this.extraData;
    }

    @Override
    public boolean hasChanged() {
        return this.changes != 0;
    }

    @Override
    public void setChanged() {
        this.changes++;
    }

    @Override
    public void setChanged(boolean changed) {
        if (changed) {
            setChanged();
        } else {
            changes = 0;
        }
    }

    @Override
    public long getBlockChanges() {
        return changes;
    }

    /**
     * Gets or create section.
     *
     * @param sectionY the section y range -4 ~ 19
     * @return the or create section
     */
    protected ChunkSection getOrCreateSection(int sectionY) {
        int minSectionY = this.getDimensionData().getMinSectionY();
        int offsetY = sectionY - minSectionY;
        for (int i = 0; i <= offsetY; i++) {
            if (sections[i] == null) {
                sections[i] = new ChunkSection((byte) (i + minSectionY));
            }
        }
        return sections[offsetY];
    }

    private void removeInvalidTile(int x, int y, int z) {
        BlockEntity entity = getTile(x, y, z);
        if (entity != null) {
            try {
                if (!entity.closed && entity.isBlockEntityValid()) {
                    return;
                }
            } catch (Exception e) {
                try {
                    log.warn("Block entity validation of {} at {}, {} {} {} failed, removing as invalid.",
                            entity.getClass().getName(),
                            getProvider().getLevel().getName(),
                            entity.x,
                            entity.y,
                            entity.z,
                            e
                    );
                } catch (Exception e2) {
                    e.addSuppressed(e2);
                    log.warn("Block entity validation failed", e);
                }
            }
            entity.close();
        }
    }

    private int ensureY(final int y) {
        final int minHeight = getDimensionData().getMinHeight();
        final int maxHeight = getDimensionData().getMaxHeight();
        return Math.max(Math.min(y, maxHeight), minHeight);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements IChunkBuilder {
        ChunkState state;
        int chunkZ;
        int chunkX;
        LevelProvider levelProvider;
        ChunkSection[] sections;
        short[] heightMap;
        List<CompoundTag> entities;
        List<CompoundTag> blockEntities;
        CompoundTag extraData;

        private Builder() {
        }

        public Builder chunkX(int chunkX) {
            this.chunkX = chunkX;
            return this;
        }

        @Override
        public int getChunkX() {
            return chunkX;
        }

        public Builder chunkZ(int chunkZ) {
            this.chunkZ = chunkZ;
            return this;
        }

        @Override
        public int getChunkZ() {
            return chunkZ;
        }

        public Builder state(ChunkState state) {
            this.state = state;
            return this;
        }

        public Builder levelProvider(LevelProvider levelProvider) {
            this.levelProvider = levelProvider;
            return this;
        }

        @Override
        public DimensionData getDimensionData() {
            Preconditions.checkNotNull(levelProvider);
            return levelProvider.getDimensionData();
        }

        public Builder sections(ChunkSection[] sections) {
            this.sections = sections;
            return this;
        }

        @Override
        public ChunkSection[] getSections() {
            return sections;
        }

        public Builder heightMap(short[] heightMap) {
            this.heightMap = heightMap;
            return this;
        }

        public Builder entities(List<CompoundTag> entities) {
            this.entities = entities;
            return this;
        }

        public Builder blockEntities(List<CompoundTag> blockEntities) {
            this.blockEntities = blockEntities;
            return this;
        }

        @Override
        public IChunkBuilder extraData(CompoundTag extraData) {
            this.extraData = extraData;
            return this;
        }

        public Chunk build() {
            Preconditions.checkNotNull(levelProvider);
            if (state == null) state = ChunkState.NEW;
            if (sections == null) sections = new ChunkSection[levelProvider.getDimensionData().getChunkSectionCount()];
            if (heightMap == null) heightMap = new short[256];
            if (entities == null) entities = new ArrayList<>();
            if (blockEntities == null) blockEntities = new ArrayList<>();
            return new Chunk(
                    state,
                    chunkX,
                    chunkZ,
                    levelProvider,
                    sections,
                    heightMap,
                    entities,
                    blockEntities,
                    extraData
            );
        }

        public Chunk emptyChunk(int chunkX, int chunkZ) {
            Preconditions.checkNotNull(levelProvider);
            return new Chunk(chunkX, chunkZ, levelProvider);
        }
    }
}
