package com.mowael.offers.dataModel;

/**
 * Created by moham on 3/13/2017.
 */

public class APIResponse {
    private String message;
    private int code;

    public APIResponse() {
    }

    public boolean isSuccess() {
        return getCode() == 200;
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
