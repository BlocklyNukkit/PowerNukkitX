package cn.nukkit.level.generator.object.legacytree;

import cn.nukkit.block.property.enums.WoodType;
import cn.nukkit.level.generator.object.BlockManager;
import cn.nukkit.utils.random.RandomSourceProvider;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class LegacyOakTree extends LegacyTreeGenerator {

    @Override
    public WoodType getType() {
        return WoodType.OAK;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void placeObject(BlockManager level, int x, int y, int z, RandomSourceProvider random) {
        this.treeHeight = random.nextInt(3) + 4;
        super.placeObject(level, x, y, z, random);
    }
}
