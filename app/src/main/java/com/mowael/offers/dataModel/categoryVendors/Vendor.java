
package com.mowael.offers.dataModel.categoryVendors;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.myOffersResponse.Feed;

import javax.annotation.Generated;

import io.realm.RealmList;
import io.realm.RealmObject;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Vendor extends RealmObject {

    @SerializedName("brochure_image")
    private String mBrochureImage;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("offers")
    private RealmList<Feed> offers;
    private int followed;

    public String getBrochureImage() {
        return mBrochureImage;
    }

    public void setBrochureImage(String brochureImage) {
        mBrochureImage = brochureImage;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public RealmList<Feed> getOffers() {
        return offers;
    }

    public void setOffers(RealmList<Feed> offers) {
        this.offers = offers;
    }

    public void follow(boolean follow) {
        this.followed = follow ? 1 : 0;
    }

    public boolean isFollowed() {
        return followed > 0;
    }
}
