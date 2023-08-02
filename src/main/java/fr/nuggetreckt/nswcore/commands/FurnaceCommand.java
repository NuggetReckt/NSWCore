package fr.nuggetreckt.nswcore.commands;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.utils.CooldownManager;
import fr.nuggetreckt.nswcore.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.UUID;

public class FurnaceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            UUID playerId = player.getUniqueId();

            CooldownManager cooldownManager = NSWCore.getCooldownManager();
            Duration timeLeft = cooldownManager.getRemainingCooldown(playerId, "furnace");

            if (player.hasPermission("nsw.command.furnace")) {
                if (timeLeft.isZero() || timeLeft.isNegative()) {
                    if (player.isOp() || player.hasPermission("nsw.bypass")) {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.NO_COOLDOWN.getValue()), "furnace");
                    } else {
                        cooldownManager.setCooldown(playerId, Duration.ofSeconds(CooldownManager.CooldownValues.FURNACE_COOLDOWN.getValue()), "furnace");
                    }

                    ItemStack item = player.getInventory().getItemInMainHand();
                    Material material = item.getType();

                    if (!material.isAir()) {
                        switch (material) {
                            case COBBLESTONE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.STONE, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case IRON_ORE, RAW_IRON -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case GOLD_ORE, RAW_GOLD -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case ANCIENT_DEBRIS -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.NETHERITE_SCRAP, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case RAW_COPPER -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COPPER_INGOT, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case SAND -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.GLASS, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case PORKCHOP -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COOKED_PORKCHOP, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case BEEF -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case CHICKEN -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case POTATO -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.BAKED_POTATO, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case RABBIT -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COOKED_RABBIT, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case MUTTON -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COOKED_MUTTON, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case LAPIS_ORE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.LAPIS_LAZULI, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case NETHERRACK -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.NETHER_BRICK, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case CLAY_BALL -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.BRICK, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case COAL_ORE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.COAL, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case DIAMOND_ORE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.DIAMOND, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case REDSTONE_ORE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.REDSTONE, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case EMERALD_ORE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.EMERALD, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case NETHER_QUARTZ_ORE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.QUARTZ, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case CACTUS -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.GREEN_DYE, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            case WET_SPONGE -> {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                player.getInventory().addItem(new ItemStack(Material.SPONGE, item.getAmount()));
                                player.sendMessage(String.format(MessageManager.ITEMS_COOKED.getMessage(), "Furnace"));
                            }
                            default ->
                                    player.sendMessage(String.format(MessageManager.NO_ITEMS_TO_FURNACE.getMessage(), "Furnace"));
                        }
                    } else {
                        player.sendMessage(String.format(MessageManager.NO_ITEMS_TO_FURNACE.getMessage(), "Furnace"));
                    }
                } else {
                    player.sendMessage(String.format(MessageManager.WAIT_BEFORE_USE_SECONDS.getMessage(), "Furnace", timeLeft.toSeconds()));
                }
            } else {
                player.sendMessage(String.format(MessageManager.NO_PERMISSION_CMD.getMessage(), "Furnace"));
            }
        }
        return true;
    }
}
