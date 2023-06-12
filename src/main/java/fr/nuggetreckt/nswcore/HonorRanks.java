package fr.nuggetreckt.nswcore;

import fr.nuggetreckt.nswcore.database.Requests;
import fr.nuggetreckt.nswcore.utils.EffectUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HonorRanks {
    public enum Rank {
        Rank_1(1, 10),
        Rank_2(2, 30),
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
        if (new Requests().hasJoinedOnce(player)) {
            int rankId = new Requests().getPlayerRankId(player);
            long points = new Requests().getPlayerPoints(player);

            playerRank.putIfAbsent(player.getUniqueId(), getRankById(rankId));
            playerPoints.putIfAbsent(player.getUniqueId(), points);
        } else {
            playerRank.putIfAbsent(player.getUniqueId(), null);
            playerPoints.putIfAbsent(player.getUniqueId(), 0L);
            new Requests().initPlayerData(player);
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

                new EffectUtils().uprankEffect(player);
            } else {
                player.sendMessage(String.format(MessageManager.NO_ENOUGH_HONORPOINTS.getMessage(), "HR", currentPoints, pointsNeeded));
            }
        } else {
            player.sendMessage(String.format(MessageManager.MAX_HONORRANK_MESSAGE.getMessage(), "HR"));
        }
    }

    public void forceUpRankPlayer(@NotNull Player player) {
        if (getNextPlayerRank(player) != null) {
            Rank nextRank = getNextPlayerRank(player);
            playerRank.replace(player.getUniqueId(), nextRank);

            Bukkit.broadcastMessage(String.format(MessageManager.HONORRANKS_UPRANK_BROADCASTMESSAGE.getBroadcastMessage(),
                    player.getName(), nextRank.getRankId()));

            new EffectUtils().uprankEffect(player);
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
        int rankId = getPlayerRankId(player);
        String format = switch (rankId) {
            case 0 -> "§7" + rankId;
            case 1 -> "§a" + rankId;
            case 2 -> "§2" + rankId;
            case 3 -> "§b" + rankId;
            case 4 -> "§3" + rankId;
            case 5 -> "§9" + rankId;
            case 6 -> "§1§l" + rankId;
            default -> null;
        };
        return "§8[" + format + "§8]";
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
