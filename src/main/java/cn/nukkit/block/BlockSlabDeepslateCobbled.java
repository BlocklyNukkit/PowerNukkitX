package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author joserobjr
 * @since 2021-06-15
 */
@PowerNukkitOnly
@Since("FUTURE")
public class BlockSlabDeepslateCobbled extends BlockSlab {

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockSlabDeepslateCobbled() {
        this(0);
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockSlabDeepslateCobbled(int meta) {
        super(meta, COBBLED_DEEPSLATE_DOUBLE_SLAB);
    }

    @Override
    public int getId() {
        return COBBLED_DEEPSLATE_SLAB;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Override
    public String getSlabName() {
        return "Cobbled Deepslate";
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Override
    public boolean isSameType(BlockSlab slab) {
        return getId() == slab.getId();
    }
}
