package fr.nuggetreckt.nswcore.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public enum CooldownValues {
        KIT_COOLDOWN(14400),
        DEFAULT_COOLDOWN(600),
        DEFAULT_RANKED_COOLDOWN(300),
        SPAWN_COOLDOWN(60),
        ANTISPAM_COOLDOWN(1),
        NO_COOLDOWN(0);

        private final long time;

        CooldownValues(long time) {
            this.time = time;
        }

        public long getValue() {
            return this.time;
        }
    }

    private final Map<UUID, Instant> map = new HashMap<>();
    private final Map<UUID, String> map2 = new HashMap<>();

    public void setCooldown(UUID key, Duration duration, String var) {
        map.put(key, Instant.now().plus(duration));
        map2.put(key, var);
    }

    public boolean hasCooldown(UUID key, String var) {
        Instant cooldown = map.get(key);
        String toCooldown = map2.get(key);

        return cooldown != null && toCooldown != null && Instant.now().isBefore(cooldown) && toCooldown.equals(var);
    }

    public void removeCooldown(UUID key) {
        map.remove(key);
        map2.remove(key);
    }

    public Duration getRemainingCooldown(UUID key, String var) {
        Instant cooldown = map.get(key);
        String toCooldown = map2.get(key);

        Instant now = Instant.now();
        if (cooldown != null && toCooldown != null && now.isBefore(cooldown) && toCooldown.equals(var)) {
            return Duration.between(now, cooldown);
        } else {
            return Duration.ZERO;
        }
    }

}
