package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.commands.*;
import fr.nuggetreckt.nswcore.commands.tabcompletion.TabCompletion;
import fr.nuggetreckt.nswcore.database.Connector;
import fr.nuggetreckt.nswcore.database.Requests;
import fr.nuggetreckt.nswcore.database.SaveTask;
import fr.nuggetreckt.nswcore.expansions.PAPIExpansion;
import fr.nuggetreckt.nswcore.listeners.*;
import fr.nuggetreckt.nswcore.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    public static String prefix;
    private static HonorRanks honorRanks;
    private static NSWCore instance;
    private static Logger logger;
    private static GuiManager guiManager;
    private static CooldownManager cooldownManager;
    private static TeleportUtils teleportUtils;
    private static EffectUtils effectUtils;
    private static StaffUtils staffUtils;
    private static ReportUtils reportUtils;
    private static ServerHandler serverHandler;
    private static Requests requestsManager;
    private static BukkitTask bukkitTask;
    private final SaveTask saveTask;
    private static Connector connector = null;
    private static int serverPort;
    private static int farmzonePort;

    public NSWCore() {
        farmzonePort = 25568;
        prefix = "§8[§3%s§8] §r";
        logger = Logger.getLogger("Minecraft");

        connector = new Connector();
        saveTask = new SaveTask();
        guiManager = new GuiManager();
        honorRanks = new HonorRanks();
        cooldownManager = new CooldownManager();
        teleportUtils = new TeleportUtils();
        effectUtils = new EffectUtils();
        staffUtils = new StaffUtils();
        reportUtils = new ReportUtils();
        serverHandler = new ServerHandler();
        requestsManager = new Requests();
    }

    @Override
    public void onEnable() {
        instance = this;

        //Set server port
        setServerPort();

        //Create table if absent
        getRequestsManager().createTables();

        //Launch BukkitTask
        saveTask.launch();

        //register commands
        Objects.requireNonNull(this.getCommand("top")).setExecutor(new TopCommand());
        Objects.requireNonNull(this.getCommand("bottom")).setExecutor(new BottomCommand());
        Objects.requireNonNull(this.getCommand("up")).setExecutor(new UpCommand());
        Objects.requireNonNull(this.getCommand("down")).setExecutor(new DownCommand());
        Objects.requireNonNull(this.getCommand("enderchest")).setExecutor(new EnderchestCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
        Objects.requireNonNull(this.getCommand("anvil")).setExecutor(new AnvilCommand());
        Objects.requireNonNull(this.getCommand("furnace")).setExecutor(new FurnaceCommand());
        Objects.requireNonNull(this.getCommand("honorrank")).setExecutor(new HonorRankCommand());
        Objects.requireNonNull(this.getCommand("staff")).setExecutor(new StaffCommand());
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KitCommand());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(this.getCommand("report")).setExecutor(new ReportCommand());

        //register TabCompleters
        Objects.requireNonNull(this.getCommand("honorrank")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("spawn")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("report")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("report")).setTabCompleter(new TabCompletion());

        //Register events
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);
        getServer().getPluginManager().registerEvents(new OnLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new OnSleepListener(), this);
        getServer().getPluginManager().registerEvents(new OnInvClickListener(), this);
        getServer().getPluginManager().registerEvents(new OnMoveListener(), this);
        getServer().getPluginManager().registerEvents(new OnDeathListener(), this);
        getServer().getPluginManager().registerEvents(new StaffEventsCanceller(), this);
        getServer().getPluginManager().registerEvents(new StaffEventsListener(), this);

        if (isFarmzone()) {
            getServer().getPluginManager().registerEvents(new OnDragonDeathListener(), this);
        }

        //Register PAPI expansion
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIExpansion().register();
        }

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
        getHonorRanks().saveAllPlayerData();
        getStaffUtils().restoreStaffData();
        saveTask.stop();
        connector.close();
        instance = null;

        logger.info(String.format("[%s] Plugin shut down successfully", getDescription().getName()));
    }

    public static String getPrefix() {
        return prefix;
    }

    public static World getOverworld() {
        return Bukkit.getWorld("world");
    }

    public static Player getPlayerByUuid(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static Player getPlayerByName(String name) {
        return Bukkit.getPlayer(name);
    }

    public static boolean hasJoinedOnce(Player player) {
        return new Requests().hasJoinedOnce(player);
    }

    public static NSWCore getInstance() {
        return instance;
    }

    public static Connector getConnector() {
        return connector;
    }

    public static GuiManager getGuiManager() {
        return guiManager;
    }

    public static HonorRanks getHonorRanks() {
        return honorRanks;
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

    public static ServerHandler getServerHandler() {
        return serverHandler;
    }

    public static Requests getRequestsManager() {
        return requestsManager;
    }

    public static boolean isFarmzone() {
        return serverPort == farmzonePort;
    }

    public static void setBukkitTask(BukkitTask task) {
        bukkitTask = task;
    }

    public static BukkitTask getBukkitTask() {
        return bukkitTask;
    }

    private void setServerPort() {
        serverPort = Bukkit.getServer().getPort();
    }
}