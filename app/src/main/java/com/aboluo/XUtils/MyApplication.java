package com.aboluo.XUtils;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by cj34920 on 2016/9/9.
 */
public class MyApplication{
    private static MyApplication instance;
    private  static  RequestQueue mRequestQueue;
    public static  String UserName =null;
    public static  String UserId =null;
    public MyApplication(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }
    /**
     * 获取消息队列
     */
    public static RequestQueue getRequestQueue(){
        throwIfNotInit();
        return mRequestQueue;
    }
    /**
     * Volley的初始化操作，使用volley前必须调用此方法
     */
    public static void init(Context context) {
        if (instance == null) {
            instance = new MyApplication(context);
        }
    }
    /**
     * 检查是否完成初始化
     */
    private static void throwIfNotInit() {
        if (instance == null) {
            throw new IllegalStateException("MyVolley尚未初始化，在使用前应该执行init()");
        }
    }

}
