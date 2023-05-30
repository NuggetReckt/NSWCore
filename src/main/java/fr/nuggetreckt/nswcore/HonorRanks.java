package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    private final Map<UUID, @Nullable Rank> playerRank = new HashMap<>();
    private final Map<UUID, Long> playerPoints = new HashMap<>();

    public void init(@NotNull Player player) {
        if (!isRanked(player)) {
            playerRank.putIfAbsent(player.getUniqueId(), null);
            playerPoints.putIfAbsent(player.getUniqueId(), 0L);
        }
    }

    public void gainPlayerPoints(@NotNull Player player, long honorPoints) {
        long oldPoints = getPlayerPoints(player);

        playerPoints.replace(player.getUniqueId(), oldPoints + honorPoints);
    }

    public void upRankPlayer(@NotNull Player player) {
        if (getNextPlayerRank(player) != null) {
            long currentPoints = getPlayerPoints(player);
            long pointsNeeded = getPointsNeeded(player);

            if (currentPoints > pointsNeeded) {
                long points = currentPoints - pointsNeeded;
                Rank nextRank = getNextPlayerRank(player);

                playerPoints.replace(player.getUniqueId(), points);
                playerRank.replace(player.getUniqueId(), nextRank);

                Bukkit.broadcastMessage(String.format(MessageManager.HONORRANKS_UPRANK_BROADCASTMESSAGE.getBroadcastMessage(),
                        player.getName(), nextRank.getRankId()));

                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 15, 1);
                //TODO: spawn particles all around the player
            } else {
                player.sendMessage(String.format(MessageManager.NO_ENOUGH_HONORPOINTS.getMessage(), "HR", currentPoints, pointsNeeded));
            }
        } else {
            player.sendMessage(String.format(MessageManager.MAX_HONORRANK_MESSAGE.getMessage(), "HR"));
        }
    }

    public long getPlayerPoints(@NotNull Player player) {
        return playerPoints.get(player.getUniqueId());
    }

    public long getPointsNeeded(@NotNull Player player) {
        if (getNextPlayerRank(player) != null) {
            return getNextPlayerRank(player).getHonorPoints();
        }
        return 0;
    }

    public Rank getPlayerRank(@NotNull Player player) {
        return playerRank.get(player.getUniqueId());
    }

    public int getPlayerRankId(@NotNull Player player) {
        if (isRanked(player)) {
            return getPlayerRank(player).getRankId();
        } else {
            return 0;
        }
    }

    public Rank getNextPlayerRank(@NotNull Player player) {
        int currentRankId = getPlayerRankId(player);
        int nextRankId = currentRankId + 1;

        if (currentRankId == 6) {
            return null;
        }
        return getRankById(nextRankId);
    }

    public String getDisplayName(@NotNull Player player) {
        return "§fRang d'Honneur §3" + getPlayerRankId(player);
    }

    public String getPrefix(@NotNull Player player) {
        return "§8[§3" + getPlayerRankId(player) + "§8]";
    }

    private Rank getRankById(int id) {
        return Rank.BY_ID.get(id);
    }

    public String getRanks(Player player) {
        StringBuilder sb = new StringBuilder();

        for (Rank i : Rank.values()) {
            sb.append(" §8|§f Rang §3").append(i.getRankId()).append(" §8(§3").append(i.getHonorPoints()).append(" §7Points d'Honneur§8)");

            if (i == getNextPlayerRank(player)) {
                sb.append(" §3§l« PROCHAIN");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isRanked(@NotNull Player player) {
        return playerRank.get(player.getUniqueId()) != null;
    }
}
