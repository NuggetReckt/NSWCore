package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.ReportUtils.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            NSWCore nswCore = NSWCore.getInstance();

            if (args.length == 0) {
                player.sendMessage(String.format(MessageManager.REPORT_TYPELIST.getMessage(), "Reports", NSWCore.getReportUtils().getTypes()));
            } else if (args.length >= 3) {
                Player target = nswCore.getPlayerByName(args[0]);
                String typeStr = args[1];
                String reason = getNextArgs(args);

                if (nswCore.hasJoinedOnce(target)) {
                    if (NSWCore.getReportUtils().getTypes().contains(typeStr)) {
                        Type type = NSWCore.getReportUtils().getTypeByName(typeStr);
                        NSWCore.getReportUtils().createReport(player, target, type, reason);
                        player.sendMessage(String.format(MessageManager.SUCCESS_PLAYER_REPORTED.getMessage(), "Reports", target.getName()));
                    } else {
                        player.sendMessage(String.format(MessageManager.UNKNOWN_REPORT_TYPE.getMessage(), "Reports", NSWCore.getReportUtils().getTypes()));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.UNKNOWN_PLAYER.getMessage()));
                }
            } else {
                //Arguments insuffisants
                player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "Reports", command.getUsage()));
            }
        }
        return true;
    }

    private @NotNull String getNextArgs(String @NotNull [] args) {
        StringJoiner joiner = new StringJoiner(" ");
        for (int i = 2; i < args.length; i++)
            joiner.add(args[i]);
        return joiner.toString().replaceAll("'", "’");
    }
}
