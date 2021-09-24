package net.dengzixu.api;

import net.dengzixu.utils.OkHTTPRequestUtil;
import okhttp3.Request;

import java.util.Objects;

public class AccountInfo {
    private static final String URL = "https://api.bilibili.com/x/space/acc/info?mid=";

    public String getFace(long uid) {
        Request request = new Request.Builder()
                .get()
                .url(URL + uid)
                .build();

        return (String) Objects.requireNonNull(OkHTTPRequestUtil.requestJson(request)).get("face");

    }
}
