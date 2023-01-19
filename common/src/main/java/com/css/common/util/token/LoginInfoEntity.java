package com.css.common.util.token;

public class LoginInfoEntity {

    private String userId;

    private String userName;

    private boolean isLogin;

    private boolean isWX;
    /**
     * 0 旧用户  1 新用户
     */
    private int isNewUser;


    public int getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(int isNewUser) {
        this.isNewUser = isNewUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isWX() {
        return isWX;
    }

    public void setWX(boolean WX) {
        isWX = WX;
    }
}
