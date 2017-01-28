package com.sunmediaeg.offers.dataModel;

/**
 * Created by Nourhan on 1/28/2017.
 */

public class Category {

    private int intentID, tileImage;
    private String tileTitle;

    public Category(int intentID, int tileImage, String tileTitle) {
        this.intentID = intentID;
        this.tileImage = tileImage;
        this.tileTitle = tileTitle;
    }

    public int getIntentID() {
        return intentID;
    }

    public int getTileImage() {
        return tileImage;
    }

    public String getTileTitle() {
        return tileTitle;
    }
}
