package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class OnRespawnListener implements Listener {

    private final NSWCore instance;

    public OnRespawnListener(NSWCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (event.getRespawnReason() == PlayerRespawnEvent.RespawnReason.DEATH) {
            if (event.isBedSpawn() || event.isAnchorSpawn()) {
                return;
            }
            if (!instance.isFarmzone()) {
                player.sendMessage(String.format(MessageManager.RESPAWN_TP.getMessage(), "TP"));
                event.setRespawnLocation(instance.getSpawnLocation());
            }
        }
    }
}
