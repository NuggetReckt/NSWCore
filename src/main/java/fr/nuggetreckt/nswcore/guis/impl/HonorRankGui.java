package fr.nuggetreckt.nswcore.guis.impl;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.impl.HonorRanksHandlerImpl;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class HonorRankGui implements CustomInventory {

    private final NSWAPI nswapi;

    public HonorRankGui(NSWAPI api) {
        this.nswapi = api;
    }

    @Override
    public String getName() {
        return "§8§l»§r §3Honneur §8§l«§r §8(§fMenu§8)";
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public Supplier<ItemStack[]> getContents(@NotNull Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];
        HonorRanksHandlerImpl hr = nswapi.getHonorRanksHandler();

        //Sub menus
        slots[20] = new ItemUtils(Material.NETHER_STAR).setName("§8§l»§r §3Progression §8§l«").hideFlags().setLore(" ", "§8| §fAffiche ta progression", "§f dans les Rangs d'Honneur").toItemStack();
        slots[22] = new ItemUtils(Material.COMPASS).setName("§8§l»§r §3Infos §8§l«").hideFlags()
                .setLore(" ", "§8| §fRang actuel : §r" + hr.getPlayerRankFormat(player.getUniqueId()), "§8| §fPoints d'Honneur : §3" + hr.getPlayerPoints(player.getUniqueId()))
                .toItemStack();
        slots[24] = new ItemUtils(Material.DIAMOND).setName("§8§l»§r §3Récompenses §8§l«").hideFlags().setLore(" ", "§8| §fAffiche tes récompenses d'uprank").toItemStack();

        //Utils
        slots[40] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();
        slots[42] = new ItemUtils(Material.LANTERN).setName("§8§l»§r §3Aide §8§l«").hideFlags().setLore(" ", "§8| §fAffiche la page d'aide", "§f des Rangs d'Honneur").toItemStack();

        //Placeholders
        slots[0] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[1] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[7] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[8] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[9] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[17] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[27] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[35] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[36] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[37] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[43] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[44] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot, boolean isLeftClick) {
        switch (clickedItem.getType()) {
            case BARRIER -> {
                player.closeInventory();
                NSWCore.getEffectUtils().playSound(player, Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
            }
            case LANTERN -> {
                player.closeInventory();
                player.performCommand("help honneur");
                NSWCore.getEffectUtils().playSound(player, Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
            }
            case NETHER_STAR -> {
                player.closeInventory();
                NSWCore.getEffectUtils().playSound(player, Sound.ITEM_BOOK_PAGE_TURN);
                NSWCore.getGuiManager().open(player, ProgressHRGui.class);
            }
            case DIAMOND -> {
                player.closeInventory();
                NSWCore.getEffectUtils().playSound(player, Sound.ITEM_BOOK_PAGE_TURN);
                NSWCore.getGuiManager().open(player, RewardsHRGui.class);
            }
        }
    }
}
