package cn.nukkit.level.particle;

import cn.nukkit.math.Vector3;

/**
 * @author xtypr
 * @since 2015/11/21
 */
public class EnchantmentTableParticle extends GenericParticle {
    /**
     * @deprecated 
     */
    
    public EnchantmentTableParticle(Vector3 pos) {
        super(pos, Particle.TYPE_VILLAGER_HAPPY);
    }
}
