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

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.UUID;

public class TopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            CooldownManager cooldownManager = NSWCore.getCooldownManager();
            TeleportUtils teleportUtils = NSWCore.getTeleportUtils();
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
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE_MESSAGE.getMessage(), "TP", timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_HR_PERMISSION_MESSAGE.getMessage(), "TP"));
            }
        }
        return true;
    }

    private void toTop(@Nonnull Player target) {
        //code here

        NSWCore.getEffectUtils().teleportEffect(target);
        target.sendMessage(String.format(MessageManager.SUCCESS_TP_MESSAGE.getMessage(), "TP"));
    }
}
