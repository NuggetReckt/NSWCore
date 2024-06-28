package fr.nuggetreckt.nswcore.utils;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.HonorRanks;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EffectUtils {

    private final NSWAPI nswapi;

    private final Random r;

    public EffectUtils(NSWAPI api) {
        this.nswapi = api;
        this.r = new Random();
    }

    public void teleportEffect(@NotNull Player player) {
        spawnParticles(player, Particle.PORTAL);
        playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT);
    }

    public void uprankEffect(@NotNull Player player) {
        //spawnParticles(player, Particle.VILLAGER_HAPPY);
        HonorRanks rank = nswapi.getHonorRanksHandler().getPlayerRank(player.getUniqueId());
        int r = 0, g = 0, b = 0;

        switch (rank.getRankId()) {
            case 1 -> {
                r = 138;
                g = 252;
                b = 110;
            }
            case 2 -> {
                r = 101;
                g = 229;
                b = 160;
            }
            case 3 -> {
                r = 66;
                g = 207;
                b = 207;
            }
            case 4 -> {
                r = 55;
                g = 158;
                b = 196;
            }
            case 5 -> {
                r = 44;
                g = 112;
                b = 186;
            }
            case 6 -> {
                r = 156;
                g = 31;
                b = 164;
            }
        }
        Color color = Color.fromRGB(r, g, b);

        player.sendTitle(String.format("%sRang supérieur !", IridiumColorAPI.process(nswapi.getHonorRanksHandler().getPlayerRank(player.getUniqueId()).getColorCode())),
                String.format("Vous êtes passé %s", nswapi.getHonorRanksHandler().getDisplayName(player.getUniqueId())), 10, 100, 40);
        spawnParticles(player, color);
        if (rank == HonorRanks.values()[HonorRanks.values().length - 1]) {
            playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE);
        } else {
            playSound(player, Sound.ENTITY_PLAYER_LEVELUP);
        }
    }

    public void gainPointsEffect(@NotNull Player player) {
        playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    private void spawnParticles(@NotNull Player player, Particle particle) {
        Location location = player.getLocation().add(0, 1, 0);

        int particleCount = r.nextInt(190, 200);

        for (int i = 0; i < particleCount; i++) {
            double offsetX = r.nextDouble(0.2, 0.6);
            double offsetY = r.nextDouble(0.3, 0.8);
            double offsetZ = r.nextDouble(0.2, 0.6);

            player.getWorld().spawnParticle(particle, location, 1, offsetX, offsetY, offsetZ, 0.7);
        }
    }

    private void spawnParticles(@NotNull Player player, Color color) {
        Location location = player.getLocation().add(0, 1, 0);

        int particleCount = r.nextInt(190, 200);

        for (int i = 0; i < particleCount; i++) {
            double offsetX = r.nextDouble(0.2, 0.6);
            double offsetY = r.nextDouble(0.3, 0.8);
            double offsetZ = r.nextDouble(0.2, 0.6);

            player.getWorld().spawnParticle(Particle.REDSTONE, location, 1, offsetX, offsetY, offsetZ, 1, new Particle.DustOptions(color, 1.0F));
        }
    }

    private void playSound(@NotNull Player player, Sound sound) {
        player.playSound(player, sound, 15, 1);
    }
}
