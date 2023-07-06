package fr.nuggetreckt.nswcore.guis.impl;

import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ReportsGui implements CustomInventory {

    @Override
    public String getName() {
        return "§8§l»§r §3Reports §8§l«§r";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];

        //Reports
        slots[0] = new ItemUtils(Material.PAPER).setName("§8§l»§r §3Name §8§l«").hideFlags().setLore("").toItemStack();

        //Utils
        slots[49] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();

        //Placeholders
        slots[45] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[46] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[47] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[48] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[50] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[51] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[52] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();
        slots[53] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").setLore("").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot) {

    }
}
