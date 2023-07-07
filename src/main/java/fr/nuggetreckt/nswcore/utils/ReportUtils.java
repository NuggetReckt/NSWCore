package fr.nuggetreckt.nswcore.utils;

import fr.nuggetreckt.nswcore.NSWCore;
import fr.nuggetreckt.nswcore.database.Requests;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ReportUtils {
    public enum Type {
        CHEAT(1, "Cheat", ""),
        GRIEF(2, "Grief", ""),
        CHAT_SPAM(3, "Spam", ""),
        CHAT_PUB(4, "Pub", ""),
        CHAT_INSULTS(5, "Insultes", ""),
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

    public String getTypes() {
        StringBuilder sb = new StringBuilder();

        for (Type i : Type.values()) {
            sb.append(" §8|§f Types de report §3").append(i.getDisplayName()).append(" §8(§f").append(i.getDesc()).append("§8)").append("\n");
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
