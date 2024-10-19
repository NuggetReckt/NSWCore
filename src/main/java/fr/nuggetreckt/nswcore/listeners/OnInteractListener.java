package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class OnInteractListener implements Listener {

    private final NSWCore instance;

    public OnInteractListener(NSWCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (instance.getStaffUtils().isFrozen(player)) {
            event.setCancelled(true);
        }
    }
}
