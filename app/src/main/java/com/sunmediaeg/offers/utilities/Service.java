package com.sunmediaeg.offers.utilities;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sunmediaeg.offers.R;

import org.json.JSONObject;

/**
 * Created by moham on 2/2/2017.
 */
public class Service {
    private static Service ourInstance;
    private final VolleySingleton volley;
    private final Context mContext;
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
    }

    public void getResponse(final int method, final String url, final JSONObject body, final ServiceResponse serviceResponse) {
        JsonObjectRequest signUpRequest = new JsonObjectRequest(method, volley.uriEncoder(url), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                serviceResponse.onResponse(response);
                Logger.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceResponse.onErrorResponse(error);
                String volleyErrorStr = error.toString();
                if (volleyErrorStr.equalsIgnoreCase("com.android.volley.TimeoutError") && numberOfRetryingToGetResponse <= TRYING_LIMIT) {
                    getResponse(method, url, body, serviceResponse);
                    numberOfRetryingToGetResponse++;
                } else {
                    Constants.toastMsg(mContext, mContext.getString(R.string.connection));
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
        volley.addToRequestQueue(signUpRequest);
    }

    public interface ServiceResponse {
        void onResponse(JSONObject response);

        void onErrorResponse(VolleyError error);

    }

}


