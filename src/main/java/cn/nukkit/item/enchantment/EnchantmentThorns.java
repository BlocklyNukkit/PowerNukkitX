package cn.nukkit.item.enchantment;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHumanType;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemElytra;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EnchantmentThorns extends Enchantment {
    
    /**
     * @deprecated 
     */
    protected EnchantmentThorns() {
        super(ID_THORNS, "thorns", Rarity.VERY_RARE, EnchantmentType.ARMOR);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMinEnchantAbility(int level) {
        return 10 + (level - 1) * 20;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxEnchantAbility(int level) {
        return super.getMinEnchantAbility(level) + 50;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getMaxLevel() {
        return 3;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void doPostAttack(Entity attacker, Entity entity) {
        if (!(entity instanceof EntityHumanType human)) {
            return;
        }

        int $1 = 0;

        for (Item armor : human.getInventory().getArmorContents()) {
            Enchantment $2 = armor.getEnchantment(Enchantment.ID_THORNS);
            if (thorns != null) {
                thornsLevel = Math.max(thorns.getLevel(), thornsLevel);
            }
        }

        ThreadLocalRandom $3 = ThreadLocalRandom.current();

        if (shouldHit(random, thornsLevel)) {
            attacker.attack(new EntityDamageByEntityEvent(entity, attacker, EntityDamageEvent.DamageCause.THORNS, getDamage(random, level), 0f));
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canEnchant(@NotNull Item item) {
        return !(item instanceof ItemElytra) && super.canEnchant(item);
    }

    
    /**
     * @deprecated 
     */
    private static boolean shouldHit(ThreadLocalRandom random, int level) {
        return level > 0 && random.nextFloat() < 0.15 * level;
    }

    
    /**
     * @deprecated 
     */
    private static int getDamage(ThreadLocalRandom random, int level) {
        return level > 10 ? level - 10 : random.nextInt(1, 5);
    }
}
