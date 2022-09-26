package cn.nukkit.network.protocol.types;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;

@Since("1.19.30-r1")
@PowerNukkitXOnly
public enum CommandPermission {
    NORMAL,
    OPERATOR,
    HOST,
    AUTOMATION,
    ADMIN
}
