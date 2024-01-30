package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                TextComponent honorRanks = createComponent("Rangs d'Honneur", "honneur", "Commandes relatives aux Rangs d'Honneur");
                TextComponent jobs = createComponent("Jobs", "jobs", "Commandes relatives aux Jobs");
                TextComponent others = createComponent("Autres", "autres", "Autres commandes");
                TextComponent links = createComponent("Liens", "liens", "Liens utiles");

                player.sendMessage(String.format(MessageManager.HELP_MAIN.getMessage(), "Aide", NSWCore.getInstance().getColoredName()));
                player.spigot().sendMessage(
                        new TextComponent("\n     "), honorRanks,
                        new TextComponent("     "), jobs,
                        new TextComponent("     "), others,
                        new TextComponent("     "), links
                );
            } else {
                switch (args[0]) {
                    case "honneur":
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Rangs d'Honneur",
                                MessageManager.HONORRANKS_HELP.getRawMessage()));
                        break;
                    case "jobs":
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Jobs",
                                MessageManager.JOBS_HELP.getRawMessage()));
                        break;
                    case "autres":
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Autres",
                                MessageManager.OTHER_HELP.getRawMessage()));
                        break;
                    case "liens":
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Liens",
                                MessageManager.LINKS_HELP.getRawMessage()));
                        break;
                    default:
                        player.sendMessage(String.format(MessageManager.INCORRECT_ARGS.getMessage(), "Aide", command.getUsage()));
                        break;
                }
            }
        }
        return true;
    }

    private @NotNull TextComponent createComponent(String name, String subCommand, String desc) {
        TextComponent message = new TextComponent("§8[§3" + name + "§8]");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + subCommand));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§8§l»§r §3Aide\n§8| §f" + desc)));
        return message;
    }
}
