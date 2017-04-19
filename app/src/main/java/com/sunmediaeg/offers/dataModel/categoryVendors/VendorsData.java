
package com.sunmediaeg.offers.dataModel.categoryVendors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VendorsData {

    @SerializedName("vendors")
    private ArrayList<Vendor> mVendors;

    public ArrayList<Vendor> getVendors() {
        return mVendors;
    }

    public void setVendors(ArrayList<Vendor> vendors) {
        mVendors = vendors;
    }

}
