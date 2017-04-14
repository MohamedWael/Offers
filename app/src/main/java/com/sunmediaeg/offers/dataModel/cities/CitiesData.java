
package com.sunmediaeg.offers.dataModel.cities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CitiesData {

    @SerializedName("cities")
    private ArrayList<City> mCities;

    public ArrayList<City> getCities() {
        return mCities;
    }

    public ArrayList<String> getCitiesNames() {
        ArrayList<String> citiesNames = new ArrayList<>();
        for (City city : getCities()) {
            citiesNames.add(city.getName());
        }
        return citiesNames;
    }

    public void setCities(ArrayList<City> cities) {
        mCities = cities;
    }

}
