package com.aboluo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.aboluo.XUtils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CJ on 2016/10/2.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {
    private MessageListener mMessageListener;
    public void setOnReceivedMessageListener(MessageListener messageListener) {
        this.mMessageListener = messageListener;
    }
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {

            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            for(Object pdu:pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte [])pdu);
                String sender = smsMessage.getDisplayOriginatingAddress();
                //短信内容
                String content = smsMessage.getDisplayMessageBody();
                long date = smsMessage.getTimestampMillis();
                Date tiemDate = new Date(date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(tiemDate);

                //过滤不需要读取的短信的发送号码
                if ("10690010213816481".equals(sender)) {
                    content = CommonUtils.getyzm(content,6);
                    mMessageListener.onReceived(content);
                    abortBroadcast();
                }
            }
        }
    }

    public  interface   MessageListener
    {
        public  void onReceived(String content);
    }
}
