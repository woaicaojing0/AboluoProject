package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.BaseSignInBean;
import com.aboluo.model.SignInfoBean;
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

/**
 * Created by cj34920 on 2016/12/1.
 */

public class SignInActivity extends Activity implements View.OnClickListener {
    private TextView txt_signInfo, txt_signvalue, txt_alreadyday, txt_againday;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Picasso picasso;
    private Gson gson;
    private String MemberId;
    private SweetAlertDialog pdialog;
    private LinearLayout unalready_signed, already_signed;
    private SignInfoBean signInfoBean;
    private boolean isfrist = true;
    private static final int signinfocode = 1;
    private boolean isfromsigninfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();
        if (MemberId == "0") {
            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            txt_signInfo.setOnClickListener(this);
            unalready_signed.setOnClickListener(this);
            GetTodayScroe();
        }
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        txt_signInfo = (TextView) findViewById(R.id.txt_signInfo);
        txt_signvalue = (TextView) findViewById(R.id.txt_signvalue);
        txt_alreadyday = (TextView) findViewById(R.id.txt_alreadyday);
        txt_againday = (TextView) findViewById(R.id.txt_againday);
        unalready_signed = (LinearLayout) findViewById(R.id.unalready_signed);
        already_signed = (LinearLayout) findViewById(R.id.already_signed);
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
        //signInfoBean= new SignInfoBean();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_signInfo:
                Intent intent = new Intent(SignInActivity.this, SignInInfoActivity.class);
                startActivityForResult(intent, signinfocode);
                break;
            case R.id.unalready_signed:
                InitSign();
        }

    }

    //签到操作
    public void InitSign() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/RecieveSignScore", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //"{\"IsSuccess\":false,\"Message\":\"今日已签到\",\"Result\":1}"
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseSignInBean messageInfo = gson.fromJson(response, BaseSignInBean.class);
                pdialog.dismiss();
                if (messageInfo.getResult() == 2) {
                    unalready_signed.setVisibility(View.GONE);
                    already_signed.setVisibility(View.VISIBLE);
                    txt_alreadyday.setText(String.valueOf(signInfoBean.getContineSignDay() + 1));
                    txt_againday.setText(String.valueOf(signInfoBean.getTotalDay() - signInfoBean.getContineSignDay() - 1));
                } else {
                }
                Toast.makeText(SignInActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] date = error.networkResponse.data;
                String s = new String(date);
                Toast.makeText(SignInActivity.this, new String(date), Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put( "APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetTodayScroe() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/RecieveTodayScore", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                signInfoBean = gson.fromJson(response, SignInfoBean.class);
                if (signInfoBean.getResult() == 2) {
                    unalready_signed.setVisibility(View.VISIBLE);
                    already_signed.setVisibility(View.GONE);
                    txt_alreadyday.setText(String.valueOf(signInfoBean.getContineSignDay()));
                    txt_againday.setText(String.valueOf(signInfoBean.getTotalDay() - signInfoBean.getContineSignDay()));
                    txt_signvalue.setText("+" + String.valueOf(signInfoBean.getForenScoreConfig()));

                } else {
                    unalready_signed.setVisibility(View.GONE);
                    already_signed.setVisibility(View.VISIBLE);
                    txt_alreadyday.setText(String.valueOf(signInfoBean.getContineSignDay() + 1));
                    txt_againday.setText(String.valueOf(signInfoBean.getTotalDay() - signInfoBean.getContineSignDay() - 1));
                    Toast.makeText(SignInActivity.this, "您已签到！", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                byte[] date = error.networkResponse.data;
                String s= new String (date);
                Toast.makeText(SignInActivity.this, new String(date), Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isfrist) {
            isfrist = false;
        } else {
            MemberId = CommonUtils.GetMemberId(this);
            if (MemberId == "0") {
                finish();
            } else {
                if (isfromsigninfo) { //是否从 签到攻略跳转的
                } else {
                    isfrist = true;
                    txt_signInfo.setOnClickListener(this);
                    unalready_signed.setOnClickListener(this);
                    GetTodayScroe();
                }
            }

        }
//        InitSign();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case signinfocode:
                isfromsigninfo = true;
                break;
            default:
                break;
        }
    }
}
