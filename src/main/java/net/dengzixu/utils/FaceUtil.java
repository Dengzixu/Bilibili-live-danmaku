package net.dengzixu.utils;

import net.dengzixu.api.AccountInfo;
import net.dengzixu.cache.FaceCache;

public class FaceUtil {
    public static String getFace(long uid) {
        // 用户头像缓存
        FaceCache faceCache = FaceCache.getInstance();

        // 判断缓存里是否存在头像
        String faceUrl = faceCache.get(uid);

        if (null == faceUrl) {
            // 当缓存中不存在头像时，从服务器中获取
            new Thread(() -> {
               try {
                   String face = new AccountInfo().getFace(uid);

                   FaceCache.getInstance().put(uid, face);
               } catch (NullPointerException ignored){

               }
            }).start();

            return null;
        } else {
            return faceUrl;
        }
    }
}
