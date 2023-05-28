package fr.nuggetreckt.nswcore.expansions;

import fr.nuggetreckt.nswcore.HonorRanks;
import fr.nuggetreckt.nswcore.NSWCore;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getAuthor() {
        return "NuggetReckt";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nswcore";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        HonorRanks hr = NSWCore.getHonorRanks();

        if (params.equalsIgnoreCase("displayname")) {
            return hr.getDisplayName(player);
        }
        if (params.equalsIgnoreCase("prefix")) {
            return hr.getPrefix(player);
        }
        if (params.equalsIgnoreCase("honorpoints")) {
            return String.valueOf(hr.getPlayerPoints(player));
        }
        if (params.equalsIgnoreCase("honorpoints_needed")) {
            return String.valueOf(hr.getPointsNeeded(player));
        }
        return null; // Placeholder is unknown by the Expansion
    }
}