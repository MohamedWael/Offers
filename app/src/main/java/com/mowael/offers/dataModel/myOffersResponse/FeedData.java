
package com.mowael.offers.dataModel.myOffersResponse;

import java.util.ArrayList;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class FeedData {

    @SerializedName("feeds")
    private ArrayList<Feed> mFeeds;

    public ArrayList<Feed> getFeeds() {
        return mFeeds;
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        mFeeds = feeds;
    }

}
