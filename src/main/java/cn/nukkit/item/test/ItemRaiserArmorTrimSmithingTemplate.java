package cn.nukkit.item.test;

import cn.nukkit.item.Item;

/**
 * @author Glorydark
 */
public class ItemRaiserArmorTrimSmithingTemplate extends Item {

    public ItemRaiserArmorTrimSmithingTemplate() {
        this(0, 1);
    }

    public ItemRaiserArmorTrimSmithingTemplate(Integer meta) {
        this(meta, 1);
    }

    public ItemRaiserArmorTrimSmithingTemplate(Integer meta, int count) {
        super(RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, meta, count, "Raiser Armor Trim Smithing Template");
    }
}
