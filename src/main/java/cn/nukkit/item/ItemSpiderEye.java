package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.effect.Effect;
import cn.nukkit.entity.effect.EffectType;

/**
 * @author Snake1999
 * @since 2016/1/14
 */
public class ItemSpiderEye extends ItemFood {
    /**
     * @deprecated 
     */
    

    public ItemSpiderEye() {
        this(0, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemSpiderEye(Integer meta) {
        this(meta, 1);
    }
    /**
     * @deprecated 
     */
    

    public ItemSpiderEye(Integer meta, int count) {
        super(SPIDER_EYE, meta, count, "Spider Eye");
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getFoodRestore() {
        return 2;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public float getSaturationRestore() {
        return 3.2F;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onEaten(Player player) {
        player.addEffect(Effect.get(EffectType.POISON).setDuration(5 * 20));

        return true;
    }
}
