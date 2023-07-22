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
                if (args.length >= 1) {
                    Player target = NSWCore.getPlayerByName(args[0]);
                    assert target != null;

                    if (!NSWCore.getStaffUtils().isFrozen(target)) {
                        NSWCore.getStaffUtils().setFrozen(target, true);
                        player.sendMessage(String.format(MessageManager.PLAYER_FREEZE_MESSAGE_STAFF.getMessage(), "NSW", target.getName()));
                        target.sendMessage(String.format(MessageManager.PLAYER_FREEZE_MESSAGE_TARGET.getMessage(), "NSW", player.getName()));

                        target.sendTitle("§4§lVous êtes freeze", "§cRendez-vous sur le Discord §8(§3/discord§8)", 10, 200, 60);
                    } else {
                        //Le joueur est déja freeze
                        player.sendMessage(String.format(MessageManager.PLAYER_ALREADY_FROZEN_MESSAGE.getMessage(), "NSW"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS_MESSAGE.getMessage(), "NSW"));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "NSW"));
            }
        }
        return true;
    }
}
