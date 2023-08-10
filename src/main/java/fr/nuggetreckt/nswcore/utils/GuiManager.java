package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.guis.impl.KitGui;
import fr.nuggetreckt.nswcore.guis.impl.ReportsGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {

    public GuiManager() {
        registersGUI();
    }

    public Map<Class<? extends CustomInventory>, CustomInventory> registeredMenus = new HashMap<>();

    public void open(Player player, Class<? extends CustomInventory> gClass) {
        if (!registeredMenus.containsKey(gClass)) return;

        CustomInventory menu = registeredMenus.get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
        inv.setContents(menu.getContents(player).get());
        player.openInventory(inv);
    }

    public void refreshInv(Player player, Class<? extends CustomInventory> gClass) {
        if (!registeredMenus.containsKey(gClass)) return;

        CustomInventory menu = registeredMenus.get(gClass);
        Inventory inv = player.getOpenInventory().getTopInventory();
        inv.setContents(menu.getContents(player).get());
    }

    public void addMenu(CustomInventory m) {
        registeredMenus.put(m.getClass(), m);
    }

    public void registersGUI() {
        addMenu(new KitGui());
        addMenu(new ReportsGui());
    }
}
