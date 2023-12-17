package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockstate.BlockState;


public class BlockHyphaeCrimson extends BlockStem {


    public BlockHyphaeCrimson() {
        this(0);
    }


    public BlockHyphaeCrimson(int meta) {
        super(meta);
    }
    
    @Override
    public int getId() {
        return CRIMSON_HYPHAE;
    }
    
    @Override
    public String getName() {
        return "Crimson Hyphae";
    }


    @Override
    public BlockState getStrippedState() {
        return getCurrentState().withBlockId(STRIPPED_CRIMSON_HYPHAE);
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

}
