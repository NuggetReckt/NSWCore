package fr.nuggetreckt.nswcore.guis;

import fr.nuggetreckt.nswcore.listeners.OnInvClickListener;
import fr.nuggetreckt.nswcore.utils.InventoryUtils;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class KitGui implements InventoryUtils {

    @Override
    public String getName() {
        return "kit";
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public Supplier<ItemStack[]> getContent(Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];

        //test
        slots[1] = new ItemUtils(Material.DIRT).setName("dirt").setLore("lore").toItemStack();

        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, @NotNull ItemStack clickedItem, int slot) {
        if (!clickedItem.hasItemMeta()) return;
        if (!Objects.requireNonNull(clickedItem.getItemMeta()).hasDisplayName()) return;

        new OnInvClickListener().setCurrentInv(getName());

        switch (clickedItem.getItemMeta().getDisplayName()) {
            case "dirt":
                player.sendMessage("bisous");
                break;
            case "test":
                player.sendMessage("prout");
                break;
        }

        player.sendMessage(String.format(MessageManager.KIT_RECEIVED.getMessage(), "Kit"));
        player.closeInventory();
    }
}
