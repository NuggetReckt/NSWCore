package fr.nuggetreckt.nswcore.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TeleportUtils {

    private final Map<Player, Boolean> isTeleporting;

    public TeleportUtils() {
        isTeleporting = new HashMap<>();
    }

    public boolean isTeleporting(Player player) {
        return isTeleporting.get(player);
    }

    public void initTeleports(Player player) {
        isTeleporting.putIfAbsent(player, false);
    }

    public void setTeleports(Player player, boolean bool) {
        isTeleporting.replace(player, bool);
    }

    public boolean isValid(@NotNull Block block) {
        Location location1 = block.getLocation().add(0.0D, 1.0D, 0.0D);
        Location location2 = location1.add(0.0D, 1.0D, 0.0D);
        Material type1 = location1.getBlock().getType();
        Material type2 = location2.getBlock().getType();

        return type1.isAir() && type2.isAir() && (block.getType().isSolid() || block.getType() == Material.WATER);
    }
}
