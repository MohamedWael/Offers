package com.mowael.offers.dataModel.cities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moham on 4/9/2017.
 */

public class CitiesResponse {
    @SerializedName("data")
    private CitiesData data;

    public CitiesData getData() {
        return data;
    }

    public void setData(CitiesData data) {
        this.data = data;
    }
}
