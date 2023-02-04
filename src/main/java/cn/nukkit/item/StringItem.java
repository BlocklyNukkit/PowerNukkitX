package cn.nukkit.item;

import cn.nukkit.api.PowerNukkitOnly;

/**
 * @author joserobjr
 * @since 2021-06-12
 */
@PowerNukkitOnly
public interface StringItem {
    String getNamespaceId();

    static String notEmpty(String value) {
        if (value != null && value.trim().isEmpty()) throw new IllegalArgumentException("The name cannot be empty");
        return value;
    }

    default int getId() {
        return ItemID.STRING_IDENTIFIED_ITEM;
    }
}
