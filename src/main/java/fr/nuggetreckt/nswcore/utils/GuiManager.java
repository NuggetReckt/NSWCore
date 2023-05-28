package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.guis.impl.KitGui;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.guis.impl.TestGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {

    public GuiManager() {
        registersGUI();
    }

    public HashMap<UUID, Class<? extends CustomInventory>> map = new HashMap<>();
    public Map<Class<? extends CustomInventory>, CustomInventory> registeredMenus = new HashMap<>();

    public Map<Class<? extends CustomInventory>, CustomInventory> getMenus() {
        return registeredMenus;
    }

    public void open(Player player, Class<? extends CustomInventory> gClass) {
        if (!registeredMenus.containsKey(gClass)) return;

        CustomInventory menu = registeredMenus.get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
        inv.setContents(menu.getContents(player).get());
        player.openInventory(inv);
    }

    public void addMenu(CustomInventory m) {
        registeredMenus.put(m.getClass(), m);
    }

    public void registersGUI() {
        addMenu(new KitGui());
        addMenu(new TestGui());
    }
}
