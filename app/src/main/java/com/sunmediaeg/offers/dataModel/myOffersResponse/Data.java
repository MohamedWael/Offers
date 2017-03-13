
package com.sunmediaeg.offers.dataModel.myOffersResponse;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("feeds")
    private ArrayList<Feed> mFeeds;

    public ArrayList<Feed> getFeeds() {
        return mFeeds;
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        mFeeds = feeds;
    }

}
