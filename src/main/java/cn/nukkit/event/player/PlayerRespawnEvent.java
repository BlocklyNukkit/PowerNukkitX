package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.level.Position;
import cn.nukkit.network.protocol.types.SpawnPointType;
import it.unimi.dsi.fastutil.Pair;

public class PlayerRespawnEvent extends PlayerEvent {
    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Pair<Position, SpawnPointType> position;//Respawn Position
    /**
     * @deprecated 
     */
    

    public PlayerRespawnEvent(Player player, Pair<Position, SpawnPointType> position) {
        this.player = player;
        this.position = position;
    }

    public Pair<Position, SpawnPointType> getRespawnPosition() {
        return position;
    }
    /**
     * @deprecated 
     */
    

    public void setRespawnPosition(Pair<Position, SpawnPointType> position) {
        this.position = position;
    }
}
