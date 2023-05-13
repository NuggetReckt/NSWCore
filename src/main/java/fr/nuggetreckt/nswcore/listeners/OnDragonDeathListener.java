package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class OnDragonDeathListener implements Listener {

    @EventHandler
    public void onDragonEggDrop(@NotNull EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            World endWorld = event.getEntity().getWorld();

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 58; i < 72; i++) {
                        Block block = endWorld.getBlockAt(0, i, 0);

                        if (block.getType() == Material.DRAGON_EGG) {
                            block.setType(Material.AIR);
                            return;
                        }
                    }
                }
            }.runTaskLater(NSWCore.getInstance(), 202L);
        }
    }
}
