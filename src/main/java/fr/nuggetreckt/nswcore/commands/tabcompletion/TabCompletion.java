package fr.nuggetreckt.nswcore.commands.tabcompletion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender player, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (command.getName().equalsIgnoreCase("honorrank")) {
            List<String> subCommands = new ArrayList<>();

            if (args.length == 1) {
                subCommands.add("rank");
                subCommands.add("points");
                subCommands.add("info");
                subCommands.add("upgrade");
                if (player.hasPermission("nsw.commands.admin")) {
                    subCommands.add("admin");
                }
                return subCommands;
            } else if (args.length == 2) {
                List<String> adminSubCommands = new ArrayList<>();

                if (args[0].equalsIgnoreCase("admin")) {
                    adminSubCommands.add("give");
                    adminSubCommands.add("upgrade");
                    return adminSubCommands;
                }
            } else if (args.length == 3) {
                if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("upgrade")) {
                    return getPlayers();
                }
            }
        }
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (args.length == 1 && player.hasPermission("nsw.commands.admin")) {
                return getPlayers();
            }
        }
        return null;
    }

    private @NotNull List<String> getPlayers() {
        List<String> playerList = new ArrayList<>();

        for (Player i : Bukkit.getOnlinePlayers()) {
            playerList.add(i.getName());
        }
        return playerList;
    }
}
