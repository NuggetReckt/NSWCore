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
                String content;

                switch (args[0]) {
                    case "honneur":
                        content = """
                                §fCommandes :
                                 §8| §3/hr §8(§7Affiche la progression de ton honneur, la liste des rangs et leur points requis§8)
                                 §8| §3/hr info §8(§7Affiche votre rang actuels le prochain et le nombre de points requis pour passer à celui-ci§8)
                                 §8| §3/hr points §8(§7Affiche le nombre de points que tu possèdes§8)
                                 §8| §3/hr points §8(§7Affiche ton rang actuel§8)
                                 §8| §3/hr upgrade §8(§7Passe au rang suivant si le quota de points est atteint§8)
                                 
                                §fInfos :
                                 §8| §7Les points d'honneur s'obtiennent en participant à des events, et en montant de niveau dans les jobs §8(§3/help jobs§8)§7.
                                 §8| §7Le rang max est le rang §36§7.
                                """;
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Rangs d'Honneur"));
                        player.sendMessage(content);
                        break;
                    case "jobs":
                        content = """
                                §fCommandes :
                                
                                §fInfos :
                                """;
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Jobs"));
                        player.sendMessage(content);
                        break;
                    case "autres":
                        content = """
                                §fCommandes :
                                
                                """;
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Autres"));
                        player.sendMessage(content);
                        break;
                    case "liens":
                        content = """
                                §fLiens utiles :
                                 §8| §fSite internet §8§l»§r §3§nhttps://play.noskillworld.fr§r
                                 §8| §fDynmap §8§l»§r §3§nhttps://dynmap.noskillworld.fr§r
                                 §8| §fPage de statut §8§l»§r §3§nhttps://statut.noskillworld.fr§r
                                 
                                §fRéseaux sociaux :
                                 §8| §fYouTube §8§l»§r §3§nhttps://www.youtube.com/@noskillworld_mc§r
                                 §8| §fTikTok §8§l»§r §3§nhttps://www.tiktok.com/@noskillworld_mc§r
                                 §8| §fInstagram §8§l»§r §3§nhttps://www.instagram.com/noskillworld§r
                                 §8| §fTwitter §8§l»§r §3§nhttps://twitter.com/NoSkillWorld§r
                                 §8| §fDiscord §8§l»§r §3§nhttps://discord.noskillworld.fr§r
                                """;
                        player.sendMessage(String.format(MessageManager.HELP_SECTION.getMessage(), "Aide", "Liens"));
                        player.sendMessage(content);
                        break;
                    default:
                        player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ARGS.getMessage(), "Aide", command.getUsage()));
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
