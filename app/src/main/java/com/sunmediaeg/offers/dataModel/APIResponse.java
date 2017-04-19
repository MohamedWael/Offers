package com.sunmediaeg.offers.dataModel;

import io.realm.RealmObject;

/**
 * Created by moham on 3/13/2017.
 */

public class APIResponse {
    private String message;
    private int code;

    public APIResponse() {
    }

    public boolean isSuccess() {
        if (code == 200) return true;
        else return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
