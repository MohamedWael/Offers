package com.mowael.offers.dataModel.myOffersResponse;

import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/13/2017.
 */

public class MyOffersResponse extends APIResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
