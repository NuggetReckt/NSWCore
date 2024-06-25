package fr.nuggetreckt.nswcore;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.utils.Credentials;
import fr.nuggetreckt.nswcore.commands.*;
import fr.nuggetreckt.nswcore.commands.tabcompletion.TabCompletion;
import fr.nuggetreckt.nswcore.database.Saver;
import fr.nuggetreckt.nswcore.expansions.PAPIExpansion;
import fr.nuggetreckt.nswcore.guis.GuiManager;
import fr.nuggetreckt.nswcore.listeners.*;
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
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    private final String prefix;

    private int serverPort;
    private final int farmzonePort;
    private Location spawnLocation;

    private static NSWCore instance;

    private static Logger logger;
    private static GuiManager guiManager;
    private static CooldownManager cooldownManager;
    private static TeleportUtils teleportUtils;
    private static EffectUtils effectUtils;
    private static StaffUtils staffUtils;
    private static ReportUtils reportUtils;
    private static Saver saver;
    private static LuckPermsUtils luckPermsUtils;
    private static Economy economy;
    private static SaveTask saveTask;

    private static NSWAPI nswapi;

    private BukkitTask bukkitTask;
    private LuckPerms luckPermsAPI;

    public NSWCore() {
        instance = this;
        farmzonePort = 25568;
        prefix = "§8[§3%s§8] §r";
        logger = Logger.getLogger("Minecraft");
        setAPI();

        saveTask = new SaveTask();
        guiManager = new GuiManager(nswapi);
        cooldownManager = new CooldownManager();
        teleportUtils = new TeleportUtils();
        effectUtils = new EffectUtils(nswapi);
        staffUtils = new StaffUtils();
        reportUtils = new ReportUtils(nswapi);
        saver = new Saver(nswapi);
        luckPermsUtils = new LuckPermsUtils();
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
        Objects.requireNonNull(this.getCommand("help")).setExecutor(new HelpCommand());
        Objects.requireNonNull(this.getCommand("top")).setExecutor(new TopCommand());
        Objects.requireNonNull(this.getCommand("bottom")).setExecutor(new BottomCommand());
        Objects.requireNonNull(this.getCommand("up")).setExecutor(new UpCommand());
        Objects.requireNonNull(this.getCommand("down")).setExecutor(new DownCommand());
        Objects.requireNonNull(this.getCommand("enderchest")).setExecutor(new EnderchestCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
        Objects.requireNonNull(this.getCommand("furnace")).setExecutor(new FurnaceCommand());
        Objects.requireNonNull(this.getCommand("honorrank")).setExecutor(new HonorRankCommand(nswapi));
        Objects.requireNonNull(this.getCommand("staff")).setExecutor(new StaffCommand());
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KitCommand());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(this.getCommand("freeze")).setExecutor(new FreezeCommand());
        Objects.requireNonNull(this.getCommand("unfreeze")).setExecutor(new UnfreezeCommand());

        //register TabCompleters
        Objects.requireNonNull(this.getCommand("honorrank")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("spawn")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("help")).setTabCompleter(new TabCompletion());

        //Register events
        getServer().getPluginManager().registerEvents(new OnJoinListener(nswapi), this);
        getServer().getPluginManager().registerEvents(new OnLeaveListener(nswapi), this);
        getServer().getPluginManager().registerEvents(new OnSleepListener(), this);
        getServer().getPluginManager().registerEvents(new OnInvClickListener(), this);
        getServer().getPluginManager().registerEvents(new OnMoveListener(), this);
        getServer().getPluginManager().registerEvents(new OnDeathListener(), this);
        getServer().getPluginManager().registerEvents(new OnRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new StaffEventsCanceller(), this);
        getServer().getPluginManager().registerEvents(new StaffEventsListener(), this);
        getServer().getPluginManager().registerEvents(new OnCommandListener(), this);
        getServer().getPluginManager().registerEvents(new OnInteractListener(), this);

        if (isFarmzone()) {
            getServer().getPluginManager().registerEvents(new OnDragonDeathListener(), this);
        }

        //Register PAPI expansion
        setPlaceholderAPI();

        //Register Luckperms API
        setLuckPermsAPI();

        //Set economy
        setEconomy();

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

    public String getPrefix() {
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

    public BukkitTask getBukkitTask() {
        return bukkitTask;
    }

    public LuckPerms getLuckPermsAPI() {
        return luckPermsAPI;
    }

    public static NSWCore getInstance() {
        return instance;
    }

    public static GuiManager getGuiManager() {
        return guiManager;
    }

    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public static TeleportUtils getTeleportUtils() {
        return teleportUtils;
    }

    public static EffectUtils getEffectUtils() {
        return effectUtils;
    }

    public static StaffUtils getStaffUtils() {
        return staffUtils;
    }

    public static ReportUtils getReportUtils() {
        return reportUtils;
    }

    public static Saver getSaver() {
        return saver;
    }

    public static LuckPermsUtils getLuckPermsUtils() {
        return luckPermsUtils;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public NSWAPI getAPI() {
        return nswapi;
    }

    public boolean isFarmzone() {
        return serverPort == farmzonePort;
    }

    public void setBukkitTask(BukkitTask task) {
        bukkitTask = task;
    }

    private void setServerPort() {
        serverPort = Bukkit.getServer().getPort();
    }

    private void setSpawnLocation() {
        spawnLocation = new Location(getOverworld(), 319.5, 65, 884.5, -90, 0);
    }

    private void setAPI() {
        Dotenv dotenv = Dotenv.configure()
                .directory("/env/")
                .filename(".env")
                .load();

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
        new PAPIExpansion(nswapi).register();
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
}