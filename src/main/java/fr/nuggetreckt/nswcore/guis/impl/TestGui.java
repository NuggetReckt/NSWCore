package fr.nuggetreckt.nswcore.guis.impl;

import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.function.Supplier;

public class TestGui implements CustomInventory {

    @Override
    public String getName() {
        return "§3» §ftest";
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];

        //test
        slots[0] = new ItemUtils(Material.DIRT).setName("test").setLore("test lore").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot) {
        if (!clickedItem.hasItemMeta()) return;
        if (!Objects.requireNonNull(clickedItem.getItemMeta()).hasDisplayName()) return;

        switch (clickedItem.getItemMeta().getDisplayName()) {
            case "test":
                player.sendMessage("yes !");
                break;
            case "e":
                player.sendMessage("non.");
                break;
        }
        player.closeInventory();
    }
}
