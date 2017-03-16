package com.sunmediaeg.offers.dataModel.offers;

import com.sunmediaeg.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/14/2017.
 */

public class OffersResponse extends APIResponse {
    private OffersData data;

    public OffersData getData() {
        return data;
    }

    public void setData(OffersData data) {
        this.data = data;
    }
}
