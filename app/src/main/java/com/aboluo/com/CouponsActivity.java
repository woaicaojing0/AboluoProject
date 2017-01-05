package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.CouponsAdapter;
import com.aboluo.model.CouponsBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2017/1/4.
 * 显示优惠券页面
 */

public class CouponsActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private String TotalMoney = "0"; //表明从个人优惠券还是下单的选择优惠券跳转的(0代表是个人中心，非0代表下单)
    private PullToRefreshListView lv_coupons_showcoupons;
    private TextView tv_coupons_clean, tv__conpous_backname;
    private LinearLayout back_last_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        init();
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
        Intent intent = getIntent();
        TotalMoney = intent.getStringExtra("allmoney");
        lv_coupons_showcoupons = (PullToRefreshListView) findViewById(R.id.lv_coupons_showcoupons);
        tv_coupons_clean = (TextView) findViewById(R.id.tv_coupons_clean);
        tv__conpous_backname = (TextView) findViewById(R.id.tv__conpous_backname);
        back_last_choose = (LinearLayout) findViewById(R.id.back_last_choose);
        if (TotalMoney.equals("0")) {
            tv_coupons_clean.setVisibility(View.GONE);
            tv__conpous_backname.setText("个人中心");
            tv__conpous_backname.setOnClickListener(this);
        } else {
            tv_coupons_clean.setVisibility(View.VISIBLE);
            tv__conpous_backname.setText("订单确认");
            tv__conpous_backname.setOnClickListener(this);
            tv_coupons_clean.setOnClickListener(this);
        }
        initData();
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/MemberApi/ReceiveMemberCanUseCoupons", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                final CouponsBean couponsBean = gson.fromJson(response, CouponsBean.class);
                if (couponsBean.getMemberCouponList() == null) {
                    Toast.makeText(CouponsActivity.this, "获取的membercouponlist为空", Toast.LENGTH_SHORT).show();
                } else {
                    CouponsAdapter couponsAdapter = new CouponsAdapter(CouponsActivity.this, couponsBean.getMemberCouponList());
                    lv_coupons_showcoupons.setAdapter(couponsAdapter);
                    if (TotalMoney.equals("0")) {
                        tv_coupons_clean.setVisibility(View.GONE);
                    } else {
                        //PullToRefreshListView中的索引是从1开始的，因为多了一个head， header 的位置是0
                        lv_coupons_showcoupons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("CouponId", String.valueOf(couponsBean.getMemberCouponList().get(position-1).getCouponId()));
                                intent.putExtra("Remark", couponsBean.getMemberCouponList().get(position-1).getRemark());
                                intent.putExtra("CouponMoney", String.valueOf(couponsBean.getMemberCouponList().get(position-1).getCouponMoney()));
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                //map.put("MemberId", MemberId);
                map.put("MemberId", MemberId);
                map.put("TotalMoney", TotalMoney);
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv__conpous_backname:
                if (TotalMoney.equals("0")) {
                    finish();
                } else { //表示返回到下单界面
                    Intent intent = new Intent();
                    intent.putExtra("CouponId", "0");
                    intent.putExtra("Remark", "");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.tv_coupons_clean:
                Intent intent = new Intent();
                intent.putExtra("CouponId", "0");
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
        }
    }
}
