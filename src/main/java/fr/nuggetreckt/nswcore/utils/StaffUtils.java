package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class StaffUtils {

    private final Map<UUID, Boolean> isStaffMode;
    private final Map<UUID, Location> oldLocation;
    private final Map<UUID, ItemStack[]> oldInventory;

    private final Map<UUID, Boolean> isFrozen;

    public StaffUtils() {
        isStaffMode = new HashMap<>();
        oldLocation = new HashMap<>();
        oldInventory = new HashMap<>();
        isFrozen = new HashMap<>();
    }

    public void init(@NotNull Player player) {
        if (NSWCore.getInstance().isStaff(player)) {
            isStaffMode.putIfAbsent(player.getUniqueId(), false);
            oldLocation.putIfAbsent(player.getUniqueId(), null);
            oldInventory.putIfAbsent(player.getUniqueId(), null);
        }
        for (UUID uuid : isStaffMode.keySet()) {
            if (isStaffMode.get(uuid)) {
                player.hidePlayer(NSWCore.getInstance(), Objects.requireNonNull(Bukkit.getPlayer(uuid)));
            }
        }
        isFrozen.putIfAbsent(player.getUniqueId(), false);
    }

    public void toggleStaffMode(Player player) {
        if (!isStaffMode(player)) {
            setLocation(player, player.getLocation());
            setInventory(player, player.getInventory().getContents());

            setStaffMode(player, true);
            setInvisible(player, true);
            setInvincible(player, true);
            setCanFly(player, true);
            setItems(player, true);

            player.sendMessage(String.format(MessageManager.STAFF_MODE_ENTER.getMessage(), "Staff"));
        } else {
            setStaffMode(player, false);
            setInvisible(player, false);
            setInvincible(player, false);
            setCanFly(player, false);
            setItems(player, false);
            player.teleport(getOldLocation(player));

            player.sendMessage(String.format(MessageManager.STAFF_MODE_LEAVE.getMessage(), "Staff"));
        }
    }

    private void setInvisible(@NotNull Player player, boolean bool) {
        player.setCanPickupItems(!bool);
        player.setCollidable(!bool);
        if (bool) {
            //Hide player from others
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(NSWCore.getInstance(), player);
            }
        } else {
            //Show player from others
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(NSWCore.getInstance(), player);
            }
        }
    }

    private void setInvincible(@NotNull Player player, boolean bool) {
        player.setInvulnerable(bool);
    }

    private void setCanFly(@NotNull Player player, boolean bool) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            player.setAllowFlight(bool);
            player.setFlying(bool);
        }
    }

    private void setItems(Player player, boolean bool) {
        if (bool) {
            player.getInventory().clear();

            player.getInventory().setItem(0, new ItemUtils(Material.BLUE_ICE).setName("§8§l»§r §3Freeze le joueur §8§l«").toItemStack());
            player.getInventory().setItem(1, new ItemUtils(Material.CHEST).setName("§8§l»§r §3Voir l'inventaire du joueur §8§l«").toItemStack());
            player.getInventory().setItem(2, new ItemUtils(Material.ENDER_CHEST).setName("§8§l»§r §3Voir l'EC du joueur §8§l«").toItemStack());
            player.getInventory().setItem(7, new ItemUtils(Material.COMPASS).setName("§8§l»§r §3Joueurs §8§l«").toItemStack());
            player.getInventory().setItem(8, new ItemUtils(Material.PAPER).setName("§8§l»§r §3Reports §8§l«").toItemStack());
        } else {
            player.getInventory().clear();
            player.getInventory().setContents(getOldInventory(player));
        }
    }

    private Location getOldLocation(@NotNull Player player) {
        return oldLocation.get(player.getUniqueId());
    }

    private ItemStack[] getOldInventory(@NotNull Player player) {
        return oldInventory.get(player.getUniqueId());
    }

    private void setLocation(Player player, Location location) {
        if (!isStaffMode(player)) {
            oldLocation.replace(player.getUniqueId(), location);
        } else {
            oldLocation.remove(player.getUniqueId());
        }
    }

    private void setInventory(Player player, ItemStack[] contents) {
        if (!isStaffMode(player)) {
            oldInventory.replace(player.getUniqueId(), contents);
        } else {
            oldInventory.remove(player.getUniqueId());
        }
    }

    public void setStaffMode(@NotNull Player player, boolean value) {
        isStaffMode.putIfAbsent(player.getUniqueId(), value);
        isStaffMode.replace(player.getUniqueId(), value);
    }

    public void setFrozen(@NotNull Player player, boolean value) {
        isFrozen.replace(player.getUniqueId(), value);
    }

    public boolean isStaffMode(@NotNull Player player) {
        if (isStaffMode.get(player.getUniqueId()) == null) {
            return false;
        }
        return isStaffMode.get(player.getUniqueId());
    }

    public boolean isFrozen(@NotNull Player player) {
        return isFrozen.get(player.getUniqueId());
    }

}
