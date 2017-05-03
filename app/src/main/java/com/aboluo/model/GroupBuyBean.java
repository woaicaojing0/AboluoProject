package com.aboluo.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cj on 2017/4/25.
 */

public class GroupBuyBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : null
     * ListResult : [{"TId":2,"GoodsId":3053,"GoodsName":"1012","GoodsTitle":"","GoodsLogo":"http: //productimg.aboluomall.com/GoodsLogo/90d4fc8e6283413faed73182447c7071.jpg","GoodsStanderId":0,"GoodsStanderName":"默认","GoodsColorId":0,"GoodsColorName":"默认","GoodsColorStanderId":0,"NeedPerson":10,"BuyedPerson":0,"TeamBuyRemarks":"","TeamNumber":"2017425722882","TeamPrice":100}]
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

    public static GroupBuyBean objectFromData(String str) {

        return new Gson().fromJson(str, GroupBuyBean.class);
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

    public static class ListResultBean implements Serializable{
        /**
         * TId : 2
         * GoodsId : 3053
         * GoodsName : 1012
         * GoodsTitle :
         * GoodsLogo : http: //productimg.aboluomall.com/GoodsLogo/90d4fc8e6283413faed73182447c7071.jpg
         * GoodsStanderId : 0
         * GoodsStanderName : 默认
         * GoodsColorId : 0
         * GoodsColorName : 默认
         * GoodsColorStanderId : 0
         * NeedPerson : 10
         * BuyedPerson : 0
         * TeamBuyRemarks :
         * TeamNumber : 2017425722882
         * TeamPrice : 100
         */
        private String OpenTime;
        private int TId;
        private int GoodsId;
        private String GoodsName;
        private String GoodsTitle;
        private String GoodsLogo;
        private int GoodsStanderId;
        private String GoodsStanderName;
        private int GoodsColorId;
        private String GoodsColorName;
        private int GoodsColorStanderId;
        private int NeedPerson;
        private int BuyPerson;
        private String Remarks;
        private String TeamNumber;
        private double TeamPrice;
        private String  GoodsPicture;
        public static ListResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ListResultBean.class);
        }

        public String getOpenTime() {
            return OpenTime;
        }

        public void setOpenTime(String openTime) {
            OpenTime = openTime;
        }

        public int getTId() {
            return TId;
        }

        public void setTId(int TId) {
            this.TId = TId;
        }

        public int getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(int GoodsId) {
            this.GoodsId = GoodsId;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getGoodsTitle() {
            return GoodsTitle;
        }

        public void setGoodsTitle(String GoodsTitle) {
            this.GoodsTitle = GoodsTitle;
        }

        public String getGoodsLogo() {
            return GoodsLogo;
        }

        public void setGoodsLogo(String GoodsLogo) {
            this.GoodsLogo = GoodsLogo;
        }

        public int getGoodsStanderId() {
            return GoodsStanderId;
        }

        public void setGoodsStanderId(int GoodsStanderId) {
            this.GoodsStanderId = GoodsStanderId;
        }

        public String getGoodsStanderName() {
            return GoodsStanderName;
        }

        public void setGoodsStanderName(String GoodsStanderName) {
            this.GoodsStanderName = GoodsStanderName;
        }

        public int getGoodsColorId() {
            return GoodsColorId;
        }

        public void setGoodsColorId(int GoodsColorId) {
            this.GoodsColorId = GoodsColorId;
        }

        public String getGoodsColorName() {
            return GoodsColorName;
        }

        public void setGoodsColorName(String GoodsColorName) {
            this.GoodsColorName = GoodsColorName;
        }

        public int getGoodsColorStanderId() {
            return GoodsColorStanderId;
        }

        public void setGoodsColorStanderId(int GoodsColorStanderId) {
            this.GoodsColorStanderId = GoodsColorStanderId;
        }

        public int getNeedPerson() {
            return NeedPerson;
        }

        public void setNeedPerson(int NeedPerson) {
            this.NeedPerson = NeedPerson;
        }

        public int getBuyPerson() {
            return BuyPerson;
        }

        public void setBuyPerson(int BuyedPerson) {
            this.BuyPerson = BuyedPerson;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String remarks) {
            Remarks = remarks;
        }

        public String getTeamNumber() {
            return TeamNumber;
        }

        public void setTeamNumber(String TeamNumber) {
            this.TeamNumber = TeamNumber;
        }

        public double getTeamPrice() {
            return TeamPrice;
        }

        public void setTeamPrice(double TeamPrice) {
            this.TeamPrice = TeamPrice;
        }

        public String getGoodsPicture() {
            return GoodsPicture;
        }

        public void setGoodsPicture(String goodsPicture) {
            GoodsPicture = goodsPicture;
        }
    }
}
