package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2016/11/27.
 */

public class BaseConfigBean {
    /**
     * AppConfigList : [{"Image":"/AppMenuImg/9caff3a2fc7f49d98d2505a855e8ddcc.png","Name":"aaa","Params":{"ParentId":55,"ChildId":31},"SortId":0,"Url":""},{"Image":"/AppMenuImg/aa204b5ad10d4c26a70b9fb1b9c683e6.png","Name":"fff","Params":{"ParentId":55,"ChildId":31},"SortId":0,"Url":""},{"Image":"/AppMenuImg/9e2f0964cd904dfca73027e4b99db7a8.png","Name":"一元购","Params":null,"SortId":1,"Url":""},{"Image":"/AppMenuImg/f06c7647039c45f4b64ccf1c794d7678.png","Name":"双十二专场","Params":{"ParentId":55,"ChildId":31},"SortId":2,"Url":""},{"Image":"/AppMenuImg/26875d59b61b4054ad823f5c10d13b59.png","Name":"寻找合伙人","Params":{"ParentId":55,"ChildId":31},"SortId":3,"Url":""},{"Image":"/AppMenuImg/f3e98d8d9bf04fa1b0e96e1477318743.png","Name":"新款特别推荐","Params":null,"SortId":3,"Url":""},{"Image":"/AppMenuImg/7224940c189f4f02bb93fb9126199024.png","Name":"中秋大促销","Params":null,"SortId":4,"Url":""}]
     * IsSuccess : true
     * Message : 数据获取成功
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    /**
     * Image : /AppMenuImg/9caff3a2fc7f49d98d2505a855e8ddcc.png
     * Name : aaa
     * Params : {"ParentId":55,"ChildId":31}
     * SortId : 0
     * Url :
     */

    private List<AppConfigListBean> AppConfigList;

    public static BaseConfigBean objectFromData(String str) {

        return new Gson().fromJson(str, BaseConfigBean.class);
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

    public List<AppConfigListBean> getAppConfigList() {
        return AppConfigList;
    }

    public void setAppConfigList(List<AppConfigListBean> AppConfigList) {
        this.AppConfigList = AppConfigList;
    }

    public static class AppConfigListBean {
        private String Image;
        private String Name;
        /**
         * ParentId : 55
         * ChildId : 31
         */

        private ParamsBean Params;
        private int SortId;
        private String Url;

        public static AppConfigListBean objectFromData(String str) {

            return new Gson().fromJson(str, AppConfigListBean.class);
        }

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

        public ParamsBean getParams() {
            return Params;
        }

        public void setParams(ParamsBean Params) {
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

        public static class ParamsBean {
            private int ParentId;
            private int ChildId;

            public static ParamsBean objectFromData(String str) {

                return new Gson().fromJson(str, ParamsBean.class);
            }

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int ParentId) {
                this.ParentId = ParentId;
            }

            public int getChildId() {
                return ChildId;
            }

            public void setChildId(int ChildId) {
                this.ChildId = ChildId;
            }
        }
    }
}
