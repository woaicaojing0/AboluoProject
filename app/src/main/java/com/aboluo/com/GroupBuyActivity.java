package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aboluo.Interface.OnRecyclerViewItemClickListener;
import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.GroupBuyAdapter;
import com.aboluo.model.GroupBuyBean;
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
 * Created by cj on 2017/4/25.
 * 团购界面
 */

public class GroupBuyActivity extends Activity implements View.OnClickListener, OnRecyclerViewItemClickListener {

    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private LinearLayout btn_groupbuy_2, btn_groupbuy_3, btn_groupbuy_4;
    private XRecyclerView recycler_groupbuy;
    private GroupBuyBean groupBuyBean;
    private int currentPage;
    private int currentState;
    private List<GroupBuyBean.ListResultBean> listResultBean;
    private GroupBuyAdapter groupBuyAdapter;
    private ImageView iv_groupbuy_back;
    private View recycle_empty_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuy);
        currentPage = 1;
        currentState = 2;
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
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(true);
        btn_groupbuy_2 = (LinearLayout) findViewById(R.id.btn_groupbuy_2);
        btn_groupbuy_3 = (LinearLayout) findViewById(R.id.btn_groupbuy_3);
        btn_groupbuy_4 = (LinearLayout) findViewById(R.id.btn_groupbuy_4);
        iv_groupbuy_back = (ImageView) findViewById(R.id.iv_groupbuy_back);
        recycle_empty_item = findViewById(R.id.recycle_empty_item);
        btn_groupbuy_2.setOnClickListener(this);
        btn_groupbuy_3.setOnClickListener(this);
        btn_groupbuy_4.setOnClickListener(this);
        iv_groupbuy_back.setOnClickListener(this);
        recycler_groupbuy = (XRecyclerView) findViewById(R.id.recycler_groupbuy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_groupbuy.setLayoutManager(linearLayoutManager);
        loadData(currentState, 1);
        recycler_groupbuy.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycler_groupbuy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadData(currentState, currentPage);
            }

            @Override
            public void onLoadMore() {
                // load more data here
                currentPage++;
                loadData(currentState, currentPage);
            }
        });
        btn_groupbuy_2.setBackgroundColor(Color.WHITE);
    }

    /**
     * @param TeamBuyState 团购状态
     * @param currentPage  ，当前页数
     */
    private void loadData(final int TeamBuyState, final int currentPage) {
        pdialog.dismiss();
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/TeamBuyApi/ReceivTeamBuyList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                groupBuyBean = gson.fromJson(response, GroupBuyBean.class);
                List<GroupBuyBean.ListResultBean> newList = groupBuyBean.getListResult();
                pdialog.dismiss();
                if (currentPage == 1) {
                    listResultBean = newList;
                    groupBuyAdapter = new GroupBuyAdapter(listResultBean, GroupBuyActivity.this);
                    groupBuyAdapter.setOnItemClickListener(GroupBuyActivity.this);
                    recycler_groupbuy.setAdapter(groupBuyAdapter);
                    recycler_groupbuy.setEmptyView(recycle_empty_item);
                    recycler_groupbuy.refreshComplete();
                } else {
                    if (listResultBean == null) {
                        listResultBean = newList;
                        groupBuyAdapter = new GroupBuyAdapter(listResultBean, GroupBuyActivity.this);
                        recycler_groupbuy.setAdapter(groupBuyAdapter);
                        groupBuyAdapter.setOnItemClickListener(GroupBuyActivity.this);
                    } else {
                        listResultBean.addAll(newList);
                        groupBuyAdapter.notifyDataSetChanged();
                        groupBuyAdapter.setOnItemClickListener(GroupBuyActivity.this);
                    }
                    recycler_groupbuy.loadMoreComplete();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] bytes = error.networkResponse.data;
//                Log.e("GroupBuyActivityError", new String(bytes));
                pdialog.dismiss();
                recycler_groupbuy.refreshComplete();
                recycler_groupbuy.loadMoreComplete();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("TeamBuyState", String.valueOf(TeamBuyState));
                map.put("PageIndex", String.valueOf(currentPage));
                map.put("PageSize", String.valueOf(8));
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void cleanButton() {
        btn_groupbuy_2.setBackgroundColor(Color.parseColor("#eeeeee"));
        btn_groupbuy_3.setBackgroundColor(Color.parseColor("#eeeeee"));
        btn_groupbuy_4.setBackgroundColor(Color.parseColor("#eeeeee"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_groupbuy_2:
                cleanButton();
                btn_groupbuy_2.setBackgroundColor(Color.WHITE);
                currentPage = 1;
                currentState = 2;
                loadData(currentState, currentPage);
                break;
            case R.id.btn_groupbuy_3:
                cleanButton();
                btn_groupbuy_3.setBackgroundColor(Color.WHITE);
                currentPage = 1;
                currentState = 3;
                loadData(currentState, currentPage);
                break;
            case R.id.btn_groupbuy_4:
                cleanButton();
                btn_groupbuy_4.setBackgroundColor(Color.WHITE);
                currentPage = 1;
                currentState = 4;
                loadData(currentState, currentPage);
                break;
            case R.id.iv_groupbuy_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, Object postion) {
        //Toast.makeText(this, postion.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(GroupBuyActivity.this, GroupBuyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("groupBuyBean", listResultBean.get((Integer) postion));
        bundle.putInt("currentState", currentState);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
