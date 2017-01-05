package com.aboluo.model;

import java.util.List;

/**
 * Created by cj34920 on 2017/1/5.
 */

public class CouponsBean  {

    /**
     * MemberCouponList : [{"CouponId":1,"CouponMoney":30,"LeastMoeny":300,"Title":"30元优惠券","Remark":"1.满300元可用n2.不可叠加","StartTime":"2016-11-17","EndTime":"2017-11-30"},{"CouponId":2,"CouponMoney":20,"LeastMoeny":200,"Title":"20元优惠券","Remark":"1.消费满20元可用n2.不可与其他优惠券叠加","StartTime":"2016-11-16","EndTime":"2017-11-30"}]
     * IsSuccess : true
     * Message : 数据获取成功！
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    /**
     * CouponId : 1
     * CouponMoney : 30
     * LeastMoeny : 300
     * Title : 30元优惠券
     * Remark : 1.满300元可用n2.不可叠加
     * StartTime : 2016-11-17
     * EndTime : 2017-11-30
     */

    private List<MemberCouponListBean> MemberCouponList;

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

    public List<MemberCouponListBean> getMemberCouponList() {
        return MemberCouponList;
    }

    public void setMemberCouponList(List<MemberCouponListBean> MemberCouponList) {
        this.MemberCouponList = MemberCouponList;
    }

    public static class MemberCouponListBean {
        private int CouponId;
        private Double CouponMoney;
        private Double LeastMoeny;
        private String Title;
        private String Remark;
        private String StartTime;
        private String EndTime;

        public int getCouponId() {
            return CouponId;
        }

        public void setCouponId(int CouponId) {
            this.CouponId = CouponId;
        }

        public Double getCouponMoney() {
            return CouponMoney;
        }

        public void setCouponMoney(Double CouponMoney) {
            this.CouponMoney = CouponMoney;
        }

        public Double getLeastMoeny() {
            return LeastMoeny;
        }

        public void setLeastMoeny(Double LeastMoeny) {
            this.LeastMoeny = LeastMoeny;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }
    }
}
