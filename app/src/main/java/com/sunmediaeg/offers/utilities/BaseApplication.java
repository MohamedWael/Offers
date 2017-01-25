package com.sunmediaeg.offers.utilities;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.sunmediaeg.offers.R;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by moham on 12/28/2016.
 */

public class BaseApplication extends Application {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private RequestQueue downloadQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/cairo_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/cairo_regular.ttf");

        volleySingleton = VolleySingleton.getInstance(this);
        requestQueue = volleySingleton.getRequestQueue();
        downloadQueue = volleySingleton.getDownloadRequestQueue();
        downloadQueue.start();
        requestQueue.start();
    }
}
