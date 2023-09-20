package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.StaffUtils;
import org.bukkit.Bukkit;
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
        StaffUtils staffUtils = NSWCore.getStaffUtils();

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

        if (staffUtils.isFrozen(player)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (NSWCore.getInstance().isStaff(p)) {
                    p.sendMessage(String.format(MessageManager.PLAYER_FROZEN_QUIT.getWarnMessage(), player.getName()));
                }
            }
            NSWCore.getInstance().getLogger().info("\033[0;36mLe joueur \033[0;31m" + player.getName() + "\033[0;36m s'est déconnecté en étant freeze.\033[0m");
        }
        if (NSWCore.getInstance().isStaff(player)) {
            if (staffUtils.isStaffMode(player)) {
                staffUtils.toggleStaffMode(player);
            }
        }
    }
}
