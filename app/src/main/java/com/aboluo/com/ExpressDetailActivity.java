package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.ExpressDetailAdapter;
import com.aboluo.model.ExpressDetailBean;
import com.aboluo.widget.MyListview;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/12/5.
 */

public class ExpressDetailActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private ExpressDetailAdapter adapter;
    private MyListview express_listview;
    private String MemberId;
    private String OrderId;
    private String ExpressId;
    private String GoodsLogoUrl;
    private ImageView express_goods_image;
    private TextView express_status, express_company_name, express_code;
    private LinearLayout back_last_choose, express_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);
        init();
        back_last_choose.setOnClickListener(new View.OnClickListener() {
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
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        express_listview = (MyListview) findViewById(R.id.express_listview);
        express_goods_image = (ImageView) findViewById(R.id.express_goods_image);
        express_status = (TextView) findViewById(R.id.express_status);
        express_company_name = (TextView) findViewById(R.id.express_company_name);
        express_code = (TextView) findViewById(R.id.express_code);
        back_last_choose = (LinearLayout) findViewById(R.id.back_last_choose);
        express_top = (LinearLayout) findViewById(R.id.express_top);
        Intent intent = getIntent();
        OrderId = String.valueOf(intent.getIntExtra("OrderId", 0));
        ExpressId = String.valueOf(intent.getIntExtra("ExpressId", 0));
        GoodsLogoUrl = intent.getStringExtra("GoodsLogoUrl");
        if (GoodsLogoUrl == null) {
            GoodsLogoUrl = "";
        } else {
        }
        picasso.load(ImageUrl + GoodsLogoUrl).placeholder(R.drawable.image_placeholder)
                .error(R.drawable.imageview_error).into(express_goods_image);
        initData();
    }

    private void initData() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveOrderExpressInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                try {

                    ExpressDetailBean expressDetailBean = gson.fromJson(response, ExpressDetailBean.class);
                    if (expressDetailBean.isIsSuccess()) {

                        if (expressDetailBean.getResult().getList() == null) {
                            Toast.makeText(ExpressDetailActivity.this, "当前没有物流状态", Toast.LENGTH_SHORT).show();
                        } else {
                            if (expressDetailBean.getResult().getList().size() == 0) {
                                express_top.setVisibility(View.GONE);
                                Toast.makeText(ExpressDetailActivity.this, "当前没有物流状态", Toast.LENGTH_SHORT).show();
                            } else {
                                express_top.setVisibility(View.VISIBLE);
                                ChangeExpressStatus(Integer.valueOf(expressDetailBean.getResult().getDeliverystatus()));
                                express_company_name.setText(expressDetailBean.getResult().getName().toString());
                                express_code.setText(expressDetailBean.getResult().getNumber());
                                adapter = new ExpressDetailAdapter(expressDetailBean.getResult().getList(), ExpressDetailActivity.this);
                                express_listview.setAdapter(adapter);
                            }
                        }
                    } else {
                        Toast.makeText(ExpressDetailActivity.this, "当前没有物流状态", Toast.LENGTH_SHORT).show();
                    }
                }catch (JsonSyntaxException exception)
                {
                    Toast.makeText(ExpressDetailActivity.this, "物流信息有误", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Log.i("woaicaojing0", new String(data));
                Toast.makeText(ExpressDetailActivity.this, new String(data), Toast.LENGTH_LONG).show();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("OrderId", OrderId);
                map.put("ExpressId", ExpressId);
//                map.put("MemberId", "1975");
//                map.put("OrderId", "608");
//                map.put("ExpressId", "1");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    private void ChangeExpressStatus(int Status) {
        switch (Status) {
            case 1:
                express_status.setText("在途中");
                break;
            case 2:
                express_status.setText("派件中");
                break;
            case 3:
                express_status.setText("已签收");
                break;
            case 4:
                express_status.setText("派送失败");
                break;
        }
    }
}
