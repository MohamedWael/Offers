package com.mowael.offers.dataModel.categories;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/14/2017.
 */

public class CategoriesResponse extends APIResponse {
    @SerializedName("data")
    CategoriesData data;

    public CategoriesData getData() {
        return data;
    }

    public void setData(CategoriesData data) {
        this.data = data;
    }
}
