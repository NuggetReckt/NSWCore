package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsUtils {

    public void savePlayerStats(@NotNull Player player) {
        int killCount = player.getStatistic(Statistic.PLAYER_KILLS);
        int deathCount = player.getStatistic(Statistic.DEATHS);
        long timePlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE);

        NSWCore.getRequestsManager().setTimePlayed(timePlayed, player.getUniqueId());
        NSWCore.getRequestsManager().setKillCount(killCount, player.getUniqueId());
        NSWCore.getRequestsManager().setDeathCount(deathCount, player.getUniqueId());
    }

    public void saveAllPlayerStats() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            savePlayerStats(p);
        }
    }
}
