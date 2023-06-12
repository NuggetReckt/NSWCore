package fr.nuggetreckt.nswcore.database;

import fr.nuggetreckt.nswcore.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class SaveTask {

    BukkitTask task;

    public void launch() {
        HonorRanks hr = NSWCore.getHonorRanks();

        task = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(NSWCore.getInstance(), () -> {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    new Requests().updatePlayerData(player, hr.getPlayerRankId(player), hr.getPlayerPoints(player));
                }
                NSWCore.getInstance().getLogger().info("Sauvegarde des données effectuée.");
            }
        }, 0L, 144000L);
    }

    public void stop() {
        task.cancel();
    }
}
