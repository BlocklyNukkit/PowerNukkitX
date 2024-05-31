package cn.nukkit.event.vehicle;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;


public class EntityExitVehicleEvent extends VehicleEvent implements Cancellable {

    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final cn.nukkit.entity.Entity riding;
    /**
     * @deprecated 
     */
    

    public EntityExitVehicleEvent(cn.nukkit.entity.Entity riding, Entity vehicle) {
        super(vehicle);
        this.riding = riding;
    }

    public cn.nukkit.entity.Entity getEntity() {
        return riding;
    }
    /**
     * @deprecated 
     */
    

    public boolean isPlayer() {
        return riding instanceof Player;
    }

}
