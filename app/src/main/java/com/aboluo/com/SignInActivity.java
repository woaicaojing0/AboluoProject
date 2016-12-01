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
import com.aboluo.fragment.IndexFragment;
import com.aboluo.fragment.ShopCarFragment;
import com.aboluo.model.BaseModel;
import com.aboluo.model.MessageInfo;
import com.aboluo.model.SignInfoBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Single;

/**
 * Created by cj34920 on 2016/12/1.
 */

public class SignInActivity extends Activity implements View.OnClickListener {
    private TextView txt_signInfo, txt_signvalue;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Picasso picasso;
    private Gson gson;
    private String MemberId;
    private SweetAlertDialog pdialog;
    private LinearLayout unalready_signed, already_signed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();
        txt_signInfo.setOnClickListener(this);
        unalready_signed.setOnClickListener(this);
        if (MemberId == "0") {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            GetTodayScroe();
        }
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        txt_signInfo = (TextView) findViewById(R.id.txt_signInfo);
        txt_signvalue = (TextView) findViewById(R.id.txt_signvalue);
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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_signInfo:
                Intent intent = new Intent(SignInActivity.this, SignInInfoActivity.class);
                startActivity(intent);
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
                MessageInfo messageInfo = gson.fromJson(response, MessageInfo.class);
                pdialog.dismiss();
                if (messageInfo.isIsSuccess()) {
                    unalready_signed.setVisibility(View.GONE);
                    already_signed.setVisibility(View.VISIBLE);
                } else {
                }
                Toast.makeText(SignInActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    private void GetTodayScroe() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/RecieveTodayScore", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //"{\"IsSuccess\":false,\"Message\":\"今日已签到\",\"Result\":1}"
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                SignInfoBean signInfoBean = gson.fromJson(response, SignInfoBean.class);
                if (signInfoBean.getResult()==1) {
                    unalready_signed.setVisibility(View.VISIBLE);
                    already_signed.setVisibility(View.GONE);
                    txt_signvalue.setText("+"+String.valueOf(signInfoBean.getMemberScoreLogEntity().getSLScoreValue()));

                } else {
                    unalready_signed.setVisibility(View.GONE);
                    already_signed.setVisibility(View.VISIBLE);
                    Toast.makeText(SignInActivity.this, "您已签到！", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
//        InitSign();
    }
}
