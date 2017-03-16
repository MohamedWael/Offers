
package com.sunmediaeg.offers.dataModel.jsonModels;

import com.google.gson.annotations.SerializedName;
import com.sunmediaeg.offers.dataModel.userResponse.User;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UserData {

    @SerializedName("user")
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

}
