package cn.nukkit.item.test;

import cn.nukkit.item.Item;

/**
 * @author Glorydark
 */
public class ItemNetheriteUpgradeSmithingTemplate extends Item {

    public ItemNetheriteUpgradeSmithingTemplate() {
        this(0, 1);
    }

    public ItemNetheriteUpgradeSmithingTemplate(Integer meta) {
        this(meta, 1);
    }

    public ItemNetheriteUpgradeSmithingTemplate(Integer meta, int count) {
        super(NETHERITE_UPGRADE_SMITHING_TEMPLATE, meta, count, "Netherite Upgrade Smithing Template");
    }
}
