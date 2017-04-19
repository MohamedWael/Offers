
package com.sunmediaeg.offers.dataModel.jsonModels;

import com.google.gson.annotations.SerializedName;
import com.sunmediaeg.offers.dataModel.APIResponse;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResponse extends APIResponse {

    @SerializedName("data")
    private UserData mUserData;
    @SerializedName("errors")
    private SignUpErrors errors;

    public UserData getData() {
        return mUserData;
    }


    public void setData(UserData userData) {
        mUserData = userData;
    }

    public SignUpErrors getErrors() {
        if (mUserData == null)
            return errors;
        else return new SignUpErrors();
    }

    public void setErrors(SignUpErrors errors) {
        if (mUserData == null)
            this.errors = errors;
    }
}
