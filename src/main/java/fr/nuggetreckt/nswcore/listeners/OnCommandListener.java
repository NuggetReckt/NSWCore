package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class OnCommandListener implements Listener {

    private final NSWCore instance;

    public OnCommandListener(NSWCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCommandListener(@NotNull PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (instance.getStaffUtils().isFrozen(player)) {
            player.sendMessage(String.format(MessageManager.COMMANDS_DISABLED.getMessage(), "NSW"));
            event.setCancelled(true);
        }
        if (event.getMessage().equalsIgnoreCase("?")) {
            event.setCancelled(true);
        }
    }
}
