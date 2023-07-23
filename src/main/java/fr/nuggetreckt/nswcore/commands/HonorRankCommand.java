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
                player.sendMessage(String.format(MessageManager.HONORRANKS_RANKLIST.getMessage(), "HR", hr.getRanks(player)));
            } else {
                if (args[0].equalsIgnoreCase("rank")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_RANK.getMessage(), "HR", hr.getDisplayName(player)));
                } else if (args[0].equalsIgnoreCase("points")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_POINTS.getMessage(), "HR", hr.getPlayerPoints(player)));
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (hr.getNextPlayerRank(player) != null) {
                        player.sendMessage(String.format(MessageManager.HONORRANKS_RANKINFO.getMessage(), "HR",
                                hr.getPlayerRankId(player), hr.getNextPlayerRank(player).getRankId(),
                                hr.getPlayerPoints(player), hr.getPointsNeeded(player)));
                    } else {
                        player.sendMessage(String.format(MessageManager.HONORRANKS_RANKINFO_MAX.getMessage(), "HR", hr.getPlayerRankId(player)));
                    }
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    hr.upRankPlayer(player);
                } else if (args[0].equalsIgnoreCase("admin")) {
                    if (player.hasPermission("nsw.commands.admin")) {
                        if (args[1].equalsIgnoreCase("give")) {
                            if (args.length == 4) {
                                Player target = NSWCore.getPlayerByName(args[2]);
                                long value = Long.parseLong(args[3]);
                                assert target != null;

                                hr.gainPlayerPoints(target, value);
                                player.sendMessage(String.format(MessageManager.SUCCESS_GIVEHP.getMessage(), "HR", value, target.getName()));
                                target.sendMessage(String.format(MessageManager.SUCCESS_GIVEHP_OTHER.getMessage(), "HR", player.getName(), value));
                            } else {
                                player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                            }
                        } else if (args[1].equalsIgnoreCase("upgrade")) {
                            if (args.length == 3) {
                                Player target = NSWCore.getPlayerByName(args[2]);
                                assert target != null;

                                hr.forceUpRankPlayer(target);
                                player.sendMessage(String.format(MessageManager.SUCCESS_UPGRADE.getMessage(), "HR", target.getName()));
                                target.sendMessage(String.format(MessageManager.SUCCESS_UPGRADE_OTHER.getMessage(), "HR", target.getName()));
                            } else {
                                player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                            }
                        } else {
                            player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.NO_PERMISSION.getMessage(), "HR"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.UNKNOWN_SUBCOMMAND.getMessage(), "HR"));
                }
            }
        }
        return true;
    }
}
