package com.aboluo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2016/11/26.
 */

public class SecKillDetailInfo {


    /**
     * SeckillList : [{"SeckillId":38,"SmId":16,"GoodsId":3053,"SeckillPrice":50,"SeckillStatus":0,"SeckCount":100,"SeckRemaid":100,"GoodsColorStandardId":0,"GoodsColorStandardName":"默认","GoodsName":"1012","GoodsLogo":"/GoodsLogo/90d4fc8e6283413faed73182447c7071.jpg","GoodsPicture":"/GoodsPicture/ead168c4862b40c088d76532ec307c5b.jpg;/GoodsPicture/856a4a7ec1b84fbbb9bbfb23ae02857b.jpg;","GoodsColorId":0,"GoodsColor":null,"GoodsStandardId":0,"GoodsStandard":null}]
     * IsSuccess : true
     * Message : 获取信息成功
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    /**
     * SeckillId : 38
     * SmId : 16
     * GoodsId : 3053
     * SeckillPrice : 50
     * SeckillStatus : 0
     * SeckCount : 100
     * SeckRemaid : 100
     * GoodsColorStandardId : 0
     * GoodsColorStandardName : 默认
     * GoodsName : 1012
     * GoodsLogo : /GoodsLogo/90d4fc8e6283413faed73182447c7071.jpg
     * GoodsPicture : /GoodsPicture/ead168c4862b40c088d76532ec307c5b.jpg;/GoodsPicture/856a4a7ec1b84fbbb9bbfb23ae02857b.jpg;
     * GoodsColorId : 0
     * GoodsColor : null
     * GoodsStandardId : 0
     * GoodsStandard : null
     */

    private List<SeckillListBean> SeckillList;

    public static SecKillDetailInfo objectFromData(String str) {

        return new Gson().fromJson(str, SecKillDetailInfo.class);
    }

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
        private int GoodsColorId;
        private Object GoodsColor;
        private int GoodsStandardId;
        private Object GoodsStandard;

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
            GoodsColorId = in.readInt();
            GoodsStandardId = in.readInt();
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

        public static SeckillListBean objectFromData(String str) {

            return new Gson().fromJson(str, SeckillListBean.class);
        }

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

        public int getGoodsColorId() {
            return GoodsColorId;
        }

        public void setGoodsColorId(int GoodsColorId) {
            this.GoodsColorId = GoodsColorId;
        }

        public Object getGoodsColor() {
            return GoodsColor;
        }

        public void setGoodsColor(Object GoodsColor) {
            this.GoodsColor = GoodsColor;
        }

        public int getGoodsStandardId() {
            return GoodsStandardId;
        }

        public void setGoodsStandardId(int GoodsStandardId) {
            this.GoodsStandardId = GoodsStandardId;
        }

        public Object getGoodsStandard() {
            return GoodsStandard;
        }

        public void setGoodsStandard(Object GoodsStandard) {
            this.GoodsStandard = GoodsStandard;
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
            dest.writeInt(GoodsColorId);
            dest.writeInt(GoodsStandardId);
        }
    }
}
