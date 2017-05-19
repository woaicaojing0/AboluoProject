package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.GroupBuyRecordAdapter;
import com.aboluo.adapter.MyGroupBuyRecordAdapter;
import com.aboluo.model.GroupBuyRecordBean;
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
 * Created by cj34920 on 2017/5/18.
 */

public class MyGroupRecordActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private GroupBuyRecordBean groupBuyRecordBean;
    private List<GroupBuyRecordBean.GroupBuyRecordItemBean> groupBuyRecordItemBean;
    private int currentPage;
    private XRecyclerView recycle_my_group;
    private RelativeLayout rl_group_show_nodata;
    private LinearLayout ll_group_show_data;
    private MyGroupBuyRecordAdapter myGroupBuyRecordAdapter;
    private TextView tv_my_group_record_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group_record);
        init();
        tv_my_group_record_back.setOnClickListener(new View.OnClickListener() {
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
        recycle_my_group = (XRecyclerView) findViewById(R.id.recycle_my_group);
        rl_group_show_nodata = (RelativeLayout) findViewById(R.id.rl_group_show_nodata);
        ll_group_show_data = (LinearLayout) findViewById(R.id.ll_group_show_data);
        tv_my_group_record_back = (TextView) findViewById(R.id.tv_my_group_record_back);
        //recyclerview 初始化设置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycle_my_group.setLayoutManager(linearLayoutManager);
        recycle_my_group.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycle_my_group.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                groupBuyRecordBean = gson.fromJson(response, GroupBuyRecordBean.class);
                if (groupBuyRecordBean.isIsSuccess()) {
                    List<GroupBuyRecordBean.GroupBuyRecordItemBean> newList = groupBuyRecordBean.getListResult();
                    if (currentPage == 1) {
                        if (groupBuyRecordBean.getListResult().size() == 0) {
                            ll_group_show_data.setVisibility(View.GONE);
                            rl_group_show_nodata.setVisibility(View.VISIBLE);
                            return;
                        }
                        groupBuyRecordItemBean = newList;
                        myGroupBuyRecordAdapter = new MyGroupBuyRecordAdapter(MyGroupRecordActivity.this, groupBuyRecordItemBean);
                        recycle_my_group.setAdapter(myGroupBuyRecordAdapter);
                        recycle_my_group.refreshComplete();
                    } else {
                        if (groupBuyRecordBean == null) {
                            groupBuyRecordItemBean = newList;
                            myGroupBuyRecordAdapter = new MyGroupBuyRecordAdapter(MyGroupRecordActivity.this, groupBuyRecordItemBean);
                            recycle_my_group.setAdapter(myGroupBuyRecordAdapter);
                        } else {
                            if (newList.size() == 0) {
                                Toast.makeText(MyGroupRecordActivity.this, "已经到底了！", Toast.LENGTH_SHORT).show();
                                recycle_my_group.noMoreLoading();
                                recycle_my_group.loadMoreComplete();
                                return;
                            }
                            groupBuyRecordItemBean.addAll(newList);
                            myGroupBuyRecordAdapter.notifyDataSetChanged();
                        }
                        recycle_my_group.loadMoreComplete();
                    }
                } else {
                    Toast.makeText(MyGroupRecordActivity.this, groupBuyRecordBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                map.put("PageIndex", String.valueOf(currentPage));
                map.put("PageSize", "10");
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
               map.put("MemberId", MemberId);
//                map.put("MemberId", "2174");
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }
}
