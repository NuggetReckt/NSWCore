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

            if (player.hasPermission("group.staff")) {
                if (args.length >= 1) {
                    Player target = nswCore.getPlayerByName(args[0]);
                    assert target != null;

                    if (player != target) {
                        if (NSWCore.getStaffUtils().isFrozen(target)) {
                            NSWCore.getStaffUtils().setFrozen(target, false);
                            player.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_STAFF.getMessage(), "NSW", target.getName()));
                            target.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_TARGET.getMessage(), "NSW", player.getName()));
                        } else {
                            //Le joueur n'est pas freeze
                            player.sendMessage(String.format(MessageManager.PLAYER_NOT_FROZEN.getMessage(), "NSW"));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_HIMSELF.getMessage(), "NSW"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "NSW"));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION.getMessage(), "NSW"));
            }
        }
        return true;
    }
}
