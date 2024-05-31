package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockAcaciaLog extends BlockLog {
    public static final BlockProperties $1 = new BlockProperties(ACACIA_LOG, CommonBlockProperties.PILLAR_AXIS);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockAcaciaLog() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockAcaciaLog(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public BlockState getStrippedState() {
        return BlockStrippedAcaciaLog.PROPERTIES.getDefaultState();
    }
}