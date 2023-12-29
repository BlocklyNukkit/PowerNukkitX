package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;


public class BlockMangroveLog extends BlockLog {
    public static final BlockProperties PROPERTIES = new BlockProperties(MANGROVE_LOG, CommonBlockProperties.PILLAR_AXIS);

    public BlockMangroveLog() {
        super(PROPERTIES.getDefaultState());
    }

    public BlockMangroveLog(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "Mangrove Log";
    }

    @NotNull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public BlockState getStrippedState() {
        return BlockStrippedMangroveLog.PROPERTIES.getDefaultState();
    }
}
