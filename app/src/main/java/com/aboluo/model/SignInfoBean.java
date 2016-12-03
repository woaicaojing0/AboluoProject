package com.aboluo.model;

/**
 * Created by cj34920 on 2016/12/1.
 */

public class SignInfoBean {


    /**
     * MemberScoreLogEntity : null
     * SignScore : 0
     * ContineSignDay : 0
     * TotalDay : 7
     * ForenScoreConfig : 10
     * IsSuccess : true
     * Message : 今日未签到
     * Result : 2
     */

    private Object MemberScoreLogEntity;
    private int SignScore;
    private int ContineSignDay;
    private int TotalDay;
    private int ForenScoreConfig;
    private boolean IsSuccess;
    private String Message;
    private int Result;

    public Object getMemberScoreLogEntity() {
        return MemberScoreLogEntity;
    }

    public void setMemberScoreLogEntity(Object MemberScoreLogEntity) {
        this.MemberScoreLogEntity = MemberScoreLogEntity;
    }

    public int getSignScore() {
        return SignScore;
    }

    public void setSignScore(int SignScore) {
        this.SignScore = SignScore;
    }

    public int getContineSignDay() {
        return ContineSignDay;
    }

    public void setContineSignDay(int ContineSignDay) {
        this.ContineSignDay = ContineSignDay;
    }

    public int getTotalDay() {
        return TotalDay;
    }

    public void setTotalDay(int TotalDay) {
        this.TotalDay = TotalDay;
    }

    public int getForenScoreConfig() {
        return ForenScoreConfig;
    }

    public void setForenScoreConfig(int ForenScoreConfig) {
        this.ForenScoreConfig = ForenScoreConfig;
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

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }
}
