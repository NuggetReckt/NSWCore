package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class CraftCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.hasPermission("nsw.commands.craft")) {
                player.openWorkbench(null, true);
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "NSW"));
            }
        }
        return true;
    }
}
