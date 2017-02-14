package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2017/2/14.
 */

public class ExpressNumBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"AllOrderCount":15,"WaitPayCount":3,"WaitSendCount":0,"WaitReceiptCont":4,"WaitEvaluateCount":4,"CancelCount":2}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    private ResultBean Result;
    private Object ListResult;

    public static ExpressNumBean objectFromData(String str) {

        return new Gson().fromJson(str, ExpressNumBean.class);
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
         * AllOrderCount : 15
         * WaitPayCount : 3
         * WaitSendCount : 0
         * WaitReceiptCont : 4
         * WaitEvaluateCount : 4
         * CancelCount : 2
         */

        private int AllOrderCount;
        private int WaitPayCount;
        private int WaitSendCount;
        private int WaitReceiptCont;
        private int WaitEvaluateCount;
        private int CancelCount;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public int getAllOrderCount() {
            return AllOrderCount;
        }

        public void setAllOrderCount(int AllOrderCount) {
            this.AllOrderCount = AllOrderCount;
        }

        public int getWaitPayCount() {
            return WaitPayCount;
        }

        public void setWaitPayCount(int WaitPayCount) {
            this.WaitPayCount = WaitPayCount;
        }

        public int getWaitSendCount() {
            return WaitSendCount;
        }

        public void setWaitSendCount(int WaitSendCount) {
            this.WaitSendCount = WaitSendCount;
        }

        public int getWaitReceiptCont() {
            return WaitReceiptCont;
        }

        public void setWaitReceiptCont(int WaitReceiptCont) {
            this.WaitReceiptCont = WaitReceiptCont;
        }

        public int getWaitEvaluateCount() {
            return WaitEvaluateCount;
        }

        public void setWaitEvaluateCount(int WaitEvaluateCount) {
            this.WaitEvaluateCount = WaitEvaluateCount;
        }

        public int getCancelCount() {
            return CancelCount;
        }

        public void setCancelCount(int CancelCount) {
            this.CancelCount = CancelCount;
        }
    }
}
