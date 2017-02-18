package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.HelpCenterListviewAdapter;
import com.aboluo.com.WebActivity.FeedBackActivity;
import com.aboluo.model.HelpCenterListBean;
import com.aboluo.widget.MyListview;
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
 * Created by CJ on 2017/2/11.
 */

public class HelpCenterActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    RelativeLayout rl_feedback, rl_help_service_protrocol;
    ImageView iv_helpcenter_back;
    MyListview mlv_helpcenter;
    HelpCenterListBean helpCenterListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__helpcenter);
        init();
        rl_feedback.setOnClickListener(this);
        rl_help_service_protrocol.setOnClickListener(this);
        iv_helpcenter_back.setOnClickListener(this);
        mlv_helpcenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelpCenterActivity.this, HelpCenterDetailActivity.class);
                intent.putExtra("helpcenterURl", helpCenterListBean.getList().get(position).getUrl());
                intent.putExtra("helpcentertitle", helpCenterListBean.getList().get(position).getTitle());
                startActivity(intent);
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
        rl_feedback = (RelativeLayout) findViewById(R.id.rl_feedback);
        rl_help_service_protrocol = (RelativeLayout) findViewById(R.id.rl_help_service_protrocol);
        iv_helpcenter_back = (ImageView) findViewById(R.id.iv_helpcenter_back);
        mlv_helpcenter = (MyListview) findViewById(R.id.mlv_helpcenter);
        initData();
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHelpMenuList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                helpCenterListBean = gson.fromJson(response, HelpCenterListBean.class);
                HelpCenterListviewAdapter helpCenterListviewAdapter = new HelpCenterListviewAdapter(HelpCenterActivity.this, helpCenterListBean.getList());
                mlv_helpcenter.setAdapter(helpCenterListviewAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("LoginPhone", "123");
                map.put("LoginCheckToken", "123");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_feedback:
                Intent intent = new Intent(HelpCenterActivity.this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_helpcenter_back:
                finish();
                break;
            case R.id.rl_help_service_protrocol:
                Intent intent2 = new Intent(HelpCenterActivity.this, ServiceProtocolActivity.class);
                startActivity(intent2);
        }
    }
}
