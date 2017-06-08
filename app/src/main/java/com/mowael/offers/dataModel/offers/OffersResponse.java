package com.mowael.offers.dataModel.offers;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/14/2017.
 */

public class OffersResponse extends APIResponse {
    @SerializedName("data")
    private OffersData data;

    public OffersResponse() {
    }

    public OffersData getData() {
        return data;
    }

    public void setData(OffersData data) {
        this.data = data;
    }
}
