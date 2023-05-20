package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.HonorRanks;
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
            HonorRanks honorRanks = new HonorRanks();

            if (args.length == 0) {
                player.sendMessage(String.format(MessageManager.HONORRANKS_RANKLIST_MESSAGE.getMessage(), "HR", honorRanks.getRanks()));
            } else {
                if (args[0].equalsIgnoreCase("rank")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_RANK_MESSAGE.getMessage(), "HR", honorRanks.getDisplayName(player)));
                } else if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_RANKINFO_MESSAGE.getMessage(), "HR",
                            honorRanks.getPlayerRankId(player), honorRanks.getNextPlayerRank(player).getRankId(),
                            honorRanks.getPlayerPoints(player), honorRanks.getPointsNeeded(player)));
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    honorRanks.upRankPlayer(player);
                } else if (args[0].equalsIgnoreCase("test")) {
                    if (args.length != 2) {
                        player.sendMessage("merci de spécifier un montant");
                    } else {
                        long value = Long.parseLong(args[1]);
                        honorRanks.gainPlayerPoints(player, value);
                    }
                }
            }
        }
        return true;
    }
}
