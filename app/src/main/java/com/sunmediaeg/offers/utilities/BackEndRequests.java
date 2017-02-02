package com.sunmediaeg.offers.utilities;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by moham on 2/2/2017.
 */
public class BackEndRequests {
    private static BackEndRequests ourInstance;
    private final VolleySingleton volley;

    public static BackEndRequests getInstance(Context mContext) {
        if (ourInstance == null) {
            ourInstance = new BackEndRequests(mContext);
        }
        return ourInstance;
    }

    private BackEndRequests(Context mContext) {
        volley = VolleySingleton.getInstance(mContext);
    }

    public void signUp(int method, String url, final JSONObject body, final NetworkResponse networkResponse) {
        JsonObjectRequest signUpRequest = new JsonObjectRequest(method, volley.uriEncoder(url), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                networkResponse.getResponse(response);
                Log.d("response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkResponse.getError(error);
                Log.d("VolleyError", error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                Log.d("body", body.toString());
                return super.getBody();
            }
        };
        volley.addToRequestQueue(signUpRequest);
    }
}


