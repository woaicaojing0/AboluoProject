package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2017/2/18.
 */

public class FastParnterMakeOrderBean {


    /**
     * {"SerialId":"38424141717832170","Amount":18.8,"PartnerType":1,"IsSuccess":true,"Message":"下单成功！","Result":null}
     */

    /**
     * SerialId : null
     * Amount : 0
     * PartnerType : 0
     * IsSuccess : false
     * Message : 金额不正确
     * Result : null
     */

    private Object SerialId;
    private double Amount;
    private int PartnerType;
    private boolean IsSuccess;
    private String Message;
    private Object Result;

    public static FastParnterMakeOrderBean objectFromData(String str) {

        return new Gson().fromJson(str, FastParnterMakeOrderBean.class);
    }

    public Object getSerialId() {
        return SerialId;
    }

    public void setSerialId(Object SerialId) {
        this.SerialId = SerialId;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public int getPartnerType() {
        return PartnerType;
    }

    public void setPartnerType(int PartnerType) {
        this.PartnerType = PartnerType;
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
