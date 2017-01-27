package com.sunmediaeg.offers.dataModel;

/**
 * Created by moham on 1/26/2017.
 */

public class Company {
    private int id, imageSourse;
    private String name, companyLogoUrl;

    public Company(int id, String name, int imageSourse) {
        this.id = id;
        this.imageSourse = imageSourse;
        this.name = name;
    }


    public Company(int id, String name, String companyLogoUrl) {
        this.id = id;
        this.name = name;
        this.companyLogoUrl = companyLogoUrl;
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

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }
}
