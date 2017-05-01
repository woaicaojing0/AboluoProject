package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by cj on 2017/5/1.
 */

public class GroupBuyRecordBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : null
     * ListResult : [{"MemberId":2172,"MemberNick":"暴力解决","PayTime":"2017-04-30T21:32:54","MemberLogo":"http://oj987uf2i.bkt.clouddn.com/memberLogo/android/7f999d92-4d3d-4d97-a97d-0d785cf05bfb","IsDealer":1,"MemberSex":0,"Province":"辽宁省","City":"丹东市"},{"MemberId":2172,"MemberNick":"暴力解决","PayTime":"2017-04-30T21:32:54","MemberLogo":"http://oj987uf2i.bkt.clouddn.com/memberLogo/android/7f999d92-4d3d-4d97-a97d-0d785cf05bfb","IsDealer":1,"MemberSex":0,"Province":"辽宁省","City":"丹东市"}]
     * PageIndex : 1
     * PageSize : 10
     * RecordCount : 2
     */

    private boolean IsSuccess;
    private Object Message;
    private Object Result;
    private int PageIndex;
    private int PageSize;
    private int RecordCount;
    private List<GroupBuyRecordItemBean> ListResult;

    public static GroupBuyRecordBean objectFromData(String str) {

        return new Gson().fromJson(str, GroupBuyRecordBean.class);
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

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int PageIndex) {
        this.PageIndex = PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public List<GroupBuyRecordItemBean> getListResult() {
        return ListResult;
    }

    public void setListResult(List<GroupBuyRecordItemBean> ListResult) {
        this.ListResult = ListResult;
    }

    public static class GroupBuyRecordItemBean {
        /**
         * MemberId : 2172
         * MemberNick : 暴力解决
         * PayTime : 2017-04-30T21:32:54
         * MemberLogo : http://oj987uf2i.bkt.clouddn.com/memberLogo/android/7f999d92-4d3d-4d97-a97d-0d785cf05bfb
         * IsDealer : 1
         * MemberSex : 0
         * Province : 辽宁省
         * City : 丹东市
         */

        private int MemberId;
        private String MemberNick;
        private String PayTime;
        private String MemberLogo;
        private int IsDealer;
        private int MemberSex;
        private String Province;
        private String City;

        public static GroupBuyRecordItemBean objectFromData(String str) {

            return new Gson().fromJson(str, GroupBuyRecordItemBean.class);
        }

        public int getMemberId() {
            return MemberId;
        }

        public void setMemberId(int MemberId) {
            this.MemberId = MemberId;
        }

        public String getMemberNick() {
            return MemberNick;
        }

        public void setMemberNick(String MemberNick) {
            this.MemberNick = MemberNick;
        }

        public String getPayTime() {
            return PayTime;
        }

        public void setPayTime(String PayTime) {
            this.PayTime = PayTime;
        }

        public String getMemberLogo() {
            return MemberLogo;
        }

        public void setMemberLogo(String MemberLogo) {
            this.MemberLogo = MemberLogo;
        }

        public int getIsDealer() {
            return IsDealer;
        }

        public void setIsDealer(int IsDealer) {
            this.IsDealer = IsDealer;
        }

        public int getMemberSex() {
            return MemberSex;
        }

        public void setMemberSex(int MemberSex) {
            this.MemberSex = MemberSex;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }
    }
}
