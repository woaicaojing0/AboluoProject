package com.aboluo.model;

/**
 * Created by cj34920 on 2017/1/5.
 */

public class MakerOrderIntergralBean {

    /**
     * IsSuccess : false
     * Message : null
     * Result : {"IsUserIntegral":1,"IntegralPrice":50,"IntegralCount":5000,"ExpressPrice":0}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    /**
     * IsUserIntegral : 1
     * IntegralPrice : 50
     * IntegralCount : 5000
     * ExpressPrice : 0
     */

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
        private int IsUserIntegral;
        private Double IntegralPrice;
        private int IntegralCount;
        private Double ExpressPrice;
        private String  Description;
        public int getIsUserIntegral() {
            return IsUserIntegral;
        }

        public void setIsUserIntegral(int IsUserIntegral) {
            this.IsUserIntegral = IsUserIntegral;
        }

        public Double getIntegralPrice() {
            return IntegralPrice;
        }

        public void setIntegralPrice(Double IntegralPrice) {
            this.IntegralPrice = IntegralPrice;
        }

        public int getIntegralCount() {
            return IntegralCount;
        }

        public void setIntegralCount(int IntegralCount) {
            this.IntegralCount = IntegralCount;
        }

        public Double getExpressPrice() {
            return ExpressPrice;
        }

        public void setExpressPrice(Double ExpressPrice) {
            this.ExpressPrice = ExpressPrice;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }
    }
}
