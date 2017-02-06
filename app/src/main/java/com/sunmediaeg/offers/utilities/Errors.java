package com.sunmediaeg.offers.utilities;

/**
 * Created by moham on 2/6/2017.
 */

public class Errors {

    private String errorMsg;

    public Errors(int errorCode) {
        switch (errorCode) {
            case Constants.CODE_SUCCESS:
                errorMsg = "CODE_SUCCESS".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_VALIDATION_ERRORS:
                errorMsg = "CODE_VALIDATION_ERRORS".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_SOMETHING_WRONG:
                errorMsg = "CODE_SOMETHING_WRONG".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_INVALID_API_TOKEN:
                errorMsg = "CODE_INVALID_API_TOKEN".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_NOT_FOUND:
                errorMsg = "CODE_NOT_FOUND".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_AUTH_FAILD:
                errorMsg = "CODE_AUTH_FAILD".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_TOKEN_NOT_FOUND:
                errorMsg = "CODE_TOKEN_NOT_FOUND".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_WRONG_PHONE_NUMBER:
                errorMsg = "CODE_WRONG_PHONE_NUMBER".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_WRONG_FORGET_PASS_VERIFY_NUMBER:
                errorMsg = "CODE_WRONG_FORGET_PASS_VERIFY_NUMBER".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_WAIT_BEFORE_RESEND:
                errorMsg = "CODE_WAIT_BEFORE_RESEND".replace("_", " ").toLowerCase();
                break;
            case Constants.CODE_DO_NOT_HAVE_PERMISSION:
                errorMsg = "CODE_DO_NOT_HAVE_PERMISSION".replace("_", " ").toLowerCase();
                break;
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
