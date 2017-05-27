
package com.mowael.offers.dataModel.jsonModels;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FbProfileData {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public String getEmail() {
        return mEmail;
    }


    public String getGender() {
        return mGender;
    }


    public String getId() {
        return mId;
    }


    public String getName() {
        return mName;
    }

}
