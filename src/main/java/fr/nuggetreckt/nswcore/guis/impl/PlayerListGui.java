package fr.nuggetreckt.nswcore.guis.impl;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Supplier;

public class PlayerListGui implements CustomInventory {

    private HashMap<Integer, Player> players;

    @Override
    public String getName() {
        return "§8§l»§r §3Joueurs §8§l«§r";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];
        players = new HashMap<>();
        int slot = 0;

        //Player items
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (slot > getSlots() - 9) {
                break;
            }
            slots[slot] = new ItemUtils(Material.PLAYER_HEAD).setName("§8§l»§r §3§l" + p.getName() + " §8§l«")
                    .setLore(" ", " §8| §fClic gauche : §aSe téléporter", " §8| §fClic droit : §aVoir l'inventaire")
                    .setSkullOwner(p.getName()).toItemStack();
            players.put(slot, p);
            slot++;
        }

        //Utils
        slots[49] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();
        slots[53] = new ItemUtils(Material.SNOWBALL).setName("§8§l»§r §3Rafraîchir §8§l«").hideFlags().setLore(" ", "§8| §fActualise la page").toItemStack();

        //Placeholders
        slots[45] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[46] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[47] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[48] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[50] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[51] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();
        slots[52] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName(" ").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot, boolean isLeftClick) {
        switch (clickedItem.getType()) {
            case BARRIER -> player.closeInventory();
            case SNOWBALL -> NSWCore.getGuiManager().refresh(player, this.getClass());
            case PLAYER_HEAD -> {
                player.closeInventory();
                Player target = players.get(slot);
                if (isLeftClick) {
                    player.teleport(target);
                } else {
                    player.openInventory(target.getInventory());
                }
            }
        }
    }
}
