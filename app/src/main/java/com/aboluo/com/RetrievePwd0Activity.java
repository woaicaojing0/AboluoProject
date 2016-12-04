package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ValidateUtils;
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
 * Created by CJ on 2016/12/4.
 */

public class RetrievePwd0Activity  extends Activity implements TextWatcher{
    private EditText phone_email_txt;
    private Button retrievepwd_btn_yzm;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private MessageInfo messageInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrievepwd0);
        init();
        phone_email_txt.addTextChangedListener(this);
        retrievepwd_btn_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = phone_email_txt.getText().toString().trim();
                if (ValidateUtils.isMobileNO(number)) {
                    GetPwdByPhone(number);
                } else {
                    if (ValidateUtils.isEmail(number)) {
                        GetPwdByEmail(number);
                    } else {
                        new SweetAlertDialog(RetrievePwd0Activity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("提示")
                                .setContentText("您输入的手机号或邮箱有误!")
                                .setConfirmText("确定")
                                .show();
                    }
                }

            }
        });
    }
    private void init()
    {
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        phone_email_txt = (EditText) findViewById(R.id.phone_email_txt);
        retrievepwd_btn_yzm = (Button) findViewById(R.id.retrievepwd_btn_yzm);
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
        if(ValidateUtils.isMobileNO(phone_email_txt.getText().toString()))
        {
                isok = true;
        }
        retrievepwd_btn_yzm.setEnabled(isok);
    }
    private  void GetPwdByPhone(final  String number)
    {
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
                            Intent intent = new Intent(RetrievePwd0Activity.this,Retrievepwd1Activity.class);
                            intent.putExtra("yzm",messageInfo.getResult().getMessageCode());
                            intent.putExtra("mode","phone");
                            intent.putExtra("EmailOrPhone",number);
                            startActivity(intent);
                        } else {
                        }
                        Toast.makeText(RetrievePwd0Activity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RetrievePwd0Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
    private  void GetPwdByEmail(final  String number)
    {
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
                            Intent intent = new Intent(RetrievePwd0Activity.this,Retrievepwd1Activity.class);
                            intent.putExtra("yzm",messageInfo.getResult().getMessageCode());
                            intent.putExtra("mode","email");
                            intent.putExtra("EmailOrPhone",number);
                            startActivity(intent);
                        } else {
                        }
                        Toast.makeText(RetrievePwd0Activity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RetrievePwd0Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
}
