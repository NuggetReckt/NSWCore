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

            Location spawnLoc = NSWCore.getInstance().getSpawnLocation();

            if (!NSWCore.getInstance().isFarmzone()) {
                if (args.length == 0) {
                    if (timeLeft.isZero() || timeLeft.isNegative()) {
                        if (player.isOp() || player.hasPermission("nsw.bypass")) {
                            cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "TP");
                        } else {
                            cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.SPAWN_COOLDOWN.getValue()), "TP");
                        }
                        player.sendMessage(String.format(MessageManager.PRE_SPAWN_TP.getMessage(), "TP"));

                        teleportUtils.setTeleports(player, true);

                        NSWCore.getPlayerDelayTask().setTask(player,
                                Bukkit.getScheduler().runTaskLater(NSWCore.getInstance(), () -> {
                                    teleportUtils.setTeleports(player, false);
                                    player.teleport(spawnLoc);
                                    player.sendMessage(String.format(MessageManager.SUCCESS_SPAWN_TP.getMessage(), "TP"));
                                    NSWCore.getEffectUtils().teleportEffect(player);
                                }, 100L)
                        );
                    } else {
                        player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE.getMessage(), "TP", timeLeft.toSeconds()));
                    }
                } else if (args.length == 1) {
                    if (player.hasPermission("nsw.commands.admin") || player.isOp()) {
                        Player target = NSWCore.getInstance().getPlayerByName(args[0]);
                        assert target != null;

                        target.teleport(spawnLoc);
                        target.sendMessage(String.format(MessageManager.SUCCESS_SPAWN_TP.getMessage(), "TP"));
                        player.sendMessage(String.format(MessageManager.SUCCESS_TP_OTHER.getMessage(), "TP", target.getName()));
                    } else {
                        player.sendMessage(MessageManager.NO_PERMISSION_CMD.getMessage(), "TP");
                    }
                }
            } else {
                player.sendMessage(String.format(MessageManager.COMMAND_ONLY_AVAILABLE_SURVIVAL.getMessage(), "TP"));
            }
        }
        return true;
    }
}
