
package com.mowael.offers.dataModel.offers;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.myOffersResponse.Feed;

import io.realm.RealmList;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OffersData {

    @SerializedName("offers")
    private RealmList<Feed> mOffers;

    public RealmList<Feed> getOffers() {
        return mOffers;
    }

    public void setOffers(RealmList<Feed> offers) {
        mOffers = offers;
    }

}
