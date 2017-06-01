package com.mowael.offers.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by moham on 6/1/2017.
 */

public class Toaster {
    private static Toaster toaster;
    private Context context;

    public static Toaster getInstance() {
        if (toaster == null) {
            toaster = new Toaster();
        }
        return toaster;
    }

    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getAppContext() {
        return context;
    }
}
