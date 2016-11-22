package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/22.
 */

public class SearchOrderBean {


    /**
     * IsSuccess : true
     * Message : null
     * Result : {"OrderList":[{"orderId":596,"orderCode":"201611201944270003","memberId":1,"addressId":35,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":200,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T19:44:27","Receiver":"曹晶","Mobile":"18360733212","Address":"许路新村","Price":300,"orderItemId":706,"goodsId":3058,"goodsName":"研发测试","goodsSub":"研发测试","goodsColorId":0,"goodsColor":"无","goodsStandardId":45,"goodsStandard":"789","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"e7eec70dca2047dbba1d9ccf00d05643.jpg","goodsQuantity":1},{"orderId":595,"orderCode":"201611201929120002","memberId":1,"addressId":34,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":550,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T19:29:12","Receiver":"曹晶","Mobile":"18360733212","Address":"许路新村","Price":300,"orderItemId":705,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":66,"goodsColor":"黄色","goodsStandardId":75,"goodsStandard":"xxxxl","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1},{"orderId":594,"orderCode":"201611201927560001","memberId":1,"addressId":33,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":800,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T19:27:56","Receiver":"曹晶","Mobile":"18360733212","Address":"许路新村","Price":300,"orderItemId":704,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":2},{"orderId":589,"orderCode":"201611201806450009","memberId":1,"addressId":27,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":800,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T18:06:45","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":701,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":2},{"orderId":588,"orderCode":"201611201757270008","memberId":1,"addressId":26,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":800,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T17:57:28","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":700,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":2},{"orderId":587,"orderCode":"201611201733370007","memberId":1,"addressId":25,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":400,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T17:33:38","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":699,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1},{"orderId":586,"orderCode":"201611201730450006","memberId":1,"addressId":24,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":600,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T17:30:45","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":698,"goodsId":3058,"goodsName":"研发测试","goodsSub":"研发测试","goodsColorId":0,"goodsColor":"无","goodsStandardId":45,"goodsStandard":"789","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"e7eec70dca2047dbba1d9ccf00d05643.jpg","goodsQuantity":1},{"orderId":586,"orderCode":"201611201730450006","memberId":1,"addressId":24,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":600,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T17:30:45","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":697,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1},{"orderId":585,"orderCode":"201611201729420005","memberId":1,"addressId":23,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":750,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T17:29:42","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":696,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":66,"goodsColor":"黄色","goodsStandardId":75,"goodsStandard":"xxxxl","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1},{"orderId":585,"orderCode":"201611201729420005","memberId":1,"addressId":23,"expressId":0,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":750,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-11-20T17:29:42","Receiver":"曹晶","Mobile":"18360733212","Address":"淮阴工学院","Price":300,"orderItemId":695,"goodsId":3058,"goodsName":"研发测试","goodsSub":"研发测试","goodsColorId":0,"goodsColor":"无","goodsStandardId":45,"goodsStandard":"789","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"e7eec70dca2047dbba1d9ccf00d05643.jpg","goodsQuantity":1}]}
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
         * orderId : 596
         * orderCode : 201611201944270003
         * memberId : 1
         * addressId : 35
         * expressId : 0
         * orderStatus : 10
         * payTypeId : 0
         * tuihuo :
         * payTime : 1900-01-01T00:00:00
         * totalPrice : 200
         * orderRemarks : null
         * shouhuoTime : 1900-01-01T00:00:00
         * addTime : 2016-11-20T19:44:27
         * Receiver : 曹晶
         * Mobile : 18360733212
         * Address : 许路新村
         * Price : 300
         * orderItemId : 706
         * goodsId : 3058
         * goodsName : 研发测试
         * goodsSub : 研发测试
         * goodsColorId : 0
         * goodsColor : 无
         * goodsStandardId : 45
         * goodsStandard : 789
         * goodsPrice : 300
         * yunfei : 0
         * goodsLogoUrl : e7eec70dca2047dbba1d9ccf00d05643.jpg
         * goodsQuantity : 1
         */

        private List<OrderListBean> OrderList;

        public List<OrderListBean> getOrderList() {
            return OrderList;
        }

        public void setOrderList(List<OrderListBean> OrderList) {
            this.OrderList = OrderList;
        }

        public static class OrderListBean {
            private int orderId;
            private String orderCode;
            private int memberId;
            private int addressId;
            private int expressId;
            private int orderStatus;
            private int payTypeId;
            private String tuihuo;
            private String payTime;
            private int totalPrice;
            private Object orderRemarks;
            private String shouhuoTime;
            private String addTime;
            private String Receiver;
            private String Mobile;
            private String Address;
            private int Price;
            private int orderItemId;
            private int goodsId;
            private String goodsName;
            private String goodsSub;
            private int goodsColorId;
            private String goodsColor;
            private int goodsStandardId;
            private String goodsStandard;
            private int goodsPrice;
            private int yunfei;
            private String goodsLogoUrl;
            private int goodsQuantity;

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

            public int getAddressId() {
                return addressId;
            }

            public void setAddressId(int addressId) {
                this.addressId = addressId;
            }

            public int getExpressId() {
                return expressId;
            }

            public void setExpressId(int expressId) {
                this.expressId = expressId;
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

            public int getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(int totalPrice) {
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

            public String getReceiver() {
                return Receiver;
            }

            public void setReceiver(String Receiver) {
                this.Receiver = Receiver;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public int getPrice() {
                return Price;
            }

            public void setPrice(int Price) {
                this.Price = Price;
            }

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

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getYunfei() {
                return yunfei;
            }

            public void setYunfei(int yunfei) {
                this.yunfei = yunfei;
            }

            public String getGoodsLogoUrl() {
                return goodsLogoUrl;
            }

            public void setGoodsLogoUrl(String goodsLogoUrl) {
                this.goodsLogoUrl = goodsLogoUrl;
            }

            public int getGoodsQuantity() {
                return goodsQuantity;
            }

            public void setGoodsQuantity(int goodsQuantity) {
                this.goodsQuantity = goodsQuantity;
            }
        }
    }
}
