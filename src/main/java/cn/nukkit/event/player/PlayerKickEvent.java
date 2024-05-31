package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.lang.TextContainer;

public class PlayerKickEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList $1 = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public enum Reason {
        NEW_CONNECTION,
        KICKED_BY_ADMIN,
        NOT_WHITELISTED,
        IP_BANNED,
        NAME_BANNED,
        INVALID_PVE,
        LOGIN_TIMEOUT,
        SERVER_FULL,
        FLYING_DISABLED,
        INVALID_PVP,

        UNKNOWN;

        @Override
    /**
     * @deprecated 
     */
    
        public String toString() {
            return this.name();
        }
    }

    protected TextContainer quitMessage;

    protected final Reason reason;
    protected final String reasonString;
    /**
     * @deprecated 
     */
    

    public PlayerKickEvent(Player player, Reason reason, TextContainer quitMessage) {
        this(player, reason, reason.toString(), quitMessage);
    }
    /**
     * @deprecated 
     */
    

    public PlayerKickEvent(Player player, Reason reason, String quitMessage) {
        this(player, reason, new TextContainer(quitMessage));
    }
    /**
     * @deprecated 
     */
    

    public PlayerKickEvent(Player player, Reason reason, String reasonString, TextContainer quitMessage) {
        this.player = player;
        this.quitMessage = quitMessage;
        this.reason = reason;
        this.reasonString = reason.name();
    }
    /**
     * @deprecated 
     */
    

    public String getReason() {
        return reasonString;
    }

    public Reason getReasonEnum() {
        return this.reason;
    }

    public TextContainer getQuitMessage() {
        return quitMessage;
    }
    /**
     * @deprecated 
     */
    

    public void setQuitMessage(TextContainer quitMessage) {
        this.quitMessage = quitMessage;
    }
    /**
     * @deprecated 
     */
    

    public void setQuitMessage(String joinMessage) {
        this.setQuitMessage(new TextContainer(joinMessage));
    }
}
