package fr.nuggetreckt.nswcore.guis.impl;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class KitGui implements CustomInventory {

    @Override
    public String getName() {
        return "§8§l»§r §3Kit §8§l«§r";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];

        //Items
        slots[13] = new ItemUtils(Material.STONE_PICKAXE).setName("§8§l»§r §3Kit du débutant §8§l«").hideFlags().setLore(" ", "§8| §fRécupérer son kit").toItemStack();

        //Placeholders
        slots[0] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[1] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[7] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[8] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[9] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[17] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[18] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[19] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[25] = new ItemUtils(Material.BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();
        slots[26] = new ItemUtils(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName("").setLore("").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot) {
        if (!clickedItem.hasItemMeta()) return;
        if (!Objects.requireNonNull(clickedItem.getItemMeta()).hasDisplayName()) return;

        if (clickedItem.getItemMeta().getDisplayName().equals("§8§l»§r §3Kit du débutant §8§l«")) {
            UUID playerId = player.getUniqueId();
            CooldownManager cooldownManager = NSWCore.getCooldownManager();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId);

            if (timeLeft.isZero() || timeLeft.isNegative()) {
                //TODO: Vérifier si l'inventaire du joueur est plein
                player.getInventory().addItem(new ItemUtils(Material.STONE_AXE).setName("§fHache du débutant").toItemStack());
                player.getInventory().addItem(new ItemUtils(Material.STONE_PICKAXE).setName("§fPioche du débutant").toItemStack());
                player.getInventory().addItem(new ItemUtils(Material.COOKED_BEEF, 16).toItemStack());
                player.getInventory().addItem(new ItemUtils(Material.LEATHER_HELMET).setName("§fCasque du débutant").toItemStack());
                player.getInventory().addItem(new ItemUtils(Material.LEATHER_CHESTPLATE).setName("§fPlastron du débutant").toItemStack());
                player.getInventory().addItem(new ItemUtils(Material.LEATHER_LEGGINGS).setName("§fJambières du débutant").toItemStack());
                player.getInventory().addItem(new ItemUtils(Material.LEATHER_BOOTS).setName("§fBottes du débutant").toItemStack());

                cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.KIT_COOLDOWN.getValue()));
                player.sendMessage(String.format(MessageManager.KIT_RECEIVED.getMessage(), "Kit"));
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 15, 1);
            } else {
                player.sendMessage(String.format(MessageManager.WAIT_BEFORE_KIT_MESSAGE.getMessage(), "Kit", timeLeft.toHours()));
            }
            player.closeInventory();
        }
    }
}
