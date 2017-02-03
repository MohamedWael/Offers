package com.sunmediaeg.offers.utilities;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.sunmediaeg.offers.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by moham on 12/28/2016.
 */

public class BaseApplication extends Application {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private RequestQueue downloadQueue;


    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "IMQdb01tqIPiUeK22nZn04VFL";
    private static final String TWITTER_SECRET = "q8ZWVv4nBCC9FhbvuyV7whR4U1ap4o3FxlMl9dfOnlKnrbDSrb";


    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        SharedPreferencesManager prefesManager = SharedPreferencesManager.getInstance(this);
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
