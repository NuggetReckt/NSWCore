package fr.nuggetreckt.nswcore.commands;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.impl.HonorRanksHandlerImpl;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.RewardUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class HonorRankCommand implements CommandExecutor {

    private final NSWAPI nswapi;

    public HonorRankCommand(NSWAPI api) {
        this.nswapi = api;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        HonorRanksHandlerImpl hr = nswapi.getHonorRanksHandler();

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                player.sendMessage(String.format(MessageManager.HONORRANKS_RANKLIST.getMessage(), "HR", hr.getRanks(player.getUniqueId())));
            } else {
                if (args[0].equalsIgnoreCase("rank")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_RANK.getMessage(), "HR", hr.getDisplayName(player.getUniqueId())));
                } else if (args[0].equalsIgnoreCase("points")) {
                    player.sendMessage(String.format(MessageManager.HONORRANKS_POINTS.getMessage(), "HR", hr.getPlayerPoints(player.getUniqueId())));
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (hr.getNextPlayerRank(player.getUniqueId()) != null) {
                        player.sendMessage(String.format(MessageManager.HONORRANKS_RANKINFO.getMessage(), "HR",
                                hr.getPlayerRankFormat(player.getUniqueId()), hr.getRankFormat(hr.getNextPlayerRank(player.getUniqueId())),
                                hr.getPlayerPoints(player.getUniqueId()), hr.getPointsNeeded(player.getUniqueId())));
                    } else {
                        player.sendMessage(String.format(MessageManager.HONORRANKS_RANKINFO_MAX.getMessage(), "HR", hr.getPlayerRankFormat(player.getUniqueId())));
                    }
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    if (hr.getNextPlayerRank(player.getUniqueId()) != null) {
                        if (hr.getPlayerPoints(player.getUniqueId()) >= hr.getPointsNeeded(player.getUniqueId())) {
                            hr.upRankPlayer(player.getUniqueId());
                            NSWCore.getEffectUtils().uprankEffect(player);
                            new RewardUtils(nswapi).setReward(player, hr.getPlayerRank(player.getUniqueId()));
                        } else {
                            player.sendMessage(String.format(MessageManager.NO_ENOUGH_HONORPOINTS.getMessage(), "HR", hr.getPlayerPoints(player.getUniqueId()), hr.getPointsNeeded(player.getUniqueId())));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.MAX_HONORRANK.getMessage(), "HR", hr.getRankFormat(hr.getPlayerRank(player.getUniqueId()))));
                    }
                } else if (args[0].equalsIgnoreCase("admin") && args.length > 1) {
                    if (player.hasPermission("nsw.commands.admin")) {
                        if (args[1].equalsIgnoreCase("give")) {
                            if (args.length == 4) {
                                Player target = NSWCore.getInstance().getPlayerByName(args[2]);
                                long value = Long.parseLong(args[3]);
                                assert target != null;

                                NSWCore.getEffectUtils().gainPointsEffect(player);
                                hr.gainPlayerPoints(target.getUniqueId(), value);
                                player.sendMessage(String.format(MessageManager.SUCCESS_GIVEHP.getMessage(), "HR", value, target.getName()));
                                target.sendMessage(String.format(MessageManager.SUCCESS_GIVEHP_OTHER.getMessage(), "HR", player.getName(), value));
                            } else {
                                player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                            }
                        } else if (args[1].equalsIgnoreCase("upgrade")) {
                            if (args.length == 3) {
                                Player target = NSWCore.getInstance().getPlayerByName(args[2]);
                                assert target != null;

                                if (hr.getNextPlayerRank(target.getUniqueId()) != null) {
                                    player.sendMessage(String.format(MessageManager.SUCCESS_UPGRADE.getMessage(), "HR", target.getName()));
                                    target.sendMessage(String.format(MessageManager.SUCCESS_UPGRADE_OTHER.getMessage(), "HR", player.getName()));
                                    hr.forceUpRankPlayer(target.getUniqueId());
                                    NSWCore.getEffectUtils().uprankEffect(player);
                                    new RewardUtils(nswapi).setReward(target, hr.getPlayerRank(target.getUniqueId()));
                                } else {
                                    player.sendMessage(String.format(MessageManager.MAX_HONORRANK_OTHER.getMessage(), "HR", target.getName()));
                                }
                            } else {
                                player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                            }
                        } else {
                            player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.NO_PERMISSION_CMD.getMessage(), "HR"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.UNKNOWN_SUBCOMMAND.getMessage(), "HR"));
                }
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (args[0].equalsIgnoreCase("admin")) {
                if (args[1].equalsIgnoreCase("give")) {
                    if (args.length == 4) {
                        Player target = NSWCore.getInstance().getPlayerByName(args[2]);
                        long value = Long.parseLong(args[3]);
                        assert target != null;

                        hr.gainPlayerPoints(target.getUniqueId(), value);
                        commandSender.sendMessage(String.format(MessageManager.SUCCESS_GIVEHP.getMessage(), "HR", value, target.getName()));
                        target.sendMessage(String.format(MessageManager.SUCCESS_GIVEHP_OTHER.getMessage(), "HR", "Console", value));
                    } else {
                        commandSender.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                    }
                } else if (args[1].equalsIgnoreCase("upgrade")) {
                    if (args.length == 3) {
                        Player target = NSWCore.getInstance().getPlayerByName(args[2]);
                        assert target != null;

                        if (hr.getNextPlayerRank(target.getUniqueId()) != null) {
                            commandSender.sendMessage(String.format(MessageManager.SUCCESS_UPGRADE.getMessage(), "HR", target.getName()));
                            target.sendMessage(String.format(MessageManager.SUCCESS_UPGRADE_OTHER.getMessage(), "HR", "Console"));
                            hr.forceUpRankPlayer(target.getUniqueId());
                            new RewardUtils(nswapi).setReward(target, hr.getPlayerRank(target.getUniqueId()));
                        } else {
                            commandSender.sendMessage(String.format(MessageManager.MAX_HONORRANK_OTHER.getMessage(), "HR", target.getName()));
                        }
                    } else {
                        commandSender.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                    }
                } else {
                    commandSender.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "HR", command.getUsage()));
                }
            }
        }
        return true;
    }
}
