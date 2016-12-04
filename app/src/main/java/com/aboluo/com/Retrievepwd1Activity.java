package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.MessageInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.paysdk.login.Login;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/9/21.
 */

public class Retrievepwd1Activity extends Activity implements View.OnClickListener, TextWatcher {
    private TextView retrievepwd_btn_close;
    private Button retrievepwd_btn_getinfo, retrievepwd_btn_next;
    private EditText retrievepwd_edit_yzm, retrievepwd;
    private MessageInfo messageInfo;
    private CountDownTimer time;
    private RequestQueue requestQueue;
    private static String URL = null;
    private static String APPToken = null;
    private String Mode = null;
    private String EmailOrPhone = null;
    private String yzm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.retrievepwd1);
        init();
        retrievepwd_btn_close = (TextView) findViewById(R.id.retrievepwd_btn_close);
        retrievepwd_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrievepwd1Activity.this.finish();
            }
        });
        retrievepwd_btn_next.addTextChangedListener(this);
        retrievepwd_edit_yzm.addTextChangedListener(this);
        retrievepwd_btn_next.setOnClickListener(this);
        retrievepwd_btn_getinfo.setOnClickListener(this);
        time.start();
    }

    private void init() {
        retrievepwd_btn_getinfo = (Button) findViewById(R.id.retrievepwd_btn_getinfo);
        retrievepwd_btn_next = (Button) findViewById(R.id.retrievepwd_btn_next);
        retrievepwd_edit_yzm = (EditText) findViewById(R.id.retrievepwd_edit_yzm);
        retrievepwd = (EditText) findViewById(R.id.retrievepwd);
        requestQueue = MyApplication.getRequestQueue();
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        Intent intent = getIntent();
        Mode = intent.getStringExtra("mode");
        EmailOrPhone = intent.getStringExtra("EmailOrPhone");
        yzm = intent.getStringExtra("yzm");
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
        if (retrievepwd_edit_yzm.getText().length() > 5) {
            isok = true;
        }
        retrievepwd_btn_next.setEnabled(isok);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retrievepwd_btn_getinfo:
                if(Mode == null)
                {}else {
                    if (Mode == "email") {
                        GetPwdByPhone(EmailOrPhone);
                    } else {
                        GetPwdByEmail(EmailOrPhone);
                    }
                    time.start();
                }
                break;
            case R.id.retrievepwd_btn_next:
                String UserYzm = retrievepwd_edit_yzm.getText().toString();
                String newpwd = retrievepwd.getText().toString();
                if (UserYzm.equals(yzm)) {
                    FindPwd(newpwd, UserYzm);
                } else {
                    new SweetAlertDialog(Retrievepwd1Activity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText("请输入正确的验证码!")
                            .setConfirmText("确定")
                            .show();
                }
                break;

        }
    }

    private void GetPwdByPhone(final String number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Login/FindPwdSendMessage",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, MessageInfo.class);
                        if (messageInfo.isIsSuccess()) {
                            yzm = messageInfo.getResult().getMessageCode();
                        } else {
                        }
                        Toast.makeText(Retrievepwd1Activity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Retrievepwd1Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
    }

    private void GetPwdByEmail(final String number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Login/FindPwdSendEmail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, MessageInfo.class);
                        if (messageInfo.isIsSuccess()) {
                            yzm = messageInfo.getResult().getMessageCode();
                        } else {
                        }
                        Toast.makeText(Retrievepwd1Activity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Retrievepwd1Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
    }

    private void FindPwd(final String newpwd, final String UserYzm) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Login/FindPwd",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, MessageInfo.class);
                        if (messageInfo.isIsSuccess()) {
                            Intent intent = new Intent(Retrievepwd1Activity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                        }
                        Toast.makeText(Retrievepwd1Activity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Retrievepwd1Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("UserLoginNumber", EmailOrPhone);
                map.put("UserPassword", newpwd);
                map.put("MessageCheckNumber", UserYzm);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            retrievepwd_btn_getinfo.setText("重新验证");
            retrievepwd_btn_getinfo.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            retrievepwd_btn_getinfo.setEnabled(false);
            retrievepwd_btn_getinfo.setText(millisUntilFinished / 1000 + "秒重新发送");
        }
    }
}
