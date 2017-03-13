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

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("Application", "Created");
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        SharedPreferencesManager prefesManager = SharedPreferencesManager.getInstance(getApplicationContext());
        CacheManager manager = CacheManager.getInstance();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/cairo_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/cairo_regular.ttf");

        boolean haveAccount = SharedPreferencesManager.getInstance(this).initSharedPreferences().getBoolean(Constants.HAVE_ACCOUNT, false);
        Logger.d("haveAccount", haveAccount + "");

        if (haveAccount) {
            manager.chacheObject(Constants.NAME, prefesManager.getPrefs().getString(Constants.NAME, ""));
            manager.chacheObject(Constants.EMAIL, prefesManager.getPrefs().getString(Constants.EMAIL, ""));
            manager.chacheObject(Constants.USER_ID, prefesManager.getPrefs().getLong(Constants.USER_ID, 0));
//            Logger.d("NAME", manager.getCachedObject(Constants.NAME) + "");
//            Logger.d("EMAIL", manager.getCachedObject(Constants.EMAIL) + "");
//            Logger.d("USER_ID", manager.getCachedObject(Constants.USER_ID) + "");
        }

        volleySingleton = VolleySingleton.getInstance(this);
        requestQueue = volleySingleton.getRequestQueue();
        requestQueue.start();
        downloadQueue = volleySingleton.getDownloadRequestQueue();
        downloadQueue.start();

    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.d("Application", "Terminated");
        volleySingleton = null;
        downloadQueue.stop();
        requestQueue.stop();
    }
}
