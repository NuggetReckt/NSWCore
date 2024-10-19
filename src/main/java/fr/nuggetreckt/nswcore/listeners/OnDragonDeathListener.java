package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class OnDragonDeathListener implements Listener {

    private final NSWCore instance;

    private BukkitTask runnable;

    public OnDragonDeathListener(NSWCore instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onDragonEggDrop(@NotNull EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            DragonBattle battle = ((EnderDragon) event.getEntity()).getDragonBattle();

            assert battle != null;
            if (battle.hasBeenPreviouslyKilled()) {
                return;
            }
            World endWorld = event.getEntity().getWorld();

            runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int y = 50; y < 100; y++) {
                        Block block = endWorld.getBlockAt(0, y, 0);

                        if (block.getType() == Material.DRAGON_EGG) {
                            block.setType(Material.AIR);
                            runnable.cancel();
                            return;
                        }
                    }
                }
            }.runTaskTimer(instance, 1L, 20L);
        }
    }
}
