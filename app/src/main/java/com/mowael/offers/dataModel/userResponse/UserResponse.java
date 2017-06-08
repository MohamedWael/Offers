package com.mowael.offers.dataModel.userResponse;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.APIResponse;

/**
 * Created by moham on 3/13/2017.
 */

public class UserResponse extends APIResponse {
    @SerializedName("data")
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
