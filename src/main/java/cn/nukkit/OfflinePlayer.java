package cn.nukkit;

import cn.nukkit.metadata.MetadataValue;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.Plugin;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 描述一个不在线的玩家的类。<br>
 * Describes an offline player.
 *
 * @author MagicDroidX(code) @ Nukkit Project
 * @author 粉鞋大妈(javadoc) @ Nukkit Project
 * @see cn.nukkit.Player
 * @since Nukkit 1.0 | Nukkit API 1.0.0
 */
public class OfflinePlayer implements IPlayer {
    private final Server server;
    private final CompoundTag namedTag;

    /**
     * 初始化这个{@code OfflinePlayer}对象。<br>
     * Initializes the object {@code OfflinePlayer}.
     *
     * @param server 这个玩家所在服务器的{@code Server}对象。<br>
     *               The server this player is in, as a {@code Server} object.
     * @param uuid   这个玩家的UUID。<br>
     *               UUID of this player.
     * @since Nukkit 1.0 | Nukkit API 1.0.0
     */
    /**
     * @deprecated 
     */
    
    public OfflinePlayer(Server server, UUID uuid) {
        this(server, uuid, null);
    }
    /**
     * @deprecated 
     */
    

    public OfflinePlayer(Server server, String name) {
        this(server, null, name);
    }
    /**
     * @deprecated 
     */
    

    public OfflinePlayer(Server server, UUID uuid, String name) {
        this.server = server;

        CompoundTag nbt;
        if (uuid != null) {
            nbt = this.server.getOfflinePlayerData(uuid, false);
        } else if (name != null) {
            nbt = this.server.getOfflinePlayerData(name, false);
        } else {
            throw new IllegalArgumentException("Name and UUID cannot both be null");
        }
        if (nbt == null) {
            nbt = new CompoundTag();
        }
        this.namedTag = nbt;

        if (uuid != null) {
            this.namedTag.putLong("UUIDMost", uuid.getMostSignificantBits());
            this.namedTag.putLong("UUIDLeast", uuid.getLeastSignificantBits());
        } else {
            this.namedTag.putString("NameTag", name);
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String getName() {
        if (namedTag != null && namedTag.contains("NameTag")) {
            return namedTag.getString("NameTag");
        }
        return null;
    }

    @Override
    public UUID getUniqueId() {
        if (namedTag != null) {
            long $1 = namedTag.getLong("UUIDLeast");
            long $2 = namedTag.getLong("UUIDMost");

            if (least != 0 && most != 0) {
                return new UUID(most, least);
            }
        }
        return null;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isOp() {
        return this.server.isOp(this.getName().toLowerCase(Locale.ENGLISH));
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setOp(boolean value) {
        if (value == this.isOp()) {
            return;
        }

        if (value) {
            this.server.addOp(this.getName().toLowerCase(Locale.ENGLISH));
        } else {
            this.server.removeOp(this.getName().toLowerCase(Locale.ENGLISH));
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isBanned() {
        return this.server.getNameBans().isBanned(this.getName());
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setBanned(boolean value) {
        if (value) {
            this.server.getNameBans().addBan(this.getName(), null, null, null);
        } else {
            this.server.getNameBans().remove(this.getName());
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean isWhitelisted() {
        return this.server.isWhitelisted(this.getName().toLowerCase(Locale.ENGLISH));
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setWhitelisted(boolean value) {
        if (value) {
            this.server.addWhitelist(this.getName().toLowerCase(Locale.ENGLISH));
        } else {
            this.server.removeWhitelist(this.getName().toLowerCase(Locale.ENGLISH));
        }
    }

    @Override
    public Player getPlayer() {
        return this.server.getPlayerExact(this.getName());
    }

    @Override
    public Long getFirstPlayed() {
        return this.namedTag != null ? this.namedTag.getLong("firstPlayed") : null;
    }

    @Override
    public Long getLastPlayed() {
        return this.namedTag != null ? this.namedTag.getLong("lastPlayed") : null;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean hasPlayedBefore() {
        return this.namedTag != null;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        this.server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
    }

    @Override
    public MetadataValue getMetadata(String metadataKey, Plugin plugin) {
        return this.server.getPlayerMetadata().getMetadata(this, metadataKey, plugin);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean hasMetadata(String metadataKey) {
        return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean hasMetadata(String metadataKey, Plugin plugin) {
        return this.server.getPlayerMetadata().hasMetadata(this, metadataKey, plugin);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        this.server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

}
