package com.aboluo.model;

/**
 * Created by CJ on 2016/12/3.
 */

public class BaseSignInBean {

    /**
     * IsSuccess : false
     * Message : 今日已签到
     * Result : 1
     */

    private boolean IsSuccess;
    private String Message;
    private int Result;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }
}
