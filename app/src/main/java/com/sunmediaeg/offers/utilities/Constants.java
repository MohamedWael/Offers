package com.sunmediaeg.offers.utilities;

import android.view.View;

import com.sunmediaeg.offers.R;

/**
 * Created by moham on 1/23/2017.
 */

public final class Constants {
    private Constants() {
    }

    public static final int SPLASH_TIME_OUT = 3000;
    public static final boolean LOG_TOGGLE = true;

    public static final String SHARED_PREFERENCES_NAME = "offers_preferences";
    public static final String ACTIVITY = "activityType";
    public static final String IS_COMPANY_PROFILE = "isCompanyProfile";
    public static final String COMPANY_PROFILE_TITLE = "CompanyProfile";
    public static final int ACTIVITY_LOGIN = 1000;
    public static final int ACTIVITY_SIGN_UP_AS_VENDOR = 1001;
    public static final int ACTIVITY_SIGN_UP_AS_USER = 1002;
    public static final int ACTIVITY_PRODUCT_DETAILS = 1003;
    public static final int ACTIVITY_CATEGORY_COMPANIES = 1013;
    public static final int ELECTRONICS = 1004;
    public static final int TRAVEL = 1005;
    public static final int AIRPLANE = 1006;
    public static final int CARS = 1007;
    public static final int COMMUNICATIONS = 1008;
    public static final int FURNITURE = 1009;
    public static final int RESTAURANTS = 1010;
    public static final int HEALTH = 1011;
    public static final int CLOTHES = 1012;

    public static final String REGEX_Mail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static final String API_Base_URL = "http://api.sunmediaeg.com/offer/public/api/";
    public static final String REGISTER_USER = API_Base_URL + "user";
    public static final String USER_LOGIN = REGISTER_USER + "/login";

    public static final String REGISTER_VENDOR = API_Base_URL + "vendor";

    public static final String HAVE_ACCOUNT = "haveAccount";

    public static final String USER_ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String _METHOD = "_method";
    public static final String _METHOD_PATCH = "PATCH";
    public static final String _METHOD_DESTROY = "DESTROY";


    public static void hideToolbarButtons(View v) {
        v.findViewById(R.id.ibBack).setVisibility(View.GONE);
        v.findViewById(R.id.ibSearch).setVisibility(View.GONE);
    }

    public static void hideBackButton(View v) {
        v.findViewById(R.id.ibBack).setVisibility(View.GONE);
    }

    public static void hideSearchButton(View v) {
        v.findViewById(R.id.ibSearch).setVisibility(View.GONE);
    }
}
