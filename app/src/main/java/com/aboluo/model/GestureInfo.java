package com.aboluo.model;

/**
 * Created by CJ on 2016/11/26.
 */

public class GestureInfo {
    private String userName;
    private boolean isOpen;
    private String Guesturepwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getGuesturepwd() {
        return Guesturepwd;
    }

    public void setGuesturepwd(String guesturepwd) {
        Guesturepwd = guesturepwd;
    }
}
