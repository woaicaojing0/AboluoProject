package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
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
 * Created by cj34920 on 2016/12/28.
 * 用户输入邀请码的界面
 */

public class InvitationCodeActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private Button btn_invitationsubmit;
    private EditText invitation_code_detail;
    private ImageView invitation_code_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_code);
        init();
        invitation_code_back.setOnClickListener(this);
        btn_invitationsubmit.setOnClickListener(this);
    }

    private void init() {
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
        btn_invitationsubmit = (Button) findViewById(R.id.btn_invitationsubmit);
        invitation_code_detail = (EditText) findViewById(R.id.invitation_code_detail);
        invitation_code_back = (ImageView) findViewById(R.id.invitation_code_back);
        initData();
    }

    private void initData() {
        String result = invitation_code_detail.getText().toString();
        if (result.length() == 0) {
            Toast.makeText(this, "请输入邀请码", Toast.LENGTH_SHORT).show();
        } else {
            pdialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response = response.replace("\\", "");
                    response = response.substring(1, response.length() - 1);
                    pdialog.dismiss();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pdialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("MemberId", "1975");
                    map.put("OrderId", "508");
                    map.put("ExpressId", "1");
                    map.put("APPToken", APPToken);
                    return map;
                }

                ;
            };
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_invitationsubmit:
                initData();
                break;
            case R.id.invitation_code_back:
                finish();
                break;
        }
    }
}
