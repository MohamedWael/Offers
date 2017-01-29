package com.sunmediaeg.offers.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.fragment.CategoryCompaniesFragment;
import com.sunmediaeg.offers.fragment.DetailsFragment;
import com.sunmediaeg.offers.fragment.LoginFragment;
import com.sunmediaeg.offers.fragment.SignUpFragment;
import com.sunmediaeg.offers.utilities.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OffersGeneralActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    private DetailsFragment detailsFragment;
    private CategoryCompaniesFragment categoryCompaniesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        Bundle data = getIntent().getExtras();
        int activityType = data.getInt(Constants.ACTIVITY);


        switch (activityType) {
            case Constants.ACTIVITY_SIGN_UP_AS_VENDOR:
                signUpFragment = SignUpFragment.newInstance("", "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, signUpFragment).commit();
                break;
            case Constants.ACTIVITY_SIGN_UP_AS_USER:
                signUpFragment = SignUpFragment.newInstance("", "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, signUpFragment).commit();
                break;
            case Constants.ACTIVITY_LOGIN:
                loginFragment = LoginFragment.newInstance("", "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, loginFragment).commit();
                break;
            case Constants.ACTIVITY_PRODUCT_DETAILS:
                detailsFragment = DetailsFragment.newInstance(getString(R.string.offerDetails), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, detailsFragment).commit();
                break;
            case Constants.ACTIVITY_CATEGORY_COMPANIES:
                categoryCompaniesFragment = CategoryCompaniesFragment.newInstance(data.getString("Category"), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, categoryCompaniesFragment).commit();
                break;
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, CalligraphyConfig.get().getAttrId()));
    }
}
