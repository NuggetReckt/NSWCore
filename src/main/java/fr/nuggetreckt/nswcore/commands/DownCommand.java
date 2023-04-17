package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.UUID;

public class DownCommand implements CommandExecutor {

    private final CooldownManager cooldownManager = new CooldownManager();

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId);

            if (player.hasPermission("nsw.commands.down")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.STAFF_COOLDOWN.getValue()));
                    } else if (player.hasPermission("nsw.commands.down.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.RANKED_COOLDOWN.getValue()));
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()));
                    }

                    toDown(player);
                } else {
                    player.sendMessage(NSWCore.getPrefix() + String.format(MessageManager.WAIT_BEFORE_USING_MESSAGE.getMessage(), timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(NSWCore.getPrefix() + MessageManager.NO_PERMISSION_MESSAGE.getMessage());
            }
        }
        return true;
    }

    private void toDown(@Nonnull Player target) {
        target.teleport(target.getWorld().getHighestBlockAt(target.getLocation()).getLocation().add(0.5, 1, 0.5));
        target.sendMessage(NSWCore.getPrefix() + MessageManager.SUCCESS_TP_MESSAGE.getMessage());
    }
}
