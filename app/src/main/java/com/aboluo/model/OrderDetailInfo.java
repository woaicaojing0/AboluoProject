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
     * Result : [{"orderId":608,"orderCode":"201612030008300002","memberId":1975,"addressId":51,"expressId":0,"orderStatus":40,"payTypeId":1,"tuihuo":"","payTime":"2016-12-07T22:47:56","totalPrice":0.01,"orderRemarks":null,"shouhuoTime":"1900-01-01T00:00:00","addTime":"2016-12-03T00:08:30","Receiver":"测试","Mobile":"15906229083","Address":"江苏省苏州市吴中区郭巷街道园区","integralPrice":0,"integralCount":0,"couponPrice":0,"couponId":0,"expressPrice":0,"deductionType":0,"orderType":0,"OrderItemList":[{"orderItemId":720,"orderId":608,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":56,"goodsStandard":"XXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"../aboluo/GoodsLogo/","goodsQuantity":1,"Price":300,"RefundStatus":-1,"EvaluationStatus":0}],"OrderExpressList":[{"Id":23,"orderId":608,"expressName":"安能快递","expressType":"YUNDA","expressNumber":"1700218956697","expressPrice":0,"expressTime":"2017-02-13T21:12:53","flag":1,"orderStatus":40,"LocalInfo":[{"time":"2016-12-22 21:42:50","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部快件已被 已签收 签收"},{"time":"2016-12-22 14:14:42","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部进行派件扫描；派送业务员：郁萍；联系电话：15312192915"},{"time":"2016-12-22 12:40:25","status":"江苏苏州吴中区郭巷公司进行快件扫描，将发往：江苏苏州吴中区郭巷公司纳米园便民寄存分部"},{"time":"2016-12-22 11:05:22","status":"江苏苏州分拨中心从站点发出，本次转运目的地：江苏苏州吴中区郭巷公司"},{"time":"2016-12-22 09:15:36","status":"江苏苏州分拨中心在分拨中心进行卸车扫描"},{"time":"2016-12-21 00:24:48","status":"北京分拨中心进行装车扫描，即将发往：江苏苏州分拨中心"},{"time":"2016-12-19 23:23:22","status":"北京分拨中心在分拨中心进行称重扫描"},{"time":"2016-12-19 21:02:03","status":"北京房山区燕化公司进行揽件扫描"},{"time":"2016-12-19 20:00:48","status":"北京房山区燕化公司进行下级地点扫描，将发往：江苏苏州网点包"},{"time":"2016-12-19 19:45:53","status":"北京房山区燕化公司进行揽件扫描"}]},{"Id":22,"orderId":608,"expressName":"华企","expressType":"YUNDA","expressNumber":"1700218956697","expressPrice":0,"expressTime":"2017-02-13T21:23:46","flag":1,"orderStatus":40,"LocalInfo":[{"time":"2016-12-22 21:42:50","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部快件已被 已签收 签收"},{"time":"2016-12-22 14:14:42","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部进行派件扫描；派送业务员：郁萍；联系电话：15312192915"},{"time":"2016-12-22 12:40:25","status":"江苏苏州吴中区郭巷公司进行快件扫描，将发往：江苏苏州吴中区郭巷公司纳米园便民寄存分部"},{"time":"2016-12-22 11:05:22","status":"江苏苏州分拨中心从站点发出，本次转运目的地：江苏苏州吴中区郭巷公司"},{"time":"2016-12-22 09:15:36","status":"江苏苏州分拨中心在分拨中心进行卸车扫描"},{"time":"2016-12-21 00:24:48","status":"北京分拨中心进行装车扫描，即将发往：江苏苏州分拨中心"},{"time":"2016-12-19 23:23:22","status":"北京分拨中心在分拨中心进行称重扫描"},{"time":"2016-12-19 21:02:03","status":"北京房山区燕化公司进行揽件扫描"},{"time":"2016-12-19 20:00:48","status":"北京房山区燕化公司进行下级地点扫描，将发往：江苏苏州网点包"},{"time":"2016-12-19 19:45:53","status":"北京房山区燕化公司进行揽件扫描"}]}]}]
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private Object ListResult;
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
        /**
         * orderId : 608
         * orderCode : 201612030008300002
         * memberId : 1975
         * addressId : 51
         * expressId : 0
         * orderStatus : 40
         * payTypeId : 1
         * tuihuo :
         * payTime : 2016-12-07T22:47:56
         * totalPrice : 0.01
         * orderRemarks : null
         * shouhuoTime : 1900-01-01T00:00:00
         * addTime : 2016-12-03T00:08:30
         * Receiver : 测试
         * Mobile : 15906229083
         * Address : 江苏省苏州市吴中区郭巷街道园区
         * integralPrice : 0
         * integralCount : 0
         * couponPrice : 0
         * couponId : 0
         * expressPrice : 0
         * deductionType : 0
         * orderType : 0
         * OrderItemList : [{"orderItemId":720,"orderId":608,"goodsId":3067,"goodsName":"20161115测试","goodsSub":"20161115测试","goodsColorId":52,"goodsColor":"黑色1","goodsStandardId":56,"goodsStandard":"XXL","goodsPrice":300,"yunfei":0,"goodsLogoUrl":"../aboluo/GoodsLogo/","goodsQuantity":1,"Price":300,"RefundStatus":-1,"EvaluationStatus":0}]
         * OrderExpressList : [{"Id":23,"orderId":608,"expressName":"安能快递","expressType":"YUNDA","expressNumber":"1700218956697","expressPrice":0,"expressTime":"2017-02-13T21:12:53","flag":1,"orderStatus":40,"LocalInfo":[{"time":"2016-12-22 21:42:50","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部快件已被 已签收 签收"},{"time":"2016-12-22 14:14:42","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部进行派件扫描；派送业务员：郁萍；联系电话：15312192915"},{"time":"2016-12-22 12:40:25","status":"江苏苏州吴中区郭巷公司进行快件扫描，将发往：江苏苏州吴中区郭巷公司纳米园便民寄存分部"},{"time":"2016-12-22 11:05:22","status":"江苏苏州分拨中心从站点发出，本次转运目的地：江苏苏州吴中区郭巷公司"},{"time":"2016-12-22 09:15:36","status":"江苏苏州分拨中心在分拨中心进行卸车扫描"},{"time":"2016-12-21 00:24:48","status":"北京分拨中心进行装车扫描，即将发往：江苏苏州分拨中心"},{"time":"2016-12-19 23:23:22","status":"北京分拨中心在分拨中心进行称重扫描"},{"time":"2016-12-19 21:02:03","status":"北京房山区燕化公司进行揽件扫描"},{"time":"2016-12-19 20:00:48","status":"北京房山区燕化公司进行下级地点扫描，将发往：江苏苏州网点包"},{"time":"2016-12-19 19:45:53","status":"北京房山区燕化公司进行揽件扫描"}]},{"Id":22,"orderId":608,"expressName":"华企","expressType":"YUNDA","expressNumber":"1700218956697","expressPrice":0,"expressTime":"2017-02-13T21:23:46","flag":1,"orderStatus":40,"LocalInfo":[{"time":"2016-12-22 21:42:50","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部快件已被 已签收 签收"},{"time":"2016-12-22 14:14:42","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部进行派件扫描；派送业务员：郁萍；联系电话：15312192915"},{"time":"2016-12-22 12:40:25","status":"江苏苏州吴中区郭巷公司进行快件扫描，将发往：江苏苏州吴中区郭巷公司纳米园便民寄存分部"},{"time":"2016-12-22 11:05:22","status":"江苏苏州分拨中心从站点发出，本次转运目的地：江苏苏州吴中区郭巷公司"},{"time":"2016-12-22 09:15:36","status":"江苏苏州分拨中心在分拨中心进行卸车扫描"},{"time":"2016-12-21 00:24:48","status":"北京分拨中心进行装车扫描，即将发往：江苏苏州分拨中心"},{"time":"2016-12-19 23:23:22","status":"北京分拨中心在分拨中心进行称重扫描"},{"time":"2016-12-19 21:02:03","status":"北京房山区燕化公司进行揽件扫描"},{"time":"2016-12-19 20:00:48","status":"北京房山区燕化公司进行下级地点扫描，将发往：江苏苏州网点包"},{"time":"2016-12-19 19:45:53","status":"北京房山区燕化公司进行揽件扫描"}]}]
         */

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
        private double couponPrice;
        private int couponId;
        private double expressPrice;
        private int deductionType;
        private int orderType;
        private List<OrderItemListBean> OrderItemList;
        private List<OrderExpressListBean> OrderExpressList;

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

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public double getExpressPrice() {
            return expressPrice;
        }

        public void setExpressPrice(double expressPrice) {
            this.expressPrice = expressPrice;
        }

        public int getDeductionType() {
            return deductionType;
        }

        public void setDeductionType(int deductionType) {
            this.deductionType = deductionType;
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

        public List<OrderExpressListBean> getOrderExpressList() {
            return OrderExpressList;
        }

        public void setOrderExpressList(List<OrderExpressListBean> OrderExpressList) {
            this.OrderExpressList = OrderExpressList;
        }

        public static class OrderItemListBean implements Parcelable {
            /**
             * orderItemId : 720
             * orderId : 608
             * goodsId : 3067
             * goodsName : 20161115测试
             * goodsSub : 20161115测试
             * goodsColorId : 52
             * goodsColor : 黑色1
             * goodsStandardId : 56
             * goodsStandard : XXL
             * goodsPrice : 300
             * yunfei : 0
             * goodsLogoUrl : ../aboluo/GoodsLogo/
             * goodsQuantity : 1
             * Price : 300
             * RefundStatus : -1
             * EvaluationStatus : 0
             */

            private int orderItemId;
            private int orderId;
            private int goodsId;
            private String goodsName;
            private String goodsSub;
            private int goodsColorId;
            private String goodsColor;
            private int goodsStandardId;
            private String goodsStandard;
            private double goodsPrice;
            private int yunfei;
            private String goodsLogoUrl;
            private int goodsQuantity;
            private double Price;
            private int RefundStatus;
            private int EvaluationStatus;

            protected OrderItemListBean(Parcel in) {
                orderItemId = in.readInt();
                orderId = in.readInt();
                goodsId = in.readInt();
                goodsName = in.readString();
                goodsSub = in.readString();
                goodsColorId = in.readInt();
                goodsColor = in.readString();
                goodsStandardId = in.readInt();
                goodsStandard = in.readString();
                goodsPrice = in.readDouble();
                yunfei = in.readInt();
                goodsLogoUrl = in.readString();
                goodsQuantity = in.readInt();
                Price = in.readDouble();
                RefundStatus = in.readInt();
                EvaluationStatus = in.readInt();
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

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(orderItemId);
                dest.writeInt(orderId);
                dest.writeInt(goodsId);
                dest.writeString(goodsName);
                dest.writeString(goodsSub);
                dest.writeInt(goodsColorId);
                dest.writeString(goodsColor);
                dest.writeInt(goodsStandardId);
                dest.writeString(goodsStandard);
                dest.writeDouble(goodsPrice);
                dest.writeInt(yunfei);
                dest.writeString(goodsLogoUrl);
                dest.writeInt(goodsQuantity);
                dest.writeDouble(Price);
                dest.writeInt(RefundStatus);
                dest.writeInt(EvaluationStatus);
            }
        }

        public static class OrderExpressListBean {
            /**
             * Id : 23
             * orderId : 608
             * expressName : 安能快递
             * expressType : YUNDA
             * expressNumber : 1700218956697
             * expressPrice : 0
             * expressTime : 2017-02-13T21:12:53
             * flag : 1
             * orderStatus : 40
             * LocalInfo : [{"time":"2016-12-22 21:42:50","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部快件已被 已签收 签收"},{"time":"2016-12-22 14:14:42","status":"江苏苏州吴中区郭巷公司纳米园便民寄存分部进行派件扫描；派送业务员：郁萍；联系电话：15312192915"},{"time":"2016-12-22 12:40:25","status":"江苏苏州吴中区郭巷公司进行快件扫描，将发往：江苏苏州吴中区郭巷公司纳米园便民寄存分部"},{"time":"2016-12-22 11:05:22","status":"江苏苏州分拨中心从站点发出，本次转运目的地：江苏苏州吴中区郭巷公司"},{"time":"2016-12-22 09:15:36","status":"江苏苏州分拨中心在分拨中心进行卸车扫描"},{"time":"2016-12-21 00:24:48","status":"北京分拨中心进行装车扫描，即将发往：江苏苏州分拨中心"},{"time":"2016-12-19 23:23:22","status":"北京分拨中心在分拨中心进行称重扫描"},{"time":"2016-12-19 21:02:03","status":"北京房山区燕化公司进行揽件扫描"},{"time":"2016-12-19 20:00:48","status":"北京房山区燕化公司进行下级地点扫描，将发往：江苏苏州网点包"},{"time":"2016-12-19 19:45:53","status":"北京房山区燕化公司进行揽件扫描"}]
             */

            private int Id;
            private int orderId;
            private String expressName;
            private String expressType;
            private String expressNumber;
            private int expressPrice;
            private String expressTime;
            private int flag;
            private int orderStatus;
            private List<LocalInfoBean> LocalInfo;

            public static OrderExpressListBean objectFromData(String str) {

                return new Gson().fromJson(str, OrderExpressListBean.class);
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

            public int getExpressPrice() {
                return expressPrice;
            }

            public void setExpressPrice(int expressPrice) {
                this.expressPrice = expressPrice;
            }

            public String getExpressTime() {
                return expressTime;
            }

            public void setExpressTime(String expressTime) {
                this.expressTime = expressTime;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public List<LocalInfoBean> getLocalInfo() {
                return LocalInfo;
            }

            public void setLocalInfo(List<LocalInfoBean> LocalInfo) {
                this.LocalInfo = LocalInfo;
            }

            public static class LocalInfoBean {
                /**
                 * time : 2016-12-22 21:42:50
                 * status : 江苏苏州吴中区郭巷公司纳米园便民寄存分部快件已被 已签收 签收
                 */

                private String time;
                private String status;

                public static LocalInfoBean objectFromData(String str) {

                    return new Gson().fromJson(str, LocalInfoBean.class);
                }

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
        }
    }
}
