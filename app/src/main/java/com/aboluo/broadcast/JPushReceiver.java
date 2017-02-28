package com.aboluo.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aboluo.XUtils.DataHelper;
import com.aboluo.XUtils.TimeUtils;
import com.aboluo.com.MainActivity;
import com.aboluo.com.MessageActivity;
import com.aboluo.model.MessageBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by CJ on 2016/12/23.
 */

public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            System.out.println("收到了自定义消息@@消息内容是:" + content);
            System.out.println("收到了自定义消息@@消息extra是:" + extra);

            //**************解析推送过来的json数据并存放到集合中 begin******************
            Map<String, Object> map = new HashMap<String, Object>();
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(extra);
                String type = jsonObject.getString("type");
                map.put("type", type);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map.put("content", content);
            //获取接收到推送时的系统时间
            Calendar rightNow = Calendar.getInstance();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String date = fmt.format(rightNow.getTime());
            //**************解析推送过来的json数据并存放到集合中 end******************
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            System.out.println("收到了通知");
            Bundle extras = intent.getExtras();
            String ss = extras.getString(JPushInterface.EXTRA_ALERT);//获取通知的消息
            String extraJson = extras.getString(JPushInterface.EXTRA_EXTRA);
            //**************解析推送过来的json数据并存放到集合中 begin******************
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(extraJson);
                String type = jsonObject.getString("aboluoLogin");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            //Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle extras = intent.getExtras();
            if (null == extras)
                return;
            String ss = extras.getString(JPushInterface.EXTRA_ALERT);//获取通知的消息
            String extraJson = extras.getString(JPushInterface.EXTRA_EXTRA);
//         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);
            DataHelper dataHelper = new DataHelper(context);
            MessageBean messageBean = new MessageBean();
            messageBean.setCreatetime(TimeUtils.date2String(new Date()));
            messageBean.setMessageinfo(ss);
            long result = dataHelper.SaveUserInfo(messageBean);
            if (result > 0) {
                Intent i2 = new Intent(context, MessageActivity.class); // 自定义打开的界面
                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i2);

                Log.i("JPushReceiverMessage", "JPushReceiverMessage Successfull");
            }
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
