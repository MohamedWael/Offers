
package com.sunmediaeg.offers.dataModel.jsonModels;

public class User {


    private String email;

    private Long id;

    private String name;

    private String image;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getToken() {
        return token;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", name: " + getName() + ", email: " + getEmail() + ", token: " + getToken();
    }
}
