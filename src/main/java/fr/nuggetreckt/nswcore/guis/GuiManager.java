package fr.nuggetreckt.nswcore.guis;

import fr.noskillworld.api.NSWAPI;
import fr.nuggetreckt.nswcore.guis.impl.KitGui;
import fr.nuggetreckt.nswcore.guis.impl.PlayerListGui;
import fr.nuggetreckt.nswcore.guis.impl.ReportsGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {

    private final NSWAPI nswapi;

    public GuiManager(NSWAPI api) {
        this.nswapi = api;
        this.registersGUI();
    }

    public final Map<Class<? extends CustomInventory>, CustomInventory> registeredMenus = new HashMap<>();

    public void open(Player player, Class<? extends CustomInventory> gClass) {
        if (!registeredMenus.containsKey(gClass)) return;

        CustomInventory menu = registeredMenus.get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
        inv.setContents(menu.getContents(player).get());
        player.openInventory(inv);
    }

    public void refresh(Player player, Class<? extends CustomInventory> gClass) {
        if (!registeredMenus.containsKey(gClass)) return;

        CustomInventory menu = registeredMenus.get(gClass);
        Inventory inv = player.getOpenInventory().getTopInventory();
        inv.setContents(menu.getContents(player).get());
    }

    private void addMenu(CustomInventory m) {
        registeredMenus.put(m.getClass(), m);
    }

    private void registersGUI() {
        addMenu(new KitGui(nswapi));
        addMenu(new ReportsGui(nswapi));
        addMenu(new PlayerListGui());
    }
}
