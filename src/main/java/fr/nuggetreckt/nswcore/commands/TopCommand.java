package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessagesManager;
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
                    cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()));

                    player.teleport(player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0, 1, 0));
                    player.sendMessage(NSWCore.getPrefix() + MessagesManager.SUCCESS_TP_MESSAGE.getMessage());
                } else {
                    player.sendMessage(NSWCore.getPrefix() + String.format(MessagesManager.WAIT_BEFORE_USING_MESSAGE.getMessage(), timeLeft.toMinutes()));
                }
            } else {
                player.sendMessage(NSWCore.getPrefix() + MessagesManager.NO_PERMISSION_MESSAGE.getMessage());
            }
        }
        return true;
    }
}
