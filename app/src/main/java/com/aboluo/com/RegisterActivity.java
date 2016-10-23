package com.aboluo.com;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ValidateUtils;
import com.aboluo.broadcast.SMSBroadcastReceiver;
import com.aboluo.model.RegisterInfo;
import com.aboluo.model.MessageInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/9/21.
 */

public class RegisterActivity extends Activity implements View.OnClickListener,TextWatcher {
    private Button btn_getinfo; // 获取验证码的按钮
    private SMSBroadcastReceiver mSMSBroadcastReceiver;  //短信的广播监听
    private EditText register_edit_auth, register_edit_phone, register_edit_pwd;
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private RequestQueue requestQueue;
    private Button btn_register;    //注册按钮
  private CountDownTimer time;
    private  MessageInfo messageInfo;
    private static  String URL =null;
    private static  String APPToken = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        init();
        btn_getinfo.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        register_edit_auth.addTextChangedListener(this);
        register_edit_phone.addTextChangedListener(this);
        register_edit_pwd.addTextChangedListener(this);
    }

    private void init() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        btn_getinfo = (Button) findViewById(R.id.btn_getinfo);
        register_edit_auth = (EditText) findViewById(R.id.register_edit_auth);
        register_edit_phone = (EditText) findViewById(R.id.register_edit_phone);
        register_edit_pwd = (EditText) findViewById(R.id.register_edit_pwd);
        btn_register = (Button) findViewById(R.id.btn_register);
        requestQueue = MyApplication.getRequestQueue();
        URL =CommonUtils.GetValueByKey(this,"apiurl");
        APPToken =CommonUtils.GetValueByKey(this,"APPToken");
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
                if (ValidateUtils.isMobileNO(number)) {
                    time.start();
                    btn_getinfo.setEnabled(false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/Login/SendMessage",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("TAG",response);
                                    Gson gson = new Gson();
                                    response=response.replace("\\", "");//去掉'/'
                                    response=response.substring(1, response.length()-1); //去掉头尾引号。
                                    messageInfo=  gson.fromJson(response, MessageInfo.class);
                                    Toast.makeText(RegisterActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
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
                            map.put("APPToken", APPToken);
                            return map;
                        }
                    };
                   requestQueue.add(stringRequest);
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText("您输入的手机号有误!")
                            .setConfirmText("确定")
                            .show();
                }
                break;
            case R.id.btn_register:
                final String number2 = register_edit_phone.getText().toString().trim();
                final String yanzhengma = register_edit_auth.getText().toString().trim();
                final String pwd = register_edit_pwd.getText().toString().trim();
                if(ValidateUtils.isMiMaRight(pwd))
                {
                    if(!messageInfo.getResult().getSendPhoneNumber().equals(number2))
                    {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("提示")
                                .setContentText("请输入正确的手机号!")
                                .setConfirmText("确定")
                                .show();
                    }else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/Login/UserRegister", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("TAG", response);
                                Gson gson = new Gson();
                                response = response.replace("\\", "");//去掉'/'
                                response = response.substring(1, response.length() - 1); //去掉头尾引号。
                                RegisterInfo registerInfo = gson.fromJson(response, RegisterInfo.class);
                                Toast.makeText(RegisterActivity.this, registerInfo.getMessage(), Toast.LENGTH_SHORT).show();
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
                                map.put("UserLoginNumber", number2);
                                map.put("UserLoginPassword", CommonUtils.getMD5(pwd));
                                map.put("MessageCheckNumber", yanzhengma);
                                map.put("APPToken", APPToken);
                                map.put("LoginCheckToken", "");
                                map.put("LoginPhone", number2);
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
                else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText("请输入正确格式的密码!")
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean isok = false;
        if(ValidateUtils.isMobileNO(register_edit_phone.getText().toString().trim()))
        {
            if(register_edit_pwd.getText().length() >5)
            {
                isok = true;
            }
        }
        btn_register.setEnabled(isok);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            btn_getinfo.setText("重新验证");
            btn_getinfo.setEnabled(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            btn_getinfo.setEnabled(false);
            btn_getinfo.setText(millisUntilFinished /1000+"秒重新发送");
        }
    }
}
