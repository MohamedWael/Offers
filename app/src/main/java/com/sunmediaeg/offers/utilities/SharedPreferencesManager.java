package com.sunmediaeg.offers.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by moham on 2/1/2017.
 */
public class SharedPreferencesManager {
    private static SharedPreferencesManager ourInstance;
    private final Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public static SharedPreferencesManager getInstance(Context mContext) {
        if (ourInstance == null)
            ourInstance = new SharedPreferencesManager(mContext);
        return ourInstance;
    }

    private SharedPreferencesManager(Context mContext) {
        this.mContext = mContext;
    }


    public SharedPreferences initSharedPreferences() {
        return prefs = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    public SharedPreferences.Editor initEditor() {
        if (prefs == null) {
            initSharedPreferences();
        }
       return editor = prefs.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}