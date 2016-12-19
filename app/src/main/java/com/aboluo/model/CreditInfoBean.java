package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2016/12/19.
 */

public class CreditInfoBean {

    /**
     * TotalScore : 0
     * CanUseScore : 0
     * TotalMoney : 0.0
     * FreeMoney : 0.0
     * CanUseMoney : 0.0
     * IsSuccess : true
     * Message : null
     * Result : null
     */

    private int TotalScore;
    private int CanUseScore;
    private double TotalMoney;
    private double FreeMoney;
    private double CanUseMoney;
    private String NickName;
    private boolean IsSuccess;
    private String MemberLogo;
    private int IsLeader;
    private Object Message;
    private Object Result;

    public static CreditInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, CreditInfoBean.class);
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int TotalScore) {
        this.TotalScore = TotalScore;
    }

    public int getCanUseScore() {
        return CanUseScore;
    }

    public void setCanUseScore(int CanUseScore) {
        this.CanUseScore = CanUseScore;
    }

    public double getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(double TotalMoney) {
        this.TotalMoney = TotalMoney;
    }

    public double getFreeMoney() {
        return FreeMoney;
    }

    public void setFreeMoney(double FreeMoney) {
        this.FreeMoney = FreeMoney;
    }

    public double getCanUseMoney() {
        return CanUseMoney;
    }

    public void setCanUseMoney(double CanUseMoney) {
        this.CanUseMoney = CanUseMoney;
    }

    public String getMemberLogo() {
        return MemberLogo;
    }

    public void setMemberLogo(String memberLogo) {
        MemberLogo = memberLogo;
    }

    public int getIsLeader() {
        return IsLeader;
    }

    public void setIsLeader(int isLeader) {
        IsLeader = isLeader;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
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
