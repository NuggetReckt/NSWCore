package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.StaffUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StaffEventsListener implements Listener {

    private final StaffUtils staffUtils;

    public StaffEventsListener() {
        this.staffUtils = NSWCore.getStaffUtils();
    }

    @EventHandler
    public void onItemClick(@NotNull PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (item.getType() == Material.AIR) return;

        if (staffUtils.isStaffMode(player)) {
            if (item.getType() == Material.BLUE_ICE) {
                staffUtils.setFrozen(target, !staffUtils.isFrozen(target));
                player.sendMessage(String.format(MessageManager.PLAYER_FREEZE_MESSAGE_STAFF.getMessage(), "NSW", target.getName()));
                target.sendMessage(String.format(MessageManager.PLAYER_FREEZE_MESSAGE_TARGET.getMessage(), "NSW", player.getName()));
            }
            if (item.getType() == Material.CHEST) {
                Objects.requireNonNull(player.openInventory(target.getInventory())).setTitle("§fInventaire de §3" + target.getName());
            }
        }
    }
}
