package com.aboluo.model;

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

    public static class ListResultBean {
        private String GoodsName;
        private Object GoodsLogo;
        private Object GoodsPicture;
        private Object GoodsSub;
        private int ColorId;
        private int StandId;
        private Object ColorStandName;
        private Object ColorName;
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

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public Object getGoodsLogo() {
            return GoodsLogo;
        }

        public void setGoodsLogo(Object GoodsLogo) {
            this.GoodsLogo = GoodsLogo;
        }

        public Object getGoodsPicture() {
            return GoodsPicture;
        }

        public void setGoodsPicture(Object GoodsPicture) {
            this.GoodsPicture = GoodsPicture;
        }

        public Object getGoodsSub() {
            return GoodsSub;
        }

        public void setGoodsSub(Object GoodsSub) {
            this.GoodsSub = GoodsSub;
        }

        public int getColorId() {
            return ColorId;
        }

        public void setColorId(int ColorId) {
            this.ColorId = ColorId;
        }

        public int getStandId() {
            return StandId;
        }

        public void setStandId(int StandId) {
            this.StandId = StandId;
        }

        public Object getColorStandName() {
            return ColorStandName;
        }

        public void setColorStandName(Object ColorStandName) {
            this.ColorStandName = ColorStandName;
        }

        public Object getColorName() {
            return ColorName;
        }

        public void setColorName(Object ColorName) {
            this.ColorName = ColorName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getFinishTime() {
            return FinishTime;
        }

        public void setFinishTime(String FinishTime) {
            this.FinishTime = FinishTime;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public double getGoodsPrice() {
            return GoodsPrice;
        }

        public void setGoodsPrice(double GoodsPrice) {
            this.GoodsPrice = GoodsPrice;
        }

        public int getCreator() {
            return Creator;
        }

        public void setCreator(int Creator) {
            this.Creator = Creator;
        }

        public int getGoodsColorId() {
            return GoodsColorId;
        }

        public void setGoodsColorId(int GoodsColorId) {
            this.GoodsColorId = GoodsColorId;
        }

        public int getGoodsId() {
            return GoodsId;
        }

        public void setGoodsId(int GoodsId) {
            this.GoodsId = GoodsId;
        }

        public int getGoodsStandColorId() {
            return GoodsStandColorId;
        }

        public void setGoodsStandColorId(int GoodsStandColorId) {
            this.GoodsStandColorId = GoodsStandColorId;
        }

        public int getGoodsStandId() {
            return GoodsStandId;
        }

        public void setGoodsStandId(int GoodsStandId) {
            this.GoodsStandId = GoodsStandId;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(int IsDelete) {
            this.IsDelete = IsDelete;
        }

        public int getJoinCount() {
            return JoinCount;
        }

        public void setJoinCount(int JoinCount) {
            this.JoinCount = JoinCount;
        }

        public int getNeedPersonCount() {
            return NeedPersonCount;
        }

        public void setNeedPersonCount(int NeedPersonCount) {
            this.NeedPersonCount = NeedPersonCount;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }
    }
}
