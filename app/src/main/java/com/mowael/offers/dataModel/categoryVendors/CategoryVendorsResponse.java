package com.mowael.offers.dataModel.categoryVendors;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/24/2017.
 */

public class CategoryVendorsResponse extends APIResponse {
    @SerializedName("data")
    private VendorsData data;

    public VendorsData getData() {
        return data;
    }

    public void setData(VendorsData data) {
        this.data = data;
    }
}
