package com.sunmediaeg.offers.utilities;

import java.util.HashMap;

/**
 * Created by mwael on 3/12/2017.
 */

public class CacheManager {

    private static CacheManager instance;
    private HashMap<String, Object> chache;

    private CacheManager() {
        chache = new HashMap<>();
    }

    public static CacheManager getInstance() {
        if (instance != null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void chacheObject(String key, Object object) {
        chache.put(key, object);
    }

    public Object getCachedObject(String key) {
        return chache.get(key);
    }

    public HashMap<String, Object> getChache() {
        return chache;
    }
}
