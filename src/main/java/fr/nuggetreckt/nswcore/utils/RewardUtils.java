package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.HonorRanks.Rank;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RewardUtils {

    public void setReward(Player player, @NotNull Rank rank) {
        switch (rank) {
            case Rank_1 -> {
                //500nsc
            }
            case Rank_2 -> {
                //1000nsc
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.top");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.bottom");
            }
            case Rank_3 -> {
                //2000nsc
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.up");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.down");
            }
            case Rank_4 -> {
                //5000nsc
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.craft");
            }
            case Rank_5 -> {
                //8000nsc
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.ec");
            }
            case Rank_6 -> {
                //10000nsc
                // /back
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.furnace");
            }
        }
        player.sendMessage(String.format(MessageManager.HONORANKS_UPRANK_REWARDS.getMessage(), "HR",
                NSWCore.getHonorRanks().getFormat(player), getRewards(rank)));
    }

    private @NotNull String getRewards(@NotNull Rank rank) {
        List<String> rewardsList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        switch (rank) {
            case Rank_1 -> rewardsList.add("2 claims supplémentaires");
            case Rank_2 -> {
                rewardsList.add("5 claims supplémentaires");
                rewardsList.add("Cooldown du RTP réduit");
            }
            case Rank_3 -> {
                rewardsList.add("5 claims supplémentaires");
                rewardsList.add("Accès aux commandes /top et /bottom");
            }
            case Rank_4 -> {
                rewardsList.add("5 claims supplémentaires");
                rewardsList.add("Accès aux commandes /up et /down");
            }
            case Rank_5 -> {
                rewardsList.add("5 claims supplémentaires");
                rewardsList.add("1 Home supplémentaire");
                rewardsList.add("Accès aux commandes /craft");
            }
            case Rank_6 -> {
                rewardsList.add("10 claims supplémentaires");
                rewardsList.add("1 Home supplémentaire");
                rewardsList.add("Accès aux commandes /ec et /furnace");
            }
        }
        sb.append("§fVoici vos récompenses : \n");

        for (String i : rewardsList) {
            sb.append(" §8|§f ").append(i).append("\n");
        }

        return sb.toString();
    }
}
