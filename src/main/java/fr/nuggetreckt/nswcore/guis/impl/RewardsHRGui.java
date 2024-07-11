package fr.nuggetreckt.nswcore.guis.impl;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.impl.HonorRanksHandlerImpl;
import fr.noskillworld.api.honorranks.rewards.HonorRankReward;
import fr.noskillworld.api.honorranks.rewards.RewardHandler;
import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.guis.CustomInventory;
import fr.nuggetreckt.nswcore.utils.ItemUtils;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import fr.nuggetreckt.nswcore.utils.RewardUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class RewardsHRGui implements CustomInventory {

    private final HashMap<Integer, HonorRankReward> rewardsSlots;

    private final NSWAPI nswapi;

    public RewardsHRGui(NSWAPI api) {
        this.nswapi = api;
        this.rewardsSlots = new HashMap<>();
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
    public Supplier<ItemStack[]> getContents(@NotNull Player player) {
        ItemStack[] slots = new ItemStack[getSlots()];
        HonorRanksHandlerImpl hr = nswapi.getHonorRanksHandler();
        RewardHandler rewardHandler = nswapi.getRewardHandler();
        List<HonorRankReward> rewards = rewardHandler.rankRewards.get(hr.getPlayerRank(player.getUniqueId()));
        int slot = 11;

        rewardsSlots.clear();

        //Reward items
        for (HonorRankReward reward : rewards) {
            slots[slot] = new ItemUtils(Material.PAPER).setName(reward.getName()).addEnchant(Enchantment.MENDING, 1).hideFlags().setLore(" ", " §8| §f" + reward.getReward().toString()).toItemStack();
            rewardsSlots.put(slot, reward);
            slot++;
        }
        //slots[13] = new ItemUtils(Material.PUFFERFISH).setName("§8§l»§r §3Soon §8§l«").hideFlags().setLore(" ", "§8| §fFonctionnalité à venir...").toItemStack();

        //Utils
        slots[21] = new ItemUtils(Material.ARROW).setName("§8§l»§r §3Retour §8§l«").hideFlags().setLore(" ", "§8| §fRetourne au menu principal").toItemStack();
        slots[22] = new ItemUtils(Material.BARRIER).setName("§8§l»§r §3Fermer §8§l«").hideFlags().setLore(" ", "§8| §fFerme le menu").toItemStack();
        slots[24] = new ItemUtils(Material.LANTERN).setName("§8§l»§r §3Infos §8§l«").hideFlags()
                .setLore(" ", "§8| §fVous êtes rang " + hr.getPlayerRankFormat(player.getUniqueId()), "§8| §fVous avez §3" + hr.getPlayerPoints(player.getUniqueId()) + " §fPoints d'Honneur").toItemStack();

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
            default -> {
                if (!isClickable(clickedItem)) return;
                RewardHandler rewardHandler = nswapi.getRewardHandler();
                HonorRankReward reward = rewardsSlots.get(slot);

                if (reward == null) return;
                if (rewardHandler.hasClaimedReward(player.getUniqueId(), reward)) {
                    player.sendMessage(String.format(MessageManager.REWARD_ALREADY_CLAIMED.getMessage(), "HR"));
                    return;
                }
                rewardHandler.setRewardClaimed(player.getUniqueId(), reward);
                RewardUtils.claimReward(player, reward);
                NSWCore.getGuiManager().refresh(player, this.getClass());
            }
        }
    }

    private boolean isClickable(@NotNull ItemStack clickedItem) {
        if (clickedItem.getType() == Material.AIR) return false;
        return clickedItem.getType() != Material.LIGHT_BLUE_STAINED_GLASS_PANE && !Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName().equals(" ");
    }
}
