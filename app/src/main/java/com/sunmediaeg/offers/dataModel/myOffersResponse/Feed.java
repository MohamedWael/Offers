
package com.sunmediaeg.offers.dataModel.myOffersResponse;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Feed extends RealmObject implements Comparable<Feed> {

    @PrimaryKey
    @SerializedName("id")
    private long mId;
    @SerializedName("category_name")
    private String mCategoryName;
    @SerializedName("vendor_name")
    private String mVendorName;
    @SerializedName("desc")
    private String mDescription;
    @SerializedName("discount")
    private long mDiscount;
    @SerializedName("end_date")
    private long mEndDate;
    @SerializedName("image")
    private String mImage;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("short_desc")
    private String mShortDesc;
    @SerializedName("start_date")
    private long mStartDate;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("category_image")
    private String mCategoryImage;
    @SerializedName("vendor_image")
    private String mVendorImage;
    @SerializedName("vendor_id")
    private long vendorId;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("liked")
    private int liked;

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

    public long getDiscount() {
        return mDiscount;
    }

    public void setDiscount(long discount) {
        mDiscount = discount;
    }

    public long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(long endDate) {
        mEndDate = endDate;
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

    public long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCategoryImage() {
        return mCategoryImage;
    }

    public void setmCategoryImage(String mCategoryImage) {
        this.mCategoryImage = mCategoryImage;
    }

    public String getVendorImage() {
        return mVendorImage;
    }

    public void setmVendorImage(String mVendorImage) {
        this.mVendorImage = mVendorImage;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getVendorName() {
        return mVendorName;
    }

    public void setVendorName(String mVendorName) {
        this.mVendorName = mVendorName;
    }

    public boolean isLiked() {
        return liked > 0;
    }

    public boolean isFeedLiked() {
        return liked == 0;
    }

    public boolean isFeedDisLiked() {
        return liked == -1;
    }

    /**
     * 1 for like
     * 0 for dislike
     *
     * @param liked
     */
    public void like(boolean like) {
        if (like) this.liked = 1;
        else this.liked = -1;
    }

    @Override
    public String toString() {
        return "id " + getId() + " desc " + getDescription();
    }

    @Override
    public int compareTo(@NonNull Feed feed) {
        if (getEndDate() <= feed.getEndDate())
            return 1;
        else return -1;
    }
}
