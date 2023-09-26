package cn.nukkit.block.impl;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.property.value.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

/**
 * @author LoboMetalurgico
 * @since 11/06/2021
 */
@PowerNukkitOnly
@Since("FUTURE")
public class BlockCopperCutOxidized extends BlockCopperCut {
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockCopperCutOxidized() {
        // Does nothing
    }

    @Override
    public String getName() {
        return "Cut Oxidized Copper";
    }

    @Override
    public int getId() {
        return OXIDIZED_CUT_COPPER;
    }

    @Since("FUTURE")
    @PowerNukkitOnly
    @NotNull @Override
    public OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.OXIDIZED;
    }
}