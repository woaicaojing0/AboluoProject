package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.UnaryRecordAdapter;
import com.aboluo.model.UnaryRecordBean;
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
import java.util.List;
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
    private UnaryRecordAdapter unaryRecordAdapter;
    private XRecyclerView recycle_unaryRecord;
    private RelativeLayout rl_show_nodata;
    private LinearLayout ll_show_data;
    private UnaryRecordBean unaryRecordBean;
    private List<UnaryRecordBean.ListResultBean> listResultBeen;
    private ImageView iv_unaryrecord_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unaryrecord);
        init();
        iv_unaryrecord_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        recycle_unaryRecord = (XRecyclerView) findViewById(R.id.recycle_unaryRecord);
        rl_show_nodata = (RelativeLayout) findViewById(R.id.rl_unary_show_nodata);
        ll_show_data = (LinearLayout) findViewById(R.id.ll_unary_show_data);
        iv_unaryrecord_back = (ImageView) findViewById(R.id.iv_unaryrecord_back);
        //recyclerview 初始化设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycle_unaryRecord.setLayoutManager(linearLayoutManager);
        recycle_unaryRecord.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycle_unaryRecord.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        initData(currentPage);
    }

    private void initData(final int currentPage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnePurchaseApi/ReceiveOnePurchaseRecords", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("UnaryRecordInfo", response);
                Log.i("groupBuyRecordInfo", response);
                unaryRecordBean = gson.fromJson(response, UnaryRecordBean.class);
                if (unaryRecordBean.isIsSuccess()) {
                    List<UnaryRecordBean.ListResultBean> newList = unaryRecordBean.getListResult();
                    if (currentPage == 1) {
                        if (unaryRecordBean.getListResult().size() == 0) {
                            ll_show_data.setVisibility(View.GONE);
                            rl_show_nodata.setVisibility(View.VISIBLE);
                            return;
                        }
                        listResultBeen = newList;
                        unaryRecordAdapter = new UnaryRecordAdapter(UnaryRecordActivity.this, listResultBeen);
                        recycle_unaryRecord.setAdapter(unaryRecordAdapter);
                        recycle_unaryRecord.refreshComplete();
                    } else {
                        if (unaryRecordBean == null) {
                            listResultBeen = newList;
                            unaryRecordAdapter = new UnaryRecordAdapter(UnaryRecordActivity.this, listResultBeen);
                            recycle_unaryRecord.setAdapter(unaryRecordAdapter);
                        } else {
                            listResultBeen.addAll(newList);
                            unaryRecordAdapter.notifyDataSetChanged();
                        }
                        recycle_unaryRecord.loadMoreComplete();
                    }
                } else {
                    Toast.makeText(UnaryRecordActivity.this, unaryRecordBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
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
