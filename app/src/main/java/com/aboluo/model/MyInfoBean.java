package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2016/12/13.
 */

public class MyInfoBean {


    /**
     * IsSuccess : true
     * Message : 获取成功！
     * Result : {"MemberId":1968,"MemberLoginNumber":"123","MemberMobile":"18360733212","MemberEmail":"","IsLeader":0,"UserNickName":null,"MemberLogoUrl":"http://oi0lhaaeo.bkt.clouddn.com/","MemberSex":0,"MemberWeChatOpenId":"65093800-6534-449a-b5aa-fd8ef5642d78","IsSuccess":false,"Message":null,"Result":null}
     */

    private boolean IsSuccess;
    private String Message;
    /**
     * MemberId : 1968
     * MemberLoginNumber : 123
     * MemberMobile : 18360733212
     * MemberEmail :
     * IsLeader : 0
     * UserNickName : null
     * MemberLogoUrl : http://oi0lhaaeo.bkt.clouddn.com/
     * MemberSex : 0
     * MemberWeChatOpenId : 65093800-6534-449a-b5aa-fd8ef5642d78
     * IsSuccess : false
     * Message : null
     * Result : null
     */

    private ResultBean Result;

    public static MyInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, MyInfoBean.class);
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

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        private int MemberId;
        private String MemberLoginNumber;
        private String MemberMobile;
        private String MemberEmail;
        private int IsLeader;
        private Object UserNickName;
        private String MemberLogoUrl;
        private int MemberSex;
        private String MemberWeChatOpenId;
        private boolean IsSuccess;
        private Object Message;
        private Object Result;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public int getMemberId() {
            return MemberId;
        }

        public void setMemberId(int MemberId) {
            this.MemberId = MemberId;
        }

        public String getMemberLoginNumber() {
            return MemberLoginNumber;
        }

        public void setMemberLoginNumber(String MemberLoginNumber) {
            this.MemberLoginNumber = MemberLoginNumber;
        }

        public String getMemberMobile() {
            return MemberMobile;
        }

        public void setMemberMobile(String MemberMobile) {
            this.MemberMobile = MemberMobile;
        }

        public String getMemberEmail() {
            return MemberEmail;
        }

        public void setMemberEmail(String MemberEmail) {
            this.MemberEmail = MemberEmail;
        }

        public int getIsLeader() {
            return IsLeader;
        }

        public void setIsLeader(int IsLeader) {
            this.IsLeader = IsLeader;
        }

        public Object getUserNickName() {
            return UserNickName;
        }

        public void setUserNickName(Object UserNickName) {
            this.UserNickName = UserNickName;
        }

        public String getMemberLogoUrl() {
            return MemberLogoUrl;
        }

        public void setMemberLogoUrl(String MemberLogoUrl) {
            this.MemberLogoUrl = MemberLogoUrl;
        }

        public int getMemberSex() {
            return MemberSex;
        }

        public void setMemberSex(int MemberSex) {
            this.MemberSex = MemberSex;
        }

        public String getMemberWeChatOpenId() {
            return MemberWeChatOpenId;
        }

        public void setMemberWeChatOpenId(String MemberWeChatOpenId) {
            this.MemberWeChatOpenId = MemberWeChatOpenId;
        }

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

        public Object getResult() {
            return Result;
        }

        public void setResult(Object Result) {
            this.Result = Result;
        }
    }
}
