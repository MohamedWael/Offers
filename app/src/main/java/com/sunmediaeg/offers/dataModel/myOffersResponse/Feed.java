
package com.sunmediaeg.offers.dataModel.myOffersResponse;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Feed extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    private Long mId;
    @SerializedName("CategoryName")
    private String mCategoryName;
    @SerializedName("desc")
    private String mDescription;
    @SerializedName("discount")
    private Long mDiscount;
    @SerializedName("end_date")
    private Long mEndDate;
    @SerializedName("image")
    private String mImage;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("ShortDesc")
    private String mShortDesc;
    @SerializedName("start_date")
    private Long mStartDate;
    @SerializedName("title")
    private String mTitle;

    public Feed() {
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String CategoryName) {
        mCategoryName = CategoryName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String desc) {
        mDescription = desc;
    }

    public Long getDiscount() {
        return mDiscount;
    }

    public void setDiscount(Long discount) {
        mDiscount = discount;
    }

    public Long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Long endDate) {
        mEndDate = endDate;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getShortDescription() {
        return mShortDesc;
    }

    public void setShortDescription(String ShortDesc) {
        mShortDesc = ShortDesc;
    }

    public Long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Long startDate) {
        mStartDate = startDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    @Override
    public String toString() {
        return "id " + getId() + " desc " + getDescription();
    }
}
