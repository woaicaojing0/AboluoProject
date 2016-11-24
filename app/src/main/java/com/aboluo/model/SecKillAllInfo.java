package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/11/24.
 */

public class SecKillAllInfo {

    /**
     * SkillMainList : [{"SId":6,"Name":null,"StartTime":"2016-11-25T08:00:00","EndTime":"2016-11-25T12:00:00","Image":"/SeckillImg/1419a406666e441aaf5375f804d6c62a.png","State":"暂未开启"},{"SId":5,"Name":null,"StartTime":"2016-11-25T02:00:00","EndTime":"2016-11-25T08:00:00","Image":"/SeckillImg/a622c8707f184fd584260b93d685736e.png","State":"即将进行"},{"SId":4,"Name":null,"StartTime":"2016-11-24T22:00:00","EndTime":"2016-11-25T02:00:00","Image":"/SeckillImg/a437392c2d844edfaf39fc1a9669f638.png","State":"正在进行"}]
     * IsSuccess : true
     * Message : null
     * Result : 信息获取成功
     */

    private boolean IsSuccess;
    private Object Message;
    private String Result;
    /**
     * SId : 6
     * Name : null
     * StartTime : 2016-11-25T08:00:00
     * EndTime : 2016-11-25T12:00:00
     * Image : /SeckillImg/1419a406666e441aaf5375f804d6c62a.png
     * State : 暂未开启
     */

    private List<SkillMainListBean> SkillMainList;

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

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public List<SkillMainListBean> getSkillMainList() {
        return SkillMainList;
    }

    public void setSkillMainList(List<SkillMainListBean> SkillMainList) {
        this.SkillMainList = SkillMainList;
    }

    public static class SkillMainListBean {
        private int SId;
        private Object Name;
        private String StartTime;
        private String EndTime;
        private String Image;
        private String State;

        public int getSId() {
            return SId;
        }

        public void setSId(int SId) {
            this.SId = SId;
        }

        public Object getName() {
            return Name;
        }

        public void setName(Object Name) {
            this.Name = Name;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }
    }
}
