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
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public Object chacheObject(String key, Object object) {
        if (object != null) {
            Logger.d("chacheObject",object.toString());
        }
       return chache.put(key, object);
    }

    public void chacheObject(String key, Object object, Object defaultValue) {
        if (object != null) {
            chache.put(key, object);
        } else chache.put(key, defaultValue);

    }

    public Object getCachedObject(String key) {
        return chache.get(key);
    }

    public Object getCachedObject(String key, Object defaultValue) {
        if (chache.get(key) != null)
            return chache.get(key);
        else return defaultValue;
    }

    public boolean hasKey(String key) {
        return chache.containsKey(key);
    }

    public HashMap<String, Object> getChache() {
        return chache;
    }
}
