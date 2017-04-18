package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.ExpressListAdapter;
import com.aboluo.adapter.OrderDetailItemAdpater;
import com.aboluo.model.BaseModel;
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
 * 订单详情接口
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
            tv_orderdetail_integral, orderdetail_allmonney, tv_orderdetail_freight, tv_orderdetail_coupons;
    private MyListview orderdetail_listview;
    private OrderDetailItemAdpater adpater;
    private RelativeLayout order_detail_bottom;
    private LinearLayout orderdetail_expressdetail, order_detail_back;
    private TextView oederdetail_findgoods, oederdetail_ok, oederdetail_cancelorder,
            oederdetail_payorder, oederdetail_cuicui, order_detail_topstatus, tv_orderdetail_code;
    private MyListview lv_expresslist;
    private SweetAlertDialog finishDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();
        order_detail_back.setOnClickListener(this);
        oederdetail_findgoods.setOnClickListener(this);
        oederdetail_ok.setOnClickListener(this);
        oederdetail_cancelorder.setOnClickListener(this);
        oederdetail_payorder.setOnClickListener(this);
        oederdetail_cuicui.setOnClickListener(this);
        lv_expresslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(OrderDetailActivity.this, ExpressDetailActivity.class);
                intent1.putExtra("OrderId", orderDetailInfo.getResult().get(0).getOrderId());
                intent1.putExtra("ExpressId", orderDetailInfo.getResult().get(0).getOrderExpressList().get(position).getId());
                intent1.putExtra("GoodsLogoUrl", orderDetailInfo.getResult().get(0).getOrderItemList().get(0).getGoodsLogoUrl());
                startActivity(intent1);
            }
        });
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
        lv_expresslist = (MyListview) findViewById(R.id.lv_expresslist);
        orderdetail_address_name = (TextView) findViewById(R.id.orderdetail_address_name);
        orderdetail_address_phone = (TextView) findViewById(R.id.orderdetail_address_phone);
        orderdetail_address_address = (TextView) findViewById(R.id.orderdetail_address_address);
        orderdetail_pay_allmonney = (TextView) findViewById(R.id.orderdetail_pay_allmonney);
        tv_orderdetail_integral = (TextView) findViewById(R.id.tv_orderdetail_integral);
        orderdetail_allmonney = (TextView) findViewById(R.id.orderdetail_allmonney);
        oederdetail_findgoods = (TextView) findViewById(R.id.oederdetail_findgoods);
        oederdetail_ok = (TextView) findViewById(R.id.oederdetail_ok);
        oederdetail_cancelorder = (TextView) findViewById(R.id.oederdetail_cancelorder);
        oederdetail_payorder = (TextView) findViewById(R.id.oederdetail_payorder);
        oederdetail_cuicui = (TextView) findViewById(R.id.oederdetail_cuicui);
        order_detail_topstatus = (TextView) findViewById(R.id.order_detail_topstatus);
        tv_orderdetail_freight = (TextView) findViewById(R.id.tv_orderdetail_freight);
        tv_orderdetail_coupons = (TextView) findViewById(R.id.tv_orderdetail_coupons);
        tv_orderdetail_code = (TextView) findViewById(R.id.tv_orderdetail_code);
        orderdetail_listview = (MyListview) findViewById(R.id.orderdetail_listview);
        order_detail_back = (LinearLayout) findViewById(R.id.order_detail_back);
        order_detail_bottom = (RelativeLayout) findViewById(R.id.order_detail_bottom);
        if (orderid == 0) {
        } else {
            initData();
        }
    }

    private void initData() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveOrderDetailByMemberIdNew", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                orderDetailInfo = gson.fromJson(response, OrderDetailInfo.class);
                if (0 == orderDetailInfo.getResult().get(0).getOrderExpressList().size()) {
                    lv_expresslist.setVisibility(View.GONE);
                } else if (null == orderDetailInfo.getResult().get(0).getOrderExpressList().get(0).getLocalInfo()) {
                    lv_expresslist.setVisibility(View.GONE);
                } else {
                    lv_expresslist.setVisibility(View.VISIBLE);
                    ExpressListAdapter expressListAdapter = new ExpressListAdapter(orderDetailInfo
                            .getResult().get(0).getOrderExpressList(), OrderDetailActivity.this);
                    lv_expresslist.setAdapter(expressListAdapter);
                }
                orderdetail_address_name.setText(orderDetailInfo.getResult().get(0).getReceiver()
                        == null ? "" : orderDetailInfo.getResult().get(0).getReceiver().toString());
                orderdetail_address_phone.setText(orderDetailInfo.getResult().get(0).getMobile()
                        == null ? "" : orderDetailInfo.getResult().get(0).getMobile().toString());
                orderdetail_address_address.setText(orderDetailInfo.getResult().get(0).getAddress()
                        == null ? "" : orderDetailInfo.getResult().get(0).getAddress().toString());
                orderdetail_pay_allmonney.setText("￥" + String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                tv_orderdetail_integral.setText("￥" + String.valueOf(orderDetailInfo.getResult().get(0).getTotalPrice()));
                double goodsAllPrice = 0; //商品总价
                for (OrderDetailInfo.ResultBean.OrderItemListBean bean : orderDetailInfo.getResult().get(0).getOrderItemList()) {
                    goodsAllPrice = goodsAllPrice + (bean.getGoodsQuantity() * bean.getGoodsPrice());
                }
                Log.i("OrderDetailActivity", goodsAllPrice + "");
                orderdetail_allmonney.setText("￥" + goodsAllPrice);
                tv_orderdetail_freight.setText("￥" + orderDetailInfo.getResult().get(0).getExpressPrice() + "");
                tv_orderdetail_coupons.setText("￥" + orderDetailInfo.getResult().get(0).getCouponPrice() + "");
                tv_orderdetail_integral.setText("￥" + orderDetailInfo.getResult().get(0).getIntegralPrice() + "");
                tv_orderdetail_code.setText(orderDetailInfo.getResult().get(0).getOrderCode() + "");
                adpater = new OrderDetailItemAdpater(OrderDetailActivity.this
                        , orderDetailInfo.getResult().get(0).getOrderItemList(),
                        orderDetailInfo.getResult().get(0).getOrderStatus()
                        , orderDetailInfo.getResult().get(0).getOrderType());
                adpater.setEvaluationOnClickListener(OrderDetailActivity.this);
                adpater.setAfterSaleOnClickListener(OrderDetailActivity.this);
                orderdetail_listview.setAdapter(adpater);
                OrderDetailStatus(orderDetailInfo.getResult().get(0).getOrderStatus());
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
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
                    bundle.putString("Imageurl", orderDetailInfo.getResult().get(0).getOrderItemList()
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
                    bundle.putString("OrderCode", orderDetailInfo.getResult().get(0).getOrderCode());
                    bundle.putParcelable("refundinfo", orderDetailInfo.getResult().get(0).
                            getOrderItemList().get((Integer) tag));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.orderdetail_expressdetail:
                Intent intent1 = new Intent(this, ExpressDetailActivity.class);
                intent1.putExtra("OrderId", orderDetailInfo.getResult().get(0).getOrderId());
                intent1.putExtra("ExpressId", orderDetailInfo.getResult().get(0).getExpressId());
                intent1.putExtra("GoodsLogoUrl", orderDetailInfo.getResult().get(0).getOrderItemList().get(0).getGoodsLogoUrl());
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
                intent2.putExtra("OrderId", orderDetailInfo.getResult().get(0).getOrderId());
                intent2.putExtra("ExpressId", orderDetailInfo.getResult().get(0).getExpressId());
                intent2.putExtra("GoodsLogoUrl", orderDetailInfo.getResult().get(0).getOrderItemList().get(0).getGoodsLogoUrl());
                startActivity(intent2);
                break;
            case R.id.oederdetail_ok:
                finishDialog = new SweetAlertDialog(OrderDetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("您确定收货")
                        .setCancelText("稍等片刻")
                        .setConfirmText("确认收货")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                finishDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finishDialog.dismiss();
                                ConfirmOrder(String.valueOf(orderDetailInfo.getResult().get(0).getOrderId()));
                            }
                        });
                finishDialog.setCancelable(false);
                finishDialog.show();

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
                oederdetail_cancelorder.setVisibility(View.VISIBLE);
                oederdetail_payorder.setVisibility(View.VISIBLE);
                break;
            case 20://待发货
                order_detail_topstatus.setText("买家已付款");
                oederdetail_cuicui.setVisibility(View.VISIBLE);
                break;
            case 30://卖家已发货
                order_detail_topstatus.setText("卖家已发货");
                // oederdetail_findgoods.setVisibility(View.VISIBLE);
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

    /**
     * 确认收货
     */
    private void ConfirmOrder(final String OrderId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveConfirmReceipt", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                Toast.makeText(OrderDetailActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                OrderDetailActivity.this.finish();
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
                map.put("OrderId", OrderId);
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
