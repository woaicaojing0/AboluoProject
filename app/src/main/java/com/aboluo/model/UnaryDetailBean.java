package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by cj on 2017/5/2.
 */

public class UnaryDetailBean {

    /**
     * AddTotalCount : 1
     * OneTimes : 1007
     * MemberNickName : ✨浩丿冬祭
     * ThisWinLotteryNumber : 无
     * LastWinLotteryNumber : 无
     * IsSuccess : true
     * Message : null
     * Result : null
     */

    private int AddTotalCount;
    private String OneTimes;
    private String MemberNickName;
    private String ThisWinLotteryNumber;
    private String LastWinLotteryNumber;
    private boolean IsSuccess;
    private Object Message;
    private Object Result;

    public static UnaryDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, UnaryDetailBean.class);
    }

    public int getAddTotalCount() {
        return AddTotalCount;
    }

    public void setAddTotalCount(int AddTotalCount) {
        this.AddTotalCount = AddTotalCount;
    }

    public String getOneTimes() {
        return OneTimes;
    }

    public void setOneTimes(String OneTimes) {
        this.OneTimes = OneTimes;
    }

    public String getMemberNickName() {
        return MemberNickName;
    }

    public void setMemberNickName(String MemberNickName) {
        this.MemberNickName = MemberNickName;
    }

    public String getThisWinLotteryNumber() {
        return ThisWinLotteryNumber;
    }

    public void setThisWinLotteryNumber(String ThisWinLotteryNumber) {
        this.ThisWinLotteryNumber = ThisWinLotteryNumber;
    }

    public String getLastWinLotteryNumber() {
        return LastWinLotteryNumber;
    }

    public void setLastWinLotteryNumber(String LastWinLotteryNumber) {
        this.LastWinLotteryNumber = LastWinLotteryNumber;
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
