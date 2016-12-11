package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.UnaryPublishAdapter;
import com.aboluo.model.UnaryListBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/12/9.
 */

public class UnaryPublishActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private PullToRefreshListView unary_publish_listview;
    private int InitPage = 1;
    private UnaryListBean unaryListBean;
    private UnaryPublishAdapter unaryPublishAdapter;
    private List<UnaryListBean.ListResultBean> listResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unary_piblish);
        init();
        unary_publish_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                //这里写下拉刷新的任务onRefreshComplete
                initData(1);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh");
                //这里写上拉加载更多的任务
                InitPage++;
                initData(InitPage);
            }
        });
    }

    private void init() {
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
        unary_publish_listview = (PullToRefreshListView) findViewById(R.id.unary_publish_listview);
        initData(InitPage);
    }

    private void initData(final int currentpage) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    listResult = unaryListBean.getListResult();
                    unaryPublishAdapter = new UnaryPublishAdapter(UnaryPublishActivity.this,
                            listResult, ImageUrl);
                    unary_publish_listview.setAdapter(unaryPublishAdapter);
                } else {
                    Toast.makeText(UnaryPublishActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
                unary_publish_listview.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] errorcode = error.networkResponse.data;
                Log.i("woaicaojing0", errorcode.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("State", "3");
                map.put("IsPaging", "true");
                map.put("CurrentPage", String.valueOf(currentpage));
                map.put("PageSize", "20");
                map.put("SortValue", "finishTime");
                map.put("SortType", "asc");
                map.put("TopCount", "0");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }
}
