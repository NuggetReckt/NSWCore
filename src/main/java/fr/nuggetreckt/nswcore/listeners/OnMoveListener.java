package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.TeleportUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DataFlowIssue")
public class OnMoveListener implements Listener {

    private final NSWCore instance;

    public OnMoveListener(NSWCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        TeleportUtils teleportUtils = instance.getTeleportUtils();

        if (teleportUtils.isTeleporting(player)) {
            int x = (int) event.getFrom().getX();
            int y = (int) event.getFrom().getY();
            int z = (int) event.getFrom().getZ();
            int toX = (int) event.getTo().getX();
            int toY = (int) event.getTo().getY();
            int toZ = (int) event.getTo().getZ();

            if (x != toX || y != toY || z != toZ) {
                teleportUtils.setTeleports(player, false);
                player.sendMessage(String.format(MessageManager.PLAYER_MOVED_TP.getMessage(), "TP"));
                instance.getPlayerDelayTask().stop(player);
            }
        }

        if (instance.getStaffUtils().isFrozen(player)) {
            double x = event.getFrom().getX();
            double y = event.getFrom().getY();
            double z = event.getFrom().getZ();
            double toX = event.getTo().getX();
            double toY = event.getTo().getY();
            double toZ = event.getTo().getZ();

            if (x != toX || y != toY || z != toZ) {
                event.setCancelled(true);
            }
        }
    }
}
