package com.aboluo.model;

/**
 * Created by cj34920 on 2016/12/1.
 */

public class SignInfoBean {

    /**
     * SLId : 2
     * SLMemberId : 1974
     * SLScoreType : 1
     * SLScoreValue : 2
     * SLRemark : ????
     * SLCreateTime : 2016-12-01T20:50:33
     * SLIsDelete : 0
     * SLDay : 20161201
     */

    private MemberScoreLogEntityBean MemberScoreLogEntity;
    /**
     * MemberScoreLogEntity : {"SLId":2,"SLMemberId":1974,"SLScoreType":1,"SLScoreValue":2,"SLRemark":"????","SLCreateTime":"2016-12-01T20:50:33","SLIsDelete":0,"SLDay":20161201}
     * IsSuccess : true
     * Message : 获取成功
     * Result : 1
     */

    private boolean IsSuccess;
    private String Message;
    private int Result;

    public MemberScoreLogEntityBean getMemberScoreLogEntity() {
        return MemberScoreLogEntity;
    }

    public void setMemberScoreLogEntity(MemberScoreLogEntityBean MemberScoreLogEntity) {
        this.MemberScoreLogEntity = MemberScoreLogEntity;
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

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public static class MemberScoreLogEntityBean {
        private int SLId;
        private int SLMemberId;
        private int SLScoreType;
        private int SLScoreValue;
        private String SLRemark;
        private String SLCreateTime;
        private int SLIsDelete;
        private String SLDay;

        public int getSLId() {
            return SLId;
        }

        public void setSLId(int SLId) {
            this.SLId = SLId;
        }

        public int getSLMemberId() {
            return SLMemberId;
        }

        public void setSLMemberId(int SLMemberId) {
            this.SLMemberId = SLMemberId;
        }

        public int getSLScoreType() {
            return SLScoreType;
        }

        public void setSLScoreType(int SLScoreType) {
            this.SLScoreType = SLScoreType;
        }

        public int getSLScoreValue() {
            return SLScoreValue;
        }

        public void setSLScoreValue(int SLScoreValue) {
            this.SLScoreValue = SLScoreValue;
        }

        public String getSLRemark() {
            return SLRemark;
        }

        public void setSLRemark(String SLRemark) {
            this.SLRemark = SLRemark;
        }

        public String getSLCreateTime() {
            return SLCreateTime;
        }

        public void setSLCreateTime(String SLCreateTime) {
            this.SLCreateTime = SLCreateTime;
        }

        public int getSLIsDelete() {
            return SLIsDelete;
        }

        public void setSLIsDelete(int SLIsDelete) {
            this.SLIsDelete = SLIsDelete;
        }

        public String getSLDay() {
            return SLDay;
        }

        public void setSLDay(String SLDay) {
            this.SLDay = SLDay;
        }
    }
}
