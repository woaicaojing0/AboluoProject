package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/5.
 */

public class ProvinceBean {
    /**
     * IsSuccess : true
     * Message : null
     * Result : {"CounryAreaList":[{"Id":110000,"AreaName":"北京"},{"Id":120000,"AreaName":"天津"},{"Id":130000,"AreaName":"河北省"},{"Id":140000,"AreaName":"山西省"},{"Id":150000,"AreaName":"内蒙古自治区"},{"Id":210000,"AreaName":"辽宁省"},{"Id":220000,"AreaName":"吉林省"},{"Id":230000,"AreaName":"黑龙江省"},{"Id":310000,"AreaName":"上海"},{"Id":320000,"AreaName":"江苏省"},{"Id":330000,"AreaName":"浙江省"},{"Id":340000,"AreaName":"安徽省"},{"Id":350000,"AreaName":"福建省"},{"Id":360000,"AreaName":"江西省"},{"Id":370000,"AreaName":"山东省"},{"Id":410000,"AreaName":"河南省"},{"Id":420000,"AreaName":"湖北省"},{"Id":430000,"AreaName":"湖南省"},{"Id":440000,"AreaName":"广东省"},{"Id":450000,"AreaName":"广西壮族自治区"},{"Id":460000,"AreaName":"海南省"},{"Id":500000,"AreaName":"重庆"},{"Id":510000,"AreaName":"四川省"},{"Id":520000,"AreaName":"贵州省"},{"Id":530000,"AreaName":"云南省"},{"Id":540000,"AreaName":"西藏自治区"},{"Id":610000,"AreaName":"陕西省"},{"Id":620000,"AreaName":"甘肃省"},{"Id":630000,"AreaName":"青海省"},{"Id":640000,"AreaName":"宁夏回族自治区"},{"Id":650000,"AreaName":"新疆维吾尔自治区"},{"Id":810000,"AreaName":"香港特别行政区"},{"Id":820000,"AreaName":"澳门特别行政区"},{"Id":710000,"AreaName":"台湾"}]}
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
         * Id : 110000
         * AreaName : 北京
         */

        private List<CounryAreaListBean> CounryAreaList;

        public List<CounryAreaListBean> getCounryAreaList() {
            return CounryAreaList;
        }

        public void setCounryAreaList(List<CounryAreaListBean> CounryAreaList) {
            this.CounryAreaList = CounryAreaList;
        }

        public static class CounryAreaListBean {
            private int Id;
            private String AreaName;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }
        }
    }
}
