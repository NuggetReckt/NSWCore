package fr.nuggetreckt.nswcore.tasks;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class SaveTask {

    private final NSWCore instance;

    private BukkitTask task;

    public SaveTask(NSWCore instance) {
        this.instance = instance;
    }

    public void launch() {
        task = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(instance, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                instance.getSaver().saveAll();
                instance.getLogger().info("Sauvegarde des données effectuée.");
            }
        }, 0L, 144000L);
    }

    public void stop() {
        task.cancel();
    }
}
