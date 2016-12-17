package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String payMoney;
    private String OrderNum;
    private String payfrom;
    private String Memberid;
    private OrderDetailInfo orderDetailInfo;
    private TextView orderdetail_express_status, orderdetail_express_time, orderdetail_address_name,
            orderdetail_address_phone, orderdetail_address_address, orderdetail_pay_allmonney,
            orderdetail_allmonney2, orderdetail_allmonney;
    private MyListview orderdetail_listview;
    private OrderDetailItemAdpater adpater;
    private RelativeLayout order_detail_bottom;
    private LinearLayout orderdetail_expressdetail, order_detail_back;
    private TextView oederdetail_findgoods, oederdetail_ok, oederdetail_cancelorder,
            oederdetail_payorder, oederdetail_cuicui, order_detail_topstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
        orderdetail_expressdetail.setOnClickListener(this);
        order_detail_back.setOnClickListener(this);
        oederdetail_findgoods.setOnClickListener(this);
        oederdetail_ok.setOnClickListener(this);
        oederdetail_cancelorder.setOnClickListener(this);
        oederdetail_payorder.setOnClickListener(this);
        oederdetail_cuicui.setOnClickListener(this);
    }

    private void init() {
        Intent intent = getIntent();
        orderid = intent.getIntExtra("orderid", 0);
        payMoney = intent.getStringExtra("payMoney");
        OrderNum = intent.getStringExtra("OrderNum");
        payfrom = intent.getStringExtra("payfrom");
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
        oederdetail_findgoods = (TextView) findViewById(R.id.oederdetail_findgoods);
        oederdetail_ok = (TextView) findViewById(R.id.oederdetail_ok);
        oederdetail_cancelorder = (TextView) findViewById(R.id.oederdetail_cancelorder);
        oederdetail_payorder = (TextView) findViewById(R.id.oederdetail_payorder);
        oederdetail_cuicui = (TextView) findViewById(R.id.oederdetail_cuicui);
        order_detail_topstatus = (TextView) findViewById(R.id.order_detail_topstatus);
        orderdetail_listview = (MyListview) findViewById(R.id.orderdetail_listview);
        orderdetail_expressdetail = (LinearLayout) findViewById(R.id.orderdetail_expressdetail);
        order_detail_back = (LinearLayout) findViewById(R.id.order_detail_back);
        order_detail_bottom = (RelativeLayout) findViewById(R.id.order_detail_bottom);
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
                orderdetail_address_name.setText(orderDetailInfo.getResult().get(0).getReceiver()
                        == null ? "" : orderDetailInfo.getResult().get(0).getReceiver().toString());
                orderdetail_address_phone.setText(orderDetailInfo.getResult().get(0).getMobile()
                        == null ? "" : orderDetailInfo.getResult().get(0).getMobile().toString());
                orderdetail_address_address.setText(orderDetailInfo.getResult().get(0).getAddress()
                        == null ? "" : orderDetailInfo.getResult().get(0).getAddress().toString());
                orderdetail_pay_allmonney.setText(String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                orderdetail_allmonney2.setText(String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                orderdetail_allmonney.setText(String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                adpater = new OrderDetailItemAdpater(OrderDetailActivity.this
                        , orderDetailInfo.getResult().get(0).getOrderItemList(), orderDetailInfo.getResult().get(0).getOrderStatus());
                adpater.setEvaluationOnClickListener(OrderDetailActivity.this);
                adpater.setAfterSaleOnClickListener(OrderDetailActivity.this);
                orderdetail_listview.setAdapter(adpater);
                OrderDetailStatus(orderDetailInfo.getResult().get(0).getOrderStatus());
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
                    bundle.putInt("orderItemId", orderDetailInfo.getResult().get(0).getOrderItemList()
                            .get(((Integer) tag).intValue()).getOrderItemId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.txt_orderdetail_after_sale: //申请售后
                if (tag != null && tag instanceof Integer) {
                    Intent intent = new Intent(this, RefundDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.getString("Imageurl", "123");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.orderdetail_expressdetail:
                Intent intent1 = new Intent(this, ExpressDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.oederdetail_cancelorder:

                break;
            case R.id.oederdetail_payorder:
                Intent intent = new Intent(this, OrderPayActivity.class);
                if (payMoney != null && OrderNum != null) {
                    intent.putExtra("payMoney", payMoney);
                    intent.putExtra("OrderNum", OrderNum);
                    intent.putExtra("payfrom", "2");
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "没有相应的订单信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.oederdetail_cuicui:
                Toast.makeText(this, "催货成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.oederdetail_findgoods:
                Intent intent2 = new Intent(this, ExpressDetailActivity.class);
                startActivity(intent2);
                break;
            case R.id.oederdetail_ok:

                break;
            case R.id.order_detail_back:
                finish();
                break;

        }

    }

    private void OrderDetailStatus(int status) {
        clean();
        switch (status) {
            case 10://待付款
                order_detail_topstatus.setText("等待买家付款");
                orderdetail_expressdetail.setVisibility(View.GONE);
                oederdetail_cancelorder.setVisibility(View.VISIBLE);
                oederdetail_payorder.setVisibility(View.VISIBLE);
                break;
            case 20://待发货
                order_detail_topstatus.setText("买家已付款");
                orderdetail_expressdetail.setVisibility(View.GONE);
                oederdetail_cuicui.setVisibility(View.VISIBLE);
                break;
            case 30://卖家已发货
                order_detail_topstatus.setText("卖家已发货");
                oederdetail_findgoods.setVisibility(View.VISIBLE);
                oederdetail_ok.setVisibility(View.VISIBLE);
                break;
            case 40://交易成功
                order_detail_topstatus.setText("交易成功");
                order_detail_bottom.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void clean() {
        oederdetail_findgoods.setVisibility(View.GONE);
        oederdetail_ok.setVisibility(View.GONE);
        oederdetail_cancelorder.setVisibility(View.GONE);
        oederdetail_payorder.setVisibility(View.GONE);
        oederdetail_cuicui.setVisibility(View.GONE);
    }
}
