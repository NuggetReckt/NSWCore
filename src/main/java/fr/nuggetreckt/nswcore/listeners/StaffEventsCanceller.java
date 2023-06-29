package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.StaffUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class StaffEventsCanceller implements Listener {

    private final StaffUtils staffUtils;

    public StaffEventsCanceller() {
        this.staffUtils = NSWCore.getStaffUtils();
    }

    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();

        staffUtils.setStaffMode(player, false);
        staffUtils.toggleStaffMode(player);
    }

    @EventHandler
    public void onPlayerBreak(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlace(@NotNull BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(@NotNull InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
        }
    }
}
