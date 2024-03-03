package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.impl.PlayerListGui;
import fr.nuggetreckt.nswcore.guis.impl.ReportsGui;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.StaffUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StaffEventsListener implements Listener {

    private final StaffUtils staffUtils;

    public StaffEventsListener() {
        this.staffUtils = NSWCore.getStaffUtils();
    }

    @EventHandler
    public void onItemClickAtEntity(@NotNull PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (!NSWCore.getInstance().isStaff(player)) return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (item.getType() == Material.AIR) return;
        if (!(event.getRightClicked() instanceof Player)) return;

        Player target = (Player) event.getRightClicked();

        if (staffUtils.isStaffMode(player)) {
            switch (item.getType()) {
                case BLUE_ICE -> {
                    if (!staffUtils.isFrozen(target)) {
                        staffUtils.setFrozen(target, true);
                        player.sendMessage(String.format(MessageManager.PLAYER_FREEZE_STAFF.getMessage(), "NSW", target.getName()));
                        target.sendMessage(String.format(MessageManager.PLAYER_FREEZE_TARGET.getMessage(), "NSW", player.getName()));

                        target.sendTitle("§4§lVous êtes freeze", "§cRendez-vous sur le Discord §8(§3/discord§8)", 10, 200, 60);
                    } else {
                        staffUtils.setFrozen(target, false);
                        player.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_STAFF.getMessage(), "NSW", target.getName()));
                        target.sendMessage(String.format(MessageManager.PLAYER_UNFREEZE_TARGET.getMessage(), "NSW", player.getName()));
                    }
                }
                case CHEST -> {
                    Inventory inventory = target.getInventory();
                    player.openInventory(inventory);

                    //Objects.requireNonNull(player.openInventory(inventory)).setTitle("§fInventaire de §3" + target.getName());
                }
                case ENDER_CHEST -> {
                    Inventory inventory = target.getEnderChest();
                    player.openInventory(inventory);

                    //Objects.requireNonNull(player.openInventory(inventory)).setTitle("§fEC de §3" + target.getName());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!NSWCore.getInstance().isStaff(player)) return;

        if (staffUtils.isStaffMode(player)) {
            staffUtils.toggleStaffMode(player);
        }
    }

    @EventHandler
    public void onItemClick(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!NSWCore.getInstance().isStaff(player)) return;

        ItemStack item = player.getInventory().getItemInMainHand();

        if (staffUtils.isStaffMode(player)) {
            event.setCancelled(true);
            switch (item.getType()) {
                case PAPER -> NSWCore.getGuiManager().open(player, ReportsGui.class);
                case COMPASS -> NSWCore.getGuiManager().open(player, PlayerListGui.class);
            }
        }
    }
}
