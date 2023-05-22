package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.guis.KitGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {

    public static HashMap<UUID, Class<? extends InventoryUtils>> map = new HashMap<>();

    public Map<Class<? extends InventoryUtils>, InventoryUtils> registeredMenus = new HashMap<>();

    public void addMenu(InventoryUtils m) {
        this.registeredMenus.put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends InventoryUtils> gClass) {
        if (!this.registeredMenus.containsKey(gClass)) return;

        InventoryUtils menu = this.registeredMenus.get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
        inv.setContents(menu.getContents(player).get());
        player.openInventory(inv);
    }

    public void registersGUI() {
        addMenu(new KitGui());
    }
}
