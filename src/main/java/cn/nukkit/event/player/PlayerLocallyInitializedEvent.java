package cn.nukkit.event.player;

import cn.nukkit.api.Since;
import cn.nukkit.event.HandlerList;
import cn.nukkit.player.Player;

/**
 * @author Extollite (Nukkit Project)
 */
@Since("1.4.0.0-PN")
public class PlayerLocallyInitializedEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    @Since("1.4.0.0-PN")
    public static HandlerList getHandlers() {
        return handlers;
    }

    @Since("1.4.0.0-PN")
    public PlayerLocallyInitializedEvent(Player player) {
        this.player = player;
    }
}
