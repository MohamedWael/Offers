package com.sunmediaeg.offers.utilities;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by moham on 2/2/2017.
 */
public class Service {
    private static Service ourInstance;
    private final VolleySingleton volley;

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
        volley = VolleySingleton.getInstance(mContext);
    }

    public void getResponse(int method, String url, final JSONObject body, final ServiceResponse serviceResponse) {
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


