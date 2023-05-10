package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.commands.*;
import fr.nuggetreckt.nswcore.listeners.OnJoinListener;
import fr.nuggetreckt.nswcore.listeners.OnLeaveListener;
import fr.nuggetreckt.nswcore.listeners.OnSleepListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    public static String prefix = "§8[§3%s§8] §r";
    private static NSWCore instance;
    private static int serverPort;
    private static final int farmzonePort = 25568;

    private static final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        instance = this;
        setServerPort();

        //register commands
        Objects.requireNonNull(this.getCommand("top")).setExecutor(new TopCommand());
        Objects.requireNonNull(this.getCommand("bottom")).setExecutor(new BottomCommand());
        Objects.requireNonNull(this.getCommand("up")).setExecutor(new UpCommand());
        Objects.requireNonNull(this.getCommand("down")).setExecutor(new DownCommand());
        Objects.requireNonNull(this.getCommand("enderchest")).setExecutor(new EnderchestCommand());
        Objects.requireNonNull(this.getCommand("craft")).setExecutor(new CraftCommand());
        Objects.requireNonNull(this.getCommand("anvil")).setExecutor(new AnvilCommand());

        //register events
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);
        getServer().getPluginManager().registerEvents(new OnLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new OnSleepListener(), this);

        logger.info(String.format("[%s] Plugin chargé avec succès", getDescription().getName()));
        logger.info("  _   _  _______          _______               ");
        logger.info(" | \\ | |/ ____\\ \\        / / ____|              ");
        logger.info(" |  \\| | (___  \\ \\  /\\  / / |     ___  _ __ ___ ");
        logger.info(" | . ` |\\___ \\  \\ \\/  \\/ /| |    / _ \\| '__/ _ \\");
        logger.info(" | |\\  |____) |  \\  /\\  / | |___| (_) | | |  __/");
        logger.info(" |_| \\_|_____/    \\/  \\/   \\_____\\___/|_|  \\___|");
    }

    @Override
    public void onDisable() {
        logger.info(String.format("[%s] Plugin éteint avec succès", getDescription().getName()));
        instance = null;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static NSWCore getInstance() {
        return instance;
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