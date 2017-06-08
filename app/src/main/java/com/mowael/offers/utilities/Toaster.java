package com.mowael.offers.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
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

    public void toast(String msg, int length) {
        if (!msg.isEmpty())
            Toast.makeText(context, msg, length).show();
    }

    public void toast(String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    public void toast(@StringRes int stirngRes) {
        try {
            toast(context.getString(stirngRes));
        } catch (Resources.NotFoundException e) {
            toast(stirngRes + "");
        }
    }


    public void toastShort(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    public void toastShort(int stirngRes) {
        toastShort(context.getString(stirngRes));
    }


    public void setContext(Context context) {
        this.context = context;
    }

    public Context getAppContext() {
        return context;
    }
}
