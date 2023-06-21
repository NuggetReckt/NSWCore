package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class OnLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();

        HonorRanks hr = NSWCore.getHonorRanks();
        NSWCore.getServerHandler().getExecutor().execute(() -> new Requests().updatePlayerData(player, hr.getPlayerRankId(player), hr.getPlayerPoints(player)));

        if (player.isOp() || player.hasPermission("group.admin")) {
            event.setQuitMessage("§8[§4-§8] §8[§4§lAdministrateur§8] §4" + player.getName() + " §fa quitté le serveur");
        } else if (player.hasPermission("group.responsable")) {
            event.setQuitMessage("§8[§4-§8] §8[§6§lResponsable§8] §6" + player.getName() + " §fa quitté le serveur");
        } else if (player.hasPermission("group.moderator")) {
            event.setQuitMessage("§8[§4-§8] §8[§9§lModérateur§8] §9" + player.getName() + " §fa quitté le serveur");
        } else if (player.hasPermission("group.helper")) {
            event.setQuitMessage("§8[§4-§8] §8[§b§lGuide§8] §b" + player.getName() + " §fa quitté le serveur");
        } else if (player.hasPermission("group.developpeur")) {
            event.setQuitMessage("§8[§4-§8]] §8[§5§lDéveloppeur§8] §5" + player.getName() + " §fa quitté le serveur");
        } else {
            event.setQuitMessage("§8[§4-§8] §3" + player.getName());
        }
    }
}
