
package com.sunmediaeg.offers.dataModel.myOffersResponse;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @SerializedName("feeds")
    private RealmList<Feed> mFeeds;

    public RealmList<Feed> getFeeds() {
        return mFeeds;
    }

    public void setFeeds(RealmList<Feed> feeds) {
        mFeeds = feeds;
    }

}
