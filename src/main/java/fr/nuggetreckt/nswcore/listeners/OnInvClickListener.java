package fr.nuggetreckt.nswcore.listeners;

import fr.nuggetreckt.nswcore.guis.KitGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnInvClickListener implements Listener {

    public String currentInv;

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
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

    public void setCurrentInv(String currentInv) {
        this.currentInv = currentInv;
    }
}
