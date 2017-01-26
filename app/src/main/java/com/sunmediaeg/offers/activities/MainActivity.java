package com.sunmediaeg.offers.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.fragment.HomeFragment;
import com.sunmediaeg.offers.fragment.LoginFragment;
import com.sunmediaeg.offers.fragment.SignUpFragment;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout flMainFragment;
    private ArrayList<ImageButton> imageButtons;
    private ImageButton ibHome, ibList, ibLogo, ibGrid, ibSetting;
    private SignUpFragment signUpFragment;
    private LoginFragment loginFragment;
    private HomeFragment homeFragment;
    private final int HOME = 100, LIST = 101, LOGO = 102, GRID = 103, SETTING = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibHome:
                changeBackground(ibHome, HOME);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, homeFragment).commit();
                break;
            case R.id.ibList:
                changeBackground(ibList, LIST);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, signUpFragment).commit();
                break;
            case R.id.ibLogo:
                changeBackground(ibLogo, LOGO);

                getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, loginFragment).commit();
                break;
            case R.id.ibGrid:
                changeBackground(ibGrid, GRID);
                break;
            case R.id.ibSetting:
                changeBackground(ibSetting, SETTING);
                break;
        }
    }

    private void initComponents() {
        ibHome = (ImageButton) findViewById(R.id.ibHome);
        ibHome.setOnClickListener(this);
        ibList = (ImageButton) findViewById(R.id.ibList);
        ibList.setOnClickListener(this);
        ibLogo = (ImageButton) findViewById(R.id.ibLogo);
        ibLogo.setOnClickListener(this);
        ibGrid = (ImageButton) findViewById(R.id.ibGrid);
        ibGrid.setOnClickListener(this);
        ibSetting = (ImageButton) findViewById(R.id.ibSetting);
        ibSetting.setOnClickListener(this);
        flMainFragment = (FrameLayout) findViewById(R.id.flMainFragment);

        homeFragment = HomeFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.flMainFragment, homeFragment).commit();

        signUpFragment = SignUpFragment.newInstance("", "");
        loginFragment = LoginFragment.newInstance("", "");

        imageButtons = new ArrayList<>();
        imageButtons.add(ibHome);
        imageButtons.add(ibList);
        imageButtons.add(ibLogo);
        imageButtons.add(ibGrid);
        imageButtons.add(ibSetting);
    }

    private void changeBackground(ImageButton current, int clikedID) {
        for (ImageButton imageButton : imageButtons) {
            if (imageButton == current) {
                imageButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground));
                switch (clikedID) {
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


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase, CalligraphyConfig.get().getAttrId()));
    }
}
