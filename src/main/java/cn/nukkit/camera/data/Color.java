package cn.nukkit.camera.data;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/6/11 <br>
 * PowerNukkitX Project <br>
 */
@PowerNukkitXOnly
@Since("1.20.0-r2")
public record Color(float r, float g, float b) implements SerializableData {
    public CompoundTag serialize() {
        return new CompoundTag("color")
                .putFloat("r", r)
                .putFloat("g", g)
                .putFloat("b", b);
    }
}
