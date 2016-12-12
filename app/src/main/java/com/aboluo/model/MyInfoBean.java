package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2016/12/13.
 */

public class MyInfoBean {

    /**
     * IsSuccess : true
     * Message : 获取成功！
     * Result : {"MemberEntity":{"MemberId":2,"WechatOpenId":"d816c761-40a2-4967-b677-b6cd3e888256","MemberPwd":"05704C0115355E2A055BEDCA73335A45B157F3D40246B8F5DA1C0FA01008090CF70AB92AE9EF07C2","WechatNickName":"abl_c5c70ceb43","WechatLogoUrl":"","WechatUnionID":"","Subscribe":1,"SubscribeTime":"2016-11-27T22:39:02","RealName":"","MemberEmail":"","MemberMobile":"18360733212","Referrer1Id":0,"Referrer2Id":0,"Referrer3Id":0,"Re1num":0,"Re2num":0,"Re3num":0,"SceneUrl":"","Turnover":0,"Incomecommission1":0,"Incomecommission":0,"Commission":0,"Settlement":0,"Jifen":0,"IsDealer":0,"Integral":0,"Level":1,"AddTime":"2016-11-27T22:39:02","IsDelete":0,"memberSex":0,"ContinueSign":0},"TrueLoginToken":null}
     */

    private boolean IsSuccess;
    private String Message;
    /**
     * MemberEntity : {"MemberId":2,"WechatOpenId":"d816c761-40a2-4967-b677-b6cd3e888256","MemberPwd":"05704C0115355E2A055BEDCA73335A45B157F3D40246B8F5DA1C0FA01008090CF70AB92AE9EF07C2","WechatNickName":"abl_c5c70ceb43","WechatLogoUrl":"","WechatUnionID":"","Subscribe":1,"SubscribeTime":"2016-11-27T22:39:02","RealName":"","MemberEmail":"","MemberMobile":"18360733212","Referrer1Id":0,"Referrer2Id":0,"Referrer3Id":0,"Re1num":0,"Re2num":0,"Re3num":0,"SceneUrl":"","Turnover":0,"Incomecommission1":0,"Incomecommission":0,"Commission":0,"Settlement":0,"Jifen":0,"IsDealer":0,"Integral":0,"Level":1,"AddTime":"2016-11-27T22:39:02","IsDelete":0,"memberSex":0,"ContinueSign":0}
     * TrueLoginToken : null
     */

    private ResultBean Result;

    public static MyInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, MyInfoBean.class);
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

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * MemberId : 2
         * WechatOpenId : d816c761-40a2-4967-b677-b6cd3e888256
         * MemberPwd : 05704C0115355E2A055BEDCA73335A45B157F3D40246B8F5DA1C0FA01008090CF70AB92AE9EF07C2
         * WechatNickName : abl_c5c70ceb43
         * WechatLogoUrl :
         * WechatUnionID :
         * Subscribe : 1
         * SubscribeTime : 2016-11-27T22:39:02
         * RealName :
         * MemberEmail :
         * MemberMobile : 18360733212
         * Referrer1Id : 0
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
         * IsDealer : 0
         * Integral : 0
         * Level : 1
         * AddTime : 2016-11-27T22:39:02
         * IsDelete : 0
         * memberSex : 0
         * ContinueSign : 0
         */

        private MemberEntityBean MemberEntity;
        private Object TrueLoginToken;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public MemberEntityBean getMemberEntity() {
            return MemberEntity;
        }

        public void setMemberEntity(MemberEntityBean MemberEntity) {
            this.MemberEntity = MemberEntity;
        }

        public Object getTrueLoginToken() {
            return TrueLoginToken;
        }

        public void setTrueLoginToken(Object TrueLoginToken) {
            this.TrueLoginToken = TrueLoginToken;
        }

        public static class MemberEntityBean {
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

            public static MemberEntityBean objectFromData(String str) {

                return new Gson().fromJson(str, MemberEntityBean.class);
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
        }
    }
}
