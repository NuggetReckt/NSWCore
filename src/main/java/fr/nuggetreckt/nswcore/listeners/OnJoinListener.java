package fr.nuggetreckt.nswcore.listeners;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import fr.nuggetreckt.nswcore.HonorRanks;
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
        String nsw = IridiumColorAPI.process("<GRADIENT:2C70BA>§lNoSkillWorld</GRADIENT:42cfcf>");

        HonorRanks hr = NSWCore.getHonorRanks();
        NSWCore.getTeleportUtils().initTeleports(player);

        NSWCore.getServerHandler().getExecutor().execute(() -> hr.init(player));
        NSWCore.getStaffUtils().init(player);

        if (!player.hasPlayedBefore()) {
            if (NSWCore.isFarmzone()) {
                player.sendMessage("§8[§3NSW§8] §fBienvenue en §3§lFarmZone §f!");
                player.sendMessage(" §8| §fUtilise §3/rtp §fpour te téléporter aléatoirement");
                player.sendMessage(" §8| §fUtilise §3/kit §fpour réclamer ton kit");
            } else {
                Bukkit.broadcastMessage(String.format(MessageManager.WELCOME_PLAYER_JOIN_MESSAGE.getBroadcastMessage(), player.getName(), nsw));
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
