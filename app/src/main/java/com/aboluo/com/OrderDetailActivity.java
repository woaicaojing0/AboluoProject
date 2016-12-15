package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.OrderDetailItemAdpater;
import com.aboluo.model.OrderDetailInfo;
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
 * Created by cj34920 on 2016/12/8.
 */

public class OrderDetailActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private int orderid;
    private String Memberid;
    private OrderDetailInfo orderDetailInfo;
    private TextView orderdetail_express_status, orderdetail_express_time, orderdetail_address_name,
            orderdetail_address_phone, orderdetail_address_address, orderdetail_pay_allmonney,
            orderdetail_allmonney2, orderdetail_allmonney;
    private MyListview orderdetail_listview;
    private OrderDetailItemAdpater adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        orderid = intent.getIntExtra("orderid", 0);
        Memberid = CommonUtils.GetMemberId(this);
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
        orderdetail_express_status = (TextView) findViewById(R.id.orderdetail_express_status);
        orderdetail_express_time = (TextView) findViewById(R.id.orderdetail_express_time);
        orderdetail_address_name = (TextView) findViewById(R.id.orderdetail_address_name);
        orderdetail_address_phone = (TextView) findViewById(R.id.orderdetail_address_phone);
        orderdetail_address_address = (TextView) findViewById(R.id.orderdetail_address_address);
        orderdetail_pay_allmonney = (TextView) findViewById(R.id.orderdetail_pay_allmonney);
        orderdetail_allmonney2 = (TextView) findViewById(R.id.orderdetail_allmonney2);
        orderdetail_allmonney = (TextView) findViewById(R.id.orderdetail_allmonney);
        orderdetail_listview = (MyListview) findViewById(R.id.orderdetail_listview);
        if (orderid == 0) {
        } else {
            initData();
        }
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveOrderDetailByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                orderDetailInfo = gson.fromJson(response, OrderDetailInfo.class);
                orderdetail_express_status.setText(orderDetailInfo.getResult().get(0).getLocalInfo() == null ? "" :
                        orderDetailInfo.getResult().get(0).getLocalInfo().toString());
                orderdetail_express_time.setText("123");
                orderdetail_address_name.setText(orderDetailInfo.getResult().get(0).getReceiver().toString());
                orderdetail_address_phone.setText(orderDetailInfo.getResult().get(0).getMobile().toString());
                orderdetail_address_address.setText(orderDetailInfo.getResult().get(0).getAddress().toString());
                orderdetail_pay_allmonney.setText(String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                orderdetail_allmonney2.setText(String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                orderdetail_allmonney.setText(String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                adpater = new OrderDetailItemAdpater(OrderDetailActivity.this
                        , orderDetailInfo.getResult().get(0).getOrderItemList(), orderDetailInfo.getResult().get(0).getOrderStatus());
                adpater.setEvaluationOnClickListener(OrderDetailActivity.this);
                orderdetail_listview.setAdapter(adpater);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", Memberid);
                map.put("OrderId", String.valueOf(orderid));
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        Object tag = v.getTag();
        int key = v.getId();
        switch (key) {
            case R.id.txt_orderdetail_evaluate:
                if (tag != null && tag instanceof Integer) {
                    Intent intent = new Intent(this, EvaluationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.getString("Imageurl", orderDetailInfo.getResult().get(0).getOrderItemList()
                            .get(((Integer) tag).intValue()).getGoodsLogoUrl());
                    bundle.putInt("Orderid", orderid);
                    bundle.putInt("Goodsid", orderDetailInfo.getResult().get(0).getOrderItemList()
                            .get(((Integer) tag).intValue()).getGoodsId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }

    }
}
