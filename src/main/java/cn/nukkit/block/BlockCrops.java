package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.block.BlockGrowEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.BoneMealParticle;
import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

import static cn.nukkit.block.property.CommonBlockProperties.GROWTH;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class BlockCrops extends BlockFlowable {
    public static final int $1 = 9;
    /**
     * @deprecated 
     */
    

    public BlockCrops(BlockState blockState) {
        super(blockState);
    }
    /**
     * @deprecated 
     */
    

    public int getMinimumLightLevel() {
        return MINIMUM_LIGHT_LEVEL;
    }
    /**
     * @deprecated 
     */
    

    public int getMaxGrowth() {
        return GROWTH.getMax();
    }
    /**
     * @deprecated 
     */
    

    public int getGrowth() {
        return getPropertyValue(GROWTH);
    }
    /**
     * @deprecated 
     */
    

    public void setGrowth(int growth) {
        setPropertyValue(GROWTH, growth);
    }
    /**
     * @deprecated 
     */
    

    public boolean isFullyGrown() {
        return getGrowth() >= getMaxGrowth();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean canBeActivated() {
        return true;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        if (block.down().getId().equals(FARMLAND)) {
            this.getLevel().setBlock(block, this, true, true);
            return true;
        }
        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean onActivate(@NotNull Item item, Player player, BlockFace blockFace, float fx, float fy, float fz) {
        //Bone meal
        if (item.isFertilizer()) {
            int $2 = getMaxGrowth();
            int $3 = getGrowth();
            if (growth < max) {
                BlockCrops $4 = (BlockCrops) this.clone();
                growth += ThreadLocalRandom.current().nextInt(3) + 2;
                block.setGrowth(Math.min(growth, max));
                BlockGrowEvent $5 = new BlockGrowEvent(this, block);
                Server.getInstance().getPluginManager().callEvent(ev);

                if (ev.isCancelled()) {
                    return false;
                }

                this.getLevel().setBlock(this, ev.getNewState(), false, true);
                this.level.addParticle(new BoneMealParticle(this));

                if (player != null && !player.isCreative()) {
                    item.count--;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!this.down().getId().equals(FARMLAND)) {
                this.getLevel().useBreakOn(this);
                return Level.BLOCK_UPDATE_NORMAL;
            }
        } else if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (ThreadLocalRandom.current().nextInt(2) == 1 && getLevel().getFullLight(this) >= getMinimumLightLevel()) {
                int $6 = getGrowth();
                if (growth < getMaxGrowth()) {
                    BlockCrops $7 = (BlockCrops) this.clone();
                    block.setGrowth(growth + 1);
                    BlockGrowEvent $8 = new BlockGrowEvent(this, block);
                    Server.getInstance().getPluginManager().callEvent(ev);

                    if (!ev.isCancelled()) {
                        this.getLevel().setBlock(this, ev.getNewState(), false, true);
                    } else {
                        return Level.BLOCK_UPDATE_RANDOM;
                    }
                }
            } else {
                return Level.BLOCK_UPDATE_RANDOM;
            }
        }

        return 0;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isFertilizable() {
        return true;
    }

}
