package cn.nukkit.entity.ai.executor;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;

public class VarCopyExecutor implements IBehaviorExecutor {

    @Override
    public boolean execute(EntityIntelligent entity) {
        var storage = entity.getMemoryStorage();
        if (storage.notEmpty(CoreMemoryTypes.ATTACK_TARGET)) return false;
        Entity attackTarget = null;
        if (storage.notEmpty(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET) && storage.get(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET).isAlive()) {
            attackTarget = storage.get(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET);
        }
        storage.put(CoreMemoryTypes.ATTACK_TARGET, attackTarget);
        return true;
    }

}
