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
        Location location = block.getLocation().add(0.0D, 1.0D, 0.0D);
        Location location2 = location.add(0.0D, 1.0D, 0.0D);
        if(block.getType().isSolid() || (block.getType() == Material.WATER)){
            if(location.getBlock().getType().isAir() && !(block.getType() == Material.WATER) && !(block.getType() == Material.LAVA)){
                if(location2.getBlock().getType().isAir() && !(block.getType() == Material.WATER) && !(block.getType() == Material.LAVA)){
                    return true;
                }
            }
        }
    return false;
    }
}
