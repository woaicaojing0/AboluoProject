package com.aboluo.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CJ on 2017/3/4.
 */

public class RecMsgBean {

    /**
     * IsSuccess : true
     * Message : 信息获取成功
     * Result : null
     * ListResult : [{"id":1,"MachineCode":"160a3797c808c9b7bc9","memberId":2170,"MessageTitle":"草鸡测试","MessageContent":"草鸡测试","MessageCompany":"草鸡测试","PushTime":"2017-03-04T22:59:56","pushType":2,"PushGuid":"4d42daab-b514-4d94-851c-afe3170c964e"}]
     * PageIndex : 1
     * PageSize : 20
     * RecordCount : 1
     */

    private boolean IsSuccess;
    private String Message;
    private Object Result;
    private int PageIndex;
    private int PageSize;
    private int RecordCount;
    private List<ListResultBean> ListResult;

    public static RecMsgBean objectFromData(String str) {

        return new Gson().fromJson(str, RecMsgBean.class);
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
        /**
         * id : 1
         * MachineCode : 160a3797c808c9b7bc9
         * memberId : 2170
         * MessageTitle : 草鸡测试
         * MessageContent : 草鸡测试
         * MessageCompany : 草鸡测试
         * PushTime : 2017-03-04T22:59:56
         * pushType : 2
         * PushGuid : 4d42daab-b514-4d94-851c-afe3170c964e
         */

        private int id;
        private String MachineCode;
        private int memberId;
        private String MessageTitle;
        private String MessageContent;
        private String MessageCompany;
        private String PushTime;
        private int pushType;
        private String PushGuid;

        public static ListResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ListResultBean.class);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMachineCode() {
            return MachineCode;
        }

        public void setMachineCode(String MachineCode) {
            this.MachineCode = MachineCode;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getMessageTitle() {
            return MessageTitle;
        }

        public void setMessageTitle(String MessageTitle) {
            this.MessageTitle = MessageTitle;
        }

        public String getMessageContent() {
            return MessageContent;
        }

        public void setMessageContent(String MessageContent) {
            this.MessageContent = MessageContent;
        }

        public String getMessageCompany() {
            return MessageCompany;
        }

        public void setMessageCompany(String MessageCompany) {
            this.MessageCompany = MessageCompany;
        }

        public String getPushTime() {
            return PushTime;
        }

        public void setPushTime(String PushTime) {
            this.PushTime = PushTime;
        }

        public int getPushType() {
            return pushType;
        }

        public void setPushType(int pushType) {
            this.pushType = pushType;
        }

        public String getPushGuid() {
            return PushGuid;
        }

        public void setPushGuid(String PushGuid) {
            this.PushGuid = PushGuid;
        }
    }
}
