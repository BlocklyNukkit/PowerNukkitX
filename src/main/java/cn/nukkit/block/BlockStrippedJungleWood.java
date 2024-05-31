package cn.nukkit.block;

import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.block.property.enums.WoodType;
import org.jetbrains.annotations.NotNull;

public class BlockStrippedJungleWood extends BlockWoodStripped {
     public static final BlockProperties $1 = new BlockProperties(STRIPPED_JUNGLE_WOOD, CommonBlockProperties.PILLAR_AXIS);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }
    /**
     * @deprecated 
     */
    

     public BlockStrippedJungleWood(BlockState blockstate) {
         super(blockstate);
     }

    @Override
    public WoodType getWoodType() {
        return WoodType.JUNGLE;
    }
}