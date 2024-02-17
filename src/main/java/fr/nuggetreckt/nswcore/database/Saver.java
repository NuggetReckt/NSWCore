package fr.nuggetreckt.nswcore.database;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.entities.NSWPlayer;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Saver {

    public void saveAll() {
        NSWAPI.getAPI().getServerHandler().getExecutor().execute(() -> {
            saveAllPlayerData();
            saveAllPlayerStats();
        });
    }

    public void saveAllPlayerData() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            savePlayerData(p);
        }
    }

    public void saveAllPlayerStats() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            savePlayerStats(p);
        }
    }

    public void savePlayerData(@NotNull Player player) {
        int rankId = NSWCore.getAPI().getHonorRanksHandler().getPlayerRankId(player.getUniqueId());
        long playerPoints = NSWCore.getAPI().getHonorRanksHandler().getPlayerPoints(player.getUniqueId());
        NSWPlayer nswPlayer = new NSWPlayer(player.getName(), player.getUniqueId());

        NSWCore.getAPI().getDatabaseManager().getRequestSender().updatePlayerData(nswPlayer, rankId, playerPoints);
    }

    public void savePlayerStats(@NotNull Player player) {
        int killCount = player.getStatistic(Statistic.PLAYER_KILLS);
        int deathCount = player.getStatistic(Statistic.DEATHS);
        long timePlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE);

        NSWCore.getAPI().getDatabaseManager().getRequestSender().setMinecraftStats(deathCount, killCount, timePlayed, player.getUniqueId());
    }
}
