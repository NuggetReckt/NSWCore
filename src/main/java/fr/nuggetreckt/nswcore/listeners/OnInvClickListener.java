package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.guis.impl.KitGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnInvClickListener implements Listener {

    public String currentInv;
    Inventory inventory;

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        inventory = event.getClickedInventory();
        ItemStack currentItem = event.getCurrentItem();
        int slot = event.getSlot();

        if (currentItem == null) return;

        switch (currentInv) {
            case "kit":
                new KitGui().onClick(player, inventory, currentItem, slot);
                break;
            case "test":
                break;
        }
    }

    @EventHandler
    public void onInventoryClick(final @NotNull InventoryDragEvent event) {
        if (event.getInventory().equals(inventory)) {
            event.setCancelled(true);
        }
    }

    public void setCurrentInv(String currentInv) {
        this.currentInv = currentInv;
    }
}
