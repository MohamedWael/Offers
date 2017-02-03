package com.sunmediaeg.offers.utilities;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by moham on 2/2/2017.
 */

public interface BackendResponse {
    void onResponse(JSONObject response);

    void onErrorResponse(VolleyError error);

}
