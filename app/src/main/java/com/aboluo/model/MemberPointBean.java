package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by cj on 2017/6/14.
 * 个人积分相关信息
 */

public class MemberPointBean {


    /**
     * MemberId : 102617
     * MemberNickName : 测试昵称
     * MemberGrade : 3
     * MemberLevel : 0
     * CommonLevelNeed : 2000
     * CommonLevelScroes : 234
     * GrowUrlAddress : http://www.baidu.com
     * IsSuccess : true
     * Message : 信息获取成功
     * Result : null
     */

    private String MemberId;
    private String MemberNickName;
    private int MemberGrade;
    private int MemberLevel;
    private double CommonLevelNeed;
    private double CommonLevelScroes;
    private String GrowUrlAddress;
    private boolean IsSuccess;
    private String Message;
    private Object Result;

    public static MemberPointBean objectFromData(String str) {

        return new Gson().fromJson(str, MemberPointBean.class);
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    public String getMemberNickName() {
        return MemberNickName;
    }

    public void setMemberNickName(String MemberNickName) {
        this.MemberNickName = MemberNickName;
    }

    public int getMemberGrade() {
        return MemberGrade;
    }

    public void setMemberGrade(int MemberGrade) {
        this.MemberGrade = MemberGrade;
    }

    public int getMemberLevel() {
        return MemberLevel;
    }

    public void setMemberLevel(int MemberLevel) {
        this.MemberLevel = MemberLevel;
    }

    public double getCommonLevelNeed() {
        return CommonLevelNeed;
    }

    public void setCommonLevelNeed(double CommonLevelNeed) {
        this.CommonLevelNeed = CommonLevelNeed;
    }

    public double getCommonLevelScroes() {
        return CommonLevelScroes;
    }

    public void setCommonLevelScroes(int CommonLevelScroes) {
        this.CommonLevelScroes = CommonLevelScroes;
    }

    public String getGrowUrlAddress() {
        return GrowUrlAddress;
    }

    public void setGrowUrlAddress(String GrowUrlAddress) {
        this.GrowUrlAddress = GrowUrlAddress;
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
