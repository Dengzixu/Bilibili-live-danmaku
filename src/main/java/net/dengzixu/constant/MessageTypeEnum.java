package net.dengzixu.constant;

import java.util.HashMap;
import java.util.Map;

public enum MessageTypeEnum {
    DANMU_MSG("DANMU_MSG"),
    INTERACT_WORD("INTERACT_WORD"),
    SEND_GIFT("SEND_GIFT"),
    COMBO_SEND("COMBO_SEND"),

    STOP_LIVE_ROOM_LIST("STOP_LIVE_ROOM_LIST"),

    AUTH_SUCCESS("AUTH_SUCCESS"),
    POPULARITY("POPULARITY"),

    UNKNOWN("UNKNOWN");

    private final String type;
    private static final Map<String, MessageTypeEnum> enumMap = new HashMap<>();

    static {
        for (MessageTypeEnum e : values()) {
            enumMap.put(e.type(), e);
        }
    }

    MessageTypeEnum(String type) {
        this.type = type;
    }

    public static MessageTypeEnum getEnum(String type) {
        return null == enumMap.get(type) ? UNKNOWN : enumMap.get(type);
    }

    public String type() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
