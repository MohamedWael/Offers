package com.mowael.offers.dataModel;

import io.realm.RealmObject;

/**
 * Created by moham on 1/26/2017.
 */

public class Company extends RealmObject {
    private int id, imageSourse, numberOfOffers = 0;
    private String name, companyLogoUrl;
    private boolean isFollwed = false;

    public Company() {
    }

    public Company(int id, String name, int imageSourse) {
        this.id = id;
        this.imageSourse = imageSourse;
        this.name = name;
    }

    public Company(int id, int imageSourse, int numberOfOffers, String name, String companyLogoUrl, boolean isFollwed) {
        this.id = id;
        this.imageSourse = imageSourse;
        this.numberOfOffers = numberOfOffers;
        this.name = name;
        this.companyLogoUrl = companyLogoUrl;
        this.isFollwed = isFollwed;
    }

    public Company(int id, String name, String companyLogoUrl) {
        this.id = id;
        this.name = name;
        this.companyLogoUrl = companyLogoUrl;
    }

    public int getNumberOfOffers() {
        return numberOfOffers;
    }

    public boolean isFollwed() {
        return isFollwed;
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


    public void setId(int id) {
        this.id = id;
    }

    public void setImageSourse(int imageSourse) {
        this.imageSourse = imageSourse;
    }

    public void setNumberOfOffers(int numberOfOffers) {
        this.numberOfOffers = numberOfOffers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    public void setFollwed(boolean follwed) {
        isFollwed = follwed;
    }
}
