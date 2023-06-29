package fr.nuggetreckt.nswcore.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
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
        Block block1 = block.getRelative(BlockFace.UP);
        Block block2 = block1.getRelative(BlockFace.UP);
        Material material1 = block1.getBlockData().getMaterial();
        Material material2 = block2.getBlockData().getMaterial();

        if(material1 == Material.LAVA || material2 == Material.LAVA) return false;

        return material1.isAir() && material2.isAir() && (block.getType().isSolid() || block.getType() == Material.WATER);
    }
}
