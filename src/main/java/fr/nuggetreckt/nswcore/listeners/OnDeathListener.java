package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OnDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        assert player != null;

        DamageCause damageCause = Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause();

        //TODO: add (if player spawn/home is not set) tp on player die to spawn
        switch (damageCause) {
            case ENTITY_ATTACK:
                Player killer = event.getEntity().getPlayer();
                event.setDeathMessage(String.format(MessageManager.PLAYER_KILL_MESSAGE.getBroadcastMessage(), player.getName(), killer.getName()));
                break;
            case LAVA, FIRE, FIRE_TICK:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_BURNED_MESSAGE.getBroadcastMessage(), player.getName()));
                break;
            case DROWNING:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_DROWNED_MESSAGE.getBroadcastMessage(), player.getName()));
                break;
            case FALL, FLY_INTO_WALL:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_FALL_MESSAGE.getBroadcastMessage(), player.getName()));
                break;
            case SUICIDE:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_SUICIDE_MESSAGE.getBroadcastMessage(), player.getName()));
                break;
            default:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_MESSAGE.getBroadcastMessage(), player.getName()));
                break;
        }
    }
}
