package fr.nuggetreckt.nswcore.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class BookUtils {

    public ItemStack getWelcomeBook(@NotNull Player player) {
        ItemStack welcomeBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) welcomeBook.getItemMeta();

        BaseComponent[] page1 = new ComponentBuilder(String.format("""
                §0Bienvenue, §3%s
                §0dans la ville de §3Kyukei§0.
                                
                §0Ici, tu es protégé.
                §0Mais si tu veux partir à l'aventure, sors de la ville en suivant la direction que tu souhaites !
                                
                                
                §8| §0Bon jeu sur
                §8| §3§lNoSkillWorld §0!
                """, player.getName()))
                .create();

        BaseComponent[] page2 = new ComponentBuilder("§0Rejoins nos réseaux :\n")
                .append("§0- §3§nDiscord\n")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.noskillworld.fr"))
                .append("§0- §3§nTwitter\n")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/NoSkillWorld"))
                .append("§0- §3§nInstagram\n")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.instagram.com/noskillworld/"))
                .append("§0- §3§nYouTube\n")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/@noskillworld_mc"))
                .append("§0- §3§nTikTok\n")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.tiktok.com/@noskillworld_mc"))
                .create();

        assert bookMeta != null;
        bookMeta.setAuthor("Bobby");
        bookMeta.setTitle("§3Welcome Book");
        bookMeta.spigot().addPage(page1);
        bookMeta.spigot().addPage(page2);
        welcomeBook.setItemMeta(bookMeta);
        return welcomeBook;
    }
}
