package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class OnCommandListener implements Listener {

    @EventHandler
    public void onCommandListener(@NotNull PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (NSWCore.getStaffUtils().isFrozen(player)) {
            player.sendMessage(String.format(MessageManager.COMMANDS_DISABLED.getMessage(), "NSW"));
            event.setCancelled(true);
        }
    }
}
