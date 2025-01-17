package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FreezeCommand implements CommandExecutor {

    private final NSWCore instance;

    public FreezeCommand(NSWCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (instance.isStaff(player)) {
                if (args.length >= 1) {
                    Player target = instance.getPlayerByName(args[0]);
                    assert target != null;

                    if (player != target) {
                        if (!instance.getStaffUtils().isFrozen(target)) {
                            instance.getStaffUtils().setFrozen(target, true);
                            player.sendMessage(String.format(MessageManager.PLAYER_FREEZE_STAFF.getMessage(), "Staff", target.getName()));
                            target.sendMessage(String.format(MessageManager.PLAYER_FREEZE_TARGET.getMessage(), "NSW", player.getName()));

                            target.sendTitle("§4§lVous êtes freeze", "§cRendez-vous sur le Discord §8(§3/discord§8)", 10, 200, 60);
                        } else {
                            //Le joueur est déja freeze
                            player.sendMessage(String.format(MessageManager.PLAYER_ALREADY_FROZEN.getMessage(), "Staff"));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.PLAYER_FREEZE_HIMSELF.getMessage(), "Staff"));
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
