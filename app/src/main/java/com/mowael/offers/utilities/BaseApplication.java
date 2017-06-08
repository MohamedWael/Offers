package com.mowael.offers.utilities;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.mowael.offers.R;
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
//    private RealmDB realmDB;

    @Override
    public void onCreate() {
        super.onCreate();
        UserUtil.getInstance().init(getApplicationContext());
        Toaster.getInstance().setContext(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
//        realmDB = RealmDB.getInstance(this);
        SharedPreferencesManager prefesManager = SharedPreferencesManager.getInstance(getApplicationContext());
        CacheManager manager = CacheManager.getInstance();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/cairo_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/cairo_regular.ttf");

        boolean haveAccount = SharedPreferencesManager.getInstance(this).initSharedPreferences().getBoolean(Constants.HAVE_ACCOUNT, false);
        Logger.d("haveAccountApp", haveAccount + "");

        if (haveAccount) {
            manager.cacheObject(Constants.NAME, prefesManager.getPrefs().getString(Constants.NAME, ""));
            manager.cacheObject(Constants.EMAIL, prefesManager.getPrefs().getString(Constants.EMAIL, ""));
            manager.cacheObject(Constants.USER_ID, prefesManager.getPrefs().getLong(Constants.USER_ID, 0));
            manager.cacheObject(Constants.TOKEN, prefesManager.getPrefs().getString(Constants.TOKEN, ""));
            manager.cacheObject(Constants.HAVE_ACCOUNT, true);
//            Logger.d("AppNAME", manager.getCachedObject(Constants.NAME) + "");
//            Logger.d("AppEMAIL", manager.getCachedObject(Constants.EMAIL) + "");
//            Logger.d("AppUSER_ID", manager.getCachedObject(Constants.USER_ID) + "");
//            Logger.d("AppToken", manager.getCachedObject(Constants.TOKEN) + "");
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
