
package com.mowael.offers.dataModel.cities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CitiesData {

    @SerializedName("cities")
    private ArrayList<City> mCities;
    private ArrayList<String> citiesNames;

    public ArrayList<City> getCities() {
        return mCities;
    }

    public ArrayList<String> getCitiesNames() {
        if (citiesNames == null) {
            citiesNames = new ArrayList<>();
            for (City city : getCities()) {
                citiesNames.add(city.getName());
            }
        }
        return citiesNames;
    }

    public City getCity(int index) {
        return getCities().get(index);
    }

    public void setCities(ArrayList<City> cities) {
        mCities = cities;
    }

}
