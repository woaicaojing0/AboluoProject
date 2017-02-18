package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2017/2/18.
 */

public class FastParnterBean {

    /**
     * Amount : 18.8
     * PartnerType : 1
     * TypeName : 快速成为合伙人
     * IsSuccess : true
     * Message : null
     * Result : null
     */

    private double Amount;
    private int PartnerType;
    private String TypeName;
    private boolean IsSuccess;
    private Object Message;
    private Object Result;

    public static FastParnterBean objectFromData(String str) {

        return new Gson().fromJson(str, FastParnterBean.class);
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

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object Result) {
        this.Result = Result;
    }
}
