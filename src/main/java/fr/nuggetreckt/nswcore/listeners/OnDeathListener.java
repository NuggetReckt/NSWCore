package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class OnDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        assert player != null;

        EntityDamageEvent entityDamageEvent = player.getLastDamageCause();
        DamageCause damageCause = Objects.requireNonNull(entityDamageEvent).getCause();
        Random r = new Random();

        //TODO: add (if player spawn/home is not set) tp on player die to spawn
        switch (damageCause) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK, ENTITY_EXPLOSION, PROJECTILE:
                if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
                    if (damager instanceof Player) {
                        int a = r.nextInt(0, 1);

                        if (a == 0) {
                            event.setDeathMessage(String.format(MessageManager.PLAYER_KILL_MESSAGE.getDeathMessage(), player.getName(), damager.getName()));
                        } else {
                            event.setDeathMessage(String.format(MessageManager.PLAYER_KILL2_MESSAGE.getDeathMessage(), player.getName(), damager.getName()));
                        }
                    } else if (damager instanceof Creeper) {
                        event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_CREEPER_MESSAGE.getDeathMessage(), player.getName()));
                    } else if (damager instanceof Projectile) {
                        Object shooter = ((Projectile) damager).getShooter();
                        if (shooter instanceof Entity) {
                            event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_MOB_MESSAGE.getDeathMessage(), player.getName(), ((Entity) shooter).getName()));
                        }
                    } else {
                        event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_MOB_MESSAGE.getDeathMessage(), player.getName(), damager.getName()));
                    }
                }
                break;
            case LAVA, FIRE, FIRE_TICK, HOT_FLOOR:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_BURNED_MESSAGE.getDeathMessage(), player.getName()));
                break;
            case DROWNING:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_DROWNED_MESSAGE.getDeathMessage(), player.getName()));
                break;
            case SUFFOCATION:
                break;
            case FALL, FLY_INTO_WALL:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_FALL_MESSAGE.getDeathMessage(), player.getName()));
                break;
            case VOID:
                event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_VOID_MESSAGE.getDeathMessage(), player.getName()));
                break;
            default:
                int a = r.nextInt(0, 1);

                if (a == 0) {
                    event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_MESSAGE.getDeathMessage(), player.getName()));
                } else {
                    event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH2_MESSAGE.getDeathMessage(), player.getName()));
                }
                break;
        }
    }
}
