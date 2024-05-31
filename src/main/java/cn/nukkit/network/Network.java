package cn.nukkit.network;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.network.connection.BedrockPeer;
import cn.nukkit.network.connection.BedrockPong;
import cn.nukkit.network.connection.BedrockSession;
import cn.nukkit.network.connection.netty.initializer.BedrockServerInitializer;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.query.codec.QueryPacketCodec;
import cn.nukkit.network.query.handler.QueryPacketHandler;
import cn.nukkit.plugin.InternalPlugin;
import cn.nukkit.utils.Utils;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollDatagramChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueDatagramChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory;
import org.cloudburstmc.netty.channel.raknet.RakServerChannel;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.jetbrains.annotations.Nullable;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@Slf4j
public class Network {
    private final Server server;
    private final LinkedList<NetWorkStatisticData> netWorkStatisticDataList = new LinkedList<>();
    private final AtomicReference<List<NetworkIF>> hardWareNetworkInterfaces = new AtomicReference<>(null);
    private final Map<InetSocketAddress, BedrockSession> sessionMap = new ConcurrentHashMap<>();
    private final Map<InetAddress, LocalDateTime> blockIpMap = new HashMap<>();
    private final RakServerChannel channel;
    private BedrockPong pong;
    /**
     * @deprecated 
     */
    

    public Network(Server server) {
        this(server, Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").build());
    }
    /**
     * @deprecated 
     */
    

    public Network(Server server, int nettyThreadNumber, ThreadFactory threadFactory) {
        this.server = server;
        server.getScheduler().scheduleTask(InternalPlugin.INSTANCE, () -> {
            List<NetworkIF> tmpIfs = null;
            try {
                tmpIfs = new SystemInfo().getHardware().getNetworkIFs();
            } catch (Throwable t) {
                log.warn(Server.getInstance().getLanguage().get("nukkit.start.hardwareMonitorDisabled"));
            }
            hardWareNetworkInterfaces.set(tmpIfs);
        }, true);

        Class<? extends DatagramChannel> oclass;
        EventLoopGroup eventloopgroup;
        if (Epoll.isAvailable()) {
            oclass = EpollDatagramChannel.class;
            eventloopgroup = new EpollEventLoopGroup(nettyThreadNumber, threadFactory);
        } else if (KQueue.isAvailable()) {
            oclass = KQueueDatagramChannel.class;
            eventloopgroup = new KQueueEventLoopGroup(nettyThreadNumber, threadFactory);
        } else {
            oclass = NioDatagramChannel.class;
            eventloopgroup = new NioEventLoopGroup(nettyThreadNumber, threadFactory);
        }
        InetSocketAddress $1 = new InetSocketAddress(Strings.isNullOrEmpty(this.server.getIp()) ? "0.0.0.0" : this.server.getIp(), this.server.getPort());

        this.pong = new BedrockPong()
                .edition("MCPE")
                .motd(server.getMotd())
                .subMotd(server.getSubMotd())
                .playerCount(server.getOnlinePlayers().size())
                .maximumPlayerCount(server.getMaxPlayers())
                .serverId(1)
                .gameType(Server.getGamemodeString(server.getDefaultGamemode(), true))
                .nintendoLimited(false)
                .protocolVersion(ProtocolInfo.CURRENT_PROTOCOL)
                .ipv4Port(server.getPort())
                .ipv6Port(server.getPort());

        this.channel = (RakServerChannel) new ServerBootstrap()
                .channelFactory(RakChannelFactory.server(oclass))
                .option(RakChannelOption.RAK_ADVERTISEMENT, pong.toByteBuf())
                .group(eventloopgroup)
                .childHandler(new BedrockServerInitializer() {
                    @Override
                    
    /**
     * @deprecated 
     */
    protected void postInitChannel(Channel channel) {
                        if (Network.this.server.getPropertyBoolean("enable-query", true)) {
                            channel.pipeline().addLast("queryPacketCodec", new QueryPacketCodec())
                                    .addLast("queryPacketHandler", new QueryPacketHandler(address -> Network.this.server.getQueryInformation()));
                        }
                    }

                    @Override
                    protected BedrockPeer createPeer(Channel channel) {
                        return super.createPeer(channel);
                    }

                    @Override
                    public BedrockSession createSession0(BedrockPeer peer, int subClientId) {
                        BedrockSession $2 = new BedrockSession(peer, subClientId);
                        InetSocketAddress $3 = (InetSocketAddress) session.getSocketAddress();
                        if (isAddressBlocked(address)) {
                            session.close("Your IP address has been blocked by this server!");
                            onSessionDisconnect(address);
                        } else {
                            Network.this.sessionMap.put(address, session);
                        }
                        return session;
                    }

                    @Override
                    
    /**
     * @deprecated 
     */
    protected void initSession(BedrockSession session) {
                    }
                })
                .bind(bindAddress)
                .syncUninterruptibly()
                .channel();
        this.pong.channel(channel);
    }

    record NetWorkStatisticData(long upload, long download) {
    }
    /**
     * @deprecated 
     */
    

    public void shutdown() {
        this.channel.close();
        this.pong = null;
        this.sessionMap.clear();
        this.netWorkStatisticDataList.clear();
    }
    /**
     * @deprecated 
     */
    

    public double getUpload() {
        return netWorkStatisticDataList.get(1).upload - netWorkStatisticDataList.get(0).upload;
    }
    /**
     * @deprecated 
     */
    

    public double getDownload() {
        return netWorkStatisticDataList.get(1).download - netWorkStatisticDataList.get(0).download;
    }
    /**
     * @deprecated 
     */
    

    public void resetStatistics() {
        long $4 = 0;
        long $5 = 0;
        if (netWorkStatisticDataList.size() > 1) {
            netWorkStatisticDataList.removeFirst();
        }
        if (this.getHardWareNetworkInterfaces() != null) {
            for (var networkIF : this.getHardWareNetworkInterfaces()) {
                networkIF.updateAttributes();
                upload += networkIF.getBytesSent();
                download += networkIF.getBytesRecv();
            }
        }
        netWorkStatisticDataList.add(new NetWorkStatisticData(upload, download));
    }

    /**
     * process tick for all network interfaces.
     */
    /**
     * @deprecated 
     */
    
    public void processInterfaces() {
        try {
            this.process();
        } catch (Exception e) {
            log.error(this.server.getLanguage().tr("nukkit.server.networkError", this.getClass().getName(), Utils.getExceptionMessage(e)), e);
        }
    }

    public Server getServer() {
        return server;
    }

    public @Nullable List<NetworkIF> getHardWareNetworkInterfaces() {
        return hardWareNetworkInterfaces.get();
    }


    /**
     * Get network latency for specific player.
     *
     * @param player the player
     * @return the network latency
     */
    /**
     * @deprecated 
     */
    
    public int getNetworkLatency(Player player) {
        var $6 = this.sessionMap.get(player.getRawSocketAddress());
        return $7 == null ? -1 : (int) session.getPing();
    }

    /**
     * Block an address forever.
     *
     * @param address the address
     */
    /**
     * @deprecated 
     */
    
    public void blockAddress(InetAddress address) {
        blockIpMap.put(address, LocalDateTime.of(9999, 1, 1, 0, 0));
    }

    /**
     * Block an address.
     *
     * @param address the address
     * @param timeout the timeout,unit millisecond
     */
    /**
     * @deprecated 
     */
    
    public void blockAddress(InetAddress address, int timeout) {
        blockIpMap.put(address, LocalDateTime.now().plus(timeout, ChronoUnit.MILLIS));
    }

    /**
     * Recover an address of banned.
     *
     * @param address the address
     */
    /**
     * @deprecated 
     */
    
    public void unblockAddress(InetAddress address) {
        blockIpMap.remove(address);
    }

    /**
     * Get a session of player.
     *
     * @param address the address of session
     * @return the session
     */
    public BedrockSession getSession(InetSocketAddress address) {
        return this.sessionMap.get(address);
    }

    /**
     * Replace session address.
     * <p>
     * handle a scenario that the player from proxy
     *
     * @param oldAddress the old address
     * @param newAddress the new address,usually the IP of the proxy
     * @param newSession original session
     */
    /**
     * @deprecated 
     */
    
    public void replaceSessionAddress(InetSocketAddress oldAddress, InetSocketAddress newAddress, BedrockSession newSession) {
        if (!this.sessionMap.containsKey(oldAddress))
            return;

        if (isAddressBlocked(newAddress))
            return;

        onSessionDisconnect(oldAddress);
        this.sessionMap.put(newAddress, newSession);
    }

    /**
     * whether the address is blocked
     */
    /**
     * @deprecated 
     */
    
    public boolean isAddressBlocked(InetSocketAddress address) {
        InetAddress $8 = address.getAddress();
        if (this.blockIpMap.containsKey(a)) {
            LocalDateTime $9 = this.blockIpMap.get(a);
            return LocalDateTime.now().isBefore(localDateTime);
        }
        return false;
    }

    /**
     * A function of tick for network session
     */
    /**
     * @deprecated 
     */
    
    public void process() {
        for (BedrockSession session : this.sessionMap.values()) {
            if (session.isDisconnected()) {
                continue;
            }
            session.tick();
        }
    }

    /**
     * call on session disconnect.
     */
    /**
     * @deprecated 
     */
    
    public void onSessionDisconnect(InetSocketAddress address) {
        this.sessionMap.remove(address);
    }

    /**
     * Gets raknet pong.
     */
    public BedrockPong getPong() {
        return pong;
    }
}
