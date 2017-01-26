package com.sunmediaeg.offers.dataModel;

/**
 * Created by moham on 1/26/2017.
 */

public class Company {
    private int id, imageSourse;
    private String name;

    public Company(int id, String name, int imageSourse) {
        this.id = id;
        this.imageSourse = imageSourse;
        this.name = name;
    }


    public int getCompanyId() {
        return id;
    }

    public int getImageSourse() {
        return imageSourse;
    }

    public String getCompanyName() {
        return name;
    }
}
