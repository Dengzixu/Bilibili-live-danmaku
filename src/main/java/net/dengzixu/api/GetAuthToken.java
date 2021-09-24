package net.dengzixu.api;

import net.dengzixu.utils.OkHTTPRequestUtil;
import okhttp3.Request;

import java.util.Objects;

public class GetAuthToken {
    private static final String URL = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=";

    public String get(long roomId) {
        Request request = new Request.Builder()
                .get()
                .url(URL + roomId)
                .build();

        return (String) Objects.requireNonNull(OkHTTPRequestUtil.requestJson(request)).get("token");

    }
}
