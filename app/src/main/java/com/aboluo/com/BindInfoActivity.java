package com.aboluo.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ValidateUtils;
import com.aboluo.broadcast.SMSBroadcastReceiver;
import com.aboluo.model.BindMsgBean;
import com.aboluo.model.MessageInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

/**
 * Created by CJ on 2017/2/16.
 */

public class BindInfoActivity extends Activity implements View.OnClickListener, TextWatcher {
    private int bindType;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private TextView tv_toolbar;
    private EditText et_bind_phone_email, et_bind_auth;
    private Button btn_bindinfo, btn_getbindinfo;
    private CountDownTimer time;
    private BindMsgBean messageInfo;
    private SharedPreferences preferences;
    private SMSBroadcastReceiver mSMSBroadcastReceiver;  //短信的广播监听

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindinfo);
        init();
        btn_getbindinfo.setOnClickListener(this);
        btn_bindinfo.setOnClickListener(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象register_edit_auth.addTextChangedListener(this);
        et_bind_phone_email.addTextChangedListener(this);
        et_bind_auth.addTextChangedListener(this);
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
                et_bind_auth.setText(message);
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        bindType = intent.getIntExtra("bindType", -1);
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
        et_bind_phone_email = (EditText) findViewById(R.id.et_bind_phone_email);
        et_bind_auth = (EditText) findViewById(R.id.et_bind_auth);
        btn_bindinfo = (Button) findViewById(R.id.btn_bindinfo);
        btn_getbindinfo = (Button) findViewById(R.id.btn_getbindinfo);
        if (bindType == 1) //绑定手机号
        {
            tv_toolbar.setText("绑定手机号");
            et_bind_phone_email.setHint("请输入手机号");
        } else {
            tv_toolbar.setText("绑定邮箱");
            et_bind_phone_email.setHint("请输入邮箱");
        }
        preferences = BindInfoActivity.this.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE);
    }

    private void btnBindInfo() {
        final String phone_email = et_bind_phone_email.getText().toString().trim();
        final String yanzhengma = et_bind_auth.getText().toString().trim();
        String url = "";
        if (bindType == 1) {
            url = "/api/MemberApi/BindPhoneNumber";
        } else {
            url = "/api/MemberApi/BindEmailFun";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                MessageInfo messageBean = gson.fromJson(response, MessageInfo.class);
                //修改成功之后
                if (messageBean.isIsSuccess()) {
                    Toast.makeText(BindInfoActivity.this, messageBean.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    intent.putExtra("bindtype", "phone");
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(BindInfoActivity.this, messageBean.getMessage() + "请重新绑定", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BindInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("MsgCode", yanzhengma);
                if (bindType == 1) {
                    map.put("BindPhoneNumber", phone_email);
                } else {
                    map.put("BindEmailAccount", phone_email);
                }
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //通过手机号码获取验证码
    private void GetPhoneAuth(final String number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/MemberApi/BindPhoneNumberSendMsg",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, BindMsgBean.class);
                        if (messageInfo.isIsSuccess()) {
                            //发送短信成功后，将发送的短信的手机号存入到shareperferences中，防止用户退出应用
                            //重新进入app进行注册，退出了应用，如果不存本地，无法判断。
                            //SharedPreferences.Editor editor = preferences.edit();
                            //editor.putString("bindPhoneNumber", messageInfo.getResult().getSendPhoneNumber().toString());
                            //editor.commit();
                            time.start();
                            btn_getbindinfo.setEnabled(false);
                        } else {
                        }
                        Toast.makeText(BindInfoActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] htmlBodyBytes = error.networkResponse.data;
//                //Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
                Toast.makeText(BindInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                map.put("MemberId", MemberId);
                map.put("BindPhoneNumber", number);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetEmailAuth(final String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL
                + "/api/MemberApi/BindEmailNumberSendMsg",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, BindMsgBean.class);
                        if (messageInfo.isIsSuccess()) {
                            btn_getbindinfo.setEnabled(false);
                            time.start();
                        } else {
                        }
                        Toast.makeText(BindInfoActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BindInfoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                map.put("MemberId", MemberId);
                map.put("BindEmailAccount", email);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bindinfo:
                btnBindInfo();
                break;
            case R.id.btn_getbindinfo:
                final String number = et_bind_phone_email.getText().toString().trim();
                if (ValidateUtils.isMobileNO(number)) {
                    GetPhoneAuth(number);
                } else {
                    if (ValidateUtils.isEmail(number)) {
                        GetEmailAuth(number);
                    } else {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("提示")
                                .setContentText("您输入的手机号或邮箱有误!")
                                .setConfirmText("确定")
                                .show();
                    }
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean isok = false;
        if (ValidateUtils.isMobileNO(et_bind_phone_email.getText().toString().trim())) {
            if (et_bind_auth.getText().length() > 5) {
                isok = true;
            }
        } else {
            if (ValidateUtils.isEmail(et_bind_phone_email.getText().toString().trim())) {
                if (et_bind_auth.getText().length() > 5) {
                    isok = true;
                }
            }

        }
        btn_bindinfo.setEnabled(isok);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_getbindinfo.setText("重新验证");
            btn_getbindinfo.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_getbindinfo.setEnabled(false);
            btn_getbindinfo.setText(millisUntilFinished / 1000 + "秒重新发送");
        }
    }
}
