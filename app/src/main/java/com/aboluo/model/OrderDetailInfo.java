package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/12/8.
 */

public class OrderDetailInfo {

    /**
     * IsSuccess : true
     * Message : null
     * Result : [{"orderId":613,"orderCode":"201612082006370002","memberId":1968,"addressId":0,"expressId":4,"orderStatus":10,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":400,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-12-08T20:06:37","Receiver":"123","Mobile":"123","Address":"123","OrderItemList":[{"orderItemId":725,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"123","goodsQuantity":1,"Price":300,"orderId":613}],"OrderExpress":{"Id":4,"orderId":613,"expressName":"","expressType":"","expressNumber":"","expressPrice":0,"expressTime":"2016-12-08T20:06:37"},"LocalInfo":[]}]
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private Object ListResult;
    /**
     * orderId : 613
     * orderCode : 201612082006370002
     * memberId : 1968
     * addressId : 0
     * expressId : 4
     * orderStatus : 10
     * payTypeId : 0
     * tuihuo :
     * payTime : 1900-01-01T00:00:00
     * totalPrice : 400.0
     * orderRemarks : null
     * shouhuoTime : 1900-01-01T00:00:00
     * addTime : 2016-12-08T20:06:37
     * Receiver : 123
     * Mobile : 123
     * Address : 123
     * OrderItemList : [{"orderItemId":725,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":57,"goodsStandard":"XXXXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"123","goodsQuantity":1,"Price":300,"orderId":613}]
     * OrderExpress : {"Id":4,"orderId":613,"expressName":"","expressType":"","expressNumber":"","expressPrice":0,"expressTime":"2016-12-08T20:06:37"}
     * LocalInfo : []
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
        private int addressId;
        private int expressId;
        private int orderStatus;
        private int payTypeId;
        private String tuihuo;
        private String payTime;
        private double totalPrice;
        private Object orderRemarks;
        private String shouhuoTime;
        private String addTime;
        private String Receiver;
        private String Mobile;
        private String Address;
        /**
         * Id : 4
         * orderId : 613
         * expressName :
         * expressType :
         * expressNumber :
         * expressPrice : 0.0
         * expressTime : 2016-12-08T20:06:37
         */

        private OrderExpressBean OrderExpress;
        /**
         * orderItemId : 725
         * goodsId : 3067
         * goodsName : 20161115测试
         * goodsSub : 20161115测试
         * goodsColorId : 52
         * goodsColor : 黑色1
         * goodsStandardId : 57
         * goodsStandard : XXXXL
         * goodsPrice : 300.0
         * yunfei : 0.0
         * goodsLogoUrl : 123
         * goodsQuantity : 1
         * Price : 300.0
         * orderId : 613
         */

        private List<OrderItemListBean> OrderItemList;
        private List<?> LocalInfo;

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

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
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

        public OrderExpressBean getOrderExpress() {
            return OrderExpress;
        }

        public void setOrderExpress(OrderExpressBean OrderExpress) {
            this.OrderExpress = OrderExpress;
        }

        public List<OrderItemListBean> getOrderItemList() {
            return OrderItemList;
        }

        public void setOrderItemList(List<OrderItemListBean> OrderItemList) {
            this.OrderItemList = OrderItemList;
        }

        public List<?> getLocalInfo() {
            return LocalInfo;
        }

        public void setLocalInfo(List<?> LocalInfo) {
            this.LocalInfo = LocalInfo;
        }

        public static class OrderExpressBean {
            private int Id;
            private int orderId;
            private String expressName;
            private String expressType;
            private String expressNumber;
            private double expressPrice;
            private String expressTime;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public String getExpressName() {
                return expressName;
            }

            public void setExpressName(String expressName) {
                this.expressName = expressName;
            }

            public String getExpressType() {
                return expressType;
            }

            public void setExpressType(String expressType) {
                this.expressType = expressType;
            }

            public String getExpressNumber() {
                return expressNumber;
            }

            public void setExpressNumber(String expressNumber) {
                this.expressNumber = expressNumber;
            }

            public double getExpressPrice() {
                return expressPrice;
            }

            public void setExpressPrice(double expressPrice) {
                this.expressPrice = expressPrice;
            }

            public String getExpressTime() {
                return expressTime;
            }

            public void setExpressTime(String expressTime) {
                this.expressTime = expressTime;
            }
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
            private double goodsPrice;
            private double yunfei;
            private String goodsLogoUrl;
            private int goodsQuantity;
            private double Price;
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

            public double getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(double goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public double getYunfei() {
                return yunfei;
            }

            public void setYunfei(double yunfei) {
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

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
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
