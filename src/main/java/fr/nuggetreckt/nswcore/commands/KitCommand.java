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

public class KitCommand implements CommandExecutor {

    private final CooldownManager cooldownManager = new CooldownManager();

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId);

            //Si le joueur à déjà rejoint le serveur EN GENERAL
            if (player.hasPlayedBefore()) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.KIT_COOLDOWN.getValue()));

                    //OUVRE UN GUI
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USING_MESSAGE.getMessage(), "Kit", timeLeft.toHours()));
                }
            }
        }
        return true;
    }
}
