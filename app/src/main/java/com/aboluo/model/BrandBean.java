package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/10/28.
 */

public class BrandBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"GoodsBrandList":[{"Id":5,"brandName":"LV","brandImg":"1474606f40e24feda0b36794bb03f32f.jpg","creator":"admin","createTime":"2016-10-20T22:03:08","flag":1},{"Id":6,"brandName":"迪奥","brandImg":"0adb9c2b0e6b4964b6144c1aa0cb121d.jpg","creator":"admin","createTime":"2016-10-20T22:19:46","flag":1},{"Id":9,"brandName":"CK","brandImg":"9867044702e847c99f5fb5fdcd55ef59.jpg","creator":"admin","createTime":"2016-10-25T21:52:24","flag":1},{"Id":7,"brandName":"费列罗                        ","brandImg":"761235ae822748b69a827b6f97eb7948.jpg","creator":"admin","createTime":"2016-10-22T08:18:07.042063","flag":1}]}
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
         * Id : 5
         * brandName : LV
         * brandImg : 1474606f40e24feda0b36794bb03f32f.jpg
         * creator : admin
         * createTime : 2016-10-20T22:03:08
         * flag : 1
         */

        private List<GoodsBrandListBean> GoodsBrandList;

        public List<GoodsBrandListBean> getGoodsBrandList() {
            return GoodsBrandList;
        }

        public void setGoodsBrandList(List<GoodsBrandListBean> GoodsBrandList) {
            this.GoodsBrandList = GoodsBrandList;
        }

        public static class GoodsBrandListBean {
            private int Id;
            private String brandName;
            private String brandImg;
            private String creator;
            private String createTime;
            private int flag;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrandImg() {
                return brandImg;
            }

            public void setBrandImg(String brandImg) {
                this.brandImg = brandImg;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
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
        }
    }
}
