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

            if (args.length == 0) {
                player.sendMessage(String.format(MessageManager.REPORT_TYPELIST_MESSAGE.getMessage(), "Reports", NSWCore.getReportUtils().getTypes()));
            } else {
                Player target = NSWCore.getPlayerByName(args[0]);
                Type type = NSWCore.getReportUtils().getTypeByName(args[1]);
                String reason = getNextArgs(args);

                if (target != null && type != null && reason != null) {
                    NSWCore.getReportUtils().createReport(player, target, type, reason);
                    player.sendMessage(String.format(MessageManager.SUCCESS_PLAYER_REPORTED.getMessage(), "Reports", target.getName()));
                } else {
                    //Arguments insuffisants
                    player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS_MESSAGE.getMessage(), "Reports"));
                }
            }
        }
        return true;
    }

    private String getNextArgs(String @NotNull [] args) {
        StringJoiner joiner = new StringJoiner(" ");
        for (int i = 3; i < args.length; i++)
            joiner.add(args[i]);
        return joiner.toString();
    }
}
