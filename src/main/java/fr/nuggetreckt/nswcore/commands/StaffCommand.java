package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class StaffCommand implements CommandExecutor {

    private boolean isStaffMode;

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            setStaffMode(false);

            if (player.hasPermission("group.staff")) {
                if (!isStaffMode) {
                    setStaffMode(true);
                    player.sendMessage(String.format(MessageManager.STAFF_MODE_ENTER_MESSAGE.getMessage(), "Staff"));
                } else {
                    setStaffMode(false);
                    player.sendMessage(String.format(MessageManager.STAFF_MODE_LEAVE_MESSAGE.getMessage(), "Staff"));
                }
            }
        }
        return true;
    }

    private void setStaffMode(boolean isStaffMode) {
        this.isStaffMode = isStaffMode;
    }
}
