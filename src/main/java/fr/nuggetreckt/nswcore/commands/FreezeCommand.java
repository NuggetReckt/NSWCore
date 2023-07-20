package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.hasPermission("group.staff")) {
                Player target = NSWCore.getPlayerByName(args[0]);
                assert target != null;

                if (!NSWCore.getStaffUtils().isFrozen(target)) {
                    NSWCore.getStaffUtils().setFrozen(target, true);
                    player.sendMessage(String.format(MessageManager.PLAYER_FREEZE_MESSAGE_STAFF.getMessage(), "NSW", target.getName()));
                    target.sendMessage(String.format(MessageManager.PLAYER_FREEZE_MESSAGE_TARGET.getMessage(), "NSW", player.getName()));
                } else {
                    //Le joueur est déja freeze
                    player.sendMessage(String.format(MessageManager.PLAYER_ALREADY_FROZEN_MESSAGE.getMessage(), "NSW"));
                }
            }
        }
        return true;
    }
}
