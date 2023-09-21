package fr.nuggetreckt.nswcore;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import fr.nuggetreckt.nswcore.database.Requests;
import fr.nuggetreckt.nswcore.utils.EffectUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.RewardUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class HonorRanks {
    public enum Rank {
        Rank_1(1, 10, "<SOLID:8afc6e>"),
        Rank_2(2, 30, "<SOLID:65e5a0>"),
        Rank_3(3, 60, "<SOLID:42cfcf>"),
        Rank_4(4, 100, "<SOLID:379ec4>"),
        Rank_5(5, 150, "<SOLID:2c70ba>"),
        Rank_6(6, 250, "<SOLID:9C1FA4>§l"),
        ;

        private static final Map<Integer, Rank> BY_ID = new HashMap<>();

        static {
            for (Rank i : values()) {
                BY_ID.put(i.rankId, i);
            }
        }

        private final int rankId;
        private final long honorPoints;
        private final String colorCode;

        Rank(int lvl, long pts, String code) {
            this.rankId = lvl;
            this.honorPoints = pts;
            this.colorCode = code;
        }

        public int getRankId() {
            return this.rankId;
        }

        public long getHonorPoints() {
            return this.honorPoints;
        }

        public String getColorCode() {
            return this.colorCode;
        }
    }

    private final Map<UUID, @Nullable Rank> playerRank = new HashMap<>();
    private final Map<UUID, Long> playerPoints = new HashMap<>();

    public void init(@NotNull Player player) {
        if (NSWCore.getInstance().hasJoinedOnce(player)) {
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
        new EffectUtils().gainPointsEffect(player);
    }

    public void upRankPlayer(@NotNull Player player) {
        if (getNextPlayerRank(player) != null) {
            long currentPoints = getPlayerPoints(player);
            long pointsNeeded = getPointsNeeded(player);

            if (currentPoints >= pointsNeeded) {
                long points = currentPoints - pointsNeeded;
                Rank nextRank = getNextPlayerRank(player);

                playerPoints.replace(player.getUniqueId(), points);
                playerRank.replace(player.getUniqueId(), nextRank);

                Bukkit.broadcastMessage(String.format(MessageManager.HONORRANKS_UPRANK_BROADCAST.getBroadcastMessage(),
                        player.getName(), getFormat(player)));

                new RewardUtils().setReward(player, nextRank);
                NSWCore.getEffectUtils().uprankEffect(player);
            } else {
                player.sendMessage(String.format(MessageManager.NO_ENOUGH_HONORPOINTS.getMessage(), "HR", currentPoints, pointsNeeded));
            }
        } else {
            player.sendMessage(String.format(MessageManager.MAX_HONORRANK.getMessage(), "HR", NSWCore.getHonorRanks().getFormat(player)));
        }
    }

    public void forceUpRankPlayer(@NotNull Player player) {
        if (getNextPlayerRank(player) != null) {
            Rank nextRank = getNextPlayerRank(player);
            playerRank.replace(player.getUniqueId(), nextRank);

            Bukkit.broadcastMessage(String.format(MessageManager.HONORRANKS_UPRANK_BROADCAST.getBroadcastMessage(),
                    player.getName(), getFormat(getPlayerRank(player))));

            new RewardUtils().setReward(player, nextRank);
            NSWCore.getEffectUtils().uprankEffect(player);
        }
    }

    public void saveAllPlayerData() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                NSWCore.getServerHandler().getExecutor().execute(() -> new Requests().updatePlayerData(player, getPlayerRankId(player), getPlayerPoints(player)));
            }
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

    public List<Rank> getPreviousPlayerRanks(Player player) {
        int currentRankId = getPlayerRankId(player);
        List<Rank> rankList = new ArrayList<>();

        for (int i = 1; i <= currentRankId; i++) {
            rankList.add(getRankById(i));
        }
        return rankList;
    }

    public String getDisplayName(@NotNull Player player) {
        return "§fRang d'Honneur " + getFormat(player);
    }

    public String getPrefix(@NotNull Player player) {
        return "§8[" + getFormat(getPlayerRank(player)) + "§8]";
    }

    public String getFormat(Player player) {
        Rank rank = getPlayerRank(player);
        return getFormat(rank);
    }

    public String getFormat(Rank rank) {
        if (rank == null) {
            return "§70";
        } else {
            return IridiumColorAPI.process(rank.getColorCode() + rank.getRankId());
        }
    }

    private Rank getRankById(int id) {
        return Rank.BY_ID.get(id);
    }

    public String getRanks(Player player) {
        StringBuilder sb = new StringBuilder();

        for (Rank i : Rank.values()) {
            sb.append("§r §8|§f Rang ").append(getFormat(i))
                    .append(" §8(§3").append(i.getHonorPoints())
                    .append(" §7Points d'Honneur§8)");

            if (getPreviousPlayerRanks(player).contains(i)) {
                sb.append(" §8[§a✔§8]");
            }
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
