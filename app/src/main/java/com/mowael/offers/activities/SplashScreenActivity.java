package com.mowael.offers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mowael.offers.R;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.userResponse.UserResponse;
import com.mowael.offers.utilities.ApiError;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.mowael.offers.utilities.Service;

import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        cacheUserData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intentIntro = new Intent(SplashScreenActivity.this, MainActivity.class);
                intentIntro.putExtra(Constants.IS_COMPANY_PROFILE, false); // <-- this extra is related only to the MainActivity
                startActivity(intentIntro);
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }

    private void cacheUserData() {
        long userID = (long) CacheManager.getInstance().getCachedObject(Constants.USER_ID, 0L);
        Logger.d("userID", userID + "");
        if (userID != 0) {
            try {
                Service.getInstance(this).getResponse(Request.Method.GET, Constants.USER + userID, new JSONObject(), new Service.ServiceResponse() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d("UserCache", response.toString());
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                        if (apiResponse.isSuccess()) {
                            UserResponse userResponse = gson.fromJson(response.toString(), UserResponse.class);
                            CacheManager.getInstance().cacheObject(Constants.USER, userResponse.getData().getUser());
                        } else {
                            ApiError apiError = new ApiError(apiResponse.getCode());
                            Logger.d(Constants.API_ERROR, apiError.getErrorMsg());
                            Constants.toastMsg(SplashScreenActivity.this, apiError.getErrorMsg());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void updateUIOnNetworkUnavailable(String noInternetMessage) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, CalligraphyConfig.get().getAttrId()));
    }
}
