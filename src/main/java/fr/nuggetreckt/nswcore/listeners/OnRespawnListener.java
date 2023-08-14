package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class OnRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        Location spawnLoc = NSWCore.getInstance().getSpawnLocation();

        /*
        TO UNCOMMENT AFTER 1.20 SERVER UPDATE !
        if (event.getRespawnReason() == PlayerRespawnEvent.RespawnReason.DEATH) {
            event.setRespawnLocation(spawnLoc);
            player.sendMessage(String.format(MessageManager.RESPAWN_TP.getMessage(), "TP"));
        }
        */

        event.setRespawnLocation(spawnLoc);
        player.sendMessage(String.format(MessageManager.RESPAWN_TP.getMessage(), "TP"));
    }
}
