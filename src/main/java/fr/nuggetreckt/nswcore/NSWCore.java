package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.commands.DownCommand;
import fr.nuggetreckt.nswcore.commands.TopCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class NSWCore extends JavaPlugin {

    public static String prefix = "§8[§3NSW§8] §r";
    private static NSWCore instance;

    @Override
    public void onEnable() {
        instance = this;

        Objects.requireNonNull(this.getCommand("top")).setExecutor(new TopCommand());
        Objects.requireNonNull(this.getCommand("down")).setExecutor(new DownCommand());

        Bukkit.getLogger().info("Plugin chargé avec succès");
        Bukkit.getLogger().info("  _   _  _______          _______               ");
        Bukkit.getLogger().info(" | \\ | |/ ____\\ \\        / / ____|              ");
        Bukkit.getLogger().info(" |  \\| | (___  \\ \\  /\\  / / |     ___  _ __ ___ ");
        Bukkit.getLogger().info(" | . ` |\\___ \\  \\ \\/  \\/ /| |    / _ \\| '__/ _ \\");
        Bukkit.getLogger().info(" | |\\  |____) |  \\  /\\  / | |___| (_) | | |  __/");
        Bukkit.getLogger().info(" |_| \\_|_____/    \\/  \\/   \\_____\\___/|_|  \\___|");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Plugin éteint avec succès");
    }

    public static String getPrefix() {
        return prefix;
    }

    public static NSWCore getInstance() {
        return instance;
    }
}