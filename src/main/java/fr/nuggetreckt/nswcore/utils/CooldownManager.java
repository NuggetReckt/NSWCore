package fr.nuggetreckt.nswcore.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public enum CooldownValues {
        DEFAULT_COOLDOWN(600),
        RANKED_COOLDOWN(300),
        ANTISPAM_COOLDOWN(5),
        STAFF_COOLDOWN(0)
        ;

        private final long time;

        CooldownValues(long time) {
            this.time = time;
        }

        public long getValue() {
            return this.time;
        }
    }

    private final Map<UUID, Instant> map = new HashMap<>();

    public void setCooldown(UUID key, Duration duration) {
        map.put(key, Instant.now().plus(duration));
    }

    public boolean hasCooldown(UUID key) {
        Instant cooldown = map.get(key);
        return cooldown != null && Instant.now().isBefore(cooldown);
    }

    public Instant removeCooldown(UUID key) {
        return map.remove(key);
    }

    public Duration getRemainingCooldown(UUID key) {
        Instant cooldown = map.get(key);
        Instant now = Instant.now();
        if (cooldown != null && now.isBefore(cooldown)) {
            return Duration.between(now, cooldown);
        } else {
            return Duration.ZERO;
        }
    }

}