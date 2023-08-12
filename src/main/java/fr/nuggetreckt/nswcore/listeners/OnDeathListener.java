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

import java.util.ArrayList;
import java.util.List;
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

        List<MessageManager> deathMessages = new ArrayList<>();
        String deathMessage;

        //TODO: add (if player spawn/home is not set) tp on player die to spawn
        switch (damageCause) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK, ENTITY_EXPLOSION, PROJECTILE -> {
                if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
                    if (damager instanceof Player) {
                        deathMessages.add(MessageManager.PLAYER_KILL);
                        deathMessages.add(MessageManager.PLAYER_KILL2);
                        deathMessages.add(MessageManager.PLAYER_KILL3);
                        deathMessages.add(MessageManager.PLAYER_KILL4);

                        int a = r.nextInt(0, deathMessages.size());
                        deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName(), damager.getName());

                        event.setDeathMessage(deathMessage);
                        deathMessages.clear();
                    } else if (damager instanceof Creeper) {
                        event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_CREEPER.getDeathMessage(), player.getName()));
                    } else if (damager instanceof Projectile) {
                        Object shooter = ((Projectile) damager).getShooter();
                        if (shooter instanceof Entity) {
                            event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_MOB.getDeathMessage(), player.getName(), ((Entity) shooter).getName()));
                        }
                    } else {
                        event.setDeathMessage(String.format(MessageManager.PLAYER_DEATH_MOB.getDeathMessage(), player.getName(), damager.getName()));
                    }
                }
            }
            case LAVA, FIRE, FIRE_TICK, HOT_FLOOR -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_BURNED);
                deathMessages.add(MessageManager.PLAYER_DEATH_BURNED2);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case DROWNING -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_DROWNED);
                deathMessages.add(MessageManager.PLAYER_DEATH_DROWNED2);
                deathMessages.add(MessageManager.PLAYER_DEATH_DROWNED3);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case SUFFOCATION, FALLING_BLOCK -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_SUFFOCATION);
                deathMessages.add(MessageManager.PLAYER_DEATH_SUFFOCATION2);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case POISON -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_MAGIC);
                deathMessages.add(MessageManager.PLAYER_DEATH_POISON);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case MAGIC -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_MAGIC);
                deathMessages.add(MessageManager.PLAYER_DEATH_MAGIC2);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case FALL, FLY_INTO_WALL -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_FALL);
                deathMessages.add(MessageManager.PLAYER_DEATH_FALL2);
                deathMessages.add(MessageManager.PLAYER_DEATH_FALL3);
                deathMessages.add(MessageManager.PLAYER_DEATH_FALL4);
                deathMessages.add(MessageManager.PLAYER_DEATH_FALL5);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case VOID -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_VOID);
                deathMessages.add(MessageManager.PLAYER_DEATH_VOID2);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            case BLOCK_EXPLOSION, LIGHTNING -> {
                deathMessages.add(MessageManager.PLAYER_DEATH_EXPLOSION);
                deathMessages.add(MessageManager.PLAYER_DEATH_EXPLOSION2);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
            default -> {
                deathMessages.add(MessageManager.PLAYER_DEATH);
                deathMessages.add(MessageManager.PLAYER_DEATH2);
                deathMessages.add(MessageManager.PLAYER_DEATH3);

                int a = r.nextInt(0, deathMessages.size());
                deathMessage = String.format(deathMessages.get(a).getDeathMessage(), player.getName());

                event.setDeathMessage(deathMessage);
                deathMessages.clear();
            }
        }
    }
}
