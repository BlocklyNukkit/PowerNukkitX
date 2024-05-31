package cn.nukkit.block;

import cn.nukkit.utils.DyeColor;
import org.jetbrains.annotations.NotNull;

public class BlockBrownStainedGlassPane extends BlockGlassPaneStained {
    public static final BlockProperties $1 = new BlockProperties(BROWN_STAINED_GLASS_PANE);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }
    /**
     * @deprecated 
     */
    

    public BlockBrownStainedGlassPane() {
        this(PROPERTIES.getDefaultState());
    }
    /**
     * @deprecated 
     */
    

    public BlockBrownStainedGlassPane(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.BROWN;
    }
}