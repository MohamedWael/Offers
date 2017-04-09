
package com.sunmediaeg.offers.dataModel.cities;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CitiesData {

    @SerializedName("cities")
    private List<City> mCities;

    public List<City> getCities() {
        return mCities;
    }

    public void setCities(List<City> cities) {
        mCities = cities;
    }

}
