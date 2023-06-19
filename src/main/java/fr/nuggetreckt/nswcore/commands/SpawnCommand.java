package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.UUID;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            CooldownManager cooldownManager = NSWCore.getCooldownManager();
            TeleportUtils teleportUtils = NSWCore.getTeleportUtils();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId, "spawn");

            Location spawnLoc = new Location(NSWCore.getOverworld(), 0.5, 84, 0.5, 180, 0);

            if (!NSWCore.isFarmzone()) {
                if (args.length == 0) {
                    if (timeLeft.isZero() || timeLeft.isNegative()) {
                        if (player.isOp() || player.hasPermission("nsw.bypass")) {
                            cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "TP");
                        } else {
                            cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.SPAWN_COOLDOWN.getValue()), "TP");
                        }
                        player.sendMessage(String.format(MessageManager.PRE_SPAWN_TP_MESSAGE.getMessage(), "TP"));

                        teleportUtils.setTeleports(player, true);

                        BukkitTask task = Bukkit.getScheduler().runTaskLater(NSWCore.getInstance(), () -> {
                            teleportUtils.setTeleports(player, false);
                            player.teleport(spawnLoc);
                            player.sendMessage(String.format(MessageManager.SUCCESS_SPAWN_TP_MESSAGE.getMessage(), "TP"));
                            NSWCore.getEffectUtils().teleportEffect(player);
                        }, 100L);
                        NSWCore.setBukkitTask(task);
                    } else {
                        player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE_MESSAGE.getMessage(), "TP", timeLeft.toSeconds()));
                    }
                } else if (args.length == 1) {
                    if (player.hasPermission("nsw.commands.admin") || player.isOp()) {
                        Player target = NSWCore.getPlayerByName(args[0]);
                        assert target != null;

                        target.teleport(spawnLoc);
                        target.sendMessage(String.format(MessageManager.SUCCESS_SPAWN_TP_MESSAGE.getMessage(), "TP"));
                        player.sendMessage(String.format(MessageManager.SUCCESS_TP_OTHER_MESSAGE.getMessage(), "TP", target.getName()));
                    } else {
                        player.sendMessage(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "TP");
                    }
                }
            } else {
                player.sendMessage(String.format(MessageManager.COMMAND_ONLY_AVAILABLE_SURVIVAL_MESSAGE.getMessage(), "TP"));
            }
        }
        return true;
    }
}
