package cn.nukkit;

import cn.nukkit.block.BlockComposter;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.PluginIdentifiableCommand;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.command.defaults.WorldCommand;
import cn.nukkit.command.function.FunctionManager;
import cn.nukkit.compression.ZlibChooser;
import cn.nukkit.config.ServerProperties;
import cn.nukkit.config.ServerPropertiesKeys;
import cn.nukkit.config.ServerSettings;
import cn.nukkit.config.YamlSnakeYamlConfigurer;
import cn.nukkit.console.NukkitConsole;
import cn.nukkit.dispenser.DispenseBehaviorRegister;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.entity.data.profession.Profession;
import cn.nukkit.entity.data.property.EntityProperty;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.level.LevelInitEvent;
import cn.nukkit.event.level.LevelLoadEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.server.QueryRegenerateEvent;
import cn.nukkit.event.server.ServerStartedEvent;
import cn.nukkit.event.server.ServerStopEvent;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.lang.BaseLang;
import cn.nukkit.lang.LangCode;
import cn.nukkit.lang.TextContainer;
import cn.nukkit.level.DimensionEnum;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.LevelConfig;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.level.format.LevelProviderManager;
import cn.nukkit.level.format.leveldb.LevelDBProvider;
import cn.nukkit.level.generator.terra.PNXPlatform;
import cn.nukkit.level.tickingarea.manager.SimpleTickingAreaManager;
import cn.nukkit.level.tickingarea.manager.TickingAreaManager;
import cn.nukkit.level.tickingarea.storage.JSONTickingAreaStorage;
import cn.nukkit.level.updater.Updater;
import cn.nukkit.level.updater.block.BlockStateUpdaterBase;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.metadata.EntityMetadataStore;
import cn.nukkit.metadata.LevelMetadataStore;
import cn.nukkit.metadata.PlayerMetadataStore;
import cn.nukkit.metrics.NukkitMetrics;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.Network;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.PlayerListPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.protocol.types.PlayerInfo;
import cn.nukkit.network.protocol.types.XboxLivePlayerInfo;
import cn.nukkit.network.rcon.RCON;
import cn.nukkit.permission.BanEntry;
import cn.nukkit.permission.BanList;
import cn.nukkit.permission.DefaultPermissions;
import cn.nukkit.permission.Permissible;
import cn.nukkit.plugin.InternalPlugin;
import cn.nukkit.plugin.JavaPluginLoader;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginLoadOrder;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.plugin.service.NKServiceManager;
import cn.nukkit.plugin.service.ServiceManager;
import cn.nukkit.positiontracking.PositionTrackingService;
import cn.nukkit.recipe.Recipe;
import cn.nukkit.registry.RecipeRegistry;
import cn.nukkit.registry.Registries;
import cn.nukkit.resourcepacks.ResourcePackManager;
import cn.nukkit.resourcepacks.loader.JarPluginResourcePackLoader;
import cn.nukkit.resourcepacks.loader.ZippedResourcePackLoader;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scoreboard.manager.IScoreboardManager;
import cn.nukkit.scoreboard.manager.ScoreboardManager;
import cn.nukkit.scoreboard.storage.JSONScoreboardStorage;
import cn.nukkit.tags.BiomeTags;
import cn.nukkit.tags.BlockTags;
import cn.nukkit.tags.ItemTags;
import cn.nukkit.utils.*;
import cn.nukkit.utils.collection.FreezableArrayManager;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.ConfigManager;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongLists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a server object, global singleton.
 * <p>is instantiated in {@link Nukkit} and later the instance object is obtained via {@link cn.nukkit.Server#getInstance}.
 * The constructor method of {@link cn.nukkit.Server} performs a number of operations, including but not limited to initializing configuration files, creating threads, thread pools, start plugins, registering recipes, blocks, entities, items, etc.
 *
 * @author MagicDroidX
 * @author Box
 */

@Slf4j
public class Server {
    public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "nukkit.broadcast.admin";
    public static final String BROADCAST_CHANNEL_USERS = "nukkit.broadcast.user";
    private static Server instance = null;

    private BanList banByName;
    private BanList banByIP;
    private Config operators;
    private Config whitelist;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);
    private final LongList busyingTime = LongLists.synchronize(new LongArrayList(0));
    private boolean hasStopped = false;
    private PluginManager pluginManager;
    private ServerScheduler scheduler;
    /**
     * A tick counter that records the number of ticks that have passed through the server.
     */
    private int tickCounter;
    private long nextTick;
    private final float[] tickAverage = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
    private final float[] useAverage = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private float maxTick = 20;
    private float maxUse = 0;
    private int sendUsageTicker = 0;
    private final NukkitConsole console;
    private final ConsoleThread consoleThread;
    /**
     * FJP thread pool responsible for terrain generation, data compression and other computing tasks
     */
    public final ForkJoinPool computeThreadPool;
    private SimpleCommandMap commandMap;
    private ResourcePackManager resourcePackManager;
    private ConsoleCommandSender consoleSender;
    private IScoreboardManager scoreboardManager;
    private FunctionManager functionManager;
    private TickingAreaManager tickingAreaManager;
    private int maxPlayers;
    private boolean autoSave = true;
    /**
     * Does the configuration item check the login time.
     */
    public boolean checkLoginTime = false;
    private RCON rcon;
    private EntityMetadataStore entityMetadata;
    private PlayerMetadataStore playerMetadata;
    private LevelMetadataStore levelMetadata;
    private Network network;
    private int serverAuthoritativeMovementMode = 0;
    private Boolean getAllowFlight = null;
    private int difficulty = Integer.MAX_VALUE;
    private int defaultGamemode = Integer.MAX_VALUE;
    private int autoSaveTicker = 0;
    private int autoSaveTicks = 6000;
    private BaseLang baseLang;
    private LangCode baseLangCode;
    private UUID serverID;
    private final String filePath;
    private final String dataPath;
    private final String pluginPath;
    private final Set<UUID> uniquePlayers = new HashSet<>();
    private final ServerProperties properties;
    private final Map<InetSocketAddress, Player> players = new ConcurrentHashMap<>();
    private final Map<UUID, Player> playerList = new ConcurrentHashMap<>();
    private QueryRegenerateEvent queryRegenerateEvent;
    private PositionTrackingService positionTrackingService;

    private final Map<Integer, Level> levels = new HashMap<>() {
        @Override
        public Level put(Integer key, Level value) {
            Level result = super.put(key, value);
            levelArray = levels.values().toArray(Level.EMPTY_ARRAY);
            return result;
        }

        @Override
        public boolean remove(Object key, Object value) {
            boolean result = super.remove(key, value);
            levelArray = levels.values().toArray(Level.EMPTY_ARRAY);
            return result;
        }

        @Override
        public Level remove(Object key) {
            Level result = super.remove(key);
            levelArray = levels.values().toArray(Level.EMPTY_ARRAY);
            return result;
        }
    };
    private Level[] levelArray;
    private final ServiceManager serviceManager = new NKServiceManager();
    private final Thread currentThread;
    private final long launchTime;
    private final ServerSettings settings;
    private Watchdog watchdog;
    private DB playerDataDB;
    private boolean useTerra;
    private FreezableArrayManager freezableArrayManager;
    public boolean enabledNetworkEncryption;

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Default Level Region] ────────────
    // ─────────────────────────────────────────────────────
    private Level defaultLevel = null;
    private Level defaultNether = null;
    private Level defaultEnd = null;
    private boolean allowNether;
    private boolean allowTheEnd;
    // ─────────────────────────────────────────────────────

    Server(final String filePath, String dataPath, String pluginPath, String predefinedLanguage) {
        Preconditions.checkState(instance == null, "Already initialized!");
        launchTime = System.currentTimeMillis();
        currentThread = Thread.currentThread(); // Saves the current thread instance as a reference, used in Server#isPrimaryThread()
        instance = this;

        this.filePath = filePath;
        if (!new File(dataPath + "worlds/").exists()) {
            new File(dataPath + "worlds/").mkdirs();
        }
        if (!new File(dataPath + "players/").exists()) {
            new File(dataPath + "players/").mkdirs();
        }
        if (!new File(pluginPath).exists()) {
            new File(pluginPath).mkdirs();
        }
        this.dataPath = new File(dataPath).getAbsolutePath() + "/";
        this.pluginPath = new File(pluginPath).getAbsolutePath() + "/";
        String commandDataPath = new File(dataPath).getAbsolutePath() + "/command_data";
        if (!new File(commandDataPath).exists()) {
            new File(commandDataPath).mkdirs();
        }

        this.console = new NukkitConsole(this);
        this.consoleThread = new ConsoleThread();
        this.consoleThread.start();

        File config = new File(this.dataPath + "nukkit.yml");
        String chooseLanguage = null;
        if (!config.exists()) {
            log.info("{}Welcome! Please choose a language first!", TextFormat.GREEN);
            try {
                InputStream languageList = this.getClass().getModule().getResourceAsStream("language/language.list");
                if (languageList == null) {
                    throw new IllegalStateException("language/language.list is missing. If you are running a development version, make sure you have run 'git submodule update --init'.");
                }
                String[] lines = Utils.readFile(languageList).split("\n");
                for (String line : lines) {
                    log.info(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (chooseLanguage == null) {
                String lang;
                if (predefinedLanguage != null) {
                    log.info("Trying to load language from predefined language: {}", predefinedLanguage);
                    lang = predefinedLanguage;
                } else {
                    lang = this.console.readLine();
                }

                try (InputStream conf = this.getClass().getClassLoader().getResourceAsStream("language/" + lang + "/lang.json")) {
                    if (conf != null) {
                        chooseLanguage = lang;
                    } else if (predefinedLanguage != null) {
                        log.warn("No language found for predefined language: {}, please choose a valid language", predefinedLanguage);
                        predefinedLanguage = null;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Config configInstance = new Config(config);
            chooseLanguage = configInstance.getString("settings.language", "eng");
        }
        this.baseLang = new BaseLang(chooseLanguage);
        this.baseLangCode = mapInternalLang(chooseLanguage);
        log.info("Loading {}...", TextFormat.GREEN + "nukkit.yml" + TextFormat.RESET);
        this.settings = ConfigManager.create(ServerSettings.class, it -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer());
            it.withBindFile(config);
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        this.settings.baseSettings().language(chooseLanguage);

        this.computeThreadPool = new ForkJoinPool(Math.min(0x7fff, Runtime.getRuntime().availableProcessors()), new ComputeThreadPoolThreadFactory(), null, false);

        levelArray = Level.EMPTY_ARRAY;

        org.apache.logging.log4j.Level targetLevel = org.apache.logging.log4j.Level.getLevel(this.settings.debugSettings().level());
        org.apache.logging.log4j.Level currentLevel = Nukkit.getLogLevel();
        if (targetLevel != null && targetLevel.intLevel() > currentLevel.intLevel()) {
            Nukkit.setLogLevel(targetLevel);
        }

        log.info("Loading {}...", TextFormat.GREEN + "server.properties" + TextFormat.RESET);
        this.properties = new ServerProperties(this.dataPath);

        var isShaded = StartArgUtils.isShaded();
        if (!StartArgUtils.isValidStart() || (JarStart.isUsingJavaJar() && !isShaded)) {
            log.error(getLanguage().tr("nukkit.start.invalid"));
            return;
        }
        if (!this.properties.get(ServerPropertiesKeys.ALLOW_SHADED, false) && isShaded) {
            log.error(getLanguage().tr("nukkit.start.shaded1"));
            log.error(getLanguage().tr("nukkit.start.shaded2"));
            log.error(getLanguage().tr("nukkit.start.shaded3"));
            return;
        }

        this.allowNether = this.properties.get(ServerPropertiesKeys.ALLOW_NETHER, true);
        this.allowTheEnd = this.properties.get(ServerPropertiesKeys.ALLOW_THE_END, true);
        this.useTerra = this.properties.get(ServerPropertiesKeys.USE_TERRA, false);
        this.checkLoginTime = this.properties.get(ServerPropertiesKeys.CHECK_LOGIN_TIME, false);

        log.info(this.getLanguage().tr("language.selected", getLanguage().getName(), getLanguage().getLang()));
        log.info(getLanguage().tr("nukkit.server.start", TextFormat.AQUA + this.getVersion() + TextFormat.RESET));

        String poolSize = settings.baseSettings().asyncWorkers();
        int poolSizeNumber;
        try {
            poolSizeNumber = Integer.parseInt(poolSize);
        } catch (Exception e) {
            poolSizeNumber = Math.max(Runtime.getRuntime().availableProcessors(), 4);
        }
        ServerScheduler.WORKERS = poolSizeNumber;
        this.scheduler = new ServerScheduler();

        ZlibChooser.setProvider(settings.networkSettings().zlibProvider());

        this.serverAuthoritativeMovementMode = switch (this.properties.get(ServerPropertiesKeys.SERVER_AUTHORITATIVE_MOVEMENT, "server-auth")) {
            case "client-auth" -> 0;
            case "server-auth" -> 1;
            case "server-auth-with-rewind" -> 2;
            default -> throw new IllegalArgumentException();
        };
        this.enabledNetworkEncryption = this.properties.get(ServerPropertiesKeys.NETWORK_ENCRYPTION, true);
        if (this.getSettings().baseSettings().waterdogpe()) {
            this.checkLoginTime = false;
        }

        if (this.properties.get(ServerPropertiesKeys.ENABLE_RCON, false)) {
            try {
                this.rcon = new RCON(this, this.properties.get(ServerPropertiesKeys.RCON_PASSWORD, ""), (!this.getIp().equals("")) ? this.getIp() : "0.0.0.0", this.getPort());
            } catch (IllegalArgumentException e) {
                log.error(getLanguage().tr(e.getMessage(), e.getCause().getMessage()));
            }
        }
        this.entityMetadata = new EntityMetadataStore();
        this.playerMetadata = new PlayerMetadataStore();
        this.levelMetadata = new LevelMetadataStore();
        this.operators = new Config(this.dataPath + "ops.txt", Config.ENUM);
        this.whitelist = new Config(this.dataPath + "white-list.txt", Config.ENUM);
        this.banByName = new BanList(this.dataPath + "banned-players.json");
        this.banByName.load();
        this.banByIP = new BanList(this.dataPath + "banned-ips.json");
        this.banByIP.load();
        this.maxPlayers = this.properties.get(ServerPropertiesKeys.MAX_PLAYERS, 20);
        this.setAutoSave(this.properties.get(ServerPropertiesKeys.AUTO_SAVE, true));
        if (this.properties.get(ServerPropertiesKeys.HARDCORE, false) && this.getDifficulty() < 3) {
            this.properties.get(ServerPropertiesKeys.DIFFICULTY, 3);
        }

        log.info(this.getLanguage().tr("nukkit.server.info", this.getName(), TextFormat.YELLOW + this.getNukkitVersion() + TextFormat.RESET + " (" + TextFormat.YELLOW + this.getGitCommit() + TextFormat.RESET + ")" + TextFormat.RESET, this.getApiVersion()));
        log.info(this.getLanguage().tr("nukkit.server.license"));
        this.consoleSender = new ConsoleCommandSender();

        // Initialize metrics
        NukkitMetrics.startNow(this);

        {//init
            Registries.POTION.init();
            Registries.PACKET.init();
            Registries.ENTITY.init();
            Registries.BLOCKENTITY.init();
            Registries.BLOCKSTATE_ITEMMETA.init();
            Registries.BLOCKSTATE.init();
            Registries.ITEM_RUNTIMEID.init();
            Registries.BLOCK.init();
            Registries.ITEM.init();
            Registries.CREATIVE.init();
            Registries.BIOME.init();
            Registries.FUEL.init();
            Registries.GENERATOR.init();
            Registries.GENERATE_STAGE.init();
            Registries.EFFECT.init();
            Registries.RECIPE.init();
            Profession.init();
            String a = BlockTags.ACACIA;
            String b = ItemTags.ARROW;
            String c = BiomeTags.WARM;
            Updater d = BlockStateUpdaterBase.INSTANCE;
            Enchantment.init();
            Attribute.init();
            BlockComposter.init();
            DispenseBehaviorRegister.init();
        }

        if (useTerra) {//load terra
            PNXPlatform instance = PNXPlatform.getInstance();
        }

        freezableArrayManager = new FreezableArrayManager(
                this.settings.freezeArraySettings().enable(),
                this.settings.freezeArraySettings().slots(),
                this.settings.freezeArraySettings().defaultTemperature(),
                this.settings.freezeArraySettings().freezingPoint(),
                this.settings.freezeArraySettings().absoluteZero(),
                this.settings.freezeArraySettings().boilingPoint(),
                this.settings.freezeArraySettings().melting(),
                this.settings.freezeArraySettings().singleOperation(),
                this.settings.freezeArraySettings().batchOperation());
        scoreboardManager = new ScoreboardManager(new JSONScoreboardStorage(commandDataPath + "/scoreboard.json"));
        functionManager = new FunctionManager(commandDataPath + "/functions");
        tickingAreaManager = new SimpleTickingAreaManager(new JSONTickingAreaStorage(this.dataPath + "worlds/"));

        // Convert legacy data before plugins get the chance to mess with it.
        try {
            playerDataDB = Iq80DBFactory.factory.open(new File(dataPath, "players"), new Options()
                    .createIfMissing(true)
                    .compressionType(CompressionType.ZLIB_RAW));
        } catch (IOException e) {
            log.error("", e);
            System.exit(1);
        }
        this.resourcePackManager = new ResourcePackManager(
                new ZippedResourcePackLoader(new File(Nukkit.DATA_PATH, "resource_packs")),
                new JarPluginResourcePackLoader(new File(this.pluginPath))
        );
        this.commandMap = new SimpleCommandMap(this);
        this.pluginManager = new PluginManager(this, this.commandMap);
        this.pluginManager.subscribeToPermission(Server.BROADCAST_CHANNEL_ADMINISTRATIVE, this.consoleSender);
        this.pluginManager.registerInterface(JavaPluginLoader.class);
        //this.pluginManager.registerInterface(JSPluginLoader.class);
        this.console.setExecutingCommands(true);

        try {
            log.debug("Loading position tracking service");
            this.positionTrackingService = new PositionTrackingService(new File(Nukkit.DATA_PATH, "services/position_tracking_db"));
        } catch (IOException e) {
            log.error("Failed to start the Position Tracking DB service!", e);
        }
        this.pluginManager.loadInternalPlugin();

        this.serverID = UUID.randomUUID();
        this.pluginManager.loadPlugins(this.pluginPath);
        {//trim
            Registries.POTION.trim();
            Registries.PACKET.trim();
            Registries.ENTITY.trim();
            Registries.BLOCKENTITY.trim();
            Registries.BLOCKSTATE_ITEMMETA.trim();
            Registries.BLOCKSTATE.trim();
            Registries.ITEM_RUNTIMEID.trim();
            Registries.BLOCK.trim();
            Registries.ITEM.trim();
            Registries.CREATIVE.trim();
            Registries.BIOME.trim();
            Registries.FUEL.trim();
            Registries.GENERATOR.trim();
            Registries.GENERATE_STAGE.trim();
            Registries.EFFECT.trim();
            Registries.RECIPE.trim();
        }

        this.enablePlugins(PluginLoadOrder.STARTUP);

        LevelProviderManager.addProvider("leveldb", LevelDBProvider.class);

        loadLevels();

        this.queryRegenerateEvent = new QueryRegenerateEvent(this, 5);
        this.network = new Network(this);
        this.getTickingAreaManager().loadAllTickingArea();

        this.properties.save();

        if (this.getDefaultLevel() == null) {
            log.error(this.getLanguage().tr("nukkit.level.defaultError"));
            this.forceShutdown();

            return;
        }

        this.autoSaveTicks = settings.baseSettings().autosave();

        this.enablePlugins(PluginLoadOrder.POSTWORLD);

        EntityProperty.buildPacketData();
        EntityProperty.buildPlayerProperty();

        if (settings.baseSettings().installSpark()) {
            SparkInstaller.initSpark(this);
        }

        if (!Boolean.parseBoolean(System.getProperty("disableWatchdog", "false"))) {
            this.watchdog = new Watchdog(this, 60000);//60s
            this.watchdog.start();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        this.start();
    }

    private void loadLevels() {
        File file = new File(this.getDataPath() + "/worlds");
        if (!file.isDirectory()) throw new RuntimeException("worlds isn't directory");
        //load all world from `worlds` folder
        for (var f : Objects.requireNonNull(file.listFiles(File::isDirectory))) {
            LevelConfig levelConfig = getLevelConfig(f.getName());
            if (levelConfig != null && !levelConfig.enable()) {
                continue;
            }

            if (!this.loadLevel(f.getName())) {
                this.generateLevel(f.getName(), null);
            }
        }

        if (this.getDefaultLevel() == null) {
            String levelFolder = this.properties.get(ServerPropertiesKeys.LEVEL_NAME, "world");
            if (levelFolder == null || levelFolder.trim().isEmpty()) {
                log.warn("level-name cannot be null, using default");
                levelFolder = "world";
                this.properties.get(ServerPropertiesKeys.LEVEL_NAME, levelFolder);
            }

            if (!this.loadLevel(levelFolder)) {
                //default world not exist
                //generate the default world
                HashMap<Integer, LevelConfig.GeneratorConfig> generatorConfig = new HashMap<>();
                //spawn seed
                long seed;
                String seedString = String.valueOf(this.properties.get(ServerPropertiesKeys.LEVEL_SEED, System.currentTimeMillis()));
                try {
                    seed = Long.parseLong(seedString);
                } catch (NumberFormatException e) {
                    seed = seedString.hashCode();
                }
                //todo nether the_end overworld
                generatorConfig.put(0, new LevelConfig.GeneratorConfig("flat", seed, false, LevelConfig.AntiXrayMode.LOW, true, DimensionEnum.OVERWORLD.getDimensionData(), Collections.emptyMap()));
                LevelConfig levelConfig = new LevelConfig("leveldb", true, generatorConfig);
                this.generateLevel(levelFolder, levelConfig);
            }
            this.setDefaultLevel(this.getLevelByName(levelFolder + " Dim0"));
        }
    }
    // ─────────────────────────────────────────────────────
    // ──────── Start of [Lifecycle & Ticking Region] ──────
    // ─────────────────────────────────────────────────────

    /**
     * Reload Server
     */
    public void reload() {
        log.info("Reloading...");
        log.info("Saving levels...");

        for (Level level : this.levelArray) {
            level.save();
        }

        this.scoreboardManager.save();
        this.pluginManager.disablePlugins();
        this.pluginManager.clearPlugins();
        this.commandMap.clearCommands();

        log.info("Reloading properties...");
        this.properties.reload();
        this.maxPlayers = this.properties.get(ServerPropertiesKeys.MAX_PLAYERS, 20);
        if (this.properties.get(ServerPropertiesKeys.HARDCORE, false) && this.getDifficulty() < 3) {
            this.properties.get(ServerPropertiesKeys.DIFFICULTY, difficulty = 3);
        }

        this.banByIP.load();
        this.banByName.load();
        this.reloadWhitelist();
        this.operators.reload();

        for (BanEntry entry : this.getIPBans().getEntires().values()) {
            try {
                this.getNetwork().blockAddress(InetAddress.getByName(entry.getName()), -1);
            } catch (UnknownHostException e) {
                // ignore
            }
        }

        this.pluginManager.registerInterface(JavaPluginLoader.class);
        //todo enable js plugin when adapt
//        JSIInitiator.reset();
//        JSFeatures.clearFeatures();
//        JSFeatures.initInternalFeatures();
//        this.pluginManager.registerInterface(JSPluginLoader.class);
        this.scoreboardManager.read();

        log.info("Reloading Registries...");
        {
            Registries.POTION.reload();
            Registries.PACKET.reload();
            Registries.ENTITY.reload();
            Registries.BLOCKENTITY.reload();
            Registries.BLOCKSTATE_ITEMMETA.reload();
            Registries.BLOCKSTATE.reload();
            Registries.ITEM_RUNTIMEID.reload();
            Registries.BLOCK.reload();
            Registries.ITEM.reload();
            Registries.CREATIVE.reload();
            Registries.BIOME.reload();
            Registries.FUEL.reload();
            Registries.GENERATOR.reload();
            Registries.GENERATE_STAGE.reload();
            Registries.EFFECT.reload();
            Registries.RECIPE.reload();
            Enchantment.reload();
        }

        this.pluginManager.loadPlugins(this.pluginPath);
        this.functionManager.reload();

        this.enablePlugins(PluginLoadOrder.STARTUP);
        {
            Registries.POTION.trim();
            Registries.PACKET.trim();
            Registries.ENTITY.trim();
            Registries.BLOCKENTITY.trim();
            Registries.BLOCKSTATE_ITEMMETA.trim();
            Registries.BLOCKSTATE.trim();
            Registries.ITEM_RUNTIMEID.trim();
            Registries.BLOCK.trim();
            Registries.ITEM.trim();
            Registries.CREATIVE.trim();
            Registries.BIOME.trim();
            Registries.FUEL.trim();
            Registries.GENERATOR.trim();
            Registries.GENERATE_STAGE.trim();
            Registries.EFFECT.trim();
            Registries.RECIPE.trim();
        }
        this.enablePlugins(PluginLoadOrder.POSTWORLD);
        ServerStartedEvent serverStartedEvent = new ServerStartedEvent();
        getPluginManager().callEvent(serverStartedEvent);
    }

    /**
     * Shut down the server
     */
    public void shutdown() {
        isRunning.compareAndSet(true, false);
    }

    /**
     * Force Shut down the server
     */
    public void forceShutdown() {
        if (this.hasStopped) {
            return;
        }

        try {
            isRunning.compareAndSet(true, false);

            this.hasStopped = true;

            ServerStopEvent serverStopEvent = new ServerStopEvent();
            getPluginManager().callEvent(serverStopEvent);

            if (this.rcon != null) {
                this.rcon.close();
            }

            for (Player player : new ArrayList<>(this.players.values())) {
                player.close(player.getLeaveMessage(), getSettings().baseSettings().shutdownMessage());
            }

            this.getSettings().save();

            log.debug("Disabling all plugins");
            this.pluginManager.disablePlugins();

            log.debug("Removing event handlers");
            HandlerList.unregisterAll();

            log.debug("Saving scoreboards data");
            this.scoreboardManager.save();

            log.debug("Stopping all tasks");
            this.scheduler.cancelAllTasks();
            this.scheduler.mainThreadHeartbeat((int) (this.getNextTick() + 10000));

            log.debug("Unloading all levels");
            for (Level level : this.levelArray) {
                this.unloadLevel(level, true);
            }

            if (positionTrackingService != null) {
                log.debug("Closing position tracking service");
                positionTrackingService.close();
            }

            log.debug("Closing console");
            this.consoleThread.interrupt();

            log.debug("Stopping network interfaces");
            network.shutdown();
            playerDataDB.close();
            //close watchdog and metrics
            if (this.watchdog != null) {
                this.watchdog.running = false;
            }
            NukkitMetrics.closeNow(this);
            //close threadPool
            ForkJoinPool.commonPool().shutdownNow();
            this.computeThreadPool.shutdownNow();
            //todo other things
        } catch (Exception e) {
            log.error("Exception happened while shutting down, exiting the process", e);
            System.exit(1);
        }
    }

    public void start() {
        for (BanEntry entry : this.getIPBans().getEntires().values()) {
            try {
                this.network.blockAddress(InetAddress.getByName(entry.getName()));
            } catch (UnknownHostException ignore) {
            }
        }
        this.tickCounter = 0;

        log.info(this.getLanguage().tr("nukkit.server.defaultGameMode", getGamemodeString(this.getGamemode())));
        log.info(this.getLanguage().tr("nukkit.server.networkStart", TextFormat.YELLOW + (this.getIp().isEmpty() ? "*" : this.getIp()), TextFormat.YELLOW + String.valueOf(this.getPort())));
        log.info(this.getLanguage().tr("nukkit.server.startFinished", String.valueOf((double) (System.currentTimeMillis() - Nukkit.START_TIME) / 1000)));

        ServerStartedEvent serverStartedEvent = new ServerStartedEvent();
        getPluginManager().callEvent(serverStartedEvent);
        this.tickProcessor();
        this.forceShutdown();
    }

    public void tickProcessor() {
        getScheduler().scheduleDelayedTask(InternalPlugin.INSTANCE, new Task() {
            @Override
            public void onRun(int currentTick) {
                System.gc();
            }
        }, 60);

        this.nextTick = System.currentTimeMillis();
        try {
            while (this.isRunning.get()) {
                try {
                    this.tick();

                    long next = this.nextTick;
                    long current = System.currentTimeMillis();

                    if (next - 0.1 > current) {
                        long allocated = next - current - 1;
                        if (allocated > 0) {
                            //noinspection BusyWait
                            Thread.sleep(allocated, 900000);
                        }
                    }
                } catch (RuntimeException e) {
                    log.error("A RuntimeException happened while ticking the server", e);
                }
            }
        } catch (Throwable e) {
            log.error("Exception happened while ticking server\n{}", Utils.getAllThreadDumps(), e);
        }
    }

    private void checkTickUpdates(int currentTick, long tickTime) {
        if (getSettings().levelSettings().alwaysTickPlayers()) {
            for (Player p : new ArrayList<>(this.players.values())) {
                p.onUpdate(currentTick);
            }
        }

        int baseTickRate = getSettings().levelSettings().baseTickRate();
        //Do level ticks
        for (Level level : this.getLevels().values()) {
            if (level.getTickRate() > baseTickRate && --level.tickRateCounter > 0) {
                continue;
            }

            try {
                long levelTime = System.currentTimeMillis();
                //Ensures that the server won't try to tick a level without providers.
                if (level.getProvider().getLevel() == null) {
                    log.warn("Tried to tick Level " + level.getName() + " without a provider!");
                    continue;
                }
                level.doTick(currentTick);
                int tickMs = (int) (System.currentTimeMillis() - levelTime);
                level.tickRateTime = tickMs;
                if ((currentTick & 511) == 0) { // % 511
                    level.tickRateOptDelay = level.recalcTickOptDelay();
                }

                if (getSettings().levelSettings().autoTickRate()) {
                    if (tickMs < 50 && level.getTickRate() > baseTickRate) {
                        int r;
                        level.setTickRate(r = level.getTickRate() - 1);
                        if (r > baseTickRate) {
                            level.tickRateCounter = level.getTickRate();
                        }
                        log.debug("Raising level \"{}\" tick rate to {} ticks", level.getName(), level.getTickRate());
                    } else if (tickMs >= 50) {
                        int autoTickRateLimit = getSettings().levelSettings().autoTickRateLimit();
                        if (level.getTickRate() == baseTickRate) {
                            level.setTickRate(Math.max(baseTickRate + 1, Math.min(autoTickRateLimit, tickMs / 50)));
                            log.debug("Level \"{}\" took {}ms, setting tick rate to {} ticks", level.getName(), NukkitMath.round(tickMs, 2), level.getTickRate());
                        } else if ((tickMs / level.getTickRate()) >= 50 && level.getTickRate() < autoTickRateLimit) {
                            level.setTickRate(level.getTickRate() + 1);
                            log.debug("Level \"{}\" took {}ms, setting tick rate to {} ticks", level.getName(), NukkitMath.round(tickMs, 2), level.getTickRate());
                        }
                        level.tickRateCounter = level.getTickRate();
                    }
                }
            } catch (Exception e) {
                log.error(this.getLanguage().tr("nukkit.level.tickError",
                        level.getFolderPath(), Utils.getExceptionMessage(e)), e);
            }
        }
    }

    public void doAutoSave() {
        if (this.getAutoSave()) {
            for (Player player : new ArrayList<>(this.players.values())) {
                if (player.isOnline()) {
                    player.save(true);
                } else if (!player.isConnected()) {
                    this.removePlayer(player);
                }
            }

            for (Level level : this.levelArray) {
                level.save();
            }
            this.getScoreboardManager().save();
        }
    }

    private void tick() {
        long tickTime = System.currentTimeMillis();

        long time = tickTime - this.nextTick;
        if (time < -25) {
            try {
                Thread.sleep(Math.max(5, -time - 25));
            } catch (InterruptedException e) {
                log.debug("The thread {} got interrupted", Thread.currentThread().getName(), e);
            }
        }

        long tickTimeNano = System.nanoTime();
        if ((tickTime - this.nextTick) < -25) {
            return;
        }

        ++this.tickCounter;
        this.network.processInterfaces();

        if (this.rcon != null) {
            this.rcon.check();
        }

        this.getScheduler().mainThreadHeartbeat(this.tickCounter);

        this.checkTickUpdates(this.tickCounter, tickTime);

        for (Player player : this.players.values()) {
            player.checkNetwork();
        }

        if ((this.tickCounter & 0b1111) == 0) {
            this.titleTick();
            this.maxTick = 20;
            this.maxUse = 0;

            if ((this.tickCounter & 0b111111111) == 0) {
                try {
                    this.getPluginManager().callEvent(this.queryRegenerateEvent = new QueryRegenerateEvent(this, 5));
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }

        if (this.autoSave && ++this.autoSaveTicker >= this.autoSaveTicks) {
            this.autoSaveTicker = 0;
            this.doAutoSave();
        }

        if (this.sendUsageTicker > 0 && --this.sendUsageTicker == 0) {
            this.sendUsageTicker = 6000;
            //todo sendUsage
        }

        // Handling freezable arrays
        int freezableArrayCompressTime = (int) (50 - (System.currentTimeMillis() - tickTime));
        if (freezableArrayCompressTime > 4) {
            getFreezableArrayManager().setMaxCompressionTime(freezableArrayCompressTime).tick();
        }

        long nowNano = System.nanoTime();
        float tick = (float) Math.min(20, 1000000000 / Math.max(1000000, ((double) nowNano - tickTimeNano)));
        float use = (float) Math.min(1, ((double) (nowNano - tickTimeNano)) / 50000000);

        if (this.maxTick > tick) {
            this.maxTick = tick;
        }

        if (this.maxUse < use) {
            this.maxUse = use;
        }

        System.arraycopy(this.tickAverage, 1, this.tickAverage, 0, this.tickAverage.length - 1);
        this.tickAverage[this.tickAverage.length - 1] = tick;

        System.arraycopy(this.useAverage, 1, this.useAverage, 0, this.useAverage.length - 1);
        this.useAverage[this.useAverage.length - 1] = use;

        if ((this.nextTick - tickTime) < -1000) {
            this.nextTick = tickTime;
        } else {
            this.nextTick += 50;
        }

    }

    public long getNextTick() {
        return nextTick;
    }

    /**
     * @return Returns the number of ticks recorded by the server
     */
    public int getTick() {
        return tickCounter;
    }

    public float getTicksPerSecond() {
        return ((float) Math.round(this.maxTick * 100)) / 100;
    }

    public float getTicksPerSecondAverage() {
        float sum = 0;
        int count = this.tickAverage.length;
        for (float aTickAverage : this.tickAverage) {
            sum += aTickAverage;
        }
        return (float) NukkitMath.round(sum / count, 2);
    }

    public float getTickUsage() {
        return (float) NukkitMath.round(this.maxUse * 100, 2);
    }

    public float getTickUsageAverage() {
        float sum = 0;
        int count = this.useAverage.length;
        for (float aUseAverage : this.useAverage) {
            sum += aUseAverage;
        }
        return ((float) Math.round(sum / count * 100)) / 100;
    }

    // TODO: Fix title tick
    public void titleTick() {
        if (!Nukkit.ANSI || !Nukkit.TITLE) {
            return;
        }

        Runtime runtime = Runtime.getRuntime();
        double used = NukkitMath.round((double) (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024, 2);
        double max = NukkitMath.round(((double) runtime.maxMemory()) / 1024 / 1024, 2);
        String usage = Math.round(used / max * 100) + "%";
        String title = (char) 0x1b + "]0;" + this.getName() + " "
                + this.getNukkitVersion()
                + " | " + this.getGitCommit()
                + " | Online " + this.players.size() + "/" + this.getMaxPlayers()
                + " | Memory " + usage;
        if (!Nukkit.shortTitle) {
            title += " | U " + NukkitMath.round((this.network.getUpload() / 1024 * 1000), 2)
                    + " D " + NukkitMath.round((this.network.getDownload() / 1024 * 1000), 2) + " kB/s";
        }
        title += " | TPS " + this.getTicksPerSecond()
                + " | Load " + this.getTickUsage() + "%" + (char) 0x07;

        System.out.print(title);
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    /**
     * Sets the server to a busy state, which can prevent related code from considering the server as unresponsive.
     * Please remember to clear it after setting.
     *
     * @param busyTime Time in milliseconds
     * @return id
     */
    public int addBusying(long busyTime) {
        this.busyingTime.add(busyTime);
        return this.busyingTime.size() - 1;
    }

    public void removeBusying(int index) {
        this.busyingTime.removeLong(index);
    }

    public long getBusyingTime() {
        if (this.busyingTime.isEmpty()) {
            return -1;
        }
        return this.busyingTime.getLong(this.busyingTime.size() - 1);
    }

    public TickingAreaManager getTickingAreaManager() {
        return tickingAreaManager;
    }

    public long getLaunchTime() {
        return launchTime;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Server Singleton Region] ─────────
    // ─────────────────────────────────────────────────────

    public static Server getInstance() {
        return instance;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Chat & Commands Region] ──────────
    // ─────────────────────────────────────────────────────

    /**
     * Broadcast a message to all players.
     *
     * @param message The message
     * @return int Number of players
     */
    public int broadcastMessage(String message) {
        return this.broadcast(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * @see #broadcastMessage(String)
     */
    public int broadcastMessage(TextContainer message) {
        return this.broadcast(message, BROADCAST_CHANNEL_USERS);
    }

    /**
     * Broadcast a message to the specified {@link CommandSender recipients}.
     *
     * @param message The message
     * @return int Number of {@link CommandSender recipients}
     */
    public int broadcastMessage(String message, CommandSender[] recipients) {
        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.length;
    }

    /**
     * @see #broadcastMessage(String, CommandSender[])
     */
    public int broadcastMessage(String message, Collection<? extends CommandSender> recipients) {
        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    /**
     * @see #broadcastMessage(String, CommandSender[])
     */
    public int broadcastMessage(TextContainer message, Collection<? extends CommandSender> recipients) {
        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    /**
     * Get the sender to broadcast a message from the specified permission name, multiple permissions can be specified, split by <b> ; </b><br>
     * The permission corresponds to a {@link CommandSender Sender} set in {@code PluginManager#permSubs}.
     *
     * @param message     Message content
     * @param permissions Permissions name, need to register first through {@link PluginManager#subscribeToPermission subscribeToPermission}
     * @return int Number of {@link CommandSender senders} who received the message
     */
    public int broadcast(String message, String permissions) {
        Set<CommandSender> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                    recipients.add((CommandSender) permissible);
                }
            }
        }

        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    /**
     * @see #broadcast(String, String)
     */
    public int broadcast(TextContainer message, String permissions) {
        Set<CommandSender> recipients = new HashSet<>();

        for (String permission : permissions.split(";")) {
            for (Permissible permissible : this.pluginManager.getPermissionSubscriptions(permission)) {
                if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                    recipients.add((CommandSender) permissible);
                }
            }
        }

        for (CommandSender recipient : recipients) {
            recipient.sendMessage(message);
        }

        return recipients.size();
    }

    /**
     * Execute one line of command as sender.
     *
     * @param sender      Command executor
     * @param commandLine One line of command
     * @return Returns 0 for failed execution, greater than or equal to 1 for successful execution
     * @throws ServerException Server exception
     */
    public int executeCommand(CommandSender sender, String commandLine) throws ServerException {
        // First we need to check if this command is on the main thread or not, if not, warn the user
        if (!this.isPrimaryThread()) {
            log.warn("Command Dispatched Async: {}\nPlease notify author of plugin causing this execution to fix this bug!", commandLine,
                    new ConcurrentModificationException("Command Dispatched Async: " + commandLine));

            this.scheduler.scheduleTask(null, () -> executeCommand(sender, commandLine));
            return 1;
        }
        if (sender == null) {
            throw new ServerException("CommandSender is not valid");
        }
        //pre
        var cmd = commandLine.stripLeading();
        cmd = cmd.charAt(0) == '/' ? cmd.substring(1) : cmd;

        return this.commandMap.executeCommand(sender, cmd);
    }

    /**
     * Execute these commands silently as the console, ignoring permissions.
     *
     * @param commands the commands
     * @throws ServerException Server exception
     */
    public void silentExecuteCommand(String... commands) {
        this.silentExecuteCommand(null, commands);
    }

    /**
     * Execute these commands silently as this player, ignoring permissions.
     *
     * @param sender   command sender
     * @param commands the commands
     * @throws ServerException server exception
     */
    public void silentExecuteCommand(@Nullable Player sender, String... commands) {
        final var revert = new ArrayList<Level>();
        final var server = Server.getInstance();
        for (var level : server.getLevels().values()) {
            if (level.getGameRules().getBoolean(GameRule.SEND_COMMAND_FEEDBACK)) {
                level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
                revert.add(level);
            }
        }
        if (sender == null) {
            for (var cmd : commands) {
                server.executeCommand(server.getConsoleSender(), cmd);
            }
        } else {
            for (var cmd : commands) {
                server.executeCommand(server.getConsoleSender(), "execute as " + "\"" + sender.getName() + "\" run " + cmd);
            }
        }

        for (var level : revert) {
            level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
        }
    }

    /**
     * Get the console sender
     *
     * @return {@link ConsoleCommandSender}
     */
    //todo: use ticker to check console
    public ConsoleCommandSender getConsoleSender() {
        return consoleSender;
    }

    public SimpleCommandMap getCommandMap() {
        return commandMap;
    }

    public PluginIdentifiableCommand getPluginCommand(String name) {
        Command command = this.commandMap.getCommand(name);
        if (command instanceof PluginIdentifiableCommand) {
            return (PluginIdentifiableCommand) command;
        } else {
            return null;
        }
    }

    public IScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public FunctionManager getFunctionManager() {
        return functionManager;
    }

    // endregion

    // region networking

    /**
     * @see #broadcastPacket(Player[], DataPacket)
     */
    public static void broadcastPacket(Collection<Player> players, DataPacket packet) {
        for (Player player : players) {
            player.dataPacket(packet);
        }
    }

    /**
     * Broadcast a packet to the specified players.
     *
     * @param players All players receiving the data package
     * @param packet  Data packet
     */
    public static void broadcastPacket(Player[] players, DataPacket packet) {
        for (Player player : players) {
            player.dataPacket(packet);
        }
    }

    public QueryRegenerateEvent getQueryInformation() {
        return this.queryRegenerateEvent;
    }

    public Network getNetwork() {
        return network;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Plugins Region] ──────────────────
    // ─────────────────────────────────────────────────────

    /**
     * Enable plugins in the specified plugin loading order
     *
     * @param type Plugin loading order
     */
    public void enablePlugins(PluginLoadOrder type) {
        for (Plugin plugin : new ArrayList<>(this.pluginManager.getPlugins().values())) {
            if (!plugin.isEnabled() && type == plugin.getDescription().getOrder()) {
                this.enablePlugin(plugin);
            }
        }

        if (type == PluginLoadOrder.POSTWORLD) {
            DefaultPermissions.registerCorePermissions();
        }
    }

    /**
     * Enable a specified plugin
     *
     * @param plugin Plugin instance
     */
    public void enablePlugin(Plugin plugin) {
        this.pluginManager.enablePlugin(plugin);
    }

    /**
     * Disable all plugins
     */
    public void disablePlugins() {
        this.pluginManager.disablePlugins();
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Players Region] ──────────────────
    // ─────────────────────────────────────────────────────

    public void onPlayerCompleteLoginSequence(Player player) {
        this.sendFullPlayerListData(player);
    }

    public void onPlayerLogin(InetSocketAddress socketAddress, Player player) {
        PlayerLoginEvent ev;
        this.getPluginManager().callEvent(ev = new PlayerLoginEvent(player, "Plugin reason"));
        if (ev.isCancelled()) {
            player.close(player.getLeaveMessage(), ev.getKickMessage());
            return;
        }

        this.players.put(socketAddress, player);
        if (this.sendUsageTicker > 0) {
            this.uniquePlayers.add(player.getUniqueId());
        }
    }

    @ApiStatus.Internal
    public void addOnlinePlayer(Player player) {
        this.playerList.put(player.getUniqueId(), player);
        this.updatePlayerListData(player.getUniqueId(), player.getId(), player.getDisplayName(), player.getSkin(), player.getLoginChainData().getXUID());
        this.getNetwork().getPong().playerCount(playerList.size()).update();
    }

    @ApiStatus.Internal
    public void removeOnlinePlayer(Player player) {
        if (this.playerList.containsKey(player.getUniqueId())) {
            this.playerList.remove(player.getUniqueId());

            PlayerListPacket pk = new PlayerListPacket();
            pk.type = PlayerListPacket.TYPE_REMOVE;
            pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(player.getUniqueId())};

            Server.broadcastPacket(this.playerList.values(), pk);
            this.getNetwork().getPong().playerCount(playerList.size()).update();
            ;
        }
    }

    /**
     * @see #updatePlayerListData(UUID, long, String, Skin, String, Player[])
     */
    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin) {
        this.updatePlayerListData(uuid, entityId, name, skin, "", this.playerList.values());
    }

    /**
     * @see #updatePlayerListData(UUID, long, String, Skin, String, Player[])
     */
    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, String xboxUserId) {
        this.updatePlayerListData(uuid, entityId, name, skin, xboxUserId, this.playerList.values());
    }

    /**
     * @see #updatePlayerListData(UUID, long, String, Skin, String, Player[])
     */
    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, Player[] players) {
        this.updatePlayerListData(uuid, entityId, name, skin, "", players);
    }

    /**
     * Update {@link PlayerListPacket} data packets (i.e. player list data) for specified players
     *
     * @param uuid       uuid
     * @param entityId   entity id
     * @param name       name
     * @param skin       skin
     * @param xboxUserId xbox user id
     * @param players    specified players to receive the data packet
     */
    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, String xboxUserId, Player[] players) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_ADD;
        pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(uuid, entityId, name, skin, xboxUserId)};
        Server.broadcastPacket(players, pk);
    }

    /**
     * @see #updatePlayerListData(UUID, long, String, Skin, String, Player[])
     */
    public void updatePlayerListData(UUID uuid, long entityId, String name, Skin skin, String xboxUserId, Collection<Player> players) {
        this.updatePlayerListData(uuid, entityId, name, skin, xboxUserId, players.toArray(Player.EMPTY_ARRAY));
    }

    public void removePlayerListData(UUID uuid) {
        this.removePlayerListData(uuid, this.playerList.values());
    }

    /**
     * Remove player list data for all players in the array.
     *
     * @param players Array of players
     */
    public void removePlayerListData(UUID uuid, Player[] players) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_REMOVE;
        pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(uuid)};
        Server.broadcastPacket(players, pk);
    }

    /**
     * Remove this player's player list data.
     *
     * @param player Player
     */

    public void removePlayerListData(UUID uuid, Player player) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_REMOVE;
        pk.entries = new PlayerListPacket.Entry[]{new PlayerListPacket.Entry(uuid)};
        player.dataPacket(pk);
    }

    public void removePlayerListData(UUID uuid, Collection<Player> players) {
        this.removePlayerListData(uuid, players.toArray(Player.EMPTY_ARRAY));
    }

    /**
     * Send a player list packet to a player.
     *
     * @param player Player
     */
    public void sendFullPlayerListData(Player player) {
        PlayerListPacket pk = new PlayerListPacket();
        pk.type = PlayerListPacket.TYPE_ADD;
        pk.entries = this.playerList.values().stream()
                .map(p -> new PlayerListPacket.Entry(
                        p.getUniqueId(),
                        p.getId(),
                        p.getDisplayName(),
                        p.getSkin(),
                        p.getLoginChainData().getXUID()))
                .toArray(PlayerListPacket.Entry[]::new);

        player.dataPacket(pk);
    }

    /**
     * Get the player instance from the specified UUID.
     *
     * @param uuid uuid
     * @return Player instance, can be null
     */
    public Optional<Player> getPlayer(UUID uuid) {
        Preconditions.checkNotNull(uuid, "uuid");
        return Optional.ofNullable(playerList.get(uuid));
    }

    /**
     * Find the UUID corresponding to the specified player name from the database.
     *
     * @param name player name
     * @return The player's UUID, which can be empty.
     */
    public Optional<UUID> lookupName(String name) {
        byte[] nameBytes = name.toLowerCase(Locale.ENGLISH).getBytes(StandardCharsets.UTF_8);
        byte[] uuidBytes = playerDataDB.get(nameBytes);
        if (uuidBytes == null) {
            return Optional.empty();
        }

        if (uuidBytes.length != 16) {
            log.warn("Invalid uuid in name lookup database detected! Removing");
            playerDataDB.delete(nameBytes);
            return Optional.empty();
        }

        ByteBuffer buffer = ByteBuffer.wrap(uuidBytes);
        return Optional.of(new UUID(buffer.getLong(), buffer.getLong()));
    }

    /**
     * Update the UUID of the specified player name in the database, or add it if it does not exist.
     *
     * @param info the player info
     */
    void updateName(PlayerInfo info) {
        var uniqueId = info.getUniqueId();
        var name = info.getUsername();

        byte[] nameBytes = name.toLowerCase(Locale.ENGLISH).getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uniqueId.getMostSignificantBits());
        buffer.putLong(uniqueId.getLeastSignificantBits());
        byte[] array = buffer.array();
        byte[] bytes = playerDataDB.get(array);
        if (bytes == null) {
            playerDataDB.put(nameBytes, array);
        }
        boolean xboxAuthEnabled = this.properties.get(ServerPropertiesKeys.XBOX_AUTH, false);
        if (info instanceof XboxLivePlayerInfo || !xboxAuthEnabled) {
            playerDataDB.put(nameBytes, array);
        }
    }

    public IPlayer getOfflinePlayer(final String name) {
        IPlayer result = this.getPlayerExact(name.toLowerCase(Locale.ENGLISH));
        if (result != null) {
            return result;
        }

        return lookupName(name).map(uuid -> new OfflinePlayer(this, uuid))
                .orElse(new OfflinePlayer(this, name));
    }

    /**
     * Get a player instance from the specified UUID, either online or offline.
     *
     * @param uuid uuid
     * @return player
     */
    public IPlayer getOfflinePlayer(UUID uuid) {
        Preconditions.checkNotNull(uuid, "uuid");
        Optional<Player> onlinePlayer = getPlayer(uuid);
        if (onlinePlayer.isPresent()) {
            return onlinePlayer.get();
        }

        return new OfflinePlayer(this, uuid);
    }

    /**
     * create is false
     *
     * @see #getOfflinePlayerData(UUID, boolean)
     */
    public CompoundTag getOfflinePlayerData(UUID uuid) {
        return getOfflinePlayerData(uuid, false);
    }

    /**
     * Get the NBT data of the player specified by UUID
     *
     * @param uuid   UUID of the player to get data from
     * @param create If player data does not exist whether to create.
     * @return {@link CompoundTag}
     */
    public CompoundTag getOfflinePlayerData(UUID uuid, boolean create) {
        return getOfflinePlayerDataInternal(uuid, create);
    }

    public CompoundTag getOfflinePlayerData(String name) {
        return getOfflinePlayerData(name, false);
    }

    public CompoundTag getOfflinePlayerData(String name, boolean create) {
        Optional<UUID> uuid = lookupName(name);
        if (uuid.isEmpty()) {
            log.warn("Invalid uuid in name lookup database detected! Removing");
            playerDataDB.delete(name.getBytes(StandardCharsets.UTF_8));
            return null;
        }
        return getOfflinePlayerDataInternal(uuid.get(), create);
    }

    public boolean hasOfflinePlayerData(String name) {
        Optional<UUID> uuid = lookupName(name);
        if (uuid.isEmpty()) {
            log.warn("Invalid uuid in name lookup database detected! Removing");
            playerDataDB.delete(name.getBytes(StandardCharsets.UTF_8));
            return false;
        }
        return hasOfflinePlayerData(uuid.get());
    }

    public boolean hasOfflinePlayerData(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        byte[] bytes = playerDataDB.get(buffer.array());
        return bytes != null;
    }

    private CompoundTag getOfflinePlayerDataInternal(UUID uuid, boolean create) {
        if (uuid == null) {
            log.error("UUID is empty, cannot query player data");
            return null;
        }
        try {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            byte[] bytes = playerDataDB.get(buffer.array());
            if (bytes != null) {
                return NBTIO.readCompressed(bytes);
            }
        } catch (IOException e) {
            log.warn(this.getLanguage().tr("nukkit.data.playerCorrupted", uuid), e);
        }

        if (create) {
            if (this.getSettings().playerSettings().savePlayerData()) {
                log.info(this.getLanguage().tr("nukkit.data.playerNotFound", uuid));
            }
            Position spawn = this.getDefaultLevel().getSafeSpawn();
            CompoundTag nbt = new CompoundTag()
                    .putLong("firstPlayed", System.currentTimeMillis() / 1000)
                    .putLong("lastPlayed", System.currentTimeMillis() / 1000)
                    .putList("Pos", new ListTag<DoubleTag>()
                            .add(new DoubleTag(spawn.x))
                            .add(new DoubleTag(spawn.y))
                            .add(new DoubleTag(spawn.z)))
                    .putString("Level", this.getDefaultLevel().getName())
                    .putList("Inventory", new ListTag<>())
                    .putCompound("Achievements", new CompoundTag())
                    .putInt("playerGameType", this.getGamemode())
                    .putList("Motion", new ListTag<DoubleTag>()
                            .add(new DoubleTag(0))
                            .add(new DoubleTag(0))
                            .add(new DoubleTag(0)))
                    .putList("Rotation", new ListTag<FloatTag>()
                            .add(new FloatTag(0))
                            .add(new FloatTag(0)))
                    .putFloat("FallDistance", 0)
                    .putShort("Fire", 0)
                    .putShort("Air", 300)
                    .putBoolean("OnGround", true)
                    .putBoolean("Invulnerable", false);

            this.saveOfflinePlayerData(uuid, nbt, true);
            return nbt;
        } else {
            log.error("Player {} does not exist and cannot read playerdata", uuid);
            return null;
        }
    }

    /**
     * @see #saveOfflinePlayerData(String, CompoundTag, boolean)
     */
    public void saveOfflinePlayerData(UUID uuid, CompoundTag tag) {
        this.saveOfflinePlayerData(uuid, tag, false);
    }

    /**
     * @see #saveOfflinePlayerData(String, CompoundTag, boolean)
     */
    public void saveOfflinePlayerData(UUID uuid, CompoundTag tag, boolean async) {
        this.saveOfflinePlayerData(uuid.toString(), tag, async);
    }

    /**
     * @see #saveOfflinePlayerData(String, CompoundTag, boolean)
     */
    public void saveOfflinePlayerData(String name, CompoundTag tag) {
        this.saveOfflinePlayerData(name, tag, false);
    }

    /**
     * Save player data, players can be offline.
     *
     * @param nameOrUUid the name or uuid
     * @param tag        NBT data
     * @param async      Whether to save asynchronously
     */
    public void saveOfflinePlayerData(String nameOrUUid, CompoundTag tag, boolean async) {
        UUID uuid = lookupName(nameOrUUid).orElse(UUID.fromString(nameOrUUid));
        if (this.getSettings().playerSettings().savePlayerData()) {
            this.getScheduler().scheduleTask(InternalPlugin.INSTANCE, new Task() {
                final AtomicBoolean hasRun = new AtomicBoolean(false);

                @Override
                public void onRun(int currentTick) {
                    this.onCancel();
                }

                //doing it like this ensures that the playerdata will be saved in a server shutdown
                @Override
                public void onCancel() {
                    if (!hasRun.getAndSet(true)) {
                        saveOfflinePlayerDataInternal(tag, uuid);
                    }
                }
            }, async);
        }
    }

    private void saveOfflinePlayerDataInternal(CompoundTag tag, UUID uuid) {
        try {
            byte[] bytes = NBTIO.writeGZIPCompressed(tag, ByteOrder.BIG_ENDIAN);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            playerDataDB.put(buffer.array(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get an online player from the player name, this method is a fuzzy match and will be returned as long as the player name has the name prefix.
     *
     * @param name player name
     * @return Player instance object, failed to get null
     */
    public Player getPlayer(String name) {
        Player found = null;
        name = name.toLowerCase(Locale.ENGLISH);
        int delta = Integer.MAX_VALUE;
        for (Player player : this.getOnlinePlayers().values()) {
            if (player.getName().toLowerCase(Locale.ENGLISH).startsWith(name)) {
                int curDelta = player.getName().length() - name.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) {
                    break;
                }
            }
        }

        return found;
    }

    /**
     * Get an online player from a player name, this method is an exact match and returns when the player name string is identical.
     *
     * @param name player name
     * @return Player instance object, failed to get null
     */
    public Player getPlayerExact(String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        for (Player player : this.getOnlinePlayers().values()) {
            if (player.getName().toLowerCase(Locale.ENGLISH).equals(name)) {
                return player;
            }
        }

        return null;
    }

    /**
     * Specify a partial player name and return all players with or equal to that name.
     *
     * @param partialName partial name
     * @return All players matched, if not matched then an empty array
     */
    public Player[] matchPlayer(String partialName) {
        partialName = partialName.toLowerCase(Locale.ENGLISH);
        List<Player> matchedPlayer = new ArrayList<>();
        for (Player player : this.getOnlinePlayers().values()) {
            if (player.getName().toLowerCase(Locale.ENGLISH).equals(partialName)) {
                return new Player[]{player};
            } else if (player.getName().toLowerCase(Locale.ENGLISH).contains(partialName)) {
                matchedPlayer.add(player);
            }
        }

        return matchedPlayer.toArray(Player.EMPTY_ARRAY);
    }

    @ApiStatus.Internal
    public void removePlayer(Player player) {
        Player toRemove = this.players.remove(player.getRawSocketAddress());
        if (toRemove != null) {
            return;
        }

        for (InetSocketAddress socketAddress : new ArrayList<>(this.players.keySet())) {
            Player p = this.players.get(socketAddress);
            if (player == p) {
                this.players.remove(socketAddress);
                break;
            }
        }
    }

    /**
     * Get all online players Map.
     *
     * @return All online players Map
     */
    public Map<UUID, Player> getOnlinePlayers() {
        return ImmutableMap.copyOf(playerList);
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Information Region] ──────────────
    // ─────────────────────────────────────────────────────

    /**
     * @return The name of server
     */
    public String getName() {
        return "PowerNukkitX";
    }

    public String getNukkitVersion() {
        return Nukkit.VERSION;
    }

    public String getBStatsNukkitVersion() {
        return Nukkit.VERSION;
    }

    public String getGitCommit() {
        return Nukkit.GIT_COMMIT;
    }

    public String getCodename() {
        return Nukkit.CODENAME;
    }

    public String getVersion() {
        return ProtocolInfo.MINECRAFT_VERSION;
    }

    public String getApiVersion() {
        return Nukkit.API_VERSION;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [File Region] ─────────────────────
    // ─────────────────────────────────────────────────────

    public String getFilePath() {
        return filePath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    /**
     * @return server UUID
     */
    public UUID getServerUniqueId() {
        return this.serverID;
    }

    public MainLogger getLogger() {
        return MainLogger.getLogger();
    }

    public EntityMetadataStore getEntityMetadata() {
        return entityMetadata;
    }

    public PlayerMetadataStore getPlayerMetadata() {
        return playerMetadata;
    }

    public LevelMetadataStore getLevelMetadata() {
        return levelMetadata;
    }

    public ResourcePackManager getResourcePackManager() {
        return resourcePackManager;
    }

    public FreezableArrayManager getFreezableArrayManager() {
        return freezableArrayManager;
    }

    @NotNull
    public PositionTrackingService getPositionTrackingService() {
        return positionTrackingService;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Crafting & Recipe Region] ────────
    // ─────────────────────────────────────────────────────

    /**
     * Send a recipe list packet to a player.
     *
     * @param player Player
     */
    public void sendRecipeList(Player player) {
        player.getSession().sendRawPacket(ProtocolInfo.CRAFTING_DATA_PACKET, Registries.RECIPE.getCraftingPacket());
    }

    /**
     * Register Recipe to Recipe Manager
     *
     * @param recipe Recipe
     */
    public void addRecipe(Recipe recipe) {
        Registries.RECIPE.register(recipe);
    }

    public RecipeRegistry getRecipeRegistry() {
        return Registries.RECIPE;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Levels Region] ───────────────────
    // ─────────────────────────────────────────────────────

    /**
     * @return Get all the game world
     */
    public Map<Integer, Level> getLevels() {
        return levels;
    }

    /**
     * @return Get the default overworld
     */
    public Level getDefaultLevel() {
        return defaultLevel;
    }

    /**
     * Set default overworld
     */
    public void setDefaultLevel(Level defaultLevel) {
        if (defaultLevel == null || (this.isLevelLoaded(defaultLevel.getName()) && defaultLevel != this.defaultLevel)) {
            this.defaultLevel = defaultLevel;
        }
    }

    /**
     * @return Get the default nether
     */
    public Level getDefaultNetherLevel() {
        return defaultNether;
    }

    /**
     * Set default nether
     */
    public void setDefaultNetherLevel(Level defaultLevel) {
        if (defaultLevel == null || (this.isLevelLoaded(defaultLevel.getName()) && defaultLevel != this.defaultNether)) {
            this.defaultNether = defaultLevel;
        }
    }

    /**
     * @return Get the default the_end level
     */
    public Level getDefaultEndLevel() {
        return defaultLevel;
    }

    /**
     * Set default the_end level
     */
    public void setDefaultEndLevel(Level defaultLevel) {
        if (defaultLevel == null || (this.isLevelLoaded(defaultLevel.getName()) && defaultLevel != this.defaultEnd)) {
            this.defaultEnd = defaultLevel;
        }
    }

    public static final String levelDimPattern = "^.*Dim[0-9]$";

    /**
     * @param name World name
     * @return Whether the world is already loaded
     */
    public boolean isLevelLoaded(String name) {
        if (!name.matches(levelDimPattern)) {
            for (int i = 0; i < 3; i++) {
                if (this.getLevelByName(name + " Dim" + i) != null) {
                    return true;
                }
            }
            return false;
        } else {
            return this.getLevelByName(name) != null;
        }
    }

    /**
     * Get world from world id, 0 OVERWORLD 1 NETHER 2 THE_END
     *
     * @param levelId world id
     * @return level instance
     */
    public Level getLevel(int levelId) {
        if (this.levels.containsKey(levelId)) {
            return this.levels.get(levelId);
        }
        return null;
    }

    /**
     * Get world from world name, {@code overworld nether the_end}
     *
     * @param name world name
     * @return level instance
     */
    public Level getLevelByName(String name) {
        if (!name.matches(levelDimPattern)) {
            name = name + " Dim0";
        }
        for (Level level : this.levelArray) {
            if (level.getName().equalsIgnoreCase(name)) {
                return level;
            }
        }
        return null;
    }

    public boolean unloadLevel(Level level) {
        return this.unloadLevel(level, false);
    }

    /**
     * Unload level
     *
     * @param level       Level
     * @param forceUnload Whether to force unload.
     * @return Whether the unload was successful
     */
    public boolean unloadLevel(Level level, boolean forceUnload) {
        if (level == this.getDefaultLevel() && !forceUnload) {
            throw new IllegalStateException("The default level cannot be unloaded while running, please switch levels.");
        }

        return level.unload(forceUnload);

    }

    @Nullable
    public LevelConfig getLevelConfig(String levelFolderName) {
        if (Objects.equals(levelFolderName.trim(), "")) {
            throw new LevelException("Invalid empty level name");
        }
        String path;
        if (levelFolderName.contains("/") || levelFolderName.contains("\\")) {
            path = levelFolderName;
        } else {
            path = new File(this.getDataPath(), "worlds/" + levelFolderName).getAbsolutePath();
        }
        Path jpath = Path.of(path);
        path = jpath.toString();
        if (!jpath.toFile().exists()) {
            log.warn(this.getLanguage().tr("nukkit.level.notFound", levelFolderName));
            return null;
        }

        File config = jpath.resolve("config.json").toFile();
        LevelConfig levelConfig;
        if (config.exists()) {
            try {
                levelConfig = JSONUtils.from(config, LevelConfig.class);
                FileUtils.write(config, JSONUtils.toPretty(levelConfig), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            //verify the provider
            Class<? extends LevelProvider> provider = LevelProviderManager.getProvider(path);
            if (provider == null) {
                log.error(this.getLanguage().tr("nukkit.level.loadError", levelFolderName, "Unknown provider"));
                return null;
            }
            Map<Integer, LevelConfig.GeneratorConfig> map = new HashMap<>();
            //todo nether the_end overworld
            map.put(0, new LevelConfig.GeneratorConfig("flat", System.currentTimeMillis(), false, LevelConfig.AntiXrayMode.LOW, true, DimensionEnum.OVERWORLD.getDimensionData(), Collections.emptyMap()));
            levelConfig = new LevelConfig(LevelProviderManager.getProviderName(provider), true, map);
            try {
                config.createNewFile();
                FileUtils.write(config, JSONUtils.toPretty(levelConfig), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return levelConfig;
    }

    /**
     * @param levelFolderName the level folder name
     * @return whether load success
     */
    public boolean loadLevel(String levelFolderName) {
        if (levelFolderName.matches(levelDimPattern)) {
            levelFolderName = levelFolderName.replaceFirst("\\sDim\\d$", "");
        }
        LevelConfig levelConfig = getLevelConfig(levelFolderName);
        if (levelConfig == null) return false;

        String path;
        if (levelFolderName.contains("/") || levelFolderName.contains("\\")) {
            path = levelFolderName;
        } else {
            path = new File(this.getDataPath(), "worlds/" + levelFolderName).getAbsolutePath();
        }
        String pathS = Path.of(path).toString();
        Class<? extends LevelProvider> provider = LevelProviderManager.getProvider(pathS);

        Map<Integer, LevelConfig.GeneratorConfig> generators = levelConfig.generators();
        for (var entry : generators.entrySet()) {
            String levelName = levelFolderName + " Dim" + entry.getKey();
            if (this.isLevelLoaded(levelName)) {
                return true;
            }
            Level level;
            try {
                if (provider == null) {
                    log.error(this.getLanguage().tr("nukkit.level.loadError", levelFolderName, "the level does not exist"));
                    return false;
                }
                level = new Level(this, levelName, pathS, generators.size(), provider, entry.getValue());
            } catch (Exception e) {
                log.error(this.getLanguage().tr("nukkit.level.loadError", levelFolderName, e.getMessage()), e);
                return false;
            }
            this.levels.put(level.getId(), level);
            level.initLevel();
            this.getPluginManager().callEvent(new LevelLoadEvent(level));
            level.setTickRate(getSettings().levelSettings().baseTickRate());
        }
        if (tickCounter != 0) {//update world enum when load  
            WorldCommand.WORLD_NAME_ENUM.updateSoftEnum();
        }
        return true;
    }

    public boolean generateLevel(String name, @Nullable LevelConfig levelConfig) {
        if (name.isBlank()) {
            return false;
        }
        String path;
        if (name.contains("/") || name.contains("\\")) {
            path = name;
        } else {
            path = this.getDataPath() + "worlds/" + name + "/";
        }

        Path jpath = Path.of(path);
        path = jpath.toString();
        File config = jpath.resolve("config.json").toFile();
        if (config.exists()) {
            try {
                levelConfig = JSONUtils.from(new FileReader(config), LevelConfig.class);
                FileUtils.write(config, JSONUtils.toPretty(levelConfig), StandardCharsets.UTF_8);
            } catch (Exception e) {
                log.error("The levelConfig is not exists under the {} path", path);
                return false;
            }
        } else if (levelConfig != null) {
            try {
                jpath.toFile().mkdirs();
                config.createNewFile();
                FileUtils.write(config, JSONUtils.toPretty(levelConfig), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("The levelConfig is not specified and no config.json exists under the {} path", path);
            return false;
        }

        for (var entry : levelConfig.generators().entrySet()) {
            LevelConfig.GeneratorConfig generatorConfig = entry.getValue();
            var provider = LevelProviderManager.getProviderByName(levelConfig.format());
            Level level;
            try {
                provider.getMethod("generate", String.class, String.class, LevelConfig.GeneratorConfig.class).invoke(null, path, name, generatorConfig);
                String levelName = name + " Dim" + entry.getKey();
                if (this.isLevelLoaded(levelName)) {
                    log.warn("level {} has already been loaded!", levelName);
                    continue;
                }
                level = new Level(this, levelName, path, levelConfig.generators().size(), provider, generatorConfig);

                this.getLevels().put(level.getId(), level);
                level.initLevel();
                level.setTickRate(getSettings().levelSettings().baseTickRate());
                this.getPluginManager().callEvent(new LevelInitEvent(level));
                this.getPluginManager().callEvent(new LevelLoadEvent(level));
            } catch (Exception e) {
                log.error(this.getLanguage().tr("nukkit.level.generationError", name, Utils.getExceptionMessage(e)), e);
                return false;
            }
        }
        return true;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Ban, OP, Whitelist Region] ───────
    // ─────────────────────────────────────────────────────
    public BanList getNameBans() {
        return this.banByName;
    }

    public BanList getIPBans() {
        return this.banByIP;
    }

    public void addOp(String name) {
        this.operators.set(name.toLowerCase(Locale.ENGLISH), true);
        Player player = this.getPlayerExact(name);
        if (player != null) {
            player.recalculatePermissions();
            player.getAdventureSettings().onOpChange(true);
            player.getAdventureSettings().update();
            player.getSession().syncAvailableCommands();
        }
        this.operators.save(true);
    }

    public void removeOp(String name) {
        this.operators.remove(name.toLowerCase(Locale.ENGLISH));
        Player player = this.getPlayerExact(name);
        if (player != null) {
            player.recalculatePermissions();
            player.getAdventureSettings().onOpChange(false);
            player.getAdventureSettings().update();
            player.getSession().syncAvailableCommands();
        }
        this.operators.save();
    }

    public void addWhitelist(String name) {
        this.whitelist.set(name.toLowerCase(Locale.ENGLISH), true);
        this.whitelist.save(true);
    }

    public void removeWhitelist(String name) {
        this.whitelist.remove(name.toLowerCase(Locale.ENGLISH));
        this.whitelist.save(true);
    }

    public boolean isWhitelisted(String name) {
        return !this.hasWhitelist() || this.operators.exists(name, true) || this.whitelist.exists(name, true);
    }

    public boolean isOp(String name) {
        return name != null && this.operators.exists(name, true);
    }

    public Config getWhitelist() {
        return whitelist;
    }

    public Config getOps() {
        return operators;
    }

    public void reloadWhitelist() {
        this.whitelist.reload();
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Configs Region] ──────────────────
    // ─────────────────────────────────────────────────────

    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Set the players count is allowed
     *
     * @param maxPlayers the max players
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.getNetwork().getPong().maximumPlayerCount(maxPlayers).update();
    }

    /**
     * @return Server port
     */
    public int getPort() {
        return this.properties.get(ServerPropertiesKeys.SERVER_PORT, 19132);
    }

    /**
     * @return Server view distance
     */
    public int getViewDistance() {
        return this.properties.get(ServerPropertiesKeys.VIEW_DISTANCE, 10);
    }

    /**
     * @return Server ip
     */
    public String getIp() {
        return this.properties.get(ServerPropertiesKeys.SERVER_IP, "0.0.0.0");
    }

    /**
     * @return Does the server automatically save
     */
    public boolean getAutoSave() {
        return this.autoSave;
    }

    /**
     * Set server autosave
     *
     * @param autoSave Whether to save automatically
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        for (Level level : this.levelArray) {
            level.setAutoSave(this.autoSave);
        }
    }

    /**
     * Get the gamemode of the server
     *
     * @return gamemode id
     */
    public int getGamemode() {
        try {
            return this.properties.get(ServerPropertiesKeys.GAMEMODE, 0) & 0b11;
        } catch (NumberFormatException exception) {
            return getGamemodeFromString(this.properties.get(ServerPropertiesKeys.GAMEMODE, "survival")) & 0b11;
        }
    }

    public boolean getForceGamemode() {
        return this.properties.get(ServerPropertiesKeys.FORCE_GAMEMODE, false);
    }

    /**
     * @see #getGamemodeString(int, boolean)
     */
    public static String getGamemodeString(int mode) {
        return getGamemodeString(mode, false);
    }

    /**
     * Get game mode string from gamemode id.
     *
     * @param mode   gamemode id
     * @param direct If true, the string is returned directly, and if false, the hard-coded string representing the game mode is returned.
     * @return Game Mode String
     */
    public static String getGamemodeString(int mode, boolean direct) {
        return switch (mode) {
            case Player.SURVIVAL -> direct ? "Survival" : "%gameMode.survival";
            case Player.CREATIVE -> direct ? "Creative" : "%gameMode.creative";
            case Player.ADVENTURE -> direct ? "Adventure" : "%gameMode.adventure";
            case Player.SPECTATOR -> direct ? "Spectator" : "%gameMode.spectator";
            default -> "UNKNOWN";
        };
    }

    /**
     * Get gamemode from string
     *
     * @param str A string representing the game mode, e.g. 0,survival...
     * @return gamemode id
     */
    public static int getGamemodeFromString(String str) {
        return switch (str.trim().toLowerCase(Locale.ENGLISH)) {
            case "0", "survival", "s" -> Player.SURVIVAL;
            case "1", "creative", "c" -> Player.CREATIVE;
            case "2", "adventure", "a" -> Player.ADVENTURE;
            case "3", "spectator", "spc", "view", "v" -> Player.SPECTATOR;
            default -> -1;
        };
    }

    /**
     * Get game difficulty from string
     *
     * @param str A string representing the game difficulty, e.g. 0,peaceful...
     * @return game difficulty id
     */
    public static int getDifficultyFromString(String str) {
        switch (str.trim().toLowerCase(Locale.ENGLISH)) {
            case "0":
            case "peaceful":
            case "p":
                return 0;

            case "1":
            case "easy":
            case "e":
                return 1;

            case "2":
            case "normal":
            case "n":
                return 2;

            case "3":
            case "hard":
            case "h":
                return 3;
        }
        return -1;
    }

    /**
     * Get server game difficulty
     *
     * @return Game difficulty id
     */
    public int getDifficulty() {
        if (this.difficulty == Integer.MAX_VALUE) {
            this.difficulty = getDifficultyFromString(this.properties.get(ServerPropertiesKeys.DIFFICULTY, "1"));
        }
        return this.difficulty;
    }

    /**
     * set server game difficulty
     *
     * @param difficulty Game difficulty id
     */
    public void setDifficulty(int difficulty) {
        int value = difficulty;
        if (value < 0) value = 0;
        if (value > 3) value = 3;
        this.difficulty = value;
        this.properties.get(ServerPropertiesKeys.DIFFICULTY, value);
    }

    /**
     * @return Whether to start server whitelist
     */
    public boolean hasWhitelist() {
        return this.properties.get(ServerPropertiesKeys.WHITE_LIST, false);
    }

    /**
     * @return Get server birth point protection radius
     */
    public int getSpawnRadius() {
        return this.properties.get(ServerPropertiesKeys.SPAWN_PROTECTION, 16);
    }

    /**
     * @return Whether the server allows flying
     */
    public boolean getAllowFlight() {
        if (getAllowFlight == null) {
            getAllowFlight = this.properties.get(ServerPropertiesKeys.ALLOW_FLIGHT, false);
        }
        return getAllowFlight;
    }

    /**
     * @return Whether the server is in hardcore mode
     */
    public boolean isHardcore() {
        return this.properties.get(ServerPropertiesKeys.HARDCORE, false);
    }

    /**
     * @return Get default gamemode
     */
    public int getDefaultGamemode() {
        if (this.defaultGamemode == Integer.MAX_VALUE) {
            this.defaultGamemode = this.getGamemode();
        }
        return this.defaultGamemode;
    }

    /**
     * Set default gamemode for the server.
     *
     * @param defaultGamemode the default gamemode
     */
    public void setDefaultGamemode(int defaultGamemode) {
        this.defaultGamemode = defaultGamemode;
        this.getNetwork().getPong().gameType(Server.getGamemodeString(defaultGamemode, true)).update();
    }

    /**
     * @return Get server motd
     */
    public String getMotd() {
        return this.properties.get(ServerPropertiesKeys.MOTD, "PowerNukkitX Server");
    }

    /**
     * Set the motd of server.
     *
     * @param motd the motd content
     */
    public void setMotd(String motd) {
        this.properties.get(ServerPropertiesKeys.MOTD, motd);
        this.getNetwork().getPong().motd(motd).update();
    }

    /**
     * @return Get the server subheading
     */
    public String getSubMotd() {
        String subMotd = this.properties.get(ServerPropertiesKeys.SUB_MOTD, "v2.powernukkitx.com");
        if (subMotd.isEmpty()) {
            subMotd = "v2.powernukkitx.com";
        }
        return subMotd;
    }

    /**
     * Set the sub motd of server.
     *
     * @param subMotd the sub motd
     */
    public void setSubMotd(String subMotd) {
        this.properties.get(ServerPropertiesKeys.SUB_MOTD, subMotd);
        this.getNetwork().getPong().subMotd(subMotd).update();
    }

    /**
     * @return Whether to force the use of server resourcepack
     */
    public boolean getForceResources() {
        return this.properties.get(ServerPropertiesKeys.FORCE_RESOURCES, false);
    }

    /**
     * @return Whether to force the use of server resourcepack while allowing the loading of client resourcepack
     */
    public boolean getForceResourcesAllowOwnPacks() {
        return this.properties.get(ServerPropertiesKeys.FORCE_RESOURCES_ALLOW_CLIENT_PACKS, false);
    }

    private LangCode mapInternalLang(String langName) {
        return switch (langName) {
            case "bra" -> LangCode.valueOf("pt_BR");
            case "chs" -> LangCode.valueOf("zh_CN");
            case "cht" -> LangCode.valueOf("zh_TW");
            case "cze" -> LangCode.valueOf("cs_CZ");
            case "deu" -> LangCode.valueOf("de_DE");
            case "fin" -> LangCode.valueOf("fi_FI");
            case "eng" -> LangCode.valueOf("en_US");
            case "fra" -> LangCode.valueOf("fr_FR");
            case "idn" -> LangCode.valueOf("id_ID");
            case "jpn" -> LangCode.valueOf("ja_JP");
            case "kor" -> LangCode.valueOf("ko_KR");
            case "ltu" -> LangCode.valueOf("en_US");
            case "pol" -> LangCode.valueOf("pl_PL");
            case "rus" -> LangCode.valueOf("ru_RU");
            case "spa" -> LangCode.valueOf("es_ES");
            case "tur" -> LangCode.valueOf("tr_TR");
            case "ukr" -> LangCode.valueOf("uk_UA");
            case "vie" -> LangCode.valueOf("en_US");
            default -> throw new IllegalArgumentException();
        };
    }

    public BaseLang getLanguage() {
        return baseLang;
    }

    public LangCode getLanguageCode() {
        return baseLangCode;
    }

    public ServerSettings getSettings() {
        return settings;
    }

    public ServerProperties getProperties() {
        return properties;
    }

    public boolean isNetherAllowed() {
        return this.allowNether;
    }

    public boolean isTheEndAllowed() {
        return this.allowTheEnd;
    }

    public boolean isIgnoredPacket(Class<? extends DataPacket> clazz) {
        return this.getSettings().debugSettings().ignoredPackets().contains(clazz.getSimpleName());
    }

    public int getServerAuthoritativeMovement() {
        return serverAuthoritativeMovementMode;
    }

    // ─────────────────────────────────────────────────────
    // ──────── Start of [Threading Region] ────────────────
    // ─────────────────────────────────────────────────────

    /**
     * Checks the current thread against the expected primary thread for the
     * server.
     * <p>
     * <b>Note:</b> this method should not be used to indicate the current
     * synchronized state of the runtime. A current thread matching the main
     * thread indicates that it is synchronized, but a mismatch does not
     * preclude the same assumption.
     *
     * @return true if the current thread matches the expected primary thread,
     * false otherwise
     */
    public final boolean isPrimaryThread() {
        return (Thread.currentThread() == currentThread);
    }

    public Thread getPrimaryThread() {
        return currentThread;
    }

    public ServerScheduler getScheduler() {
        return scheduler;
    }

    public ForkJoinPool getComputeThreadPool() {
        return computeThreadPool;
    }

    //todo NukkitConsole
    private class ConsoleThread extends Thread implements InterruptibleThread {
        public ConsoleThread() {
            super("Console Thread");
        }

        @Override
        public void run() {
            console.start();
        }
    }

    private static class ComputeThread extends ForkJoinWorkerThread {
        /**
         * Creates a ForkJoinWorkerThread operating in the given pool.
         *
         * @param pool the pool this thread works in
         * @throws NullPointerException if pool is null
         */
        ComputeThread(ForkJoinPool pool, AtomicInteger threadCount) {
            super(pool);
            this.setName("ComputeThreadPool-thread-" + threadCount.getAndIncrement());
        }
    }

    private static class ComputeThreadPoolThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
        private static final AtomicInteger threadCount = new AtomicInteger(0);
        @SuppressWarnings("removal")
        private static final AccessControlContext ACC = contextWithPermissions(
                new RuntimePermission("getClassLoader"),
                new RuntimePermission("setContextClassLoader"));

        @SuppressWarnings("removal")
        static AccessControlContext contextWithPermissions(@NotNull Permission... perms) {
            Permissions permissions = new Permissions();
            for (var perm : perms)
                permissions.add(perm);
            return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
        }

        @SuppressWarnings("removal")
        public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
            return AccessController.doPrivileged((PrivilegedAction<ForkJoinWorkerThread>) () -> new ComputeThread(pool, threadCount), ACC);
        }
    }

}
