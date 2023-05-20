package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class HonorRanks {
    public enum Rank {
        Rank_1(1, 10),
        Rank_2(2, 36),
        Rank_3(3, 60),
        Rank_4(4, 100),
        Rank_5(5, 150),
        Rank_6(6, 250),
        ;

        private static final Map<Integer, Rank> BY_ID = new HashMap<>();

        static {
            for (Rank i : values()) {
                BY_ID.put(i.rankId, i);
            }
        }

        private final int rankId;
        private final long honorPoints;

        Rank(int lvl, long pts) {
            this.rankId = lvl;
            this.honorPoints = pts;
        }

        public int getRankId() {
            return this.rankId;
        }

        public long getHonorPoints() {
            return this.honorPoints;
        }
    }

    public HashMap<Player, @Nullable Rank> playerRank = new HashMap<>();
    public HashMap<Player, Long> playerPoints = new HashMap<>();

    public void init(Player player) {
        if (!isRanked(player)) {
            playerRank.put(player, null);
            playerPoints.put(player, 0L);
        }
    }

    public void gainPlayerPoints(Player player, long honorPoints) {
        long oldPoints = getPlayerPoints(player);

        playerPoints.replace(player, oldPoints + honorPoints);
    }

    public void upRankPlayer(Player player) {
        if (getNextPlayerRank(player) != null) {
            long oldPoints = getPlayerPoints(player);
            long currentPoints = getPlayerPoints(player);
            long pointsNeeded = getPointsNeeded(player);

            if (currentPoints > pointsNeeded) {
                Bukkit.broadcastMessage(String.format(MessageManager.HONORRANKS_UPRANK_BROADCASTMESSAGE.getMessage(),
                        player.getName(), getNextPlayerRank(player).getRankId()));

                long points = oldPoints - currentPoints;
                playerPoints.replace(player, points);
                playerRank.replace(player, getNextPlayerRank(player));
            } else {
                player.sendMessage(String.format(MessageManager.NO_ENOUGH_HONORPOINTS.getMessage(), "HR", currentPoints, pointsNeeded));
            }
        } else {
            player.sendMessage(String.format(MessageManager.MAX_HONORRANK_MESSAGE.getMessage(), "HR"));
        }
    }

    public long getPlayerPoints(Player player) {
        return playerPoints.get(player);
    }

    public long getPointsNeeded(Player player) {
        return getNextPlayerRank(player).getHonorPoints();
    }

    public Rank getPlayerRank(Player player) {
        return playerRank.get(player);
    }

    public int getPlayerRankId(Player player) {
        if (isRanked(player)) {
            return getPlayerRank(player).getRankId();
        } else {
            return 0;
        }
    }

    public Rank getNextPlayerRank(Player player) {
        int currentRankId = getPlayerRankId(player);
        int nextRankId = currentRankId + 1;

        if (currentRankId == 6) {
            return null;
        }
        return getRankById(nextRankId);
    }

    public String getDisplayName(Player player) {
        return "§fRang d'Honneur §3" + getPlayerRankId(player);
    }

    public String getPrefix(Player player) {
        return "§8[§3" + getPlayerRankId(player) + "§8]";
    }

    private Rank getRankById(int id) {
        return Rank.BY_ID.get(id);
    }

    public String getRanks() {
        StringBuilder sb = new StringBuilder();

        for (Rank i : Rank.values()) {
            sb.append(" §8|§f Rang §3").append(i.getRankId()).append(" §8(§3").append(i.getHonorPoints()).append(" §7points d'honneur§8)\n");
        }
        return sb.toString();
    }

    public boolean isRanked(Player player) {
        return playerRank.get(player) != null;
    }
}
