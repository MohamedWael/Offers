package com.sunmediaeg.offers.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.facebook.FacebookSdk;
import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.fragment.CategoriesFragment;
import com.sunmediaeg.offers.fragment.CompanyProfileFragment;
import com.sunmediaeg.offers.fragment.HomeFragment;
import com.sunmediaeg.offers.fragment.LoginChoiceFragment;
import com.sunmediaeg.offers.fragment.LoginFragment;
import com.sunmediaeg.offers.fragment.OffersFragment;
import com.sunmediaeg.offers.fragment.SearchFragment;
import com.sunmediaeg.offers.fragment.SettingsFragment;
import com.sunmediaeg.offers.utilities.Constants;
import com.sunmediaeg.offers.utilities.SharedPreferencesManager;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout flMainFragment;
    private ArrayList<ImageButton> imageButtons;
    private ImageButton ibHome, ibList, ibLogo, ibGrid, ibSetting;
    private OffersFragment offersFragment;
    private LoginFragment loginFragment;
    private HomeFragment homeFragment;
    private final int HOME = 100, LIST = 101, LOGO = 102, GRID = 103, SETTING = 104;
    private CategoriesFragment categoriesFragment;
    private SearchFragment searchFragment;
    private SettingsFragment settingsFragment;
    private CompanyProfileFragment companyProfileFragment;
    private boolean isCompanyProfile = false;
    private boolean haveAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(this);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        haveAccount = SharedPreferencesManager.getInstance(this).initSharedPreferences().getBoolean(Constants.HAVE_ACCOUNT, false);
        isCompanyProfile = bundle.getBoolean(Constants.IS_COMPANY_PROFILE, false);
        initComponents(bundle);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibHome:
                changeBackground(ibHome, HOME);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, homeFragment).commit();
                break;
            case R.id.ibOffers:
                changeBackground(ibList, LIST);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, offersFragment).commit();
                break;
            case R.id.ibLogo:
                changeBackground(ibLogo, LOGO);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, searchFragment).commit();
                break;
            case R.id.ibCategories:
                changeBackground(ibGrid, GRID);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, categoriesFragment).commit();
                break;
            case R.id.ibSetting:
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, settingsFragment).commit();

                /*
                CompanyProfileFragment companyProfileFragment = CompanyProfileFragment.newInstance("", "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, companyProfileFragment).commit();
                * */

                changeBackground(ibSetting, SETTING);
                break;
        }
    }

    private void initComponents(Bundle bundle) {
        if (haveAccount) {
            ibHome = (ImageButton) findViewById(R.id.ibHome);
            ibHome.setOnClickListener(this);
            ibList = (ImageButton) findViewById(R.id.ibOffers);
            ibList.setOnClickListener(this);
            ibLogo = (ImageButton) findViewById(R.id.ibLogo);
            ibLogo.setOnClickListener(this);
            ibGrid = (ImageButton) findViewById(R.id.ibCategories);
            ibGrid.setOnClickListener(this);
            ibSetting = (ImageButton) findViewById(R.id.ibSetting);
            ibSetting.setOnClickListener(this);
            flMainFragment = (FrameLayout) findViewById(R.id.flMainFragment);


            if (!isCompanyProfile) {
                toolbarVisibility(View.VISIBLE);
                homeFragment = HomeFragment.newInstance(getString(R.string.onViewStuff), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, homeFragment).commit();

                offersFragment = OffersFragment.newInstance(getString(R.string.offers), "");
                loginFragment = LoginFragment.newInstance("", "");
                categoriesFragment = CategoriesFragment.newInstance(getString(R.string.categories), "");
                searchFragment = SearchFragment.newInstance(getString(R.string.search), "");
                settingsFragment = SettingsFragment.newInstance(getString(R.string.settings), "");

                imageButtons = new ArrayList<>();
                imageButtons.add(ibHome);
                imageButtons.add(ibList);
                imageButtons.add(ibLogo);
                imageButtons.add(ibGrid);
                imageButtons.add(ibSetting);
            } else {
                toolbarVisibility(View.GONE);
                companyProfileFragment = CompanyProfileFragment.newInstance(bundle.getString(Constants.COMPANY_PROFILE_TITLE), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, companyProfileFragment).commit();
            }
        } else {
            toolbarVisibility(View.GONE);
            LoginChoiceFragment loginChoice = LoginChoiceFragment.newInstance("", "");
            getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, loginChoice).commit();
        }

    }

    private void changeBackground(ImageButton current, int clickedID) {
        for (ImageButton imageButton : imageButtons) {
            if (imageButton == current) {
                imageButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground));
                switch (clickedID) {
                    case HOME:
                        imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_icon));
                        break;
                    case LIST:
                        imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.list_blue));
                        break;
                    case GRID:
                        imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.grid_blue));
                        break;
                    case SETTING:
                        imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.settings_bluepsb));
                        break;
                }
            } else {
                imageButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                if (imageButton == ibHome)
                    imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.home_off_white));
                else if (imageButton == ibList)
                    imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.list_icon));
                else if (imageButton == ibGrid)
                    imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.grid_icon));
                else if (imageButton == ibSetting)
                    imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.settings_icon));

            }
        }
    }

    private void toolbarVisibility(int status) {
        findViewById(R.id.tbToolBar).setVisibility(status);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, CalligraphyConfig.get().getAttrId()));
    }
}
