package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.StaffUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class StaffEventsCanceller implements Listener {

    private final NSWCore instance;
    private final StaffUtils staffUtils;

    public StaffEventsCanceller(@NotNull NSWCore instance) {
        this.instance = instance;
        this.staffUtils = instance.getStaffUtils();
    }

    @EventHandler
    public void onPlayerBreak(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!instance.isStaff(player)) return;

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPlace(@NotNull BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!instance.isStaff(player)) return;

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(@NotNull PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (!instance.isStaff(player)) return;

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onXPOrbPickup(@NotNull EntityTargetEvent event) {
        Entity target = event.getTarget();
        Entity entity = event.getEntity();

        if (!(target instanceof Player)) return;
        if (!(entity instanceof ExperienceOrb)) return;

        Player player = (Player) target;
        ExperienceOrb orb = (ExperienceOrb) entity;

        if (!instance.isStaff(player)) return;
        if (!staffUtils.isStaffMode(player)) return;

        final Location pLoc = player.getLocation();
        final Location oLoc = orb.getLocation();
        final Vector dir = oLoc.toVector().subtract(pLoc.toVector());
        final double dx = Math.abs(dir.getX());
        final double dz = Math.abs(dir.getZ());
        if ((dx == 0.0) && (dz == 0.0)) {
            // Special case probably never happens
            dir.setX(0.001);
        }
        if (dx < 3.0 && dz < 3.0) {
            final Vector nDir = dir.normalize();
            final Vector newV = nDir.clone().multiply(0.3);
            newV.setY(0);
            orb.setVelocity(newV);
            if ((dx < 1.0) && (dz < 1.0)) {
                // maybe oLoc
                orb.teleport(oLoc.clone().add(nDir.multiply(1.0)), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
            if ((dx < 0.5) && (dz < 0.5)) {
                orb.remove();
            }
        }
        event.setCancelled(true);
        event.setTarget(null);
    }
}
