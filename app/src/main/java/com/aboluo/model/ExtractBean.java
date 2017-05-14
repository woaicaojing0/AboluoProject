package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2017/3/1.
 */

public class ExtractBean {


    /**
     * IsSuccess : true
     * Message : null
     * Result : {"WithdrawalsInfo":{"Id":66,"memberId":2172,"bankCard":"6013826110012617946","bankCardName":"长城电子借记卡","bankProvince":"江苏","bankCity":"淮安","bankType":"借记卡","bankCardLength":19,"bankName":"中国银行","bankLogo":"http://api.jisuapi.com/bankcard/upload/80/4.png","bankTel":"95566","bankWebsite":"http://www.boc.cn","mobile":null,"price":10,"email":"1206067690@qq.com","realName":"曹静","reason":"无佣金记录","validType":2,"auditStatus":2,"createTime":"2017-03-05T01:29:30","auditor":"caozong108","auditTime":"2017-03-07T12:59:30","flag":1,"WechatLogoUrl":"http://oj987uf2i.bkt.clouddn.com/memberLogo/android/23c9bd6a-93e9-4add-b29e-d6f1e4c491be","TotalMoney":1017.1,"FreezeMoney":-19.8,"CanUserMoney":31.05,"TotalRemaidMoney":0},"APPToken":null,"LoginCheckToken":null,"LoginPhone":null}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private ResultBean Result;
    private Object ListResult;

    public static ExtractBean objectFromData(String str) {

        return new Gson().fromJson(str, ExtractBean.class);
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
         * WithdrawalsInfo : {"Id":66,"memberId":2172,"bankCard":"6013826110012617946","bankCardName":"长城电子借记卡","bankProvince":"江苏","bankCity":"淮安","bankType":"借记卡","bankCardLength":19,"bankName":"中国银行","bankLogo":"http://api.jisuapi.com/bankcard/upload/80/4.png","bankTel":"95566","bankWebsite":"http://www.boc.cn","mobile":null,"price":10,"email":"1206067690@qq.com","realName":"曹静","reason":"无佣金记录","validType":2,"auditStatus":2,"createTime":"2017-03-05T01:29:30","auditor":"caozong108","auditTime":"2017-03-07T12:59:30","flag":1,"WechatLogoUrl":"http://oj987uf2i.bkt.clouddn.com/memberLogo/android/23c9bd6a-93e9-4add-b29e-d6f1e4c491be","TotalMoney":1017.1,"FreezeMoney":-19.8,"CanUserMoney":31.05,"TotalRemaidMoney":0}
         * APPToken : null
         * LoginCheckToken : null
         * LoginPhone : null
         */

        private WithdrawalsInfoBean WithdrawalsInfo;
        private Object APPToken;
        private Object LoginCheckToken;
        private Object LoginPhone;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public WithdrawalsInfoBean getWithdrawalsInfo() {
            return WithdrawalsInfo;
        }

        public void setWithdrawalsInfo(WithdrawalsInfoBean WithdrawalsInfo) {
            this.WithdrawalsInfo = WithdrawalsInfo;
        }

        public Object getAPPToken() {
            return APPToken;
        }

        public void setAPPToken(Object APPToken) {
            this.APPToken = APPToken;
        }

        public Object getLoginCheckToken() {
            return LoginCheckToken;
        }

        public void setLoginCheckToken(Object LoginCheckToken) {
            this.LoginCheckToken = LoginCheckToken;
        }

        public Object getLoginPhone() {
            return LoginPhone;
        }

        public void setLoginPhone(Object LoginPhone) {
            this.LoginPhone = LoginPhone;
        }

        public static class WithdrawalsInfoBean {
            /**
             * Id : 66
             * memberId : 2172
             * bankCard : 6013826110012617946
             * bankCardName : 长城电子借记卡
             * bankProvince : 江苏
             * bankCity : 淮安
             * bankType : 借记卡
             * bankCardLength : 19
             * bankName : 中国银行
             * bankLogo : http://api.jisuapi.com/bankcard/upload/80/4.png
             * bankTel : 95566
             * bankWebsite : http://www.boc.cn
             * mobile : null
             * price : 10
             * email : 1206067690@qq.com
             * realName : 曹静
             * reason : 无佣金记录
             * validType : 2
             * auditStatus : 2
             * createTime : 2017-03-05T01:29:30
             * auditor : caozong108
             * auditTime : 2017-03-07T12:59:30
             * flag : 1
             * WechatLogoUrl : http://oj987uf2i.bkt.clouddn.com/memberLogo/android/23c9bd6a-93e9-4add-b29e-d6f1e4c491be
             * TotalMoney : 1017.1
             * FreezeMoney : -19.8
             * CanUserMoney : 31.05
             * TotalRemaidMoney : 0
             */

            private int Id;
            private int memberId;
            private String bankCard;
            private String bankCardName;
            private String bankProvince;
            private String bankCity;
            private String bankType;
            private int bankCardLength;
            private String bankName;
            private String bankLogo;
            private String bankTel;
            private String bankWebsite;
            private Object mobile;
            private int price;
            private String email;
            private String realName;
            private String reason;
            private int validType;
            private int auditStatus;
            private String createTime;
            private String auditor;
            private String auditTime;
            private int flag;
            private String WechatLogoUrl;
            private double TotalMoney;
            private double FreezeMoney;
            private double CanUserMoney;
            private double TotalRemaidMoney;

            public static WithdrawalsInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, WithdrawalsInfoBean.class);
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getBankCardName() {
                return bankCardName;
            }

            public void setBankCardName(String bankCardName) {
                this.bankCardName = bankCardName;
            }

            public String getBankProvince() {
                return bankProvince;
            }

            public void setBankProvince(String bankProvince) {
                this.bankProvince = bankProvince;
            }

            public String getBankCity() {
                return bankCity;
            }

            public void setBankCity(String bankCity) {
                this.bankCity = bankCity;
            }

            public String getBankType() {
                return bankType;
            }

            public void setBankType(String bankType) {
                this.bankType = bankType;
            }

            public int getBankCardLength() {
                return bankCardLength;
            }

            public void setBankCardLength(int bankCardLength) {
                this.bankCardLength = bankCardLength;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankLogo() {
                return bankLogo;
            }

            public void setBankLogo(String bankLogo) {
                this.bankLogo = bankLogo;
            }

            public String getBankTel() {
                return bankTel;
            }

            public void setBankTel(String bankTel) {
                this.bankTel = bankTel;
            }

            public String getBankWebsite() {
                return bankWebsite;
            }

            public void setBankWebsite(String bankWebsite) {
                this.bankWebsite = bankWebsite;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getValidType() {
                return validType;
            }

            public void setValidType(int validType) {
                this.validType = validType;
            }

            public int getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus) {
                this.auditStatus = auditStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getAuditor() {
                return auditor;
            }

            public void setAuditor(String auditor) {
                this.auditor = auditor;
            }

            public String getAuditTime() {
                return auditTime;
            }

            public void setAuditTime(String auditTime) {
                this.auditTime = auditTime;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getWechatLogoUrl() {
                return WechatLogoUrl;
            }

            public void setWechatLogoUrl(String WechatLogoUrl) {
                this.WechatLogoUrl = WechatLogoUrl;
            }

            public double getTotalMoney() {
                return TotalMoney;
            }

            public void setTotalMoney(double TotalMoney) {
                this.TotalMoney = TotalMoney;
            }

            public double getFreezeMoney() {
                return FreezeMoney;
            }

            public void setFreezeMoney(double FreezeMoney) {
                this.FreezeMoney = FreezeMoney;
            }

            public double getCanUserMoney() {
                return CanUserMoney;
            }

            public void setCanUserMoney(double CanUserMoney) {
                this.CanUserMoney = CanUserMoney;
            }

            public double getTotalRemaidMoney() {
                return TotalRemaidMoney;
            }

            public void setTotalRemaidMoney(double TotalRemaidMoney) {
                this.TotalRemaidMoney = TotalRemaidMoney;
            }
        }
    }
}
