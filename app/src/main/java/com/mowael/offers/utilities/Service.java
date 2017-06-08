package com.mowael.offers.utilities;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mowael.offers.R;
import com.mowael.offers.dataModel.APIResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by moham on 2/2/2017.
 */
public class Service implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static Service ourInstance;
    private final VolleySingleton volley;
    private final Context mContext;
    private static DialogUtil dialogUtil;
    private ArrayList<ServiceRequest> requests;
    private ServiceRequest request;
    private int numberOfRetryingToGetResponse = 0;
    private final int TRYING_LIMIT = 9;


    public static Service getInstance(Context mContext) {
        dialogUtil = new DialogUtil(mContext);
        if (ourInstance == null) {
            Logger.d("Service", "new Service");
            ourInstance = new Service(mContext);
            return ourInstance;
        } else {
            Logger.d("Service", "old Service");
            return ourInstance;
        }
    }

    private Service(Context mContext) {
        this.mContext = mContext;
        AndroidNetworking.initialize(mContext.getApplicationContext());
        volley = VolleySingleton.getInstance(mContext);
        NetworkStateReceiver receiver = NetworkStateReceiver.getInstance();
        mContext.registerReceiver(receiver, receiver.getIntentFilter());
        requests = new ArrayList<>();
    }

    public void post(String url, JSONObject body, final ServiceResponse serviceResponse) {
        AndroidNetworking.post(url)
                .addJSONObjectBody(body)
                .setTag("POST")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Logger.d("response", response.toString());
                        serviceResponse.onResponse(response);
                        reLogin(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void get(String url, JSONObject body, final ServiceResponse serviceResponse) {
        try {
            AndroidNetworking.get(url)
                    .addHeaders(toMap(body.toString()))
                    .setTag("GET")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.d("response", response.toString());
                            serviceResponse.onResponse(response);
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (JSONException e) {
            AndroidNetworking.get(url)
                    .setTag("GET")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            serviceResponse.onResponse(response);
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        }

    }

    private void reLogin(JSONObject response) {
        Gson gson = new Gson();
        APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
        if (apiResponse.getCode() == Constants.CODE_VALIDATION_ERRORS) {
            Logger.d("VALIDATION_ERRORS", "VALIDATION_ERRORS 402");
            String email = SharedPreferencesManager.getInstance(mContext).initSharedPreferences().getString(Constants.EMAIL, "");
            String password = SharedPreferencesManager.getInstance(mContext).initSharedPreferences().getString(Constants.PASSWORD, "");
            LoginService.getInstance(mContext, email, password).reLogIn();
        }
    }

    public void getResponse(final int method, final String url, JSONObject body, final ServiceResponse serviceResponse) throws Exception, JsonSyntaxException {
        Logger.d("url", url);
        if (body != null) Logger.d("body", body.toString());
//        switch (method) {
//            case Request.Method.POST:
//                post(url, body, serviceResponse);
//                break;
//            case Request.Method.GET:
//                get(url, body, serviceResponse);
//                break;
//        }

        Logger.d("request", volley.uriEncoder(url));
        if (body == null) {
            body = new JSONObject();
        }
        request = new ServiceRequest(method, volley.uriEncoder(url), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.d("Response", response.toString());
                try {
                    int responseCode = response.getInt("code");
                    if (responseCode == 200) {

                    }
                    serviceResponse.onResponse(response);
                    reLogin(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
//            private boolean cached = false;

            @Override
            public void onErrorResponse(VolleyError error) {
                serviceResponse.onErrorResponse(error);
                String volleyErrorStr = error.toString();
                if (volleyErrorStr.contains("com.android.volley.TimeoutError") && numberOfRetryingToGetResponse <= TRYING_LIMIT) {
                    Logger.e("if", volleyErrorStr);
//                    getResponse(method, url, body, serviceResponse);
                    volley.addToRequestQueue(request);
//                    if (!cached) {
//                        cacheRequest(request, cached);
//                        cached = true;
//                    }
                    numberOfRetryingToGetResponse++;
                } else if (volleyErrorStr.contains("com.android.volley.NoConnectionError")) {
                    Logger.e("elseIf", volleyErrorStr);
                    Toaster.getInstance().toastShort(R.string.connection);
//                    dialogUtil.showMessage(mContext.getString(R.string.connection));
                    serviceResponse.updateUIOnNetworkUnavailable(mContext.getString(R.string.connection));
//                    if (!cached) cacheRequest(request);
                } else {
                    Logger.e("volleyErrorStr", volleyErrorStr);

                    Toaster.getInstance().toastShort(R.string.connectionError);
//                    dialogUtil.showMessage(mContext.getString(R.string.connectionError));
                }
                Logger.d("VolleyError", error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
//                Logger.d("body", body.toString());
                return super.getBody();
            }
        };
        volley.addToRequestQueue(request);
    }

    public static Map<String, String> toMap(String jsonString) throws JSONException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map = gson.fromJson(jsonString, type);
        return map;
    }

    public void hideDialog() {
        getDialogUtil().hide();
    }

    private static DialogUtil getDialogUtil() {
        return dialogUtil;
    }

    public ServiceRequest getRequest() {
        return request;
    }

    /**
     * cache option is still under development
     *
     * @param request
     * @param cached
     */
    private void cacheRequest(ServiceRequest request, boolean cached) {
        if (!cached) cacheRequest(request);
    }

    /**
     * cache option is still under development
     *
     * @param objectRequest
     */
    private void cacheRequest(ServiceRequest objectRequest) {
        if (objectRequest != null && !objectRequest.isCached()) {
            if (!requests.contains(objectRequest)) {
                requests.add(objectRequest);
                objectRequest.setCached(true);
            }
        } else {
            Logger.e("CachedRequestError", "request Object is null");
        }
        Logger.d("NumberOfCachedRequests", requests.size() + "");
    }

    @Override
    public void networkAvailable() {
        if (request != null) {
            volley.addToRequestQueue(request);
        }
//        if (!requests.isEmpty()) {
//            for (ServiceRequest objectRequest : requests) {
//                volley.addToRequestQueue(request);
//            }
//        }

    }

    @Override
    public void networkUnavailable() {
        Toaster.getInstance().toastShort(R.string.connection);
//        dialogUtil.showMessage(mContext.getString(R.string.connection));
    }

    public interface ServiceResponse {
        void onResponse(JSONObject response);

        //        void onResponseSuccess(JSONObject response);

        void onErrorResponse(VolleyError error);

        void updateUIOnNetworkUnavailable(String noInternetMessage);
    }

}


