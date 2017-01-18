package com.aboluo.XUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.aboluo.Gesture.SecondActivity;
import com.aboluo.GestureUtils.Contants;
import com.baidu.android.bba.common.util.Util;
import com.leo.gesturelibray.enums.LockMode;
import com.pgyersdk.crash.PgyCrashManager;
//
//import cn.beecloud.BeeCloud;
import cn.beecloud.BeeCloud;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by CJ on 2016/11/2.
 */

public class BaseApplication extends Application {
    public int count = 0;
    public boolean out = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化volley
        MyApplication.init(this);
        //初始化工具类
        Utils.init(this);
        //蒲公英的初始化
        PgyCrashManager.register(this);
        //分享初始化
        ShareSDK.initSDK(this);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //开启测试模式
        BeeCloud.setSandbox(false);
        //此处第二个参数是控制台的test secret
        BeeCloud.setAppIdAndSecret(CommonUtils.GetValueByKey(this,"BeeClound"),
                CommonUtils.GetValueByKey(this,"LiveSecret"));
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                Log.v("viclee", activity + "onActivityStopped");
                count--;
                if (count == 0) {
                    Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.v("viclee", activity + "onActivityStarted");
                if (count == 0) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                    Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                    if (preferences.getBoolean("isstartgesture", false)) {
                        actionSecondActivity(LockMode.VERIFY_PASSWORD, activity);
                    } else {
                    }
                }
                count++;

//                if (preferences.getBoolean("isstartgesture", false)) {
//
//                } else {
//                    if (activity.equals(SecondActivity.class)) {
//                    } else {
//                        actionSecondActivity(LockMode.VERIFY_PASSWORD, activity);
//                    }
//                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.v("viclee", activity + "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.v("viclee", activity + "onActivityResumed");
//                if(out)
//                {
//                    actionSecondActivity(LockMode.VERIFY_PASSWORD, activity);
//                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.v("viclee", activity + "onActivityPaused");
                out = true;
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.v("viclee", activity + "onActivityDestroyed");
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.v("viclee", activity + "onActivityCreated");
            }
        });
    }

    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode, Activity activity) {

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
        activity.startActivityForResult(intent, 1);
    }

}
