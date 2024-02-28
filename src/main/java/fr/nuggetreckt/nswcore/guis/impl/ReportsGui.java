package fr.nuggetreckt.nswcore.guis.impl;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.reports.Report;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.ReportUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ReportsGui implements CustomInventory {

    private boolean maskResolvedReports = false;

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
        List<ItemStack> reportItems = NSWCore.getReportUtils().getReportItems(maskResolvedReports, player);
        ReportUtils reportUtils = NSWCore.getReportUtils();


        //Display items
        for (int i = 0; i < getSlots() && i < reportItems.size(); i++) {
            slots[i] = reportItems.get(i);
        }

        //Utils
        slots[48] = new ItemUtils(Material.SLIME_BALL).setName("§8§l»§r §3Masquer les reports résolus §8§l«").hideFlags().setLore(" ", "§8| §fMasqués : " + reportUtils.getShownStatus(maskResolvedReports)).toItemStack();
        slots[49] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();
        slots[50] = new ItemUtils(Material.HOPPER).setName("§8§l»§r §3Trier §8§l«").hideFlags().setLore(" ", "§8| §fTrié par " + reportUtils.getCurrentSortReport(player).getName()).toItemStack();
        slots[53] = new ItemUtils(Material.SNOWBALL).setName("§8§l»§r §3Rafraîchir §8§l«").hideFlags().setLore(" ", "§8| §fActualise la page").toItemStack();

        //Placeholders
        slots[45] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[46] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[47] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[51] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[52] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot, boolean isLeftClick) {
        switch (clickedItem.getType()) {
            case BARRIER -> player.closeInventory();
            case SNOWBALL -> NSWCore.getGuiManager().refresh(player, this.getClass());
            case PAPER -> {
                Report report = NSWCore.getReportUtils().getReportBySlot(slot);

                if (report == null) return;
                if (isLeftClick) {
                    NSWCore.getReportUtils().markReportResolved(report);
                } else {
                    if (player.hasPermission("nsw.*")) {
                        NSWCore.getReportUtils().deleteReport(report);
                    } else {
                        player.sendMessage(String.format(MessageManager.NO_PERMISSION.getMessage(), "Reports"));
                        return;
                    }
                }
                NSWAPI.getAPI().getServerHandler().getExecutor().schedule(() -> NSWCore.getGuiManager().refresh(player, this.getClass()), 250, TimeUnit.MILLISECONDS);
            }
            case SLIME_BALL -> {
                maskResolvedReports = !maskResolvedReports;
                NSWCore.getGuiManager().refresh(player, this.getClass());
            }
            case HOPPER -> {
                NSWCore.getReportUtils().toggleReportSort(player);
                NSWCore.getGuiManager().refresh(player, this.getClass());
            }
        }
    }
}
