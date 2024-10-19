package fr.nuggetreckt.nswcore.listeners;

import fr.noskillworld.api.NSWAPI;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnInvClickListener implements Listener {

    private final NSWAPI nswapi;

    public OnInvClickListener(@NotNull NSWCore instance) {
        this.nswapi = instance.getAPI();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(@NotNull InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        InventoryView inventoryView = event.getView();
        ItemStack currentItem = event.getCurrentItem();
        int slot = event.getSlot();
        boolean isLeftClick = event.isLeftClick();

        if (currentItem == null) return;

        nswapi.getGuiManager().registeredMenus.values().stream().filter(menu -> inventoryView.getTitle().equalsIgnoreCase(menu.getName()))
                .forEach(menu -> {
                    menu.onClick(player, inventory, currentItem, slot, isLeftClick);
                    event.setCancelled(true);
                });
    }

    @EventHandler
    public void onInventoryDrag(@NotNull InventoryDragEvent event) {
        InventoryView inventoryView = event.getView();

        nswapi.getGuiManager().registeredMenus.values().stream().filter(menu -> inventoryView.getTitle().equalsIgnoreCase(menu.getName()))
                .forEach(menu -> event.setCancelled(true));
    }
}
