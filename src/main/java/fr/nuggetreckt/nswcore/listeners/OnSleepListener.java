package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.jetbrains.annotations.NotNull;

public class OnSleepListener implements Listener {

    @EventHandler
    public void onPlayerSleep(@NotNull PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        String message = String.format(MessageManager.PLAYER_SLEEPING_MESSAGE.getMessage(), "NSW", player.getName());

        if (!event.isCancelled()) {
            Bukkit.getServer().getOnlinePlayers().forEach(players -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message)));

            Bukkit.getServer().getScheduler().runTaskLater(NSWCore.getInstance(), () -> {
                if (player.isSleeping()) {
                    player.getWorld().setTime(0L);
                    player.getWorld().setStorm(false);
                    player.getWorld().setThundering(false);
                    event.getPlayer().setStatistic(Statistic.TIME_SINCE_REST, 0);
                }
            }, 100L);
        }
    }
}
