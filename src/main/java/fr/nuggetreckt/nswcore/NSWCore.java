package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.commands.*;
import fr.nuggetreckt.nswcore.commands.tabcompletion.TabCompletion;
import fr.nuggetreckt.nswcore.expansions.PAPIExpansion;
import fr.nuggetreckt.nswcore.listeners.*;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.EffectUtils;
import fr.nuggetreckt.nswcore.utils.GuiManager;
import fr.nuggetreckt.nswcore.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    public static String prefix;
    private static HonorRanks honorRanks;
    private static NSWCore instance;
    private static GuiManager guiManager;
    private static CooldownManager cooldownManager;
    private static TeleportUtils teleportUtils;
    private static EffectUtils effectUtils;
    private static BukkitTask bukkitTask;
    private static int serverPort;
    private static int farmzonePort;

    private static final Logger logger = Logger.getLogger("Minecraft");

    public NSWCore() {
        farmzonePort = 25568;
        prefix = "§8[§3%s§8] §r";

        guiManager = new GuiManager();
        honorRanks = new HonorRanks();
        cooldownManager = new CooldownManager();
        teleportUtils = new TeleportUtils();
        effectUtils = new EffectUtils();
    }

    @Override
    public void onEnable() {
        instance = this;

        //Set server port
        setServerPort();

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

        //register TabCompleters
        Objects.requireNonNull(this.getCommand("honorrank")).setTabCompleter(new TabCompletion());
        Objects.requireNonNull(this.getCommand("spawn")).setTabCompleter(new TabCompletion());

        //Register events
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);
        getServer().getPluginManager().registerEvents(new OnLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new OnSleepListener(), this);
        getServer().getPluginManager().registerEvents(new OnInvClickListener(), this);
        getServer().getPluginManager().registerEvents(new OnMoveListener(), this);
        getServer().getPluginManager().registerEvents(new OnDeathListener(), this);

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
        logger.info(String.format("[%s] Plugin shut down successfully", getDescription().getName()));
        instance = null;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static World getOverworld() {
        return Bukkit.getWorld("world");
    }

    public static NSWCore getInstance() {
        return instance;
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