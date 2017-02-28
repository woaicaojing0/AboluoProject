package com.aboluo.XUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.aboluo.ExceptionUtils.Cockroach;
import com.aboluo.Gesture.SecondActivity;
import com.aboluo.GestureUtils.Contants;
import com.baidu.wallet.core.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.leo.gesturelibray.enums.LockMode;
import com.pgyersdk.crash.PgyCrashManager;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;

import java.util.Set;

import cn.beecloud.BeeCloud;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.ShareSDK;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

//
//import cn.beecloud.BeeCloud;

/**
 * Created by CJ on 2016/11/2.
 */

public class BaseApplication extends MultiDexApplication {
    public int count = 0;
    public boolean out = false;

    // 如果返回值为null，则全部使用默认参数。
    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化七鱼客服
        Unicorn.init(this, "219a5cea9ad19bbf6fea3add081a1d27", options(), new PicassoImageLoader()); //
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
        //String registrationid= JPushInterface.getRegistrationID(BaseApplication.this);
        //开启测试模式
        BeeCloud.setSandbox(false);
        //此处第二个参数是控制台的test secret
        BeeCloud.setAppIdAndSecret(CommonUtils.GetValueByKey(this, "BeeClound"),
                CommonUtils.GetValueByKey(this, "LiveSecret"));
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
                MyApplication.init(BaseApplication.this);
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
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            //Toast.makeText(BaseApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                            Toast.makeText(BaseApplication.this,"当前网路异常，请退出应用重试",Toast.LENGTH_SHORT).show();
                        } catch (Throwable e) {
                        }
                    }
                });
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
