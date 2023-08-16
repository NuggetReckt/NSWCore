package fr.nuggetreckt.nswcore.guis.impl;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

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

        //Init items
        NSWCore.getReportUtils().setReportItems(maskResolvedReports);

        //Display items
        for (int i = 0; i < getSlots(); i++) {
            slots[i] = NSWCore.getReportUtils().getReportItem(i + 1);
        }

        //Utils
        //TODO: Tri par date > et <, nom du joueur report alphabétique > et <
        slots[48] = new ItemUtils(Material.SLIME_BALL).setName("§8§l»§r §3Masquer les reports résolus §8§l«").hideFlags().setLore(" ", "§8| §fMasqués : " + NSWCore.getReportUtils().getShownStatus(maskResolvedReports)).toItemStack();
        slots[49] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();
        slots[50] = new ItemUtils(Material.HOPPER).setName("§8§l»§r §3Trier §8§l«").hideFlags().setLore(" ", "§8| §fTrié par §a ", "§cTODO").toItemStack();
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
                if (isLeftClick) {
                    NSWCore.getReportUtils().markReportAsResolved(slot + 1);
                    NSWCore.getGuiManager().refresh(player, this.getClass());
                } else {
                    if (player.hasPermission("nsw.*")) {
                        NSWCore.getReportUtils().deleteReport(slot + 1);
                        NSWCore.getGuiManager().refresh(player, this.getClass());
                    } else {
                        player.sendMessage(String.format(MessageManager.NO_PERMISSION.getMessage(), "Reports"));
                    }
                }
            }
            case SLIME_BALL -> {
                maskResolvedReports = !maskResolvedReports;
                NSWCore.getGuiManager().refresh(player, this.getClass());
            }
            case HOPPER -> {
                player.sendMessage("§cfonctionnalité non disponible pour le moment.");
                NSWCore.getGuiManager().refresh(player, this.getClass());
            }
        }
    }
}
