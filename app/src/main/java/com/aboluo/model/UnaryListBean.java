package com.aboluo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by CJ on 2016/12/6.
 */

public class UnaryListBean {

    /**
     * IsSuccess : true
     * Message : 信息获取成功！
     * Result : null
     * ListResult : [{"GoodsName":"xxx","GoodsLogo":null,"GoodsPicture":null,"GoodsSub":null,"ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"1900-01-01T00:00:00","FinishTime":"1900-01-01T00:00:00","StartTime":"1900-01-01T00:00:00","GoodsPrice":1,"Creator":1,"GoodsColorId":0,"GoodsId":3056,"GoodsStandColorId":0,"GoodsStandId":0,"Id":6,"IsDelete":0,"JoinCount":0,"NeedPersonCount":10,"State":2},{"GoodsName":"1011","GoodsLogo":null,"GoodsPicture":null,"GoodsSub":"1011","ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"1900-01-01T00:00:00","FinishTime":"1900-01-01T00:00:00","StartTime":"1900-01-01T00:00:00","GoodsPrice":1,"Creator":1,"GoodsColorId":0,"GoodsId":3050,"GoodsStandColorId":0,"GoodsStandId":0,"Id":8,"IsDelete":0,"JoinCount":0,"NeedPersonCount":10,"State":2},{"GoodsName":"xxx","GoodsLogo":null,"GoodsPicture":null,"GoodsSub":null,"ColorId":0,"StandId":0,"ColorStandName":null,"ColorName":null,"CreateTime":"2016-12-04T04:08:27","FinishTime":"1900-01-01T00:00:00","StartTime":"1900-01-01T00:00:00","GoodsPrice":66,"Creator":1,"GoodsColorId":0,"GoodsId":3056,"GoodsStandColorId":0,"GoodsStandId":0,"Id":11,"IsDelete":0,"JoinCount":0,"NeedPersonCount":52,"State":2}]
     * PageIndex : 1
     * PageSize : 5
     * RecordCount : 0
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    private int PageIndex;
    private int PageSize;
    private int RecordCount;
    /**
     * GoodsName : xxx
     * GoodsLogo : null
     * GoodsPicture : null
     * GoodsSub : null
     * ColorId : 0
     * StandId : 0
     * ColorStandName : null
     * ColorName : null
     * CreateTime : 1900-01-01T00:00:00
     * FinishTime : 1900-01-01T00:00:00
     * StartTime : 1900-01-01T00:00:00
     * GoodsPrice : 1
     * Creator : 1
     * GoodsColorId : 0
     * GoodsId : 3056
     * GoodsStandColorId : 0
     * GoodsStandId : 0
     * Id : 6
     * IsDelete : 0
     * JoinCount : 0
     * NeedPersonCount : 10
     * State : 2
     */

    private List<ListResultBean> ListResult;

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

    public static class ListResultBean implements  Parcelable{
        private String GoodsName;
        private String GoodsLogo;
        private String GoodsPicture;
        private String GoodsSub;
        private int ColorId;
        private int StandId;
        private String ColorStandName;
        private String ColorName;
        private String CreateTime;
        private String FinishTime;
        private String StartTime;
        private double GoodsPrice;
        private int Creator;
        private int GoodsColorId;
        private int GoodsId;
        private int GoodsStandColorId;
        private int GoodsStandId;
        private int Id;
        private int IsDelete;
        private int JoinCount;
        private int NeedPersonCount;
        private int State;

        protected ListResultBean(Parcel in) {
            GoodsName = in.readString();
            GoodsLogo = in.readString();
            GoodsPicture = in.readString();
            GoodsSub = in.readString();
            ColorId = in.readInt();
            StandId = in.readInt();
            ColorStandName = in.readString();
            ColorName = in.readString();
            CreateTime = in.readString();
            FinishTime = in.readString();
            StartTime = in.readString();
            GoodsPrice = in.readDouble();
            Creator = in.readInt();
            GoodsColorId = in.readInt();
            GoodsId = in.readInt();
            GoodsStandColorId = in.readInt();
            GoodsStandId = in.readInt();
            Id = in.readInt();
            IsDelete = in.readInt();
            JoinCount = in.readInt();
            NeedPersonCount = in.readInt();
            State = in.readInt();
        }

        public static final Parcelable.Creator<ListResultBean> CREATOR = new Creator<ListResultBean>() {
            @Override
            public ListResultBean createFromParcel(Parcel in) {
                return new ListResultBean(in);
            }

            @Override
            public ListResultBean[] newArray(int size) {
                return new ListResultBean[size];
            }
        };

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String goodsName) {
            GoodsName = goodsName;
        }

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public String getGoodsLogo() {
            return GoodsLogo;
        }

        public void setGoodsLogo(String goodsLogo) {
            GoodsLogo = goodsLogo;
        }

        public String getGoodsPicture() {
            return GoodsPicture;
        }

        public void setGoodsPicture(String goodsPicture) {
            GoodsPicture = goodsPicture;
        }

        public String getGoodsSub() {
            return GoodsSub;
        }

        public void setGoodsSub(String goodsSub) {
            GoodsSub = goodsSub;
        }

        public int getColorId() {
            return ColorId;
        }

        public void setColorId(int colorId) {
            ColorId = colorId;
        }

        public int getStandId() {
            return StandId;
        }

        public void setStandId(int standId) {
            StandId = standId;
        }

        public String getColorStandName() {
            return ColorStandName;
        }

        public void setColorStandName(String colorStandName) {
            ColorStandName = colorStandName;
        }

        public String getColorName() {
            return ColorName;
        }

        public void setColorName(String colorName) {
            ColorName = colorName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getFinishTime() {
            return FinishTime;
        }

        public void setFinishTime(String finishTime) {
            FinishTime = finishTime;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String startTime) {
            StartTime = startTime;
        }

        public double getGoodsPrice() {
            return GoodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            GoodsPrice = goodsPrice;
        }

        public int getCreator() {
            return Creator;
        }

        public void setCreator(int creator) {
            Creator = creator;
        }

        public int getGoodsColorId() {
            return GoodsColorId;
        }

        public void setGoodsColorId(int goodsColorId) {
            GoodsColorId = goodsColorId;
        }

        public int getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(int goodsId) {
            GoodsId = goodsId;
        }

        public int getGoodsStandColorId() {
            return GoodsStandColorId;
        }

        public void setGoodsStandColorId(int goodsStandColorId) {
            GoodsStandColorId = goodsStandColorId;
        }

        public int getGoodsStandId() {
            return GoodsStandId;
        }

        public void setGoodsStandId(int goodsStandId) {
            GoodsStandId = goodsStandId;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int isDelete) {
            IsDelete = isDelete;
        }

        public int getJoinCount() {
            return JoinCount;
        }

        public void setJoinCount(int joinCount) {
            JoinCount = joinCount;
        }

        public int getNeedPersonCount() {
            return NeedPersonCount;
        }

        public void setNeedPersonCount(int needPersonCount) {
            NeedPersonCount = needPersonCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(GoodsName);
            dest.writeString(GoodsLogo);
            dest.writeString(GoodsPicture);
            dest.writeString(GoodsSub);
            dest.writeInt(ColorId);
            dest.writeInt(StandId);
            dest.writeString(ColorStandName);
            dest.writeString(ColorName);
            dest.writeString(CreateTime);
            dest.writeString(FinishTime);
            dest.writeString(StartTime);
            dest.writeDouble(GoodsPrice);
            dest.writeInt(Creator);
            dest.writeInt(GoodsColorId);
            dest.writeInt(GoodsId);
            dest.writeInt(GoodsStandColorId);
            dest.writeInt(GoodsStandId);
            dest.writeInt(Id);
            dest.writeInt(IsDelete);
            dest.writeInt(JoinCount);
            dest.writeInt(NeedPersonCount);
            dest.writeInt(State);
        }
    }
}
