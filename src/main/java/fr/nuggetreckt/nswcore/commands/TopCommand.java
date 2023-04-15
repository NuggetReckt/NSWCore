package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class TopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.hasPermission("nsw.commands.top")) {
                player.sendMessage(NSWCore.getPrefix() + "Oui bon");
            } else {
                player.sendMessage(NSWCore.getPrefix() + "§cVous n'avez pas la permission d'exécuter cette commande !");
            }
        }
        return true;
    }
}
