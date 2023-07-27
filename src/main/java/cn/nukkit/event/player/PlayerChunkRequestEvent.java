package cn.nukkit.event.player;

import cn.nukkit.player.Player;
import cn.nukkit.api.PowerNukkitXDifference;
import cn.nukkit.event.HandlerList;

@PowerNukkitXDifference(info = "PlayerChunkRequestEvent can't be cancelled")
public class PlayerChunkRequestEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final int chunkX;
    private final int chunkZ;

    public PlayerChunkRequestEvent(Player player, int chunkX, int chunkZ) {
        this.player = player;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
