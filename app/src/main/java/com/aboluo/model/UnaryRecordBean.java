package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by cj on 2017/5/3.
 */

public class UnaryRecordBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : null
     * ListResult : [{"MemberId":2174,"WechatNickName":"✨浩丿冬祭","WechatLogoUrl":"http://oj987uf2i.bkt.clouddn.com/memberLogo/ios/1489020572901610","JoinTime":"2017-05-01T21:03:47","IsWinLottery":0,"IsDealer":1,"TotalBuyCount":1}]
     * PageIndex : 1
     * PageSize : 10
     * RecordCount : 1
     */

    private boolean IsSuccess;
    private Object Message;
    private Object Result;
    private int PageIndex;
    private int PageSize;
    private int RecordCount;
    private List<ListResultBean> ListResult;

    public static UnaryRecordBean objectFromData(String str) {

        return new Gson().fromJson(str, UnaryRecordBean.class);
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

    public List<ListResultBean> getListResult() {
        return ListResult;
    }

    public void setListResult(List<ListResultBean> ListResult) {
        this.ListResult = ListResult;
    }

    public static class ListResultBean {
        /**
         * MemberId : 2174
         * WechatNickName : ✨浩丿冬祭
         * WechatLogoUrl : http://oj987uf2i.bkt.clouddn.com/memberLogo/ios/1489020572901610
         * JoinTime : 2017-05-01T21:03:47
         * IsWinLottery : 0
         * IsDealer : 1
         * TotalBuyCount : 1
         */

        private int MemberId;
        private String WechatNickName;
        private String WechatLogoUrl;
        private String JoinTime;
        private int IsWinLottery;
        private int IsDealer;
        private int TotalBuyCount;

        public static ListResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ListResultBean.class);
        }

        public int getMemberId() {
            return MemberId;
        }

        public void setMemberId(int MemberId) {
            this.MemberId = MemberId;
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

        public String getJoinTime() {
            return JoinTime;
        }

        public void setJoinTime(String JoinTime) {
            this.JoinTime = JoinTime;
        }

        public int getIsWinLottery() {
            return IsWinLottery;
        }

        public void setIsWinLottery(int IsWinLottery) {
            this.IsWinLottery = IsWinLottery;
        }

        public int getIsDealer() {
            return IsDealer;
        }

        public void setIsDealer(int IsDealer) {
            this.IsDealer = IsDealer;
        }

        public int getTotalBuyCount() {
            return TotalBuyCount;
        }

        public void setTotalBuyCount(int TotalBuyCount) {
            this.TotalBuyCount = TotalBuyCount;
        }
    }
}
