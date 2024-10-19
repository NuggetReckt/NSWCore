package fr.nuggetreckt.nswcore.expansions;

import fr.noskillworld.api.NSWAPI;
import fr.noskillworld.api.honorranks.HonorRanksHandler;
import fr.nuggetreckt.nswcore.NSWCore;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIExpansion extends PlaceholderExpansion {

    private final NSWCore instance;
    private final NSWAPI nswapi;

    public PAPIExpansion(@NotNull NSWCore instance) {
        this.instance = instance;
        this.nswapi = instance.getAPI();
    }

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
        HonorRanksHandler hr = nswapi.getHonorRanksHandler();

        if (params.equalsIgnoreCase("coloredname")) {
            return instance.getColoredName();
        }
        if (params.equalsIgnoreCase("rank_displayname")) {
            return hr.getDisplayName(player.getUniqueId());
        }
        if (params.equalsIgnoreCase("rank_nbcolored")) {
            return hr.getPlayerRankFormat(player.getUniqueId());
        }
        if (params.equalsIgnoreCase("rank_prefix")) {
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