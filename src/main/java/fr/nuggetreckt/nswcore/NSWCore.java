package fr.nuggetreckt.nswcore;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.gui.GuiManager;
import fr.noskillworld.api.utils.Credentials;
import fr.nuggetreckt.nswcore.commands.*;
import fr.nuggetreckt.nswcore.commands.tabcompletion.TabCompletion;
import fr.nuggetreckt.nswcore.database.Saver;
import fr.nuggetreckt.nswcore.expansions.PAPIExpansion;
import fr.nuggetreckt.nswcore.guis.*;
import fr.nuggetreckt.nswcore.listeners.*;
import fr.nuggetreckt.nswcore.tasks.MoneyGiveTask;
import fr.nuggetreckt.nswcore.tasks.PlayerDelayTask;
import fr.nuggetreckt.nswcore.tasks.SaveTask;
import fr.nuggetreckt.nswcore.utils.*;
import io.github.cdimascio.dotenv.Dotenv;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    private static String prefix;

    private int serverPort;
    private final int farmzonePort;
    private Location spawnLocation;

    private static NSWCore instance;

    private final Logger logger;
    private final CooldownManager cooldownManager;
    private final TeleportUtils teleportUtils;
    private final EffectUtils effectUtils;
    private final StaffUtils staffUtils;
    private final ReportUtils reportUtils;
    private final Saver saver;
    private final LuckPermsUtils luckPermsUtils;
    private Economy economy;
    private final SaveTask saveTask;
    private final MoneyGiveTask moneyGiveTask;
    private final PlayerDelayTask playerDelayTask;

    private NSWAPI nswapi;

    private LuckPerms luckPermsAPI;

    public NSWCore() {
        instance = this;
        farmzonePort = 25568;
        prefix = "§8[§3%s§8] §r";
        logger = Logger.getLogger("Minecraft");
        setAPI();

        saveTask = new SaveTask(this);
        moneyGiveTask = new MoneyGiveTask(this);
        playerDelayTask = new PlayerDelayTask();
        cooldownManager = new CooldownManager();
        teleportUtils = new TeleportUtils();
        effectUtils = new EffectUtils(this);
        staffUtils = new StaffUtils(this);
        reportUtils = new ReportUtils(this);
        saver = new Saver(this);
        luckPermsUtils = new LuckPermsUtils(this);
    }

    @Override
    public void onEnable() {
        //Set server port
        setServerPort();

        //Set spawn location
        setSpawnLocation();

        //Launch BukkitTask
        saveTask.launch();

        //register commands
        Objects.requireNonNull(this.getCommand("help")).setExecutor(new HelpCommand(this));
        Objects.requireNonNull(this.getCommand("top")).setExecutor(new TopCommand(this));
        Objects.requireNonNull(this.getCommand("bottom")).setExecutor(new BottomCommand(this));
        Objects.requireNonNull(this.getCommand("up")).setExecutor(new UpCommand(this));
        Objects.requireNonNull(this.getCommand("down")).setExecutor(new DownCommand(this));
        Objects.requireNonNull(this.getCommand("enderchest")).setExecutor(new EnderchestCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
        Objects.requireNonNull(this.getCommand("furnace")).setExecutor(new FurnaceCommand(this));
        Objects.requireNonNull(this.getCommand("honorrank")).setExecutor(new HonorRankCommand(this));
        Objects.requireNonNull(this.getCommand("staff")).setExecutor(new StaffCommand(this));
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KitCommand(this));
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand(this));
        Objects.requireNonNull(this.getCommand("freeze")).setExecutor(new FreezeCommand(this));
        Objects.requireNonNull(this.getCommand("unfreeze")).setExecutor(new UnfreezeCommand(this));

        //register TabCompleters
        Objects.requireNonNull(this.getCommand("honorrank")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("spawn")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("help")).setTabCompleter(new TabCompletion());

        //Register events
        getServer().getPluginManager().registerEvents(new OnJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new OnLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new OnSleepListener(this), this);
        getServer().getPluginManager().registerEvents(new OnInvClickListener(this), this);
        getServer().getPluginManager().registerEvents(new OnMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new OnDeathListener(), this);
        getServer().getPluginManager().registerEvents(new OnRespawnListener(this), this);
        getServer().getPluginManager().registerEvents(new StaffEventsCanceller(this), this);
        getServer().getPluginManager().registerEvents(new StaffEventsListener(this), this);
        getServer().getPluginManager().registerEvents(new OnCommandListener(this), this);
        getServer().getPluginManager().registerEvents(new OnInteractListener(this), this);

        if (isFarmzone()) {
            getServer().getPluginManager().registerEvents(new OnDragonDeathListener(this), this);
        }

        //Register PAPI expansion
        setPlaceholderAPI();

        //Register Luckperms API
        setLuckPermsAPI();

        //Set economy
        setEconomy();

        //Register GUIs
        registerGUIs();

        logger.info(String.format("[%s] Plugin loaded successfully", getDescription().getName()));
        logger.info("  _   _  _______          _______               ");
        logger.info(" | \\ | |/ ____\\ \\        / / ____|              ");
        logger.info(" |  \\| | (___  \\ \\  /\\  / / |     ___  _ __ ___ ");
        logger.info(" | . ` |\\___ \\  \\ \\/  \\/ /| |    / _ \\| '__/ _ \\");
        logger.info(" | |\\  |____) |  \\  /\\  / | |___| (_) | | |  __/");
        logger.info(" |_| \\_|_____/    \\/  \\/   \\_____\\___/|_|  \\___|");
    }

    @Override
    public void onDisable() {
        getSaver().saveAllPlayerData();
        getSaver().saveAllPlayerStats();
        saveTask.stop();
        getAPI().getDatabaseManager().getConnector().close();
        instance = null;

        logger.info(String.format("[%s] Plugin shut down successfully", getDescription().getName()));
    }

    public static String getPrefix() {
        return prefix;
    }

    public String getColoredName() {
        return IridiumColorAPI.process("<GRADIENT:2C70BA>§lNoSkillWorld</GRADIENT:42cfcf>");
    }

    public World getOverworld() {
        if (isFarmzone()) {
            return Bukkit.getWorld("main");
        } else {
            return Bukkit.getWorld("world");
        }
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public boolean isStaff(@NotNull Player player) {
        return player.hasPermission("nsw.staff") || player.isOp();
    }

    public Player getPlayerByUuid(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public Player getPlayerByName(String name) {
        return Bukkit.getPlayer(name);
    }

    public LuckPerms getLuckPermsAPI() {
        return luckPermsAPI;
    }

    public NSWCore getInstance() {
        return instance;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public TeleportUtils getTeleportUtils() {
        return teleportUtils;
    }

    public EffectUtils getEffectUtils() {
        return effectUtils;
    }

    public StaffUtils getStaffUtils() {
        return staffUtils;
    }

    public ReportUtils getReportUtils() {
        return reportUtils;
    }

    public Saver getSaver() {
        return saver;
    }

    public MoneyGiveTask getMoneyGiveTask() {
        return moneyGiveTask;
    }

    public PlayerDelayTask getPlayerDelayTask() {
        return playerDelayTask;
    }

    public LuckPermsUtils getLuckPermsUtils() {
        return luckPermsUtils;
    }

    public Economy getEconomy() {
        return economy;
    }

    public NSWAPI getAPI() {
        return nswapi;
    }

    public boolean isFarmzone() {
        return serverPort == farmzonePort;
    }

    private void setServerPort() {
        serverPort = Bukkit.getServer().getPort();
    }

    private void setSpawnLocation() {
        spawnLocation = new Location(getOverworld(), 319.5, 65, 884.5, -90, 0);
    }

    private void setAPI() {
        Dotenv dotenv = Dotenv.configure().directory("/env/").filename(".env").load();

        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        String name = dotenv.get("DB_NAME");

        nswapi = NSWAPI.create(new Credentials(user, password, name));
    }

    private void setLuckPermsAPI() {
        if (getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            logger.severe("The LuckPerms plugin was not found! Please install it before starting the server.");
            logger.info("Stopping the server...");
            getServer().shutdown();
            return;
        }
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPermsAPI = provider.getProvider();
        }
    }

    private void setPlaceholderAPI() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.severe("The PlaceholderAPI plugin was not found! Please install it before starting the server.");
            logger.info("Stopping the server...");
            getServer().shutdown();
            return;
        }
        new PAPIExpansion(this).register();
    }

    private void setEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            logger.severe("The Vault plugin was not found! Please install it before starting the server.");
            logger.info("Stopping the server...");
            getServer().shutdown();
            return;
        }
        RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);
        if (provider != null) {
            economy = provider.getProvider();
        }
    }

    private void registerGUIs() {
        GuiManager guiManager = nswapi.getGuiManager();

        guiManager.addMenu(new HonorRankGui(this));
        guiManager.addMenu(new KitGui(this));
        guiManager.addMenu(new PlayerListGui(this));
        guiManager.addMenu(new ProgressHRGui(this));
        guiManager.addMenu(new ReportsGui(this));
        guiManager.addMenu(new RewardsHRGui(this));
    }
}