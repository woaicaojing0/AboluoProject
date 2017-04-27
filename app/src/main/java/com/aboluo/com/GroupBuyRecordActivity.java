package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2017/4/27.
 * 团购记录界面
 */

public class GroupBuyRecordActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private XRecyclerView recycle_groupBuyRecord;
    private int currentPage;
    private static int PageSize = 10;
    private int TeamBuyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuyrecord);
        initComponent();
    }

    private void initComponent() {
        recycle_groupBuyRecord = (XRecyclerView) findViewById(R.id.recycle_groupBuyRecord);
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
        TeamBuyId = intent.getIntExtra("TeamBuyId", 0);
        //recyclerview 初始化设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycle_groupBuyRecord.setLayoutManager(linearLayoutManager);
        recycle_groupBuyRecord.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycle_groupBuyRecord.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                initData(currentPage);
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                initData(currentPage);
            }
        });
        initData(1);
    }

    private void initData(final int currentPage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/TeamBuyApi/TeamBuyRecords", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("groupBuyRecordInfo", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytes = error.networkResponse.data;
                Log.d("groupBuyRecordError", new String(bytes));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("TeamBuyId", String.valueOf(TeamBuyId));
                map.put("PageIndex", String.valueOf(currentPage));
                map.put("PageSize", String.valueOf(PageSize));
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
