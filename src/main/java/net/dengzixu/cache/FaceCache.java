package net.dengzixu.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FaceCache {
    private final Logger logger = LoggerFactory.getLogger(FaceCache.class);

    private static final Map<Long, String> faceCacheMap = new HashMap<>();

    private static volatile FaceCache faceCache = null;

    private FaceCache() {

    }

    public static FaceCache getInstance() {
        if (null == faceCache) {
            synchronized (FaceCache.class) {
                if (null == faceCache)
                    faceCache = new FaceCache();
            }
        }

        return faceCache;
    }

    public String get(long uid) {
        String face = faceCacheMap.get(uid);

        if (face != null) {
            logger.debug("[Face Cache] [UID: {}] 头像缓存命中成功, URL: {}", uid, face);
        } else {
            logger.debug("[Face Cache] [UID: {}] 头像缓存命中失败", uid);
        }

        return face;
    }

    public void put(long uid, String face) {
        faceCacheMap.put(uid, face);
    }

    public void remove(long uid) {
        faceCacheMap.remove(uid);
    }
}
