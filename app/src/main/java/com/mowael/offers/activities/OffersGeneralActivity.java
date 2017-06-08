package com.mowael.offers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mowael.offers.R;
import com.mowael.offers.fragment.AccountSettingFragment;
import com.mowael.offers.fragment.CategoryCompaniesFragment;
import com.mowael.offers.fragment.DetailsFragment;
import com.mowael.offers.fragment.LoginFragment;
import com.mowael.offers.fragment.SignUpFragment;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.Logger;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OffersGeneralActivity extends BaseActivity {

    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    private DetailsFragment detailsFragment;
    private CategoryCompaniesFragment categoryCompaniesFragment;
    private AccountSettingFragment accountSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login_main);
        Bundle data = getIntent().getExtras();
        int activityType = data.getInt(Constants.ACTIVITY);


        switch (activityType) {
            case Constants.ACTIVITY_SIGN_UP_AS_VENDOR:
                signUpFragment = SignUpFragment.newInstance(Constants.REGISTER_VENDOR, "إنشاء حساب كبائع");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, signUpFragment).commit();
                break;
            case Constants.ACTIVITY_SIGN_UP_AS_USER:
                signUpFragment = SignUpFragment.newInstance(Constants.USER, getString(R.string.tvCreateAccount));
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, signUpFragment).commit();
                break;
            case Constants.ACTIVITY_LOGIN:
                loginFragment = LoginFragment.newInstance(getString(R.string.btnLogin), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, loginFragment).commit();
                break;
            case Constants.ACTIVITY_PRODUCT_DETAILS:
                detailsFragment = DetailsFragment.newInstance(getString(R.string.offerDetails), data.getInt(Constants.ITEM_POSITION));
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, detailsFragment).commit();
                break;
            case Constants.ACTIVITY_CATEGORY_COMPANIES:
                categoryCompaniesFragment = CategoryCompaniesFragment.newInstance(data.getString(Constants.CATEGORY_TITLE), data.getInt(Constants.CATEGORY_ID));
                Logger.d(Constants.CATEGORY_ID, data.getInt(Constants.CATEGORY_ID) + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, categoryCompaniesFragment).commit();
                break;
            case Constants.ACTIVITY_ACCOUNT_SETTING:
                accountSettingFragment = AccountSettingFragment.newInstance(getString(R.string.btnSignUp), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flLogin, accountSettingFragment).commit();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (signUpFragment != null) {
            signUpFragment.onActivityResult(requestCode, resultCode, data);
        }
//        if (accountSettingFragment != null) {
//            accountSettingFragment.onActivityResult(requestCode, resultCode, data);
//        }

    }
}
