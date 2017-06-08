package com.mowael.offers.dataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moham on 3/13/2017.
 */

public class APIResponse {
    @SerializedName("message")
    private String msg;
    @SerializedName("code")
    private int mCode;

    public APIResponse() {
    }

    public boolean isSuccess() {
        return getCode() == 200;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }
}
