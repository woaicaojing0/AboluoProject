package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2017/2/17.
 */

public class BindMsgBean {
    /**
     *
     * {"MsgCode":"513331","IsSuccess":true,"Message":"发送成功！","Result":null}
     */
    /**
     * MsgCode : null
     * IsSuccess : false
     * Message : 该手机号已经绑定过其他账户！
     * Result : null
     */

    private Object MsgCode;
    private boolean IsSuccess;
    private String Message;
    private Object Result;

    public static BindMsgBean objectFromData(String str) {

        return new Gson().fromJson(str, BindMsgBean.class);
    }

    public Object getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(Object MsgCode) {
        this.MsgCode = MsgCode;
    }

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
