package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ReportUtils {
    public enum Type {
        CHEAT(1, "Cheat", "Le joueur triche"),
        GRIEF(2, "Grief", "Le joueur grief"),
        CHAT_SPAM(3, "Spam", "Le joueur spam/flood dans le chat"),
        CHAT_PUB(4, "Pub", "Le joueur fait de la publicité"),
        CHAT_INSULTS(5, "Insultes", "Le joueur insulte d'autres joueurs"),
        ;

        private static final Map<Integer, Type> BY_ID = new HashMap<>();
        private static final Map<String, Type> BY_NAME = new HashMap<>();

        static {
            for (Type i : values()) {
                BY_ID.put(i.typeId, i);
                BY_NAME.put(i.getDisplayName(), i);
            }
        }

        private final int typeId;
        private final String displayName;
        private final String desc;

        Type(int id, String name, String desc) {
            this.typeId = id;
            this.displayName = name;
            this.desc = desc;
        }

        public int geTypeId() {
            return this.typeId;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getDisplayName() {
            return this.displayName;
        }
    }

    public void createReport(Player creator, Player reported, @NotNull Type type, String reason) {
        NSWCore.getServerHandler().getExecutor().execute(() -> new Requests().setReport(creator, reported, type.typeId, reason));
    }

    public void markReportAsResolved(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> new Requests().markReportAsResolved(id));
    }

    public void deleteReport(int id) {
        NSWCore.getServerHandler().getExecutor().execute(() -> new Requests().deleteReport(id));
    }

    public boolean isResolved(int id) {
        int resolved = new Requests().getResolved(id);

        return resolved == 1;
    }

    public String getTypes() {
        StringBuilder sb = new StringBuilder();

        for (Type i : Type.values()) {
            sb.append(" §8|§3 ").append(i.getDisplayName()).append(" §8(§f").append(i.getDesc()).append("§8)").append("\n");
        }
        return sb.toString();
    }

    public Type getTypeByName(String name) {
        return Type.BY_NAME.get(name);
    }

    public Type getTypeById(int id) {
        return Type.BY_ID.get(id);
    }
}
