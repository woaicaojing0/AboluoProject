package com.aboluo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.aboluo.com.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by CJ on 2016/12/23.
 */

public class JPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            Intent i = new Intent(context, MainActivity.class);
            Bundle bundle = new Bundle();
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);

        }
    }
}
