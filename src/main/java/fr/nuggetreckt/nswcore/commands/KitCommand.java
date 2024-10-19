package fr.nuggetreckt.nswcore.commands;

import fr.noskillworld.api.NSWAPI;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.KitGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class KitCommand implements CommandExecutor {

    private final NSWAPI nswapi;

    public KitCommand(@NotNull NSWCore instance) {
        this.nswapi = instance.getAPI();
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            nswapi.getGuiManager().open(player, KitGui.class);
        }
        return true;
    }
}
