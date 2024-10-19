package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LuckPermsUtils {

    private final NSWCore instance;

    public LuckPermsUtils(@NotNull NSWCore instance) {
        this.instance = instance;
    }

    public void setPermission(Player player, String permission) {
        User user = getUser(player);

        user.data().add(Node.builder(permission).build());
        instance.getLuckPermsAPI().getUserManager().saveUser(user);
    }

    public void unsetPermission(Player player, String permission) {
        User user = getUser(player);

        user.data().remove(Node.builder(permission).build());
        instance.getLuckPermsAPI().getUserManager().saveUser(user);
    }

    public boolean hasPermission(Player player, String permission) {
        return getUser(player).getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    private @NotNull User getUser(Player player) {
        return instance.getLuckPermsAPI().getPlayerAdapter(Player.class).getUser(player);
    }
}
