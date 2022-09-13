package cn.nukkit.entity;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;

@PowerNukkitXOnly
@Since("1.19.21-r4")
public interface CanAttack {
    float[] getDiffHandDamage();

    float getDiffHandDamage(int difficulty);

    void setDiffHandDamage(float[] damages);

    void setDiffHandDamage(int difficulty, float damage);
}
