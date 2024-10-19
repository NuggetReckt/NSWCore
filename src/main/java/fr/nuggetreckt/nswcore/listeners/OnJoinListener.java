package fr.nuggetreckt.nswcore.listeners;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.HonorRanksHandler;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.BookUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class OnJoinListener implements Listener {

    private final NSWCore instance;
    private final NSWAPI nswapi;

    public OnJoinListener(@NotNull NSWCore instance) {
        this.instance = instance;
        this.nswapi = instance.getAPI();
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String nsw = instance.getColoredName();

        HonorRanksHandler hr = nswapi.getHonorRanksHandler();
        instance.getTeleportUtils().initTeleports(player);

        Bukkit.getScheduler().runTaskLater(instance, () -> instance.getMoneyGiveTask().start(player), instance.getMoneyGiveTask().getSecondsWait() * 20L);

        nswapi.getServerHandler().getExecutor().execute(() -> hr.init(player.getUniqueId(), player.getName()));
        instance.getStaffUtils().init(player);

        if (!player.hasPlayedBefore()) {
            if (instance.isFarmzone()) {
                player.sendMessage(String.format(MessageManager.FARMZONE_WELCOME.getMessage(), "NSW"));
            } else {
                Bukkit.broadcastMessage(String.format(MessageManager.WELCOME_PLAYER_JOIN.getBroadcastMessage(), player.getName(), nsw));
                player.teleport(instance.getSpawnLocation());
                player.openBook(new BookUtils().getWelcomeBook(player));
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
