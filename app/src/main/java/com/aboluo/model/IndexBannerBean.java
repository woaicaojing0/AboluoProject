package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/27.
 */

public class IndexBannerBean {

    /**
     * AppConfigList : [{"Image":"/AppMenuImg/a0b0b474d6ea4da28763c972d0e0d935.jpg","Name":"测试","Params":"","SortId":1,"Url":""},{"Image":"/AppMenuImg/3d8cea8e1024416d9cf14b027790c4b3.jpg","Name":"123","Params":"","SortId":2,"Url":""},{"Image":"/AppMenuImg/f83a24a2235846bfa02257773312e784.jpg","Name":"345","Params":"","SortId":3,"Url":""}]
     * IsSuccess : true
     * Message : 数据获取成功
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    /**
     * Image : /AppMenuImg/a0b0b474d6ea4da28763c972d0e0d935.jpg
     * Name : 测试
     * Params :
     * SortId : 1
     * Url :
     */

    private List<AppConfigListBean> AppConfigList;

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

    public List<AppConfigListBean> getAppConfigList() {
        return AppConfigList;
    }

    public void setAppConfigList(List<AppConfigListBean> AppConfigList) {
        this.AppConfigList = AppConfigList;
    }

    public static class AppConfigListBean {
        private String Image;
        private String Name;
        private String Params;
        private int SortId;
        private String Url;

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getParams() {
            return Params;
        }

        public void setParams(String Params) {
            this.Params = Params;
        }

        public int getSortId() {
            return SortId;
        }

        public void setSortId(int SortId) {
            this.SortId = SortId;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }
    }
}
