package com.sunmediaeg.offers.utilities;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;
import com.sunmediaeg.offers.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by moham on 2/2/2017.
 */
public class Service implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static Service ourInstance;
    private final VolleySingleton volley;
    private final Context mContext;
    private ArrayList<ServiceRequest> requests;
    private ServiceRequest request;
    private int numberOfRetryingToGetResponse = 0;
    private final int TRYING_LIMIT = 9;


    public static Service getInstance(Context mContext) {
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
        volley = VolleySingleton.getInstance(mContext);
        NetworkStateReceiver receiver = NetworkStateReceiver.getInstance();
        mContext.registerReceiver(receiver, receiver.getIntentFilter());
        requests = new ArrayList<>();
    }

    public void getResponse(final int method, final String url, final JSONObject body, final ServiceResponse serviceResponse) throws Exception, JsonSyntaxException {
        Logger.d("request", volley.uriEncoder(url));
        request = new ServiceRequest(method, volley.uriEncoder(url), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.d("Response", response.toString());
                serviceResponse.onResponse(response);
            }
        }, new Response.ErrorListener() {
            private boolean cached = false;

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
                    Constants.toastMsg(mContext, mContext.getString(R.string.connection));
                    serviceResponse.updateUIOnNetworkUnavailable(mContext.getString(R.string.connection));
//                    if (!cached) cacheRequest(request);
                } else {
                    Logger.e("volleyErrorStr", volleyErrorStr);
                    Constants.toastMsg(mContext, mContext.getString(R.string.connectionError));
                }
                Logger.d("VolleyError", error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                Logger.d("body", body.toString());
                return super.getBody();
            }
        };
        volley.addToRequestQueue(request);
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
        Constants.toastMsg(mContext, mContext.getString(R.string.connection));
    }

    public interface ServiceResponse {
        void onResponse(JSONObject response);

        void onErrorResponse(VolleyError error);

        void updateUIOnNetworkUnavailable(String noInternetMessage);
    }

}


