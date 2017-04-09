package com.sunmediaeg.offers.dataModel.vendor;

import com.sunmediaeg.offers.dataModel.APIResponse;
import com.sunmediaeg.offers.dataModel.categoryVendors.VendorsData;

/**
 * Created by moham on 4/7/2017.
 */

public class VendorResponse extends APIResponse {
    private VendorData data;

    public VendorData getData() {
        return data;
    }
}
