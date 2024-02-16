package fr.nuggetreckt.nswcore.utils;

import fr.noskillworld.api.honorranks.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RewardUtils {

    public void setReward(Player player, @NotNull HonorRanks rank) {
        switch (rank) {
            case Rank_1 -> NSWCore.getEconomy().depositPlayer(player, 500.0);
            case Rank_2 -> {
                NSWCore.getEconomy().depositPlayer(player, 1000.0);
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.top");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.bottom");
            }
            case Rank_3 -> {
                NSWCore.getEconomy().depositPlayer(player, 2000.0);
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.up");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.down");
            }
            case Rank_4 -> {
                NSWCore.getEconomy().depositPlayer(player, 5000.0);
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.craft");
            }
            case Rank_5 -> {
                NSWCore.getEconomy().depositPlayer(player, 8000.0);
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.ec");
            }
            case Rank_6 -> {
                NSWCore.getEconomy().depositPlayer(player, 10000.0);
                // /back
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.furnace");
            }
        }
        player.sendMessage(String.format(MessageManager.HONORANKS_UPRANK_REWARDS.getMessage(), "HR",
                NSWCore.getAPI().getHonorRanksHandler().getPlayerRankFormat(player.getUniqueId()), getRewards(rank)));
    }

    private @NotNull String getRewards(@NotNull HonorRanks rank) {
        List<String> rewardsList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        switch (rank) {
            case Rank_1 -> rewardsList.add("500 NSc");
            case Rank_2 -> {
                rewardsList.add("1000 NSc");
                rewardsList.add("Accès aux commandes /top et /bottom");
            }
            case Rank_3 -> {
                rewardsList.add("2000 NSc");
                rewardsList.add("Accès aux commandes /up et /down");
            }
            case Rank_4 -> {
                rewardsList.add("5000 NSc");
                rewardsList.add("Accès à la commande /craft");
            }
            case Rank_5 -> {
                rewardsList.add("8000 NSc");
                rewardsList.add("Accès à la commande /ec");
            }
            case Rank_6 -> {
                rewardsList.add("10000 NSc");
                rewardsList.add("Accès à la commande /furnace");
            }
        }
        sb.append("§fVoici vos récompenses : \n");

        for (String i : rewardsList) {
            sb.append(" §8|§f ").append(i).append("\n");
        }
        return sb.toString();
    }
}
