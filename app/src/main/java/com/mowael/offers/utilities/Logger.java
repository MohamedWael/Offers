package com.mowael.offers.utilities;

import android.util.Log;

/**
 * Created by moham on 1/23/2017.
 */

public class Logger {

    private Logger() {
    }

    public static void d(String tag, String message) {
        if (Constants.LOG_TOGGLE)
            android.util.Log.d(tag, getMsg(message));
    }

    public static void e(String tag, String message) {
        if (Constants.LOG_TOGGLE)
            android.util.Log.e(tag, getMsg(message));
    }

    public static void i(String tag, String msg) {
        if (Constants.LOG_TOGGLE)
            Log.i(tag, getMsg(msg));
    }

    public static void w(String tag, String msg) {
        if (Constants.LOG_TOGGLE)
            Log.w(tag, getMsg(msg));
    }

    private static String getMsg(String msg) {
        if (msg != null) return msg;
        else return "the message you entered is empty or null";
    }
}
