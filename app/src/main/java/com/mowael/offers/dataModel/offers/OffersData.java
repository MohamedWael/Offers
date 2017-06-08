
package com.mowael.offers.dataModel.offers;

import com.google.gson.annotations.SerializedName;
import com.mowael.offers.dataModel.myOffersResponse.Feed;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OffersData {

    @SerializedName("offers")
    private ArrayList<Feed> mOffers;

    public ArrayList<Feed> getOffers() {
        if (mOffers == null) {
            mOffers = new ArrayList<>();
        }
        return mOffers;
    }

    public void setOffers(ArrayList<Feed> offers) {
        mOffers = offers;
    }

}
