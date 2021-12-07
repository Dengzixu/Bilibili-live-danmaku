package net.dengzixu.constant;

import java.util.HashMap;
import java.util.Map;

public enum BodyCommandEnum {
    DANMU_MSG("DANMU_MSG"),
    INTERACT_WORD("INTERACT_WORD"),
    SEND_GIFT("SEND_GIFT"),
    COMBO_SEND("COMBO_SEND"),

    STOP_LIVE_ROOM_LIST("STOP_LIVE_ROOM_LIST"),

    @Deprecated
    AUTH_SUCCESS("AUTH_SUCCESS"),
    @Deprecated
    POPULARITY("POPULARITY"),

    UNKNOWN("UNKNOWN");

    private final String command;

    private static final Map<String, BodyCommandEnum> enumMap = new HashMap<>();

    static {
        for (BodyCommandEnum e : values()) {
            enumMap.put(e.command(), e);
        }
    }

    BodyCommandEnum(String command) {
        this.command = command;
    }

    public static BodyCommandEnum getEnum(String command) {
        return null == enumMap.get(command) ? UNKNOWN : enumMap.get(command);
    }

    public String command() {
        return command;
    }

    @Override
    public String toString() {
        return command;
    }
}
