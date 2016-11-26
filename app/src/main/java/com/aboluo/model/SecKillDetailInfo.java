package com.aboluo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by CJ on 2016/11/26.
 */

public class SecKillDetailInfo {

    /**
     * SeckillList : [{"SeckillId":13,"SmId":4,"GoodsId":3047,"SeckillPrice":100,"SeckillStatus":0,"SeckCount":80,"SeckRemaid":80,"GoodsColorStandardId":96,"GoodsColorStandardName":"白色 - 521 - 1011.00","GoodsName":"阿波罗研发测试商品","GoodsLogo":"/GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg","GoodsPicture":"/GoodsPicture/c4915713bc494558b214ceda14b64631.jpg;/GoodsPicture/a874a9679b0a479bb71ebf14c49d8686.jpg;/GoodsPicture/a8cc79ca5cee4f828b5bec06d5eb39df.jpg;/GoodsPicture/892f84a9837c4fbdb9b28b0af36684f9.jpg;"}]
     * IsSuccess : true
     * Message : 获取信息成功
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    /**
     * SeckillId : 13
     * SmId : 4
     * GoodsId : 3047
     * SeckillPrice : 100
     * SeckillStatus : 0
     * SeckCount : 80
     * SeckRemaid : 80
     * GoodsColorStandardId : 96
     * GoodsColorStandardName : 白色 - 521 - 1011.00
     * GoodsName : 阿波罗研发测试商品
     * GoodsLogo : /GoodsLogo/87f7222bd99d4d64b2333bb26887ee6b.jpg
     * GoodsPicture : /GoodsPicture/c4915713bc494558b214ceda14b64631.jpg;/GoodsPicture/a874a9679b0a479bb71ebf14c49d8686.jpg;/GoodsPicture/a8cc79ca5cee4f828b5bec06d5eb39df.jpg;/GoodsPicture/892f84a9837c4fbdb9b28b0af36684f9.jpg;
     */

    private List<SeckillListBean> SeckillList;

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Object getResult() {
        return Result;
    }

    public void setResult(Object Result) {
        this.Result = Result;
    }

    public List<SeckillListBean> getSeckillList() {
        return SeckillList;
    }

    public void setSeckillList(List<SeckillListBean> SeckillList) {
        this.SeckillList = SeckillList;
    }

    public static class SeckillListBean implements Parcelable {
        private int SeckillId;
        private int SmId;
        private int GoodsId;
        private double SeckillPrice;
        private int SeckillStatus;
        private int SeckCount;
        private int SeckRemaid;
        private int GoodsColorStandardId;
        private String GoodsColorStandardName;
        private String GoodsName;
        private String GoodsLogo;
        private String GoodsPicture;

        protected SeckillListBean(Parcel in) {
            SeckillId = in.readInt();
            SmId = in.readInt();
            GoodsId = in.readInt();
            SeckillPrice = in.readDouble();
            SeckillStatus = in.readInt();
            SeckCount = in.readInt();
            SeckRemaid = in.readInt();
            GoodsColorStandardId = in.readInt();
            GoodsColorStandardName = in.readString();
            GoodsName = in.readString();
            GoodsLogo = in.readString();
            GoodsPicture = in.readString();
        }

        public static final Creator<SeckillListBean> CREATOR = new Creator<SeckillListBean>() {
            @Override
            public SeckillListBean createFromParcel(Parcel in) {
                return new SeckillListBean(in);
            }

            @Override
            public SeckillListBean[] newArray(int size) {
                return new SeckillListBean[size];
            }
        };

        public int getSeckillId() {
            return SeckillId;
        }

        public void setSeckillId(int SeckillId) {
            this.SeckillId = SeckillId;
        }

        public int getSmId() {
            return SmId;
        }

        public void setSmId(int SmId) {
            this.SmId = SmId;
        }

        public int getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(int GoodsId) {
            this.GoodsId = GoodsId;
        }

        public double getSeckillPrice() {
            return SeckillPrice;
        }

        public void setSeckillPrice(double SeckillPrice) {
            this.SeckillPrice = SeckillPrice;
        }

        public int getSeckillStatus() {
            return SeckillStatus;
        }

        public void setSeckillStatus(int SeckillStatus) {
            this.SeckillStatus = SeckillStatus;
        }

        public int getSeckCount() {
            return SeckCount;
        }

        public void setSeckCount(int SeckCount) {
            this.SeckCount = SeckCount;
        }

        public int getSeckRemaid() {
            return SeckRemaid;
        }

        public void setSeckRemaid(int SeckRemaid) {
            this.SeckRemaid = SeckRemaid;
        }

        public int getGoodsColorStandardId() {
            return GoodsColorStandardId;
        }

        public void setGoodsColorStandardId(int GoodsColorStandardId) {
            this.GoodsColorStandardId = GoodsColorStandardId;
        }

        public String getGoodsColorStandardName() {
            return GoodsColorStandardName;
        }

        public void setGoodsColorStandardName(String GoodsColorStandardName) {
            this.GoodsColorStandardName = GoodsColorStandardName;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public String getGoodsLogo() {
            return GoodsLogo;
        }

        public void setGoodsLogo(String GoodsLogo) {
            this.GoodsLogo = GoodsLogo;
        }

        public String getGoodsPicture() {
            return GoodsPicture;
        }

        public void setGoodsPicture(String GoodsPicture) {
            this.GoodsPicture = GoodsPicture;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(SeckillId);
            dest.writeInt(SmId);
            dest.writeInt(GoodsId);
            dest.writeDouble(SeckillPrice);
            dest.writeInt(SeckillStatus);
            dest.writeInt(SeckCount);
            dest.writeInt(SeckRemaid);
            dest.writeInt(GoodsColorStandardId);
            dest.writeString(GoodsColorStandardName);
            dest.writeString(GoodsName);
            dest.writeString(GoodsLogo);
            dest.writeString(GoodsPicture);
        }
    }
}
