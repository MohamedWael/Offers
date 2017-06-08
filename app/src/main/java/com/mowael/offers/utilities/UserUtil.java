package com.mowael.offers.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by moham on 6/1/2017.
 */

public class UserUtil {
    private static UserUtil userUtil;
    private SharedPreferences.Editor editor;
    private SharedPreferencesManager prefs;
    private String NOT_EXIST = "notExist";

    private UserUtil() {
    }

    public static UserUtil getInstance() {
        if (userUtil == null) {
            userUtil = new UserUtil();
        }
        return userUtil;
    }

    public void init(Context context) {
        prefs = SharedPreferencesManager.getInstance(context);
        editor = prefs.initEditor();
    }

    public void saveUser(String name, String email, long id) {
        editor.putString(Constants.NAME, name);
        editor.putString(Constants.EMAIL, email);
        editor.putLong(Constants.USER_ID, id);
        editor.putBoolean(Constants.HAVE_ACCOUNT, true);
        editor.commit();
    }

    public void saveUser(String name, String email, long id, String token) {
        editor.putString(Constants.TOKEN, token);
        saveUser(name, email, id);
    }

    public String getUserName() {
        return prefs.getPrefs().getString(Constants.NAME, NOT_EXIST);
    }

    public String getToken() {
        return prefs.getPrefs().getString(Constants.TOKEN, NOT_EXIST);
    }

    public String getEmail() {
        return prefs.getPrefs().getString(Constants.EMAIL, NOT_EXIST);
    }

    public long getId() {
        return prefs.getPrefs().getLong(Constants.USER_ID, -1);
    }

    public String getPassword(){
        return prefs.getPrefs().getString(Constants.PASSWORD, "");
    }

    public boolean isHaveAccount() {
        return prefs.getPrefs().getBoolean(Constants.HAVE_ACCOUNT, false);
    }


}
