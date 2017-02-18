package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.FastParnterBean;
import com.aboluo.model.FastParnterMakeOrderBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2017/2/17.
 */

public class FastPartnerActivity extends Activity {
    Button btn_fastpartner;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private FastParnterBean fastParnterBean;
    private FastParnterMakeOrderBean fastParnterMakeOrderBean;
    private TextView tv_Amount;
    private ImageView iv_fastpartner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastpartner);
        init();
        btn_fastpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastParnterMakeOrder();
            }
        });
        iv_fastpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FastPartnerActivity.this, GoodsDetailImageActivity.class);
                ArrayList<String> listimage = new ArrayList<String>();
                listimage.add(String.valueOf(R.drawable.fastparnter));
                intent.putStringArrayListExtra("imgeurl", listimage);
                intent.putExtra("position", 0);
                String transitionName = "images";
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(FastPartnerActivity.this,v, transitionName);
                startActivity(intent,activityOptionsCompat.toBundle());
            }
        });
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl2");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        btn_fastpartner = (Button) findViewById(R.id.btn_fastpartner);
        tv_Amount = (TextView) findViewById(R.id.tv_Amount);
        iv_fastpartner = (ImageView) findViewById(R.id.iv_fastpartner);
        initData();
    }

    private void initData() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL+
                "/api/PartnerApi/ReceivePartner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                fastParnterBean = gson.fromJson(response, FastParnterBean.class);
                if (fastParnterBean.isIsSuccess()) {
                    btn_fastpartner.setText(fastParnterBean.getTypeName());
                    tv_Amount.setText(fastParnterBean.getAmount() + "");
                } else {
                    Toast.makeText(FastPartnerActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] htmlBodyBytes = error.networkResponse.data;
//                //Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
                //sweetAlertDialog.dismiss();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    private void fastParnterMakeOrder() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL+"/api/PartnerApi/PartnerOrder", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                fastParnterMakeOrderBean = gson.fromJson(response, FastParnterMakeOrderBean.class);
                //fastParnterBean = gson.fromJson(response, FastParnterBean.class);
                if (fastParnterMakeOrderBean.isIsSuccess()) {
                    Toast.makeText(FastPartnerActivity.this, fastParnterMakeOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FastPartnerActivity.this, OrderPayActivity.class);
                    intent.putExtra("payMoney", String.valueOf(fastParnterMakeOrderBean.getAmount()));
                    intent.putExtra("OrderNum", String.valueOf(fastParnterMakeOrderBean.getSerialId().toString()));
                    intent.putExtra("payfrom", "6");
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(FastPartnerActivity.this, fastParnterMakeOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] htmlBodyBytes = error.networkResponse.data;
//                //Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
//                //sweetAlertDialog.dismiss();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("Amount", String.valueOf(fastParnterBean.getAmount()));
                map.put("PartnerType", String.valueOf(fastParnterBean.getPartnerType()));
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        } else {
            finish();
        }
    }
}
