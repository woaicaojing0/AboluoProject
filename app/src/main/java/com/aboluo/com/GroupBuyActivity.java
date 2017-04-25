package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.LinearLayout;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.RBCallbkRecyclerView2;
import com.aboluo.adapter.GroupBuyAdapter;
import com.aboluo.model.GroupBuyBean;
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
 * Created by cj on 2017/4/25.
 * 团购界面
 */

public class GroupBuyActivity extends Activity {

    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private LinearLayout btn_groupbuy_2, btn_groupbuy_3, btn_groupbuy_4;
    private RBCallbkRecyclerView2 recycler_groupbuy;
    private GroupBuyBean groupBuyBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuy);
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
        btn_groupbuy_2 = (LinearLayout) findViewById(R.id.btn_groupbuy_2);
        btn_groupbuy_3 = (LinearLayout) findViewById(R.id.btn_groupbuy_3);
        btn_groupbuy_4 = (LinearLayout) findViewById(R.id.btn_groupbuy_4);
        recycler_groupbuy = (RBCallbkRecyclerView2) findViewById(R.id.recycler_groupbuy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_groupbuy.setLayoutManager(linearLayoutManager);
        initData(2, 1);
    }

    private void initData(final int TeamBuyState, final int currentPage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/TeamBuyApi/ReceivTeamBuyList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                groupBuyBean = gson.fromJson(response, GroupBuyBean.class);
                GroupBuyAdapter groupBuyAdapter = new GroupBuyAdapter(groupBuyBean.getListResult(), GroupBuyActivity.this);
                recycler_groupbuy.setAdapter(groupBuyAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytes = error.networkResponse.data;
                Log.e("GroupBuyActivityError", new String(bytes));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("TeamBuyState", String.valueOf(TeamBuyState));
                map.put("TId", "1");
                map.put("PageIndex", String.valueOf(currentPage));
                map.put("PageSize", String.valueOf(10));
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }
}
