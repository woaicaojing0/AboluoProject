package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2017/3/2.
 */

public class InvitationInfoBean {

    /**
     * IsSuccess : true
     * Message : 获取成功！
     * Result : [{"MemberId":2178,"WechatOpenId":"o2e5bw1LGlM1__dXa3jQW6t7xX4w","MemberPwd":"","WechatNickName":"美燕","WechatLogoUrl":"memberLogo/ios/1485306405015150","WechatUnionID":"oF5uMxPStvkGdw-GiQ2kIITxim5I","Subscribe":1,"SubscribeTime":"2017-01-25T08:45:10","RealName":"","MemberEmail":"","MemberMobile":"","Referrer1Id":2167,"Referrer2Id":0,"Referrer3Id":0,"Re1num":0,"Re2num":0,"Re3num":0,"SceneUrl":"","Turnover":0,"Incomecommission1":0,"Incomecommission":0,"Commission":0,"Settlement":0,"Jifen":0,"IsDealer":1,"Integral":0,"Level":1,"AddTime":"2017-01-25T08:45:10","IsDelete":0,"memberSex":0,"ContinueSign":1,"InvitationCode":"170125084509","MechineType":0,"MechineCode":""},{"MemberId":2167,"WechatOpenId":"o2e5bw5cUQkBvQyHPEwESSLpQFgc","MemberPwd":"","WechatNickName":"百川山河","WechatLogoUrl":"memberLogo/android/eaee61c5-c9c5-4581-afc5-f5fc4a46f81a","WechatUnionID":"","Subscribe":1,"SubscribeTime":"2017-01-17T09:20:41","RealName":"","MemberEmail":"","MemberMobile":"15051226865","Referrer1Id":0,"Referrer2Id":0,"Referrer3Id":0,"Re1num":0,"Re2num":0,"Re3num":0,"SceneUrl":"","Turnover":0,"Incomecommission1":0,"Incomecommission":0,"Commission":0,"Settlement":0,"Jifen":0,"IsDealer":1,"Integral":0,"Level":1,"AddTime":"2017-01-17T09:20:41","IsDelete":0,"memberSex":0,"ContinueSign":7,"InvitationCode":"170117092040","MechineType":0,"MechineCode":""}]
     */

    private boolean IsSuccess;
    private String Message;
    private List<ResultBean> Result;

    public static InvitationInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, InvitationInfoBean.class);
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

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * MemberId : 2178
         * WechatOpenId : o2e5bw1LGlM1__dXa3jQW6t7xX4w
         * MemberPwd :
         * WechatNickName : 美燕
         * WechatLogoUrl : memberLogo/ios/1485306405015150
         * WechatUnionID : oF5uMxPStvkGdw-GiQ2kIITxim5I
         * Subscribe : 1
         * SubscribeTime : 2017-01-25T08:45:10
         * RealName :
         * MemberEmail :
         * MemberMobile :
         * Referrer1Id : 2167
         * Referrer2Id : 0
         * Referrer3Id : 0
         * Re1num : 0
         * Re2num : 0
         * Re3num : 0
         * SceneUrl :
         * Turnover : 0
         * Incomecommission1 : 0
         * Incomecommission : 0
         * Commission : 0
         * Settlement : 0
         * Jifen : 0
         * IsDealer : 1
         * Integral : 0
         * Level : 1
         * AddTime : 2017-01-25T08:45:10
         * IsDelete : 0
         * memberSex : 0
         * ContinueSign : 1
         * InvitationCode : 170125084509
         * MechineType : 0
         * MechineCode :
         */

        private int MemberId;
        private String WechatOpenId;
        private String MemberPwd;
        private String WechatNickName;
        private String WechatLogoUrl;
        private String WechatUnionID;
        private int Subscribe;
        private String SubscribeTime;
        private String RealName;
        private String MemberEmail;
        private String MemberMobile;
        private int Referrer1Id;
        private int Referrer2Id;
        private int Referrer3Id;
        private int Re1num;
        private int Re2num;
        private int Re3num;
        private String SceneUrl;
        private int Turnover;
        private int Incomecommission1;
        private int Incomecommission;
        private int Commission;
        private int Settlement;
        private int Jifen;
        private int IsDealer;
        private int Integral;
        private int Level;
        private String AddTime;
        private int IsDelete;
        private int memberSex;
        private int ContinueSign;
        private String InvitationCode;
        private int MechineType;
        private String MechineCode;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public int getMemberId() {
            return MemberId;
        }

        public void setMemberId(int MemberId) {
            this.MemberId = MemberId;
        }

        public String getWechatOpenId() {
            return WechatOpenId;
        }

        public void setWechatOpenId(String WechatOpenId) {
            this.WechatOpenId = WechatOpenId;
        }

        public String getMemberPwd() {
            return MemberPwd;
        }

        public void setMemberPwd(String MemberPwd) {
            this.MemberPwd = MemberPwd;
        }

        public String getWechatNickName() {
            return WechatNickName;
        }

        public void setWechatNickName(String WechatNickName) {
            this.WechatNickName = WechatNickName;
        }

        public String getWechatLogoUrl() {
            return WechatLogoUrl;
        }

        public void setWechatLogoUrl(String WechatLogoUrl) {
            this.WechatLogoUrl = WechatLogoUrl;
        }

        public String getWechatUnionID() {
            return WechatUnionID;
        }

        public void setWechatUnionID(String WechatUnionID) {
            this.WechatUnionID = WechatUnionID;
        }

        public int getSubscribe() {
            return Subscribe;
        }

        public void setSubscribe(int Subscribe) {
            this.Subscribe = Subscribe;
        }

        public String getSubscribeTime() {
            return SubscribeTime;
        }

        public void setSubscribeTime(String SubscribeTime) {
            this.SubscribeTime = SubscribeTime;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getMemberEmail() {
            return MemberEmail;
        }

        public void setMemberEmail(String MemberEmail) {
            this.MemberEmail = MemberEmail;
        }

        public String getMemberMobile() {
            return MemberMobile;
        }

        public void setMemberMobile(String MemberMobile) {
            this.MemberMobile = MemberMobile;
        }

        public int getReferrer1Id() {
            return Referrer1Id;
        }

        public void setReferrer1Id(int Referrer1Id) {
            this.Referrer1Id = Referrer1Id;
        }

        public int getReferrer2Id() {
            return Referrer2Id;
        }

        public void setReferrer2Id(int Referrer2Id) {
            this.Referrer2Id = Referrer2Id;
        }

        public int getReferrer3Id() {
            return Referrer3Id;
        }

        public void setReferrer3Id(int Referrer3Id) {
            this.Referrer3Id = Referrer3Id;
        }

        public int getRe1num() {
            return Re1num;
        }

        public void setRe1num(int Re1num) {
            this.Re1num = Re1num;
        }

        public int getRe2num() {
            return Re2num;
        }

        public void setRe2num(int Re2num) {
            this.Re2num = Re2num;
        }

        public int getRe3num() {
            return Re3num;
        }

        public void setRe3num(int Re3num) {
            this.Re3num = Re3num;
        }

        public String getSceneUrl() {
            return SceneUrl;
        }

        public void setSceneUrl(String SceneUrl) {
            this.SceneUrl = SceneUrl;
        }

        public int getTurnover() {
            return Turnover;
        }

        public void setTurnover(int Turnover) {
            this.Turnover = Turnover;
        }

        public int getIncomecommission1() {
            return Incomecommission1;
        }

        public void setIncomecommission1(int Incomecommission1) {
            this.Incomecommission1 = Incomecommission1;
        }

        public int getIncomecommission() {
            return Incomecommission;
        }

        public void setIncomecommission(int Incomecommission) {
            this.Incomecommission = Incomecommission;
        }

        public int getCommission() {
            return Commission;
        }

        public void setCommission(int Commission) {
            this.Commission = Commission;
        }

        public int getSettlement() {
            return Settlement;
        }

        public void setSettlement(int Settlement) {
            this.Settlement = Settlement;
        }

        public int getJifen() {
            return Jifen;
        }

        public void setJifen(int Jifen) {
            this.Jifen = Jifen;
        }

        public int getIsDealer() {
            return IsDealer;
        }

        public void setIsDealer(int IsDealer) {
            this.IsDealer = IsDealer;
        }

        public int getIntegral() {
            return Integral;
        }

        public void setIntegral(int Integral) {
            this.Integral = Integral;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int IsDelete) {
            this.IsDelete = IsDelete;
        }

        public int getMemberSex() {
            return memberSex;
        }

        public void setMemberSex(int memberSex) {
            this.memberSex = memberSex;
        }

        public int getContinueSign() {
            return ContinueSign;
        }

        public void setContinueSign(int ContinueSign) {
            this.ContinueSign = ContinueSign;
        }

        public String getInvitationCode() {
            return InvitationCode;
        }

        public void setInvitationCode(String InvitationCode) {
            this.InvitationCode = InvitationCode;
        }

        public int getMechineType() {
            return MechineType;
        }

        public void setMechineType(int MechineType) {
            this.MechineType = MechineType;
        }

        public String getMechineCode() {
            return MechineCode;
        }

        public void setMechineCode(String MechineCode) {
            this.MechineCode = MechineCode;
        }
    }
}
