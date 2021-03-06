package com.mowael.offers.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.mowael.offers.R;

/**
 * Created by moham on 1/23/2017.
 */

public final class Constants {


    private Constants() {
    }

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "IMQdb01tqIPiUeK22nZn04VFL";
    public static final String TWITTER_SECRET = "q8ZWVv4nBCC9FhbvuyV7whR4U1ap4o3FxlMl9dfOnlKnrbDSrb";

    public static final int SPLASH_TIME_OUT = 3000;
    public static final boolean LOG_TOGGLE = true;

    public static final String PLAIN_TEXT = "text/plain";
    public static final String IMAGE_JPG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE = "image";


    public static final String SHARED_PREFERENCES_NAME = "offers_preferences";
    public static final String ACTIVITY = "activityType";
    public static final String IS_COMPANY_PROFILE = "isCompanyProfile";
    public static final String COMPANY_PROFILE_TITLE = "CompanyProfile";
    public static final int ACTIVITY_LOGIN = 1000;
    public static final int ACTIVITY_SIGN_UP_AS_VENDOR = 1001;
    public static final int ACTIVITY_SIGN_UP_AS_USER = 1002;
    public static final int ACTIVITY_PRODUCT_DETAILS = 1003;
    public static final int ACTIVITY_CATEGORY_COMPANIES = 1013;
    public static final int ACTIVITY_ACCOUNT_SETTING = 1016;
    public static final int ELECTRONICS = 1004;
    public static final int TRAVEL = 1005;
    public static final int AIRPLANE = 1006;
    public static final int CARS = 1007;
    public static final int COMMUNICATIONS = 1008;
    public static final int FURNITURE = 1009;
    public static final int RESTAURANTS = 1010;
    public static final int HEALTH = 1011;
    public static final int CLOTHES = 1012;
    public static final int TYPE_USER = 1014;
    public static final int TYPE_VENDOR = 1015;


    public static final int CODE_SUCCESS = 200;
    public static final int CODE_VALIDATION_ERRORS = 402;
    public static final int CODE_SOMETHING_WRONG = 100;
    public static final int CODE_INVALID_API_TOKEN = 111;
    public static final int CODE_NOT_FOUND = 404; //something not found like an ad
    public static final int CODE_AUTH_FAILED = 103;
    public static final int CODE_TOKEN_NOT_FOUND = 115;
    public static final int CODE_WRONG_PHONE_NUMBER = 405;
    public static final int CODE_WRONG_FORGET_PASS_VERIFY_NUMBER = 406;
    public static final int CODE_WAIT_BEFORE_RESEND = 410;
    public static final int CODE_DO_NOT_HAVE_PERMISSION = 412;

    public static final int LIMIT_VALUE = 0;


    public static final String REGEX_Mail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static final String API_Base_URL = "http://api.sunmediaeg.com/offer/public/api/";
//    public static final String API_Base_URL = "http://maguedfathey.me/offer/public/api/";
    public static final String USER = API_Base_URL + "user/";
    public static final String USER_UPDATE = USER + "update";
    public static final String USER2 = API_Base_URL + "user";
    public static final String USER_LOGIN = USER + "login";
    public static final String USER_FEEDS = USER + "feeds/";
    public static final String VENDOR = API_Base_URL + "vendor/";//{vendor_id}
    public static final String GET_CITIES = API_Base_URL + "cities";
    public static final String SEARCH = API_Base_URL + "search";
    public static final String SEARCH_KEY = "search";
    public static final String CITIY_OFFERS = API_Base_URL + "city/offers/";

    public static final String REGISTER_VENDOR = API_Base_URL + "vendor";
    public static final String FORGET_PASSWORD = API_Base_URL + "password/forget";

    public static final String HAVE_ACCOUNT = "haveAccount";

    public static final String ITEM_POSITION = "ItemPosition";

    public static final String ADD_USER_ID = "?user_id=";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CITY = "city_id";
    public static final String ID = "id";
    public static final String _METHOD = "_method";
    public static final String _METHOD_POST = "POST";
    public static final String _METHOD_PATCH = "PATCH";
    public static final String _METHOD_DESTROY = "DESTROY";
    public static final String KEY_LIMIT = "limit";
    public static final String API_ERROR = "apiError";


    public static final String CATEGORY = API_Base_URL + "category/";
    public static final String GET_ALL_CATEGORIES = CATEGORY + "all";
    public static final String CATEGORY_VENDORS = CATEGORY + "vendors/";//{categoryId}
    public static final String CATEGORY_OFFERS = CATEGORY + "offers/";//{categoryId}

    public static final String VENDOR_ID = "vendor_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_TITLE = "Category";

    public static final String SHOW_ALL_OFFERS = API_Base_URL + "offer/";

    public static final String FOLLOW_VENDOR = API_Base_URL + "follow";

    public static final String LIKE_OFFER = API_Base_URL + "like";
    public static final String DISLIKE_OFFER = API_Base_URL + "dislike";
    public static final String OFFER_ID = "offer_id";
    public static final String TOKEN = "token";


    public static final String GET_ALL_VENDORS = API_Base_URL + "vendor/";


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


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static void toastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String removeLastSlash(String url) {
        return url.substring(0, url.length() - 1);
    }

    public static void openShareDialog(Context context, String contentType, String title, @Nullable Bundle extras) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(contentType);
        if (extras != null) share.putExtras(extras);
        context.startActivity(Intent.createChooser(share, title));
    }

}
