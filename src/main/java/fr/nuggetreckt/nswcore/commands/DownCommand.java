package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.TeleportUtils;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.UUID;

public class DownCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            CooldownManager cooldownManager = NSWCore.getCooldownManager();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId, "down");

            if (player.hasPermission("nsw.commands.down")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "down");
                    } else if (player.hasPermission("nsw.commands.down.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_RANKED_COOLDOWN.getValue()), "down");
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()), "down");
                    }
                    toDown(player);
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE.getMessage(), "TP", timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_HR_PERMISSION.getMessage(), "TP"));
            }
        }
        return true;
    }

    private void toDown(@NotNull Player target) {
        int blockX = target.getLocation().getBlockX();
        int blockY = target.getLocation().getBlockY();
        int blockZ = target.getLocation().getBlockZ();

        Block block;
        TeleportUtils teleportUtils = NSWCore.getTeleportUtils();

        for (int i = blockY - 3; i > -64; i--) {
            block = target.getWorld().getBlockAt(blockX, i, blockZ);

            if (teleportUtils.isValid(block)) {
                target.teleport(block.getLocation().add(0.5D, 1.0D, 0.05D));
                target.sendMessage(String.format(MessageManager.SUCCESS_TP.getMessage(), "TP"));
                NSWCore.getEffectUtils().teleportEffect(target);
                return;
            }
        }
        target.sendMessage(String.format(MessageManager.PLAYER_NOT_TP.getMessage(), "TP"));
    }
}
