package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2017/2/18.
 */

public class HelpCenterListBean {

    /**
     * List : [{"ID":1,"Sort":0,"Title":"测试数据","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=1"},{"ID":2,"Sort":2,"Title":"常见问题","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=2"},{"ID":3,"Sort":3,"Title":"代金券说明","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=3"},{"ID":4,"Sort":4,"Title":"积分说明","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=4"},{"ID":5,"Sort":5,"Title":"股东说明","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=5"},{"ID":6,"Sort":6,"Title":"关于佣金","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=6"},{"ID":7,"Sort":7,"Title":"资产说明","Url":"http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=7"}]
     * IsSuccess : true
     * Message : 数据获取成功！
     * Result : null
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    private java.util.List<ListBean> List;

    public static HelpCenterListBean objectFromData(String str) {

        return new Gson().fromJson(str, HelpCenterListBean.class);
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

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * ID : 1
         * Sort : 0
         * Title : 测试数据
         * Url : http://t.back.aboluomall.com/Moblie/ShowHelpCenter?helpMenuId=1
         */

        private int ID;
        private int Sort;
        private String Title;
        private String Url;

        public static ListBean objectFromData(String str) {

            return new Gson().fromJson(str, ListBean.class);
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }
    }
}
