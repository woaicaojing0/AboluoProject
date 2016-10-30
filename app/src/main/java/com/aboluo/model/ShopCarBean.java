package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/10/24.
 */

public class ShopCarBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsShoppingCartList":[{"Id":10,"goodsId":1154,"goodsColor":"黑色","goodsStandard":"M","goodsCount":1,"memberId":6,"shopId":1,"createTime":"2016-10-24T20: 36: 12.510901","flag":1,"goodsName":"新款青春潮流休闲西服男士修身爆款外套","goodsSub":"","yunfei":0,"goodsLogo":"/GoodsLogo//uploads/goods/574d27626cfa7.jpg;","goodsPrice":150,"hyPrice":128},{"Id":4,"goodsId":1103,"goodsColor":"red","goodsStandard":"12","goodsCount":5,"memberId":6,"shopId":1,"createTime":"2016-10-24T00: 04: 38.97938","flag":1,"goodsName":"新款男式休闲裤薄款韩版修身中腰直筒青年男士免烫长裤（浅色系列）","goodsSub":"","yunfei":0,"goodsLogo":"/GoodsLogo//uploads/goods/574c2f5a9dfc2.jpg;","goodsPrice":132,"hyPrice":112}]}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private ResultBean Result;
    private Object ListResult;

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
         * Id : 10
         * goodsId : 1154
         * goodsColor : 黑色
         * goodsStandard : M
         * goodsCount : 1
         * memberId : 6
         * shopId : 1
         * createTime : 2016-10-24T20: 36: 12.510901
         * flag : 1
         * goodsName : 新款青春潮流休闲西服男士修身爆款外套
         * goodsSub :
         * yunfei : 0
         * goodsLogo : /GoodsLogo//uploads/goods/574d27626cfa7.jpg;
         * goodsPrice : 150
         * hyPrice : 128
         */

        private List<GoodsShoppingCartListBean> GoodsShoppingCartList;

        public List<GoodsShoppingCartListBean> getGoodsShoppingCartList() {
            return GoodsShoppingCartList;
        }

        public void setGoodsShoppingCartList(List<GoodsShoppingCartListBean> GoodsShoppingCartList) {
            this.GoodsShoppingCartList = GoodsShoppingCartList;
        }

        public static class GoodsShoppingCartListBean {
            private int Id;
            private int goodsId;
            private String goodsColor;
            private String goodsStandard;
            private int goodsCount;
            private int memberId;
            private int shopId;
            private String createTime;
            private int flag;
            private String goodsName;
            private String goodsSub;
            private int yunfei;
            private String goodsLogo;
            private double goodsPrice;
            private double hyPrice;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(String goodsColor) {
                this.goodsColor = goodsColor;
            }

            public String getGoodsStandard() {
                return goodsStandard;
            }

            public void setGoodsStandard(String goodsStandard) {
                this.goodsStandard = goodsStandard;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsSub() {
                return goodsSub;
            }

            public void setGoodsSub(String goodsSub) {
                this.goodsSub = goodsSub;
            }

            public int getYunfei() {
                return yunfei;
            }

            public void setYunfei(int yunfei) {
                this.yunfei = yunfei;
            }

            public String getGoodsLogo() {
                return goodsLogo;
            }

            public void setGoodsLogo(String goodsLogo) {
                this.goodsLogo = goodsLogo;
            }

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public double getHyPrice() {
                return hyPrice;
            }

            public void setHyPrice(double hyPrice) {
                this.hyPrice = hyPrice;
            }
        }
    }
}
