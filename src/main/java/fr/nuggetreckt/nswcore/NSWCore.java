package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.commands.*;
import fr.nuggetreckt.nswcore.expansions.PAPIExpansion;
import fr.nuggetreckt.nswcore.listeners.*;
import fr.nuggetreckt.nswcore.utils.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    public static String prefix = "§8[§3%s§8] §r";
    private static NSWCore instance;
    private static GuiManager guiManager;
    private static int serverPort;
    private static final int farmzonePort = 25568;

    private static final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        instance = this;
        guiManager = new GuiManager();

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
        Objects.requireNonNull(this.getCommand("honorrank")).setExecutor(new HonorRankCommand());
        Objects.requireNonNull(this.getCommand("staff")).setExecutor(new StaffCommand());
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KitCommand());

        //Register events
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);
        getServer().getPluginManager().registerEvents(new OnLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new OnSleepListener(), this);
        getServer().getPluginManager().registerEvents(new OnInvClickListener(), this);

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

    public static NSWCore getInstance() {
        return instance;
    }

    public static GuiManager getGuiManager() {
        return guiManager;
    }

    private void setServerPort() {
        serverPort = Bukkit.getServer().getPort();
    }

    public int getServerPort() {
        return serverPort;
    }

    public static boolean isFarmzone() {
        return serverPort == farmzonePort;
    }
}