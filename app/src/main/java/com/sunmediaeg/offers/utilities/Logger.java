package com.sunmediaeg.offers.utilities;

import android.util.Log;

/**
 * Created by moham on 1/23/2017.
 */

public class Logger {

    private Logger() {
    }

    public static void d(String tag, String message) {
        if (Constants.LOG_TOGGLE)
            android.util.Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        if (Constants.LOG_TOGGLE)
            android.util.Log.e(tag, message);
    }

    public static void i(String tag, String msg) {
        if (Constants.LOG_TOGGLE)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (Constants.LOG_TOGGLE)
            Log.w(tag, msg);
    }
}
