package com.mowael.offers.dataModel.categories;

import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/14/2017.
 */

public class CategoriesResponse extends APIResponse {
    CategoriesData data;

    public CategoriesData getData() {
        return data;
    }

    public void setData(CategoriesData data) {
        this.data = data;
    }
}