package cn.nukkit.level.particle;

import cn.nukkit.math.Vector3;

/**
 * @author xtypr
 * @since 2015/11/21
 */
public class InstantEnchantParticle extends GenericParticle {
    /**
     * @deprecated 
     */
    
    public InstantEnchantParticle(Vector3 pos) {
        super(pos, Particle.TYPE_MOB_SPELL_INSTANTANEOUS);
    }
}
