package cn.nukkit.level.generator.task;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.utils.random.NukkitRandomSource;
import cn.nukkit.scheduler.AsyncTask;


public class ChunkPopulationTask extends AsyncTask {

    private final ChunkManager level;
    private final FullChunk chunk;
    private final Populator[] populators;

    public ChunkPopulationTask(ChunkManager level, FullChunk chunk, Populator... populators) {
        this.level = level;
        this.chunk = chunk;
        this.populators = populators;
    }

    @Override
    public void onRun() {
        int chunkX = this.chunk.getX();
        int chunkZ = this.chunk.getZ();
        NukkitRandomSource random = new NukkitRandomSource(0xdeadbeef ^ ((long) chunkX << 8) ^ chunkZ ^ this.level.getSeed());
        for (var populator : this.populators) {
            populator.populate(this.level, chunkX, chunkZ, random, this.chunk);
        }
    }
}
