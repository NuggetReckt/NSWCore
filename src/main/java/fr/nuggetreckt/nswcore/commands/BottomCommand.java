package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.UUID;

public class BottomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            CooldownManager cooldownManager = NSWCore.getCooldownManager();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId);

            if (player.hasPermission("nsw.commands.bottom")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.STAFF_COOLDOWN.getValue()));
                    } else if (player.hasPermission("nsw.commands.bottom.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.RANKED_COOLDOWN.getValue()));
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()));
                    }
                    toBottom(player);
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USING_MESSAGE.getMessage(), "TP", timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "TP"));
            }
        }
        return true;
    }

    private void toBottom(@NotNull Player target) {
        //code here

        target.sendMessage(String.format(MessageManager.SUCCESS_TP_MESSAGE.getMessage(), "TP"));
    }

    private boolean isValid(@NotNull Block block) {
        Location location = block.getLocation().add(0.0D, 1.0D, 0.0D);
        Location location2 = location.add(0.0D, 1.0D, 0.0D);
        return (location.getBlock().getType().isAir() && location2.getBlock().getType().isAir());
    }
}
