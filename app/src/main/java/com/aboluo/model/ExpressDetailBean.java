package com.aboluo.model;

import java.util.List;

/**
 * Created by CJ on 2016/12/5.
 */

public class ExpressDetailBean {

    /**
     * IsSuccess : true
     * Message : null
     * Result : {"number":"419596411839","type":"zto","deliverystatus":"3","list":[{"time":"2016-11-23T18:02:52","status":"[苏州市]  快件已在 苏州斜塘 签收  签收照片,感谢您使用中通快递，期待再次为您服务!"},{"time":"2016-11-23T14:39:22","status":"[苏州市] 快件已到达 苏州斜塘,业务员 东平街[13912631455] 正在派件"},{"time":"2016-11-23T09:02:47","status":"[苏州市] 快件离开 苏州中转部 已发往 苏州斜塘"},{"time":"2016-11-22T20:04:07","status":"[金华市] 快件离开 金华江北 已发往 苏州中转部"},{"time":"2016-11-22T19:26:12","status":"[金华市] 金华江北 的  朱建华方景旭[13335930685] 已收件"}]}
     * ListResult : null
     */

    private boolean IsSuccess;
    private Object Message;
    /**
     * number : 419596411839
     * type : zto
     * deliverystatus : 3
     * list : [{"time":"2016-11-23T18:02:52","status":"[苏州市]  快件已在 苏州斜塘 签收  签收照片,感谢您使用中通快递，期待再次为您服务!"},{"time":"2016-11-23T14:39:22","status":"[苏州市] 快件已到达 苏州斜塘,业务员 东平街[13912631455] 正在派件"},{"time":"2016-11-23T09:02:47","status":"[苏州市] 快件离开 苏州中转部 已发往 苏州斜塘"},{"time":"2016-11-22T20:04:07","status":"[金华市] 快件离开 金华江北 已发往 苏州中转部"},{"time":"2016-11-22T19:26:12","status":"[金华市] 金华江北 的  朱建华方景旭[13335930685] 已收件"}]
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
        private String name;
        private String number;
        private String type;
        private String deliverystatus;
        /**
         * time : 2016-11-23T18:02:52
         * status : [苏州市]  快件已在 苏州斜塘 签收  签收照片,感谢您使用中通快递，期待再次为您服务!
         */

        private List<ListBean> list;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeliverystatus() {
            return deliverystatus;
        }

        public void setDeliverystatus(String deliverystatus) {
            this.deliverystatus = deliverystatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String time;
            private String status;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
