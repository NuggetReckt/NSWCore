package fr.nuggetreckt.nswcore.utils;

import fr.noskillworld.api.honorranks.rewards.HonorRankReward;
import fr.nuggetreckt.nswcore.NSWCore;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RewardUtils {

    public static void claimReward(NSWCore instance, Player player, HonorRankReward reward) {
        if (reward == null) return;

        switch (reward.getRewardType()) {
            case COMMAND, PERMISSION -> instance.getLuckPermsUtils().setPermission(player, (String) reward.getReward());
            case NS_COINS -> instance.getEconomy().depositPlayer(player, (long) reward.getReward());
        }
        player.sendMessage(String.format(MessageManager.REWARD_CLAIMED.getMessage(), "HR", reward.getName()));
        instance.getEffectUtils().playSound(player, Sound.BLOCK_AMETHYST_BLOCK_BREAK);
    }
}
