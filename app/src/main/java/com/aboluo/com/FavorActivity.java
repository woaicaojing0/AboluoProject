package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.FavorAdapter;
import com.aboluo.model.FavorBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.tandong.bottomview.view.BottomView;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/12/9.
 * 这是收藏页面
 */

public class FavorActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private PullToRefreshListView favor_listview;
    private String MemberId;
    private FavorBean favorBean;
    private FavorAdapter favorAdapter;
    private BottomView bottomView;
    private LinearLayout un_favor, favor_share, favor_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        init();
        favor_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FavorActivity.this, position + "item click", Toast.LENGTH_SHORT).show();
            }
        });
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
        favor_listview = (PullToRefreshListView) findViewById(R.id.favor_listview);
        initData();
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/CollectApi/ReceiveGoodsCollectList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                favorBean = gson.fromJson(response, FavorBean.class);
                favorAdapter = new FavorAdapter(FavorActivity.this, favorBean.getResult(), ImageUrl);
                favorAdapter.setSetOnMoreItemClickListener(FavorActivity.this);
                favor_listview.setAdapter(favorAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                Log.i("woaicaojing", bytecode.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("CurrentPage", "1");
                map.put("PageSize", "20");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int key = v.getId();
        switch (v.getId()) {
            case R.id.favor_item_more:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
                    int position = (Integer) tag;
                    Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
                    bottomView = new BottomView(this,
                            R.style.BottomViewTheme_Defalut, R.layout.favor_bottom);
                    bottomView.setAnimation(R.style.BottomToTopAnim);//设置动画，可选
                    un_favor = (LinearLayout) bottomView.getView().findViewById(R.id.un_favor);
                    favor_share = (LinearLayout) bottomView.getView().findViewById(R.id.favor_share);
                    favor_cancel = (LinearLayout) bottomView.getView().findViewById(R.id.favor_cancel);
                    un_favor.setOnClickListener(this);
                    favor_share.setOnClickListener(this);
                    favor_cancel.setOnClickListener(this);
                    un_favor.setTag(position);
                    favor_share.setTag(position);
                    bottomView.showBottomView(true);
                } else {
                }
                break;
            case R.id.un_favor:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
                    int position = (Integer) tag;
                    Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
                } else {
                }
                break;
            case R.id.favor_share:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
                    int position = (Integer) tag;
                    Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
                } else {
                }
                break;
            case R.id.favor_cancel:
                bottomView.dismissBottomView();
                break;
        }
    }
}
