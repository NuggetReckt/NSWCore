package fr.nuggetreckt.nswcore.tasks;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class SaveTask {

    private BukkitTask task;

    public void launch() {
        task = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(NSWCore.getInstance(), () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                NSWCore.getSaver().saveAll();
                NSWCore.getInstance().getLogger().info("Sauvegarde des données effectuée.");
            }
        }, 0L, 144000L);
    }

    public void stop() {
        task.cancel();
    }
}
