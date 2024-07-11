package fr.nuggetreckt.nswcore.guis.impl;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.HonorRanks;
import fr.noskillworld.api.honorranks.impl.HonorRanksHandlerImpl;
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

public class ProgressHRGui implements CustomInventory {

    private final NSWAPI nswapi;

    public ProgressHRGui(NSWAPI api) {
        this.nswapi = api;
    }

    @Override
    public String getName() {
        return "§8§l»§r §3Honneur §8§l«§r §8(§fProgression§8)";
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        //Progress items
        ItemStack[] slots = getProgressItems(player);
        HonorRanksHandlerImpl hr = nswapi.getHonorRanksHandler();

        //Utils
        slots[39] = new ItemUtils(Material.ARROW).setName("§8§l»§r §3Retour §8§l«").hideFlags().setLore(" ", "§8| §fRetourne au menu principal").toItemStack();
        slots[40] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();
        slots[42] = new ItemUtils(Material.LANTERN).setName("§8§l»§r §3Infos §8§l«").hideFlags()
                .setLore(" ", "§8| §fVous êtes rang " + hr.getPlayerRankFormat(player.getUniqueId()), "§8| §fVous avez §3" + hr.getPlayerPoints(player.getUniqueId()) + " §fPoints d'Honneur")
                .toItemStack();

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
        HonorRanksHandlerImpl hr = nswapi.getHonorRanksHandler();

        switch (clickedItem.getType()) {
            case BARRIER -> player.closeInventory();
            case ARROW -> {
                player.closeInventory();
                NSWCore.getGuiManager().open(player, HonorRankGui.class);
            }
            case YELLOW_STAINED_GLASS_PANE -> {
                if (hr.getNextPlayerRank(player.getUniqueId()) != null) {
                    if (hr.getPlayerPoints(player.getUniqueId()) >= hr.getPointsNeeded(player.getUniqueId())) {
                        player.closeInventory();
                        hr.upRankPlayer(player.getUniqueId());
                        NSWCore.getEffectUtils().uprankEffect(player);
                    } else {
                        player.sendMessage(String.format(MessageManager.NO_ENOUGH_HONORPOINTS.getMessage(), "HR", hr.getPlayerPoints(player.getUniqueId()), hr.getPointsNeeded(player.getUniqueId())));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.MAX_HONORRANK.getMessage(), "HR", hr.getRankFormat(hr.getPlayerRank(player.getUniqueId()))));
                }
            }
        }
    }

    private ItemStack @NotNull [] getProgressItems(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];
        HonorRanksHandlerImpl hr = nswapi.getHonorRanksHandler();
        int current = 19;

        for (HonorRanks rank : HonorRanks.values()) {
            ItemStack tmp;

            if (hr.getPreviousPlayerRanks(player.getUniqueId()).contains(rank)) {
                tmp = new ItemUtils(Material.LIME_STAINED_GLASS_PANE).setName("§8§l»§r §fRang " + hr.getRankFormat(rank) + " §8§l« §8[§a✔§8]").setLore(" ", " §8|§f §3" + rank.getHonorPoints() + "§f Points d'Honneur requis").hideFlags().toItemStack();
            } else if (rank == hr.getNextPlayerRank(player.getUniqueId())) {
                String lore = " §8§l»§r §6§lClique pour uprank !";

                if (hr.getPlayerPoints(player.getUniqueId()) < hr.getPointsNeeded(player.getUniqueId())) {
                    lore = " §8§l»§r §cTu n'as pas assez de points pour uprank §8(§3" + hr.getPlayerPoints(player.getUniqueId()) + "§8)";
                }
                tmp = new ItemUtils(Material.YELLOW_STAINED_GLASS_PANE).setName("§8§l»§r §fRang " + hr.getRankFormat(rank) + " §8§l« §8[§3§lPROCHAIN§8]").setLore(" ", " §8|§f §3" + rank.getHonorPoints() + "§f Points d'Honneur requis", " ", lore).hideFlags().toItemStack();
            } else {
                tmp = new ItemUtils(Material.GRAY_STAINED_GLASS_PANE).setName("§8§l»§r §fRang " + hr.getRankFormat(rank) + " §8§l«").setLore(" ", " §8|§f §3" + rank.getHonorPoints() + "§f Points d'Honneur requis").hideFlags().toItemStack();
            }
            if (rank == HonorRanks.values()[HonorRanks.values().length - 1]) {
                if (hr.getNextPlayerRank(player.getUniqueId()) == rank) {
                    tmp.setType(Material.YELLOW_STAINED_GLASS_PANE);
                } else {
                    tmp.setType(Material.MAGENTA_STAINED_GLASS_PANE);
                }
                slots[current + 1] = tmp;
            } else {
                slots[current] = tmp;
            }
            current++;
        }
        return slots;
    }
}
