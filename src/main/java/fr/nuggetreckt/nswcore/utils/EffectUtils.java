package fr.nuggetreckt.nswcore.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EffectUtils {

    private Location location;

    public void teleportEffect(@NotNull Player player) {
        location = player.getLocation().add(0, 1, 0);
        Random r = new Random();

        int particleCount = r.nextInt(95, 100);

        for (int i = 0; i < particleCount; i++) {
            double offsetX = r.nextDouble(0.2, 0.6);
            double offsetY = r.nextDouble(0.3, 0.8);
            double offsetZ = r.nextDouble(0.2, 0.6);

            player.getWorld().spawnParticle(Particle.PORTAL, location, 1, offsetX, offsetY, offsetZ);
        }
        playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT);
    }

    public void uprankEffect(@NotNull Player player) {
        location = player.getLocation().add(0, 1, 0);
        Random r = new Random();

        int particleCount = r.nextInt(95, 100);

        for (int i = 0; i < particleCount; i++) {
            double offsetX = r.nextDouble(0.2, 0.6);
            double offsetY = r.nextDouble(0.3, 0.8);
            double offsetZ = r.nextDouble(0.2, 0.6);

            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 1, offsetX, offsetY, offsetZ);
        }
        playSound(player, Sound.ENTITY_PLAYER_LEVELUP);
    }

    public void gainPointsEffect(@NotNull Player player) {
        playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    public void playSound(@NotNull Player player, Sound sound) {
        player.playSound(player, sound, 15, 1);
    }
}
