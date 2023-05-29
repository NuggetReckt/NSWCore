package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class HonorRankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            HonorRanks hr = NSWCore.getHonorRanks();

            if (args.length == 0) {
                player.sendMessage(String.format(MessageManager.HONORRANKS_RANKLIST_MESSAGE.getMessage(), "HR", hr.getRanks(player)));
            } else {
                if (args[0].equalsIgnoreCase("rank")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_RANK_MESSAGE.getMessage(), "HR", hr.getDisplayName(player)));
                } else if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_RANKINFO_MESSAGE.getMessage(), "HR",
                            hr.getPlayerRankId(player), hr.getNextPlayerRank(player).getRankId(),
                            hr.getPlayerPoints(player), hr.getPointsNeeded(player)));
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    hr.upRankPlayer(player);
                } else if (args[0].equalsIgnoreCase("test")) {
                    if (args.length != 2) {
                        player.sendMessage("merci de spécifier un montant");
                    } else {
                        long value = Long.parseLong(args[1]);
                        hr.gainPlayerPoints(player, value);
                    }
                }
            }
        }
        return true;
    }
}
