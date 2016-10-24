package com.aboluo.model;

/**
 * Created by CJ on 2016/10/24.
 */

public class AddShopCarBean {

    /**
     * IsSuccess : true
     * Message : 添加购物车成功
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
