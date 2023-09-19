package cn.nukkit.block.impl;

import cn.nukkit.block.BlockTransparentMeta;
import cn.nukkit.block.property.ArrayBlockProperty;
import cn.nukkit.block.property.BlockProperties;

public class BlockSnifferEgg extends BlockTransparentMeta {
    public static final ArrayBlockProperty<String> CRACKED_STATE =
            new ArrayBlockProperty("cracked_state", false, new String[] {"cracked", "max_cracked", "no_cracks"});

    public static final BlockProperties PROPERTIES = new BlockProperties(CRACKED_STATE);

    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockSnifferEgg() {}

    public BlockSnifferEgg(int meta) {
        super(meta);
    }

    public int getId() {
        return SNIFFER_EGG;
    }

    public String getName() {
        return "Sniffer Egg";
    }
}