package com.sunmediaeg.offers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.utilities.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setProgress(50);
//        progressBar.getProgressDrawable().setColorFilter(
//                ContextCompat.getColor(this, R.color.colorPrimaryDarker), android.graphics.PorterDuff.Mode.SRC_IN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentIntro = new Intent(SplashScreenActivity.this, MainActivity.class);
                intentIntro.putExtra(Constants.IS_COMPANY_PROFILE, false);
//                Intent intentIntro = new Intent(SplashScreenActivity.this, LoginChoiceActivity.class);
                startActivity(intentIntro);
                finish();
            }
        }, Constants.SPLASH_TIME_OUT);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, CalligraphyConfig.get().getAttrId()));
    }
}
