package sky.test.parental.control.service;

import java.util.HashMap;
import java.util.Map;

public enum ParentalControlLevel {

    U("U"), PG("PG"), M_12("12"), M_15("15"), M_18("18");

    private static final Map<String, ParentalControlLevel> LEVELS = new HashMap<>();

    private final String value;

    ParentalControlLevel(String value) {
        this.value = value;
    }

    public static ParentalControlLevel getValueOf(String s) {
        return LEVELS.get(s);
    }

    public String value() {
        return this.value;
    }

    static {
        for (ParentalControlLevel p : ParentalControlLevel.values()) {
            LEVELS.put(p.value, p);
        }
    }

}
