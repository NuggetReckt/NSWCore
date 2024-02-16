package fr.nuggetreckt.nswcore.listeners;

import fr.noskillworld.api.entities.NSWPlayer;
import fr.noskillworld.api.honorranks.HonorRanksHandler;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class OnJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        NSWPlayer nswPlayer = new NSWPlayer(player.getName(), player.getUniqueId());
        String nsw = NSWCore.getInstance().getColoredName();

        HonorRanksHandler hr = NSWCore.getAPI().getHonorRanksHandler();
        NSWCore.getTeleportUtils().initTeleports(player);

        NSWCore.getAPI().getServerHandler().getExecutor().execute(() -> hr.init(nswPlayer));
        NSWCore.getStaffUtils().init(player);

        if (!player.hasPlayedBefore()) {
            if (NSWCore.getInstance().isFarmzone()) {
                player.sendMessage(String.format(MessageManager.FARMZONE_WELCOME.getMessage(), "NSW"));
            } else {
                Bukkit.broadcastMessage(String.format(MessageManager.WELCOME_PLAYER_JOIN.getBroadcastMessage(), player.getName(), nsw));
                player.teleport(NSWCore.getInstance().getSpawnLocation());
                // Uncomment after testing
                //player.openBook(new BookUtils().getWelcomeBook(player));
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
}
