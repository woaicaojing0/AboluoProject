package com.aboluo.model;

/**
 * Created by CJ on 2016/10/7.
 */

public class MessageInfo {

    /**
     * IsSuccess : true
     * Message : 发送成功！
     * Result : {"SendPhoneNumber":"18360733214","MessageCode":"510650"}
     */

    private boolean IsSuccess;
    private String Message;
    /**
     * SendPhoneNumber : 18360733214
     * MessageCode : 510650
     */

    private ResultBean Result;

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
        private String SendPhoneNumber;
        private String MessageCode;

        public String getSendPhoneNumber() {
            return SendPhoneNumber;
        }

        public void setSendPhoneNumber(String SendPhoneNumber) {
            this.SendPhoneNumber = SendPhoneNumber;
        }

        public String getMessageCode() {
            return MessageCode;
        }

        public void setMessageCode(String MessageCode) {
            this.MessageCode = MessageCode;
        }
    }
}
