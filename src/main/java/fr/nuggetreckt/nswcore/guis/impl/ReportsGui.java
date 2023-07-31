package fr.nuggetreckt.nswcore.guis.impl;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        setReportItems();

        for (int i = 0; i < getSlots(); i++) {
            slots[i] = getReportItem(i);
        }

        //Utils
        slots[49] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();

        //Placeholders
        slots[45] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
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
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot) {
        if (!clickedItem.hasItemMeta()) return;
        if (!Objects.requireNonNull(clickedItem.getItemMeta()).hasDisplayName()) return;

        if (clickedItem.getItemMeta().getDisplayName().equals("§8§l»§r §3Fermer §8§l«")) {
            player.closeInventory();
        }
        if (clickedItem.getType().equals(Material.PAPER)) {
            System.out.println("DEBUG: clickedSlot = " + slot);

            //TOFIX
            if (!NSWCore.getReportUtils().isResolved(slot)) {
                NSWCore.getReportUtils().markReportAsResolved(slot);
                player.closeInventory();
                NSWCore.getGuiManager().open(player, this.getClass());
            }
        }
    }

    private final Map<Integer, ItemStack> reportItems = new HashMap<>();

    private void setReportItems() {
        Requests req = NSWCore.getRequestsManager();

        final int reportsCount = req.getReportsCount();

        String reportedName;
        String creatorName;
        String reportType;
        String reportReason;
        Timestamp timestamp;
        String resolved = "";

        for (int i = 1; i <= reportsCount; i++) {
            if (i <= 44) {
                reportedName = req.getReportedName(i);
                creatorName = req.getCreatorName(i);
                reportType = req.getReportType(i);
                reportReason = req.getReportReason(i);
                timestamp = req.getReportTime(i);

                String reportDate = new SimpleDateFormat("MM/dd/yyyy").format(timestamp);
                String reportTime = new SimpleDateFormat("HH:mm").format(timestamp);

                if (NSWCore.getReportUtils().isResolved(i - 1)) {
                    resolved = " §8(§l§aRésolu§8)";
                }

                ItemStack item = new ItemUtils(Material.PAPER).setName("§8§l»§r §c§l" + reportedName + " §8§l«" + resolved).hideFlags()
                        .setLore(" ", "§8| §fPar §3" + creatorName, "§8| §fPour §3" + reportType, "§8| §fLe §3" +
                                reportDate + " §fà §3" + reportTime, "§8| §fRaison : §7" + reportReason)
                        .toItemStack();

                setReportItem(i - 1, item);
            }
        }
    }

    private void setReportItem(int key, ItemStack item) {
        this.reportItems.putIfAbsent(key, item);
    }

    private ItemStack getReportItem(int key) {
        return this.reportItems.get(key);
    }
}
