package cn.nukkit.item.enchantment.damage;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityArthropod;
import cn.nukkit.entity.effect.Effect;
import cn.nukkit.entity.effect.EffectType;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EnchantmentDamageArthropods extends EnchantmentDamage {
    /**
     * @deprecated 
     */
    

    public EnchantmentDamageArthropods() {
        super(ID_DAMAGE_ARTHROPODS, "arthropods", Rarity.UNCOMMON, TYPE.SMITE);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMinEnchantAbility(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 20;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public double getDamageBonus(Entity entity) {
        if (entity instanceof EntityArthropod) {
            return getLevel() * 2.5;
        }

        return 0;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void doAttack(Entity attacker, Entity entity) {
        if (entity instanceof EntityArthropod) {
            int $1 = 20 + ThreadLocalRandom.current().nextInt(10 * this.level);
            entity.addEffect(Effect.get(EffectType.SLOWNESS).setDuration(duration).setAmplifier(3));
        }
    }
}
