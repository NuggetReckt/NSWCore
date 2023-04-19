package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.commands.DownCommand;
import fr.nuggetreckt.nswcore.commands.TopCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public class NSWCore extends JavaPlugin {

    public static String prefix = "§8[§3%s§8] §r";
    private static NSWCore instance;

    private static final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        instance = this;

        Objects.requireNonNull(this.getCommand("top")).setExecutor(new TopCommand());
        Objects.requireNonNull(this.getCommand("down")).setExecutor(new DownCommand());

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
    }

    public static String getPrefix() {
        return prefix;
    }

    public static NSWCore getInstance() {
        return instance;
    }
}