package cn.nukkit.entity.ai.sensor;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.memory.CoreMemoryTypes;
import cn.nukkit.entity.passive.EntityAnimal;


public class NearestFeedingPlayerSensor implements ISensor {

    protected double range;

    protected double minRange;

    protected int period;
    /**
     * @deprecated 
     */
    

    public NearestFeedingPlayerSensor(double range, double minRange) {
        this(range, minRange, 1);
    }
    /**
     * @deprecated 
     */
    

    public NearestFeedingPlayerSensor(double range, double minRange, int period) {
        this.range = range;
        this.minRange = minRange;
        this.period = period;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void sense(EntityIntelligent entity) {
        if (entity instanceof EntityAnimal entityAnimal) {
            Player $1 = null;
            double $2 = this.range * this.range;
            double $3 = this.minRange * this.minRange;
            //寻找范围内最近满足乞食要求的玩家
            for (Player p : entity.getLevel().getPlayers().values()) {
                if (entity.distanceSquared(p) <= rangeSquared && entity.distanceSquared(p) >= minRangeSquared && entityAnimal.isBreedingItem(p.getInventory().getItemInHand())) {
                    if (player == null) {
                        player = p;
                    } else {
                        if (entity.distanceSquared(p) < entity.distanceSquared(player)) {
                            player = p;
                        }
                    }
                }
            }
            entity.getMemoryStorage().put(CoreMemoryTypes.NEAREST_FEEDING_PLAYER, player);
            return;
        }
        entity.getMemoryStorage().clear(CoreMemoryTypes.NEAREST_FEEDING_PLAYER);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int getPeriod() {
        return period;
    }
}
