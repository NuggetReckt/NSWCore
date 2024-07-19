package fr.nuggetreckt.nswcore.tasks;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class MoneyGiveTask {

    private final NSWCore instance;
    private final HashMap<UUID, BukkitTask> playerTasks;

    //25NSc every 20 minutes
    private long secondsWait = 1200L;
    private int moneyAmount = 25;

    public MoneyGiveTask(NSWCore instance) {
        this.instance = instance;
        this.playerTasks = new HashMap<>();
    }

    public void start(@NotNull Player player) {
        if (playerTasks.containsKey(player.getUniqueId()) || playerTasks.get(player.getUniqueId()) != null) return;

        playerTasks.put(player.getUniqueId(),
                Bukkit.getServer().getScheduler().runTaskTimer(instance, () -> {
                    NSWCore.getEconomy().depositPlayer(player, moneyAmount);
                    player.sendMessage(String.format(MessageManager.PLAYER_PLAYTIME_REWARD.getMessage(), "Eco", moneyAmount));
                }, 0L, secondsWait * 20L)
        );
    }

    public void stop(@NotNull Player player) {
        if (!playerTasks.containsKey(player.getUniqueId()) || playerTasks.get(player.getUniqueId()) == null) return;

        playerTasks.get(player.getUniqueId()).cancel();
        playerTasks.remove(player.getUniqueId());
    }

    public long getSecondsWait() {
        return secondsWait;
    }

    public void setMoneyAmount(int amount) {
        moneyAmount = amount;
    }

    public void setSecondsWait(long seconds) {
        secondsWait = seconds;
    }
}
