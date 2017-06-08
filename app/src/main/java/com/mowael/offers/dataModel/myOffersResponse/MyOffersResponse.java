package com.mowael.offers.dataModel.myOffersResponse;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/13/2017.
 */

public class MyOffersResponse extends APIResponse {
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
