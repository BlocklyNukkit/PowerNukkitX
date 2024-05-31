package cn.nukkit.block;

import cn.nukkit.event.block.BlockFadeEvent;
import cn.nukkit.level.Level;
import cn.nukkit.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBubbleCoral extends BlockCoral {
    public static final BlockProperties $1 = new BlockProperties(BUBBLE_CORAL);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockBubbleCoral() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockBubbleCoral(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isDead() {
        return false;
    }

    @Override
    public Block getDeadCoral() {
        return new BlockDeadBubbleCoral();
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (this.getId().equals(BUBBLE_CORAL)) {
                this.getLevel().scheduleUpdate(this, 60 + ThreadLocalRandom.current().nextInt(40));
            }
            return type;
        } else if (type == Level.BLOCK_UPDATE_SCHEDULED) {
            for (BlockFace face : BlockFace.values()) {
                if (getSideAtLayer(0, face) instanceof BlockFlowingWater ||
                        getSideAtLayer(1, face) instanceof BlockFlowingWater ||
                        getSideAtLayer(0, face) instanceof BlockFrostedIce || getSideAtLayer(1, face) instanceof BlockFrostedIce) {
                    return type;
                }
            }
            BlockFadeEvent $2 = new BlockFadeEvent(this, getDeadCoral());
            if (!event.isCancelled()) {
                this.getLevel().setBlock(this, event.getNewState(), true, true);
            }
            return type;
        }
        return 0;
    }
}