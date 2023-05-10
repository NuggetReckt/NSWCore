package fr.nuggetreckt.nswcore;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class HonorRanks {
    public enum Ranks {
        Rank_1(1, 10),
        Rank_2(2, 36),
        Rank_3(3, 60),
        Rank_4(4, 100),
        Rank_5(5, 150),
        Rank_6(6, 250),
        ;

        private final int rankId;
        private final long honorPoints;

        Ranks(int lvl, long pts) {
            this.rankId = lvl;
            this.honorPoints = pts;
        }

        public int getRankId() {
            return this.rankId;
        }

        public double getHonorPoints() {
            return this.honorPoints;
        }
    }

    public HashMap<Player, @Nullable Ranks> playerRank = new HashMap<>();
    public HashMap<Player, Integer> playerPoints = new HashMap<>();

    public void init(Player player) {
        setPlayerRank(player, null);
        setPlayerPoints(player, 0);
    }

    public void setPlayerRank(Player player, Ranks rank) {
        playerRank.putIfAbsent(player, rank);
    }

    public void setPlayerPoints(Player player, int honorPoints) {
        playerPoints.putIfAbsent(player, honorPoints);
    }

    public void updatePlayerPoints(Player player, int honorPoints) {
        int oldPoints = playerPoints.get(player);

        playerPoints.replace(player, oldPoints + honorPoints);
    }

    public void updatePlayerRank(Player player, Ranks rank) {
        playerRank.replace(player, rank);
    }

    public int getPlayerPoints(Player player) {
        return playerPoints.get(player);
    }

    public Ranks getPlayerRank(Player player) {
        if (isRanked(player)) {
            return playerRank.get(player);
        } else {
            return null;
        }
    }

    public String getDisplayName(@NotNull Ranks rank, Player player) {
        if (isRanked(player)) {
            return "§fRang d'honneur §3" + getPlayerRank(player).getRankId();
        } else {
            return "§fRang d'honneur §30";
        }
    }

    public String getPrefix(@NotNull Ranks rank, Player player) {
        if (isRanked(player)) {
            return "§8[§3" + getPlayerRank(player).getRankId() + "§8]";
        } else {
            return "§8[§30§8]";
        }
    }

    public String getRanks() {
        StringBuilder sb = new StringBuilder();

        for (Ranks i : Ranks.values()) {
            sb.append("Rang §3").append(i.getRankId()).append(" §8(§3").append(i.getHonorPoints()).append("points d'honneur§8)\n");
        }
        return sb.toString();
    }

    public boolean isRanked(Player player) {
        return playerRank.get(player) == null;
    }
}
