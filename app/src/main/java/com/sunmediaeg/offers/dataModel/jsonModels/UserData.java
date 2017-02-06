
package com.sunmediaeg.offers.dataModel.jsonModels;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UserData {

    @SerializedName("user")
    private User mUser;
    @SerializedName("vendor")
    private User mVendor;

    public User getUser() {
        if (mVendor == null)
            return mUser;
        else return mVendor;
    }

    public void setUser(User user) {
        mUser = user;
    }

}
