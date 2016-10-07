package com.aboluo.model;

/**
 * Created by CJ on 2016/10/7.
 */

public class LoginInfo {

    /**
     * IsSuccess : true
     * Message : 登录成功！
     * Result : {"MemberEntity":{"MemberId":1972,"WechatOpenId":"044a32b2-7669-4ec4-81e9-a87aa9f3f537","MemberPwd":"","WechatNickName":"abl_15252664102","WechatLogoUrl":"","WechatUnionID":"","Subscribe":1,"SubscribeTime":"2016-10-07T18:16:10","RealName":"","MemberEmail":"","MemberMobile":"15252664102","Referrer1Id":0,"Referrer2Id":0,"Referrer3Id":0,"Re1num":0,"Re2num":0,"Re3num":0,"SceneUrl":"","Turnover":0,"Incomecommission1":0,"Incomecommission":0,"Commission":0,"Settlement":0,"Jifen":0,"IsDealer":0,"Integral":0,"Level":1,"AddTime":"2016-10-07T18:16:10","IsDelete":0},"TrueLoginToken":"7B4CC40A800DB8935A47D6E56C0E5690CA9F502F9F38CAAC72C0C0C62FC8CDEB6815C5E4EFBD89577B83250FCBA490809466760EC067A7F55614ADDA485A1753793A5A993253970D"}
     * ListResult : null
     */

    private boolean IsSuccess;
    private String Message;
    /**
     * MemberEntity : {"MemberId":1972,"WechatOpenId":"044a32b2-7669-4ec4-81e9-a87aa9f3f537","MemberPwd":"","WechatNickName":"abl_15252664102","WechatLogoUrl":"","WechatUnionID":"","Subscribe":1,"SubscribeTime":"2016-10-07T18:16:10","RealName":"","MemberEmail":"","MemberMobile":"15252664102","Referrer1Id":0,"Referrer2Id":0,"Referrer3Id":0,"Re1num":0,"Re2num":0,"Re3num":0,"SceneUrl":"","Turnover":0,"Incomecommission1":0,"Incomecommission":0,"Commission":0,"Settlement":0,"Jifen":0,"IsDealer":0,"Integral":0,"Level":1,"AddTime":"2016-10-07T18:16:10","IsDelete":0}
     * TrueLoginToken : 7B4CC40A800DB8935A47D6E56C0E5690CA9F502F9F38CAAC72C0C0C62FC8CDEB6815C5E4EFBD89577B83250FCBA490809466760EC067A7F55614ADDA485A1753793A5A993253970D
     */

    private ResultBean Result;
    private Object ListResult;

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

    public Object getListResult() {
        return ListResult;
    }

    public void setListResult(Object ListResult) {
        this.ListResult = ListResult;
    }

    public static class ResultBean {
        /**
         * MemberId : 1972
         * WechatOpenId : 044a32b2-7669-4ec4-81e9-a87aa9f3f537
         * MemberPwd :
         * WechatNickName : abl_15252664102
         * WechatLogoUrl :
         * WechatUnionID :
         * Subscribe : 1
         * SubscribeTime : 2016-10-07T18:16:10
         * RealName :
         * MemberEmail :
         * MemberMobile : 15252664102
         * Referrer1Id : 0
         * Referrer2Id : 0
         * Referrer3Id : 0
         * Re1num : 0
         * Re2num : 0
         * Re3num : 0
         * SceneUrl :
         * Turnover : 0.0
         * Incomecommission1 : 0.0
         * Incomecommission : 0.0
         * Commission : 0.0
         * Settlement : 0.0
         * Jifen : 0
         * IsDealer : 0
         * Integral : 0.0
         * Level : 1
         * AddTime : 2016-10-07T18:16:10
         * IsDelete : 0
         */

        private MemberEntityBean MemberEntity;
        private String TrueLoginToken;

        public MemberEntityBean getMemberEntity() {
            return MemberEntity;
        }

        public void setMemberEntity(MemberEntityBean MemberEntity) {
            this.MemberEntity = MemberEntity;
        }

        public String getTrueLoginToken() {
            return TrueLoginToken;
        }

        public void setTrueLoginToken(String TrueLoginToken) {
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
            private double Turnover;
            private double Incomecommission1;
            private double Incomecommission;
            private double Commission;
            private double Settlement;
            private int Jifen;
            private int IsDealer;
            private double Integral;
            private int Level;
            private String AddTime;
            private int IsDelete;

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

            public double getTurnover() {
                return Turnover;
            }

            public void setTurnover(double Turnover) {
                this.Turnover = Turnover;
            }

            public double getIncomecommission1() {
                return Incomecommission1;
            }

            public void setIncomecommission1(double Incomecommission1) {
                this.Incomecommission1 = Incomecommission1;
            }

            public double getIncomecommission() {
                return Incomecommission;
            }

            public void setIncomecommission(double Incomecommission) {
                this.Incomecommission = Incomecommission;
            }

            public double getCommission() {
                return Commission;
            }

            public void setCommission(double Commission) {
                this.Commission = Commission;
            }

            public double getSettlement() {
                return Settlement;
            }

            public void setSettlement(double Settlement) {
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

            public double getIntegral() {
                return Integral;
            }

            public void setIntegral(double Integral) {
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
        }
    }
}
