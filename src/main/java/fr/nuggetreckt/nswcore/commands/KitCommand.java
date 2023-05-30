package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.impl.KitGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            //TO UNCOMMENT AFTER TESTS
            /*
            if (NSWCore.isFarmzone()) {
                NSWCore.getGuiManager().open(player, KitGui.class);
            } else {
                player.sendMessage(String.format(MessageManager.COMMAND_NOT_AVAILABLE_FARMZONE_MESSAGE.getMessage(), "Kit"));
            }
             */
            NSWCore.getGuiManager().open(player, KitGui.class);
        }
        return true;
    }
}
