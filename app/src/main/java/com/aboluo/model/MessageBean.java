package com.aboluo.model;

/**
 * Created by CJ on 2016/12/26.
 */

public class MessageBean {
    public static final String ID = "_id";
    public static final String MESSAGEINFO = "messageinfo";
    public static final String CREATIME = "createtime";
    private String _id;
    private String messageinfo;
    private String createtime;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMessageinfo() {
        return messageinfo;
    }

    public void setMessageinfo(String messageinfo) {
        this.messageinfo = messageinfo;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
