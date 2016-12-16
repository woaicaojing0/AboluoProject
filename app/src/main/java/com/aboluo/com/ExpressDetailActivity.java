package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);
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
        express_listview= (MyListview) findViewById(R.id.express_listview);
        initData();
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/Order/ReceiveOrderExpressInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                ExpressDetailBean expressDetailBean=gson.fromJson(response,ExpressDetailBean.class);
                if(expressDetailBean.isIsSuccess())
                {
                    if(expressDetailBean.getResult().getList().size() ==0)
                    {
                        Toast.makeText(ExpressDetailActivity.this, "当前没有物流状态", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        adapter = new ExpressDetailAdapter(expressDetailBean.getResult().getList(),ExpressDetailActivity.this);
                        express_listview.setAdapter(adapter);
                    }
                }else {}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Log.i("woaicaojing0",new String(data));
                Toast.makeText(ExpressDetailActivity.this,new String(data),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1975");
                map.put("OrderId", "608");
                map.put("ExpressId", "1");
                map.put("APPToken", APPToken);
                return map;
            };
        };
        requestQueue.add(stringRequest);
    }
}
