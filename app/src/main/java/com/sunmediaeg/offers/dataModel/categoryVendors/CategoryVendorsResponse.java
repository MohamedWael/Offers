package com.sunmediaeg.offers.dataModel.categoryVendors;

import com.sunmediaeg.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/24/2017.
 */

public class CategoryVendorsResponse extends APIResponse {
    private VendorsData data;

    public VendorsData getData() {
        return data;
    }

    public void setData(VendorsData data) {
        this.data = data;
    }
}
