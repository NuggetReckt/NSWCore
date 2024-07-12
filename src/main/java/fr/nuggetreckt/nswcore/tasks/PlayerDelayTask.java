package fr.nuggetreckt.nswcore.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDelayTask {

    public final HashMap<UUID, BukkitTask> playerDelayTasks;

    public PlayerDelayTask() {
        playerDelayTasks = new HashMap<>();
    }

    public void setTask(Player player, BukkitTask task) {
        if (exists(player)) {
            playerDelayTasks.get(player.getUniqueId()).cancel();
            playerDelayTasks.replace(player.getUniqueId(), task);
            return;
        }
        playerDelayTasks.put(player.getUniqueId(), task);
    }

    public boolean exists(@NotNull Player player) {
        return playerDelayTasks.containsKey(player.getUniqueId()) || playerDelayTasks.get(player.getUniqueId()) != null;
    }

    public void stop(Player player) {
        if (!exists(player)) return;
        playerDelayTasks.get(player.getUniqueId()).cancel();
    }
}
