package com.mowael.offers.utilities;

/**
 * Created by moham on 2/1/2017.
 */
public class SignUpUtility {

    private static SignUpUtility newInstance;
    private String userName, email, password, status;
    private final static String REGEX_Mail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static boolean firstTime = true;

    /**
     *
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
                        nullPointerEx(email + " is not valid email");
                    }
                } else {
                    nullPointerEx("password is empty");
                }
            } else {
                nullPointerEx("email is empty");
            }
        } else {
            nullPointerEx("userName is empty");
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
        Logger.e("SIGN_UP", msg);
        throw new NullPointerException(msg);
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
        if (isNotEmpty(userName)) this.userName = userName;
    }

    public void setEmail(String email) {
        if (isNotEmpty(email)) this.email = email;
    }

    public void setPassword(String password) {
        if (isNotEmpty(password)) this.password = password;
    }

    public static boolean isFirstTime() {
        return firstTime;
    }

    public static boolean isMailValid(String email) {
        return email.matches(REGEX_Mail);
    }


    public boolean isNotEmpty(String userName) {
        return !userName.isEmpty();
    }


}
