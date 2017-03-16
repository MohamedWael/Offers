
package com.sunmediaeg.offers.dataModel.userResponse;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import com.sunmediaeg.offers.utilities.Constants;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class User extends RealmObject {

    @SerializedName("email")
    private String mEmail;
    @PrimaryKey
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("is_admin")
    private Long mIsAdmin;
    @SerializedName("name")
    private String mName;
    private int userType;

    public User() {
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public Long getIsAdmin() {
        return mIsAdmin;
    }

    public void setIsAdmin(Long isAdmin) {
        mIsAdmin = isAdmin;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
