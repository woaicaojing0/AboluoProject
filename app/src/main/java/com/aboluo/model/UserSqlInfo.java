package com.aboluo.model;

/**
 * Created by CJ on 2017/1/7.
 */

public class UserSqlInfo {
    public static final String ID = "_id";
    public static final String MemberId = "memberid";
    public static final String GESTURE = "gesture";
    public static final String ISUSE = "isuse";
    private String _id;
    private String memberid;
    private String gesture;
    private String isuse;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture = gesture;
    }

    public String getisuse() {
        return isuse;
    }

    public void setisuse(String isuse) {
        this.isuse = isuse;
    }
}
