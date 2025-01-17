package fr.nuggetreckt.nswcore.guis;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.entities.NSWPlayer;
import fr.noskillworld.api.gui.CustomInventory;
import fr.nuggetreckt.nswcore.NSWCore;
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
import java.util.UUID;
import java.util.function.Supplier;

public class KitGui implements CustomInventory {

    private final NSWCore instance;
    private final NSWAPI nswapi;

    public KitGui(@NotNull NSWCore instance) {
        this.instance = instance;
        this.nswapi = instance.getAPI();
    }

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

        //Utils
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
            case BARRIER -> {
                player.closeInventory();
                instance.getEffectUtils().playSound(player, Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
            }
            case STONE_PICKAXE -> {
                UUID playerId = player.getUniqueId();
                NSWPlayer nswPlayer = new NSWPlayer(player.getName(), player.getUniqueId());
                int kitUses = nswapi.getDatabaseManager().getRequestSender().getKitUses(nswPlayer);

                if (kitUses >= 2) {
                    player.sendMessage(String.format(MessageManager.NO_KIT_USES_LEFT.getMessage(), "Kit"));
                    player.closeInventory();
                    return;
                }
                CooldownManager cooldownManager = instance.getCooldownManager();
                Duration timeLeft = cooldownManager.getRemainingCooldown(playerId, "kit");

                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    //Vérifie si l'inventaire du joueur est plein
                    int itemCount = 0;
                    for (ItemStack i : player.getInventory().getContents()) {
                        if (i != null && !i.getType().isAir()) {
                            itemCount++;
                        }
                    }

                    if (itemCount <= 29) {
                        player.getInventory().addItem(new ItemUtils(Material.STONE_AXE).setName("§fHache du débutant").toItemStack());
                        player.getInventory().addItem(new ItemUtils(Material.STONE_PICKAXE).setName("§fPioche du débutant").toItemStack());
                        player.getInventory().addItem(new ItemUtils(Material.COOKED_BEEF, 16).toItemStack());
                        player.getInventory().addItem(new ItemUtils(Material.LEATHER_HELMET).setName("§fCasque du débutant").toItemStack());
                        player.getInventory().addItem(new ItemUtils(Material.LEATHER_CHESTPLATE).setName("§fPlastron du débutant").toItemStack());
                        player.getInventory().addItem(new ItemUtils(Material.LEATHER_LEGGINGS).setName("§fJambières du débutant").toItemStack());
                        player.getInventory().addItem(new ItemUtils(Material.LEATHER_BOOTS).setName("§fBottes du débutant").toItemStack());

                        if (player.hasPermission("nsw.bypass") || player.isOp()) {
                            cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "kit");
                        } else {
                            cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.KIT_COOLDOWN.getValue()), "kit");
                        }
                        player.sendMessage(String.format(MessageManager.KIT_RECEIVED.getMessage(), "Kit"));
                        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 15, 1);
                        nswapi.getServerHandler().getExecutor().execute(() -> nswapi.getDatabaseManager().getRequestSender().updateKitUses(nswPlayer, kitUses + 1));
                    } else {
                        player.sendMessage(String.format(MessageManager.NOT_ENOUGH_ROOM_INV.getMessage(), "Kit"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_KIT.getMessage(), "Kit", timeLeft.toHours()));
                }
                player.closeInventory();
            }
        }
    }
}
