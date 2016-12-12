package com.aboluo.model;

import com.google.gson.Gson;

/**
 * Created by CJ on 2016/12/12.
 */

public class QiNiuToken {

    /**
     * FileUploadToken : y3-kRL62yL-FztNMCiIlD2xwGYhc1OXR6QiZTJy1:dSizR4INy37V4Y4oYtWKGJ5Nmf0=:eyJzY29wZSI6ImFib2x1b21hbGwiLCJkZWFkbGluZSI6MTQ4MTU1MjYxMH0=
     * FileUploadNamespace : aboluomall
     * FileUrl : http://oi0lhaaeo.bkt.clouddn.com/
     * IsSuccess : true
     * Message : 获取成功
     * Result : null
     */

    private String FileUploadToken;
    private String FileUploadNamespace;
    private String FileUrl;
    private boolean IsSuccess;
    private String Message;
    private Object Result;

    public static QiNiuToken objectFromData(String str) {

        return new Gson().fromJson(str, QiNiuToken.class);
    }

    public String getFileUploadToken() {
        return FileUploadToken;
    }

    public void setFileUploadToken(String FileUploadToken) {
        this.FileUploadToken = FileUploadToken;
    }

    public String getFileUploadNamespace() {
        return FileUploadNamespace;
    }

    public void setFileUploadNamespace(String FileUploadNamespace) {
        this.FileUploadNamespace = FileUploadNamespace;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String FileUrl) {
        this.FileUrl = FileUrl;
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
}
