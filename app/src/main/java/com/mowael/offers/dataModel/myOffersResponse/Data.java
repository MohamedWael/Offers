
package com.mowael.offers.dataModel.myOffersResponse;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

import io.realm.RealmList;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("feeds")
    private ArrayList<Feed> mFeeds;

    @SerializedName("offers")
    private ArrayList<Feed> offers;

    public ArrayList<Feed> getFeeds() {
        if (mFeeds == null) {
            return offers;
        }
        return mFeeds;
    }

//    public void setFeeds(RealmList<Feed> feeds) {
//        mFeeds = feeds;
//    }

}
