package fr.nuggetreckt.nswcore.expansions;

import fr.nuggetreckt.nswcore.HonorRanks;
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
        HonorRanks ranks = new HonorRanks();

        if (params.equalsIgnoreCase("displayname")) {
            return ranks.getDisplayName(player);
        }
        if (params.equalsIgnoreCase("prefix")) {
            return ranks.getPrefix(player);
        }
        if (params.equalsIgnoreCase("honorpoints")) {
            //return String.valueOf(ranks.getPlayerPoints(player));
            return "honorpoints";
        }
        if (params.equalsIgnoreCase("honorpoints_needed")) {
            //return String.valueOf(ranks.getPointsNeeded(player));
            return "honorpoints_needed";
        }
        return null; // Placeholder is unknown by the Expansion
    }
}