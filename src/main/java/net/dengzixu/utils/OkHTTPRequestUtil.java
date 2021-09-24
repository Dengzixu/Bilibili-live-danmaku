package net.dengzixu.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OkHTTPRequestUtil {

    public static Map<?, ?> requestJson(Request request) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() != 200) {
                return null;
            }

            String body = Objects.requireNonNull(response.body()).string();

            if ("".equals(body)) {
                return null;
            }


            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> bodyMap = objectMapper.readValue(body, new TypeReference<>() {
            });

            Map<?, ?> dataMap = new HashMap<>();

            if (bodyMap.get("data") instanceof Map) {
                dataMap = (Map<?, ?>) bodyMap.get("data");
            }

            return dataMap;
        } catch (NullPointerException | IOException e) {
            return null;
        }
    }
}
