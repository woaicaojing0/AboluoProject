package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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
 * Created by cj on 2017/5/1.
 */

public class UnaryRecordActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private int purchaseId;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unaryrecord);
        init();
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl3");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        purchaseId = intent.getIntExtra("PurchaseId", 0);
        currentPage = 1;
        initData(currentPage);
    }

    private void initData(final int currentPage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/ api/OnePurchaseApi/ReceiveOnePurchaseRecords", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("UnaryRecordInfo", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytes = error.networkResponse.data;
                Log.d("Error", new String(bytes));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("PurchaseId", String.valueOf(purchaseId));
                map.put("PageIndex", String.valueOf(currentPage));
                map.put("PageSize", "10");
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }
}
