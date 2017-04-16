package com.sunmediaeg.offers.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.dataModel.jsonModels.LoginResponse;

import org.json.JSONObject;

/**
 * Created by moham on 4/16/2017.
 */

public class LoginService {
    private static LoginService instance;
    private final CacheManager manager;
    private final SharedPreferences.Editor editor;
    private final Context context;
    private final String email;
    private final String password;
    private SharedPreferencesManager preferencesManager;

    private LoginService(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
        manager = CacheManager.getInstance();
        preferencesManager = SharedPreferencesManager.getInstance(context);
        preferencesManager.initSharedPreferences();
        editor = preferencesManager.initEditor();

    }

    public static LoginService getInstance(Context context, String email, String password) {
        if (instance == null) {
            instance = new LoginService(context, email, password);
        }
        return instance;
    }

    public void reLogIn() {
        Logger.d("LoginService", "reLogIn");
        Logger.d("email", email);
        Logger.d("password", password);
        if (password.toCharArray().length >= 6 && password.toCharArray().length <= 255
                && email.matches(Constants.REGEX_Mail)) {
            try {
                JSONObject body = new JSONObject();
                body.put(Constants.EMAIL, email);
                body.put(Constants.PASSWORD, password);
                Service.getInstance(context).getResponse(Request.Method.POST, Constants.USER_LOGIN, body, new Service.ServiceResponse() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                        if (apiResponse.isSuccess()) {
                            LoginResponse login = gson.fromJson(response.toString(), LoginResponse.class);
                            editor.putString(Constants.NAME, login.getData().getUser().getName());
                            editor.putString(Constants.EMAIL, login.getData().getUser().getEmail());
                            editor.putLong(Constants.USER_ID, login.getData().getUser().getId());
                            editor.putString(Constants.TOKEN, login.getData().getUser().getToken());
                            Logger.d(Constants.USER_ID, login.getData().getUser().getId() + "");
                            editor.putBoolean(Constants.HAVE_ACCOUNT, true);
                            editor.commit();
                            manager.cacheObject(Constants.NAME, login.getData().getUser().getName());
                            manager.cacheObject(Constants.EMAIL, login.getData().getUser().getEmail());
                            manager.cacheObject(Constants.TOKEN, login.getData().getUser().getToken());
                            manager.cacheObject(Constants.USER_ID, login.getData().getUser().getId());
                            manager.cacheObject(Constants.HAVE_ACCOUNT, true);

                        } else {
                            ApiError apiError = new ApiError(apiResponse.getCode());
                            editor.putBoolean(Constants.HAVE_ACCOUNT, false);
                            editor.commit();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.d("LoginError", error.toString());
                        editor.putBoolean(Constants.HAVE_ACCOUNT, false);
                    }

                    @Override
                    public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                        editor.putBoolean(Constants.HAVE_ACCOUNT, false);
                    }
                });
            } catch (Exception e) {
                editor.putBoolean(Constants.HAVE_ACCOUNT, false);
                e.printStackTrace();
            }
            preferencesManager.initEditor().putBoolean(Constants.HAVE_ACCOUNT, true).commit();
        } else {
            editor.putBoolean(Constants.HAVE_ACCOUNT, false);
        }

    }
}
