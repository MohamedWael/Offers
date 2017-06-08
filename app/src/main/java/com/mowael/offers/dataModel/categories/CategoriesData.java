
package com.mowael.offers.dataModel.categories;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CategoriesData {

    @SerializedName("categories")
    private ArrayList<Category> mCategories;

    public ArrayList<Category> getCategories() {
        if (mCategories == null) {
            mCategories = new ArrayList<>();
        }
        return mCategories;
    }

    public void setCategories(ArrayList<Category> categories) {
        mCategories = categories;
    }

}
