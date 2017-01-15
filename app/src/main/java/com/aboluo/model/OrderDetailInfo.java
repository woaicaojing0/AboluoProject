package com.aboluo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2016/12/8.
 */

public class OrderDetailInfo {

    /**
     * IsSuccess : true
     * Message : null
     * Result : [{"orderId":621,"orderCode":"201612111402240008","memberId":1968,"addressId":68,"expressId":12,"orderStatus":40,"payTypeId":0,"tuihuo":"","payTime":"1900-01-01T00:00:00","totalPrice":250,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-12-11T14:02:25","Receiver":"qw","Mobile":"18360733212","Address":"wewqfffvvvvvggtgggggggt","OrderItemList":[{"orderItemId":735,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":56,"goodsStandard":"XXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1,"Price":300,"RefundStatus":0,"EvaluationStatus":18,"orderId":621}],"OrderExpress":{"Id":12,"orderId":621,"expressName":"","expressType":"","expressNumber":"","expressPrice":0,"expressTime":"2016-12-11T14:02:25"},"LocalInfo":[]}]
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private Object ListResult;
    /**
     * orderId : 621
     * orderCode : 201612111402240008
     * memberId : 1968
     * addressId : 68
     * expressId : 12
     * orderStatus : 40
     * payTypeId : 0
     * tuihuo :
     * payTime : 1900-01-01T00:00:00
     * totalPrice : 250
     * orderRemarks : null
     * shouhuoTime : 1900-01-01T00:00:00
     * addTime : 2016-12-11T14:02:25
     * Receiver : qw
     * Mobile : 18360733212
     * Address : wewqfffvvvvvggtgggggggt
     * integralPrice: 2,
     * integralCount: 200,
     * couponPrice: 0,
     * couponId: 0,
     * expressPrice: 0,
     * orderType: 5,
     * OrderItemList : [{"orderItemId":735,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":56,"goodsStandard":"XXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":null,"goodsQuantity":1,"Price":300,"RefundStatus":0,"EvaluationStatus":18,"orderId":621}]
     * OrderExpress : {"Id":12,"orderId":621,"expressName":"","expressType":"","expressNumber":"","expressPrice":0,"expressTime":"2016-12-11T14:02:25"}
     * LocalInfo : []
     */

    private List<ResultBean> Result;

    public static OrderDetailInfo objectFromData(String str) {

        return new Gson().fromJson(str, OrderDetailInfo.class);
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
        private double integralPrice;
        private int integralCount;
        private  double couponPrice;
        private  double expressPrice;
        private  int orderType;
        /**
         * Id : 12
         * orderId : 621
         * expressName :
         * expressType :
         * expressNumber :
         * expressPrice : 0
         * expressTime : 2016-12-11T14:02:25
         */

        private OrderExpressBean OrderExpress;
        /**
         * orderItemId : 735
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
         * RefundStatus : 0
         * EvaluationStatus : 18
         * orderId : 621
         */

        private List<OrderItemListBean> OrderItemList;
        private List<ListBean> LocalInfo;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

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

        public double getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(double integralPrice) {
            this.integralPrice = integralPrice;
        }

        public int getIntegralCount() {
            return integralCount;
        }

        public void setIntegralCount(int integralCount) {
            this.integralCount = integralCount;
        }

        public double getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(double couponPrice) {
            this.couponPrice = couponPrice;
        }

        public double getExpressPrice() {
            return expressPrice;
        }

        public void setExpressPrice(double expressPrice) {
            this.expressPrice = expressPrice;
        }

        public OrderExpressBean getOrderExpress() {
            return OrderExpress;
        }

        public void setOrderExpress(OrderExpressBean OrderExpress) {
            this.OrderExpress = OrderExpress;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public List<OrderItemListBean> getOrderItemList() {
            return OrderItemList;
        }

        public void setOrderItemList(List<OrderItemListBean> OrderItemList) {
            this.OrderItemList = OrderItemList;
        }

        public List<ListBean> getLocalInfo() {
            return LocalInfo;
        }

        public void setLocalInfo(List<ListBean> LocalInfo) {
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

            public static OrderExpressBean objectFromData(String str) {

                return new Gson().fromJson(str, OrderExpressBean.class);
            }

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
        public static class ListBean {
            private String time;
            private String status;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
        public static class OrderItemListBean implements Parcelable{
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
            private int RefundStatus;
            private int EvaluationStatus;
            private int orderId;

            protected OrderItemListBean(Parcel in) {
                orderItemId = in.readInt();
                goodsId = in.readInt();
                goodsName = in.readString();
                goodsSub = in.readString();
                goodsColorId = in.readInt();
                goodsColor = in.readString();
                goodsStandardId = in.readInt();
                goodsStandard = in.readString();
                goodsPrice = in.readDouble();
                yunfei = in.readDouble();
                goodsLogoUrl = in.readString();
                goodsQuantity = in.readInt();
                Price = in.readDouble();
                RefundStatus = in.readInt();
                EvaluationStatus = in.readInt();
                orderId = in.readInt();
            }

            public static final Creator<OrderItemListBean> CREATOR = new Creator<OrderItemListBean>() {
                @Override
                public OrderItemListBean createFromParcel(Parcel in) {
                    return new OrderItemListBean(in);
                }

                @Override
                public OrderItemListBean[] newArray(int size) {
                    return new OrderItemListBean[size];
                }
            };

            public static OrderItemListBean objectFromData(String str) {

                return new Gson().fromJson(str, OrderItemListBean.class);
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

            public int getRefundStatus() {
                return RefundStatus;
            }

            public void setRefundStatus(int RefundStatus) {
                this.RefundStatus = RefundStatus;
            }

            public int getEvaluationStatus() {
                return EvaluationStatus;
            }

            public void setEvaluationStatus(int EvaluationStatus) {
                this.EvaluationStatus = EvaluationStatus;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(orderItemId);
                dest.writeInt(goodsId);
                dest.writeString(goodsName);
                dest.writeString(goodsSub);
                dest.writeInt(goodsColorId);
                dest.writeString(goodsColor);
                dest.writeInt(goodsStandardId);
                dest.writeString(goodsStandard);
                dest.writeDouble(goodsPrice);
                dest.writeDouble(yunfei);
                dest.writeString(goodsLogoUrl);
                dest.writeInt(goodsQuantity);
                dest.writeDouble(Price);
                dest.writeInt(RefundStatus);
                dest.writeInt(EvaluationStatus);
                dest.writeInt(orderId);
            }
        }
    }
}
