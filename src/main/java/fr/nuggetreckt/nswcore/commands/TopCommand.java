package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

            Location location = player.getLocation();
            World world = location.getWorld();
            assert world != null;
            Block block = world.getHighestBlockAt(location);

            if (player.hasPermission("nsw.commands.top")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.STAFF_COOLDOWN.getValue()));
                    } else if (player.hasPermission("nsw.commands.top.1")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.RANKED_COOLDOWN.getValue()));
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.DEFAULT_COOLDOWN.getValue()));
                    }

                    if (isValid(block)) {
                        toTop(player, block);
                        return true;
                    } else {
                        while (!isValid(block)) {
                            block = world.getBlockAt(block.getLocation().add(0.0D, 1.0D, 0.0D));
                            if (isValid(block))
                                break;
                        }
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USING_MESSAGE.getMessage(), "TP", timeLeft.toMinutes()));
                    return false;
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_MESSAGE.getMessage(), "TP"));
                return false;
            }
        }
        return true;
    }

    private void toTop(@Nonnull Player target, @NotNull Block block) {
        target.teleport(block.getLocation().add(0.0D, 1.0D, 0.0D));
        target.sendMessage(String.format(MessageManager.SUCCESS_TP_MESSAGE.getMessage(), "TP"));
    }

    private boolean isValid(@NotNull Block block) {
        Location location = block.getLocation().add(0.0D, 1.0D, 0.0D);
        Location location2 = location.add(0.0D, 1.0D, 0.0D);
        return (location.getBlock().getType().isAir() && location2.getBlock().getType().isAir());
    }
}
