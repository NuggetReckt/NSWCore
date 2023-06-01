package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.TeleportUtils;
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
            TeleportUtils teleportUtils = NSWCore.getTeleportUtils();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId, "bottom");

            if (player.hasPermission("nsw.commands.bottom")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "bottom");
                    } else if (player.hasPermission("nsw.commands.bottom.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_RANKED_COOLDOWN.getValue()), "bottom");
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()), "bottom");
                    }
                    toBottom(player);
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE_MESSAGE.getMessage(), "TP", timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "TP"));
            }
        }
        return true;
    }

    private void toBottom(@NotNull Player target) {
        //code here

        NSWCore.getEffectUtils().teleportEffect(target);
        target.sendMessage(String.format(MessageManager.SUCCESS_TP_MESSAGE.getMessage(), "TP"));
    }
}
