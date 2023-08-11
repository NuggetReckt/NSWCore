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
            case Rank_1 -> NSWCore.getLuckPermsUtils().setPermission(player, "lands.chunks.5");
            case Rank_2 -> {
                NSWCore.getLuckPermsUtils().unsetPermission(player, "lands.chunks.5");
                NSWCore.getLuckPermsUtils().setPermission(player, "lands.chunks.10");
            }
            case Rank_3 -> {
                NSWCore.getLuckPermsUtils().unsetPermission(player, "lands.chunks.10");
                NSWCore.getLuckPermsUtils().setPermission(player, "lands.chunks.15");

                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.top");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.bottom");
            }
            case Rank_4 -> {
                NSWCore.getLuckPermsUtils().unsetPermission(player, "lands.chunks.15");
                NSWCore.getLuckPermsUtils().setPermission(player, "lands.chunks.20");

                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.up");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.down");
            }
            case Rank_5 -> {
                NSWCore.getLuckPermsUtils().unsetPermission(player, "lands.chunks.20");
                NSWCore.getLuckPermsUtils().setPermission(player, "lands.chunks.25");

                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.craft");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.anvil");
            }
            case Rank_6 -> {
                NSWCore.getLuckPermsUtils().unsetPermission(player, "lands.chunks.25");
                NSWCore.getLuckPermsUtils().setPermission(player, "lands.chunks.35");

                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.ec");
                NSWCore.getLuckPermsUtils().setPermission(player, "nsw.command.furnace");
            }
        }
        player.sendMessage(String.format(MessageManager.HONORANKS_UPRANK_REWARDS.getMessage(), "HR", NSWCore.getHonorRanks().getFormat(player), getRewards(rank)));
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
                rewardsList.add("Accès aux commandes /craft et /anvil");
            }
            case Rank_6 -> {
                rewardsList.add("10 claims supplémentaires");
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
