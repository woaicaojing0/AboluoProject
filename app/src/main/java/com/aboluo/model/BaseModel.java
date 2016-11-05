package com.aboluo.model;

/**
 * Created by CJ on 2016/11/6.
 */

public class BaseModel {

    /**
     * IsSuccess : true
     * Message : 添加收货地址成功
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;

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

    public Object getResult() {
        return Result;
    }

    public void setResult(Object Result) {
        this.Result = Result;
    }
}
