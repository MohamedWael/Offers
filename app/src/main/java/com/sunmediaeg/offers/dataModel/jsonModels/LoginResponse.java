
package com.sunmediaeg.offers.dataModel.jsonModels;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResponse {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private UserData mUserData;
    @SerializedName("vendor")
    private UserData mVendorData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("errors")
    private SignUpErrors errors;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public UserData getData() {
        if (mVendorData == null) {
            return mUserData;
        } else {
            return mVendorData;
        }
    }


    public void setData(UserData userData) {
        mUserData = userData;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
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
