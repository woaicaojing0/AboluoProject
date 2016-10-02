package com.aboluo.com;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.broadcast.SMSBroadcastReceiver;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/9/21.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    private View btn_getinfo;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;
    private EditText register_edit_auth, register_edit_phone;
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        init();
        btn_getinfo.setOnClickListener(this);
    }

    private void init() {
        btn_getinfo = findViewById(R.id.btn_getinfo);
        register_edit_auth = (EditText) findViewById(R.id.register_edit_auth);
        register_edit_phone = (EditText) findViewById(R.id.register_edit_phone);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSMSBroadcastReceiver = new SMSBroadcastReceiver();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(ACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);
        //注册广播
        this.registerReceiver(mSMSBroadcastReceiver, intentFilter);
        mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
            @Override
            public void onReceived(String message) {

                register_edit_auth.setText(message);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getinfo:
                final String number = register_edit_phone.getText().toString().trim();
                final String apptoken = MyApplication.APPToken;
                if (CommonUtils.isMobileNO(number)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://m.abl.weidustudio.com/api/Login/SendMessage",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("UserLoginNumber", number);
                            map.put("APPToken", apptoken);
                            return map;
                        }
                    };
                    MyApplication.getRequestQueue().add(stringRequest);
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText("您输入的手机号有误!")
                            .setConfirmText("确定")
                            .show();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mSMSBroadcastReceiver);
    }
}
