
package com.mowael.offers.dataModel.categoryVendors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VendorsData {

    @SerializedName("vendors")
    private ArrayList<Vendor> mVendors;

    public ArrayList<Vendor> getVendors() {
        if (mVendors == null) {
            mVendors = new ArrayList<>();
        }
        return mVendors;
    }

    public void setVendors(ArrayList<Vendor> vendors) {
        mVendors = vendors;
    }

}
