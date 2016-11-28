package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/22.
 */

public class SearchOrderBean {


    /**
     * IsSuccess : true
     * Message : null
     * Result : [{"orderId":599,"orderCode":"201611282312570002","memberId":2,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":800,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-28T23:12:57","OrderItemList":[{"orderItemId":710,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":56,"goodsStandard":"XXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1,"Price":300,"orderId":599},{"orderItemId":709,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":66,"goodsColor":"黄色1","goodsStandardId":75,"goodsStandard":"xxxxl","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1,"Price":300,"orderId":599}]},{"orderId":598,"orderCode":"201611282312330001","memberId":2,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":200,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-28T23:12:33","OrderItemList":[{"orderItemId":708,"goodsId":3058,"goodsName":"研发测试","goodsSub":"研发测试","goodsColorId":0,"goodsColor":"","goodsStandardId":45,"goodsStandard":"789","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"e7eec70dca2047dbba1d9ccf00d05643.jpg","goodsQuantity":1,"Price":300,"orderId":598}]}]
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private Object ListResult;
    /**
     * orderId : 599
     * orderCode : 201611282312570002
     * memberId : 2
     * orderStatus : 10
     * payTypeId : 0
     * tuihuo :
     * payTime : 1900-01-01T00:00:00
     * totalPrice : 800
     * orderRemarks : null
     * shouhuoTime : 1900-01-01T00:00:00
     * addTime : 2016-11-28T23:12:57
     * OrderItemList : [{"orderItemId":710,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":56,"goodsStandard":"XXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1,"Price":300,"orderId":599},{"orderItemId":709,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":66,"goodsColor":"黄色1","goodsStandardId":75,"goodsStandard":"xxxxl","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1,"Price":300,"orderId":599}]
     */

    private List<ResultBean> Result;

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

    public Object getListResult() {
        return ListResult;
    }

    public void setListResult(Object ListResult) {
        this.ListResult = ListResult;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        private int orderId;
        private String orderCode;
        private int memberId;
        private int orderStatus;
        private int payTypeId;
        private String tuihuo;
        private String payTime;
        private Double totalPrice;
        private Object orderRemarks;
        private String shouhuoTime;
        private String addTime;
        /**
         * orderItemId : 710
         * goodsId : 3067
         * goodsName : 20161115测试
         * goodsSub : 20161115测试
         * goodsColorId : 52
         * goodsColor : 黑色1
         * goodsStandardId : 56
         * goodsStandard : XXL
         * goodsPrice : 300
         * yunfei : 0
         * goodsLogoUrl : null
         * goodsQuantity : 1
         * Price : 300
         * orderId : 599
         */

        private List<OrderItemListBean> OrderItemList;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getPayTypeId() {
            return payTypeId;
        }

        public void setPayTypeId(int payTypeId) {
            this.payTypeId = payTypeId;
        }

        public String getTuihuo() {
            return tuihuo;
        }

        public void setTuihuo(String tuihuo) {
            this.tuihuo = tuihuo;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Object getOrderRemarks() {
            return orderRemarks;
        }

        public void setOrderRemarks(Object orderRemarks) {
            this.orderRemarks = orderRemarks;
        }

        public String getShouhuoTime() {
            return shouhuoTime;
        }

        public void setShouhuoTime(String shouhuoTime) {
            this.shouhuoTime = shouhuoTime;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public List<OrderItemListBean> getOrderItemList() {
            return OrderItemList;
        }

        public void setOrderItemList(List<OrderItemListBean> OrderItemList) {
            this.OrderItemList = OrderItemList;
        }

        public static class OrderItemListBean {
            private int orderItemId;
            private int goodsId;
            private String goodsName;
            private String goodsSub;
            private int goodsColorId;
            private String goodsColor;
            private int goodsStandardId;
            private String goodsStandard;
            private Double goodsPrice;
            private int yunfei;
            private Object goodsLogoUrl;
            private int goodsQuantity;
            private Double Price;
            private int orderId;

            public int getOrderItemId() {
                return orderItemId;
            }

            public void setOrderItemId(int orderItemId) {
                this.orderItemId = orderItemId;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
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

            public int getGoodsColorId() {
                return goodsColorId;
            }

            public void setGoodsColorId(int goodsColorId) {
                this.goodsColorId = goodsColorId;
            }

            public String getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(String goodsColor) {
                this.goodsColor = goodsColor;
            }

            public int getGoodsStandardId() {
                return goodsStandardId;
            }

            public void setGoodsStandardId(int goodsStandardId) {
                this.goodsStandardId = goodsStandardId;
            }

            public String getGoodsStandard() {
                return goodsStandard;
            }

            public void setGoodsStandard(String goodsStandard) {
                this.goodsStandard = goodsStandard;
            }

            public Double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(Double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getYunfei() {
                return yunfei;
            }

            public void setYunfei(int yunfei) {
                this.yunfei = yunfei;
            }

            public Object getGoodsLogoUrl() {
                return goodsLogoUrl;
            }

            public void setGoodsLogoUrl(Object goodsLogoUrl) {
                this.goodsLogoUrl = goodsLogoUrl;
            }

            public int getGoodsQuantity() {
                return goodsQuantity;
            }

            public void setGoodsQuantity(int goodsQuantity) {
                this.goodsQuantity = goodsQuantity;
            }

            public Double getPrice() {
                return Price;
            }

            public void setPrice(Double Price) {
                this.Price = Price;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }
        }
    }
}
