package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2016/12/19.
 */

public class CreditInfoBean {

    /**
     * TotalScore : 10687
     * CanUseScore : 10448
     * TotalMoney : 1057.1
     * FreeMoney : -19.8
     * CanUseMoney : 1120.05
     * MemberLogo : http://oj987uf2i.bkt.clouddn.com/memberLogo/android/23c9bd6a-93e9-4add-b29e-d6f1e4c491be
     * IsLeader : 1
     * NickName : 暴力解决
     * TotalTurnover : 0
     * PartnersMonthlyTurnover : 0
     * PersonalMonthlyTurnover : 0
     * IsSuccess : true
     * Message : null
     * Result : null
     */

    private int TotalScore;
    private int CanUseScore;
    private double TotalMoney;
    private double FreeMoney;
    private double CanUseMoney;
    private String MemberLogo;
    private int IsLeader;
    private String NickName;
    private double TotalTurnover;
    private double PartnersMonthlyTurnover;
    private double PersonalMonthlyTurnover;
    private boolean IsSuccess;
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

    public void setMemberLogo(String MemberLogo) {
        this.MemberLogo = MemberLogo;
    }

    public int getIsLeader() {
        return IsLeader;
    }

    public void setIsLeader(int IsLeader) {
        this.IsLeader = IsLeader;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public double getTotalTurnover() {
        return TotalTurnover;
    }

    public void setTotalTurnover(double TotalTurnover) {
        this.TotalTurnover = TotalTurnover;
    }

    public double getPartnersMonthlyTurnover() {
        return PartnersMonthlyTurnover;
    }

    public void setPartnersMonthlyTurnover(double PartnersMonthlyTurnover) {
        this.PartnersMonthlyTurnover = PartnersMonthlyTurnover;
    }

    public double getPersonalMonthlyTurnover() {
        return PersonalMonthlyTurnover;
    }

    public void setPersonalMonthlyTurnover(double PersonalMonthlyTurnover) {
        this.PersonalMonthlyTurnover = PersonalMonthlyTurnover;
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
