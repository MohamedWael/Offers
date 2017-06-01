package com.mowael.offers.utilities;

import android.content.Context;

/**
 * Created by moham on 2/1/2017.
 */
public class SignUpUtility {

    private static SignUpUtility newInstance;
    private String userName, email, password, status;
    private final static String REGEX_Mail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static boolean firstTime = true;
    private Context context;

    private enum Errors {
        USERNAME("منفضلك أدخل إسم المستخدم بشكل صحيح"),
        EMAIL("من فضلك أدخل البريد الإلكترونى بشكل صحيح"), PASSWORD("من فضلك أدخل كلمة السر بشكل صحيح");
        private final String title;

        Errors(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    /**
     * @param userName
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public static SignUpUtility getInstance(String userName, String email, String password) throws Exception {
        userName = userName.trim();
        email = email.trim();
        password = password.trim();


        if (!userName.isEmpty()) {
            if (!email.isEmpty()) {
                if (!password.isEmpty()) {
                    if (isMailValid(email)) {
                        if (newInstance == null) {
                            Logger.d("newInstance", "yes");
                            newInstance = new SignUpUtility(userName, email, password);
                            firstTime = false;
                            return newInstance;
                        }
                    } else {
                        nullPointerEx(Errors.EMAIL.getTitle());
                    }
                } else {
                    nullPointerEx(Errors.PASSWORD.getTitle());
                }
            } else {
                nullPointerEx(Errors.EMAIL.getTitle());
            }
        } else {
            nullPointerEx(Errors.USERNAME.getTitle());
        }

        Logger.d("newInstance", "no");
        return newInstance;
    }


    /**
     * @param userName
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public static SignUpUtility newInstance(String userName, String email, String password) throws Exception {
        userName = userName.trim();
        email = email.trim();
        password = password.trim();

        if (isNotEmpty(userName, Errors.USERNAME.getTitle()) &&
                isMailValid(email, Errors.EMAIL.getTitle()) &&
                isNotEmpty(password, Errors.PASSWORD.getTitle())) {

            Logger.d("newInstance", "yes");
            firstTime = true;
            return new SignUpUtility(userName, email, password);
        }
        Logger.d("newInstance", "no");
        return newInstance;
    }

    private SignUpUtility(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    private static void nullPointerEx(String msg) {
        newInstance = null;
        Toaster.getInstance().toast(msg);
        Logger.e("SIGN_UP", msg);
//        throw new NullPointerException(msg);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private SignUpUtility() {
    }


    public static SignUpUtility getCurrentInstance() {
        return newInstance;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        if (isNotEmpty(userName, Errors.USERNAME.getTitle())) this.userName = userName;
    }

    public void setEmail(String email) {
        if (isMailValid(email, Errors.EMAIL.getTitle())) this.email = email;
    }

    public void setPassword(String password) {
        if (isNotEmpty(password, Errors.USERNAME.getTitle())) this.password = password;
    }

    public static boolean isFirstTime() {
        return firstTime;
    }

    public static boolean isMailValid(String email, String msg) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toaster.getInstance().toast(msg);
            return false;
        }
    }

    public static boolean isMailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public boolean isNotEmpty(String userName) {
        return !userName.isEmpty();
    }

    public static boolean isNotEmpty(String s, String msg) {
        if (s.isEmpty()) {
            Toaster.getInstance().toast(msg);
        }
        return !s.isEmpty();
    }


}
