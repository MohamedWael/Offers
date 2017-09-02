package com.mowael.offers.utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mowael.offers.dataModel.APIResponse;
import com.mowael.offers.dataModel.cities.CitiesResponse;
import com.mowael.offers.dataModel.cities.City;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by moham on 9/2/2017.
 */

public abstract class OffersCitesUtil {

    public void getCities(Context context) {
        try {
            Service.getInstance(context).getResponse(Request.Method.GET, Constants.GET_CITIES, new JSONObject(), new Service.ServiceResponse() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    APIResponse apiResponse = gson.fromJson(response.toString(), APIResponse.class);
                    if (apiResponse.isSuccess()) {
                        final CitiesResponse citiesResponse = gson.fromJson(response.toString(), CitiesResponse.class);

                        onCitesArrive(citiesResponse.getData().getCities());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toaster.getInstance().toast("فشل فى تحميل المدن حاول فى وقت لاحق");
                }

                @Override
                public void updateUIOnNetworkUnavailable(String noInternetMessage) {
                    Toaster.getInstance().toast(noInternetMessage);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onCitesArrive(ArrayList<City> cities);
}
