package com.mowael.offers.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.mowael.offers.R;
import com.mowael.offers.fragment.CategoriesFragment;
import com.mowael.offers.fragment.CompanyProfileFragment;
import com.mowael.offers.fragment.HomeFragment;
import com.mowael.offers.fragment.LoginChoiceFragment;
import com.mowael.offers.fragment.LoginFragment;
import com.mowael.offers.fragment.OffersFragment;
import com.mowael.offers.fragment.SearchFragment;
import com.mowael.offers.fragment.SettingsFragment;
import com.mowael.offers.utilities.CacheManager;
import com.mowael.offers.utilities.Constants;
import com.mowael.offers.utilities.NetworkStateReceiver;
import com.mowael.offers.utilities.SharedPreferencesManager;
import com.mowael.offers.utilities.Toaster;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener, NetworkStateReceiver.NetworkStateReceiverListener {

    private final int HOME = 100, LIST = 101, LOGO = 102, GRID = 103, SETTING = 104;
    private FrameLayout flMainFragment;
    private ArrayList<ImageButton> imageButtons;
    private ImageButton ibHome, ibList, ibLogo, ibGrid, ibSetting;
    private OffersFragment offersFragment, cityFragment;
    private LoginFragment loginFragment;
    private HomeFragment homeFragment;
    private CategoriesFragment categoriesFragment;
    private SearchFragment searchFragment;
    private SettingsFragment settingsFragment;
    private CompanyProfileFragment companyProfileFragment;
    private boolean isCompanyProfile = false;
    private boolean haveAccount = false;
    private Toolbar tbToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(this);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        NetworkStateReceiver receiver = NetworkStateReceiver.getInstance();
        registerReceiver(receiver, receiver.getIntentFilter());
        SharedPreferencesManager prefesManager = SharedPreferencesManager.getInstance(this);
        CacheManager manager = CacheManager.getInstance();
        haveAccount = prefesManager.initSharedPreferences().getBoolean(Constants.HAVE_ACCOUNT, false);
        if (haveAccount) {
//            cacheUserData();
            manager.cacheObject(Constants.NAME, prefesManager.getPrefs().getString(Constants.NAME, ""));
            manager.cacheObject(Constants.EMAIL, prefesManager.getPrefs().getString(Constants.EMAIL, ""));
            manager.cacheObject(Constants.USER_ID, prefesManager.getPrefs().getLong(Constants.USER_ID, 0));
//            Logger.d("NAME", manager.getCachedObject(Constants.NAME) + "");
//            Logger.d("EMAIL", manager.getCachedObject(Constants.EMAIL) + "");
//            Logger.d("USER_ID", manager.getCachedObject(Constants.USER_ID) + "");
        }
        if (bundle != null) {
            isCompanyProfile = bundle.getBoolean(Constants.IS_COMPANY_PROFILE, false);
            initComponents(bundle);
        }
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
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, cityFragment).commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, searchFragment).commit();
                break;
            case R.id.ibCategories:
                changeBackground(ibGrid, GRID);
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, categoriesFragment).commit();
                break;
            case R.id.ibSetting:
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, settingsFragment).commit();
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
//                tbToolBar = (Toolbar) findViewById(R.id.tbToolBar);
//                setSupportActionBar(tbToolBar);

                homeFragment = HomeFragment.newInstance(getString(R.string.onViewStuff), "");
                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, homeFragment).commit();

                offersFragment = OffersFragment.newInstance(getString(R.string.offers), "");
                cityFragment = OffersFragment.newInstance(getString(R.string.stuffInCity), "");
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
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {
        Toaster.getInstance().toast(getString(R.string.connection));
//        registerReceiver(NetworkStateReceiver.getInstance(), NetworkStateReceiver.getInstance().getIntentFilter())
    }
}
