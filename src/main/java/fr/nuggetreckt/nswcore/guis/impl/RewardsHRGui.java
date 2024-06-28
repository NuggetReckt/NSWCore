package fr.nuggetreckt.nswcore.guis.impl;

import fr.noskillworld.api.NSWAPI;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RewardsHRGui implements CustomInventory {

    private final NSWAPI nswapi;

    public RewardsHRGui(NSWAPI api) {
        this.nswapi = api;
    }

    @Override
    public String getName() {
        return "§8§l»§r §3Honneur §8§l«§r §8(§fRécompenses§8)";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];

        //Reward items

        //Utils
        slots[21] = new ItemUtils(Material.ARROW).setName("§8§l»§r §3Retour §8§l«").hideFlags().setLore(" ", "§8| §fRetourne au menu principal").toItemStack();
        slots[22] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();

        //Placeholders
        slots[0] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[1] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[7] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[8] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[9] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[17] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[18] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[19] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[25] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[26] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot, boolean isLeftClick) {
        switch (clickedItem.getType()) {
            case BARRIER -> player.closeInventory();
            case ARROW -> {
                player.closeInventory();
                NSWCore.getGuiManager().open(player, HonorRankGui.class);
            }
        }
    }
}
