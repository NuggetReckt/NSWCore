package fr.nuggetreckt.nswcore.expansions;

import fr.noskillworld.api.honorranks.HonorRanksHandler;
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
        HonorRanksHandler hr = NSWCore.getAPI().getHonorRanksHandler();

        if (params.equalsIgnoreCase("coloredname")) {
            return NSWCore.getInstance().getColoredName();
        }
        if (params.equalsIgnoreCase("displayname")) {
            return hr.getDisplayName(player.getUniqueId());
        }
        if (params.equalsIgnoreCase("prefix")) {
            return hr.getPrefix(player.getUniqueId());
        }
        if (params.equalsIgnoreCase("honorpoints")) {
            return String.valueOf(hr.getPlayerPoints(player.getUniqueId()));
        }
        if (params.equalsIgnoreCase("honorpoints_needed")) {
            return String.valueOf(hr.getPointsNeeded(player.getUniqueId()));
        }
        return null; // Placeholder is unknown by the Expansion
    }
}