package com.sunmediaeg.offers.utilities;

/**
 * Created by moham on 1/23/2017.
 */

public class Log {


    public static void d(String tag, String message) {
        if (Constants.LOG_TOGGLE)
            android.util.Log.d(tag, message);
    }

}
