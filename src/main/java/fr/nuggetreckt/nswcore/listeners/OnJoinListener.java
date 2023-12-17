package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class OnJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String nsw = NSWCore.getInstance().getColoredName();

        HonorRanks hr = NSWCore.getHonorRanks();
        NSWCore.getTeleportUtils().initTeleports(player);

        NSWCore.getServerHandler().getExecutor().execute(() -> hr.init(player));
        NSWCore.getStaffUtils().init(player);

        if (!player.hasPlayedBefore()) {
            if (NSWCore.getInstance().isFarmzone()) {
                player.sendMessage(String.format(MessageManager.FARMZONE_WELCOME.getMessage(), "NSW"));
            } else {
                Bukkit.broadcastMessage(String.format(MessageManager.WELCOME_PLAYER_JOIN.getBroadcastMessage(), player.getName(), nsw));
                send_welcome_book(player);
            }
        }
        if (player.isOp() || player.hasPermission("group.admin")) {
            event.setJoinMessage("§8[§2+§8] §8[§4§lAdministrateur§8] §4" + player.getName() + " §fa rejoint le serveur");
        } else if (player.hasPermission("group.responsable")) {
            event.setJoinMessage("§8[§2+§8] §8[§6§lResponsable§8] §6" + player.getName() + " §fa rejoint le serveur");
        } else if (player.hasPermission("group.moderator")) {
            event.setJoinMessage("§8[§2+§8] §8[§9§lModérateur§8] §9" + player.getName() + " §fa rejoint le serveur");
        } else if (player.hasPermission("group.helper")) {
            event.setJoinMessage("§8[§2+§8] §8[§b§lGuide§8] §b" + player.getName() + " §fa rejoint le serveur");
        } else if (player.hasPermission("group.developpeur")) {
            event.setJoinMessage("§8[§2+§8] §8[§5§lDéveloppeur§8] §5" + player.getName() + " §fa rejoint le serveur");
        } else {
            event.setJoinMessage("§8[§2+§8] §3" + player.getName());
        }
    }

    public void send_welcome_book(@NotNull Player player) {
        ItemStack welcomeBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) welcomeBook.getItemMeta();

        BaseComponent[] page1 = new ComponentBuilder(String.format("""
                §0Bienvenue, §3%s
                §0dans la ville de §3Kyukei§0.
                """, player.getName()))
                .create();

        BaseComponent[] page2 = new ComponentBuilder("Rejoins nos réseaux :\n")
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
        player.openBook(welcomeBook);
    }
}
