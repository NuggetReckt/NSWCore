package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.jetbrains.annotations.NotNull;

public class OnSleepListener implements Listener {

    @EventHandler
    public void onPlayerSleep(@NotNull PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!event.isCancelled()) {
            Bukkit.getServer().getScheduler().runTaskLater(NSWCore.getInstance(), () -> {
                String message = "§3 " + player.getName() + " §fdort profondément...";
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                }
            }, 1L);

            Bukkit.getServer().getScheduler().runTaskLater(NSWCore.getInstance(), () -> {
                if (player.isSleeping()) {
                    world.setStorm(false);
                    world.setThundering(false);
                    player.setStatistic(Statistic.TIME_SINCE_REST, 0);

                    world.setTime(23500);
                }
            }, 100L);
        }
    }
}
