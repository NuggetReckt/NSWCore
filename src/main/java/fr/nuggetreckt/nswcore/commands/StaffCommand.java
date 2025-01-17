package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public class StaffCommand implements CommandExecutor, Listener {

    private final NSWCore instance;

    public StaffCommand(NSWCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (instance.isStaff(player)) {
                instance.getStaffUtils().toggleStaffMode(player);
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_CMD.getMessage(), "Staff"));
            }
        }
        return true;
    }
}
