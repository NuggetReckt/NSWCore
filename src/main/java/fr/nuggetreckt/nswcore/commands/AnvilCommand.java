package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class AnvilCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.hasPermission("nsw.commands.anvil")) {
                Inventory anvilInventory = Bukkit.createInventory(player, InventoryType.ANVIL);
                player.openInventory(anvilInventory);
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION.getMessage(), "NSW"));
            }
        }
        return true;
    }
}
