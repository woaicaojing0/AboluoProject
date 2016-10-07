package com.aboluo.model;

/**
 * Created by CJ on 2016/10/6.
 */

public class RegisterInfo {

    /**
     * ListResult : null
     * IsSuccess : false
     * Message : 用户名或密码错误！
     * Result : null
     */
    private String ListResult;
    private boolean IsSuccess;
    private String Message;
    private String Result;

    public void setListResult(String ListResult) {
        this.ListResult = ListResult;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getListResult() {
        return ListResult;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public String getResult() {
        return Result;
    }
}
