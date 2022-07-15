package cn.nukkit.entity.ai.memory;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;

/**
 * 此接口抽象了一个记忆存储器 <br/>
 * 记忆存储器用于存储多个记忆单元{@link IMemory}
 */
@PowerNukkitXOnly
@Since("1.6.0.0-PNX")
public interface IMemoryStorage {
    void put(IMemory<?> memory);
    IMemory<?> get(Class<?> memoryClazz);
    void remove(Class<?> memoryClazz);
    boolean contains(Class<?> memoryClazz);
}
