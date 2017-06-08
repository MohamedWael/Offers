package com.mowael.offers.dataModel.vendor;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 4/7/2017.
 */

public class VendorResponse extends APIResponse {
    @SerializedName("data")
    private VendorData data;

    public VendorData getData() {
        return data;
    }
}
