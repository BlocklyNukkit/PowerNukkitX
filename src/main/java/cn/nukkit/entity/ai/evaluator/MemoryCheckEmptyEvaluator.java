package cn.nukkit.entity.ai.evaluator;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.memory.IMemory;

@PowerNukkitXOnly
@Since("1.6.0.0-PNX")
public class MemoryCheckEmptyEvaluator implements IBehaviorEvaluator {

    protected Class<? extends IMemory<?>> memoryClazz;

    public MemoryCheckEmptyEvaluator(Class<? extends IMemory<?>> memoryClazz) {
        this.memoryClazz = memoryClazz;
    }

    @Override
    public boolean evaluate(EntityIntelligent entity) {
        return entity.getBehaviorGroup().getMemoryStorage().isEmpty(memoryClazz);
    }
}
