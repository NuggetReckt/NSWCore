package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnfreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            NSWCore nswCore = NSWCore.getInstance();

            if (NSWCore.getInstance().isStaff(player)) {
                if (args.length >= 1) {
                    Player target = nswCore.getPlayerByName(args[0]);
                    assert target != null;

                    if (player != target) {
                        if (NSWCore.getStaffUtils().isFrozen(target)) {
                            NSWCore.getStaffUtils().setFrozen(target, false);
                            player.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_STAFF.getMessage(), "Staff", target.getName()));
                            target.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_TARGET.getMessage(), "NSW", player.getName()));
                        } else {
                            //Le joueur n'est pas freeze
                            player.sendMessage(String.format(MessageManager.PLAYER_NOT_FROZEN.getMessage(), "Staff"));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_HIMSELF.getMessage(), "Staff"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "Staff", command.getUsage()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_CMD.getMessage(), "Staff"));
            }
        }
        return true;
    }
}
