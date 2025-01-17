package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.UUID;

public class TopCommand implements CommandExecutor {

    private final NSWCore instance;

    public TopCommand(NSWCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            CooldownManager cooldownManager = instance.getCooldownManager();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId, "top");

            if (player.hasPermission("nsw.commands.top")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "top");
                    } else if (player.hasPermission("nsw.commands.top.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_RANKED_COOLDOWN.getValue()), "top");
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()), "top");
                    }
                    toTop(player);
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE.getMessage(), "TP", timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_HR_PERMISSION.getMessage(), "TP"));
            }
        }
        return true;
    }

    private void toTop(@NotNull Player target) {
        int blockX = target.getLocation().getBlockX();
        int blockY = target.getLocation().getBlockY();
        int blockZ = target.getLocation().getBlockZ();

        Block block;
        TeleportUtils teleportUtils = instance.getTeleportUtils();

        for (int i = 457; i > blockY; i--) {
            block = target.getWorld().getBlockAt(blockX, i, blockZ);

            if (target.getWorld().getName().contains("nether") && i >= 127) {
                continue;
            }
            if (teleportUtils.isValid(block)) {
                target.sendMessage(String.format(MessageManager.PRE_TP.getMessage(), "TP"));
                teleportUtils.setTeleports(target, true);

                Location location = block.getLocation().add(0.5D, 1.0D, 0.5D);

                instance.getPlayerDelayTask().setTask(target,
                        Bukkit.getScheduler().runTaskLater(instance, () -> {
                            teleportUtils.setTeleports(target, false);
                            target.teleport(location);
                            target.sendMessage(String.format(MessageManager.SUCCESS_TP.getMessage(), "TP"));
                            instance.getEffectUtils().teleportEffect(target);
                        }, 40L)
                );
                return;
            }
        }
        target.sendMessage(String.format(MessageManager.PLAYER_NOT_TP.getMessage(), "TP"));
    }
}