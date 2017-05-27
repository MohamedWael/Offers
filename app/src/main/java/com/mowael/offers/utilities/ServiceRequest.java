package com.mowael.offers.utilities;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by moham on 3/13/2017.
 */

public class ServiceRequest extends JsonObjectRequest {

    private boolean cached;
    private static ServiceRequest serviceRequest;
    private String url;
    private JSONObject jsonRequestBody;

    public ServiceRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.url = url;
        this.jsonRequestBody = jsonRequest;
    }

    public ServiceRequest(String url, JSONObject jsonRequestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequestBody, listener, errorListener);
        this.url = url;
        this.jsonRequestBody = jsonRequestBody;
    }

    private static ServiceRequest newInstance(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        if (serviceRequest != null) {
            if (serviceRequest.getUrl().equalsIgnoreCase(url) && serviceRequest.getJsonRequestBody().equals(jsonRequest)) {
                Logger.d("serviceRequest", "oldInstance");
                return serviceRequest;
            }
        } else {
            serviceRequest = new ServiceRequest(method, url, jsonRequest, listener, errorListener);
            Logger.d("serviceRequest", "newInstance");
        }
        return serviceRequest;
    }

    private static ServiceRequest newInstance(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        if (serviceRequest != null) {
            if (serviceRequest.getUrl().equalsIgnoreCase(url) && serviceRequest.getJsonRequestBody().equals(jsonRequest)) {
                return serviceRequest;
            }
        } else {
            serviceRequest = new ServiceRequest(url, jsonRequest, listener, errorListener);
        }
        return serviceRequest;
    }


    @Override
    public byte[] getBody() {
        Logger.d("body", getJsonRequestBody().toString());
        return super.getBody();
    }

    public boolean isCached() {
        return cached;
    }

    public JSONObject getJsonRequestBody() {
        return jsonRequestBody;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }
}
