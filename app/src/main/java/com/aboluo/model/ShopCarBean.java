package com.aboluo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by CJ on 2016/10/24.
 */

public class ShopCarBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsShoppingCartList":[{"Id":32,"goodsId":3067,"goodsColorId":52,"goodsColor":"黑色","goodsStandardId":52,"goodsStandard":"XXXXL","goodsCount":1,"memberId":1,"shopId":1,"createTime":"2016-11-20T14:01:55.878265","flag":1,"goodsName":"20161115测试","goodsSub":"20161115测试","yunfei":0,"goodsLogo":null,"goodsPrice":300,"hyPrice":250}]}
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
         * Id : 32
         * goodsId : 3067
         * goodsColorId : 52
         * goodsColor : 黑色
         * goodsStandardId : 52
         * goodsStandard : XXXXL
         * goodsCount : 1
         * memberId : 1
         * shopId : 1
         * createTime : 2016-11-20T14:01:55.878265
         * flag : 1
         * goodsName : 20161115测试
         * goodsSub : 20161115测试
         * yunfei : 0
         * goodsLogo : null
         * goodsPrice : 300
         * hyPrice : 250
         */

        private List<GoodsShoppingCartListBean> GoodsShoppingCartList;

        public List<GoodsShoppingCartListBean> getGoodsShoppingCartList() {
            return GoodsShoppingCartList;
        }

        public void setGoodsShoppingCartList(List<GoodsShoppingCartListBean> GoodsShoppingCartList) {
            this.GoodsShoppingCartList = GoodsShoppingCartList;
        }

        public static class GoodsShoppingCartListBean   implements Parcelable {
            private int Id;
            private int goodsId;
            private int goodsColorId;
            private String goodsColor;
            private int goodsStandardId;
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

            protected GoodsShoppingCartListBean(Parcel in) {
                Id = in.readInt();
                goodsId = in.readInt();
                goodsColor = in.readString();
                goodsColorId = in.readInt();
                goodsStandard = in.readString();
                goodsStandardId = in.readInt();
                goodsCount = in.readInt();
                memberId = in.readInt();
                shopId = in.readInt();
                createTime = in.readString();
                flag = in.readInt();
                goodsName = in.readString();
                goodsSub = in.readString();
                yunfei = in.readInt();
                goodsLogo = in.readString();
                goodsPrice = in.readDouble();
                hyPrice = in.readDouble();
            }

            public static final Creator<GoodsShoppingCartListBean> CREATOR = new Creator<GoodsShoppingCartListBean>() {
                @Override
                public GoodsShoppingCartListBean createFromParcel(Parcel in) {
                    return new GoodsShoppingCartListBean(in);
                }

                @Override
                public GoodsShoppingCartListBean[] newArray(int size) {
                    return new GoodsShoppingCartListBean[size];
                }
            };

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(Id);
                dest.writeInt(goodsId);
                dest.writeString(goodsColor);
                dest.writeInt(goodsColorId);
                dest.writeString(goodsStandard);
                dest.writeInt(goodsStandardId);
                dest.writeInt(goodsCount);
                dest.writeInt(memberId);
                dest.writeInt(shopId);
                dest.writeString(createTime);
                dest.writeInt(flag);
                dest.writeString(goodsName);
                dest.writeString(goodsSub);
                dest.writeInt(yunfei);
                dest.writeString(goodsLogo);
                dest.writeDouble(goodsPrice);
                dest.writeDouble(hyPrice);
            }
        }
    }
}
