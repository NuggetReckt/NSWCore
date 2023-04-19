package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.UUID;

public class TopCommand implements CommandExecutor {

    private final CooldownManager cooldownManager = new CooldownManager();

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId);

            if (player.hasPermission("nsw.commands.top")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.STAFF_COOLDOWN.getValue()));
                    } else if (player.hasPermission("nsw.commands.top.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.RANKED_COOLDOWN.getValue()));
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()));
                    }

                    toTop(player);
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USING_MESSAGE.getMessage(), "TP", timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "TP"));
            }
        }
        return true;
    }

    private void toTop(@Nonnull Player target) {
        target.teleport(target.getWorld().getHighestBlockAt(target.getLocation()).getLocation().add(0.5, 1, 0.5));
        target.sendMessage(String.format(MessageManager.SUCCESS_TP_MESSAGE.getMessage(), "TP"));
    }
}
