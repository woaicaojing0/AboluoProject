package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.RecycleViewAdapter;
import com.aboluo.model.GoodsListInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/10/18.
 */

public class GoodsListActivity extends Activity implements RecycleViewAdapter.OnRecyclerViewItemClickListener {
    private RequestQueue requestQueue;
    private StringRequest requestlist;
    private RecyclerView recyclerView;
    private String url;
    private static String APPToken;
    private RecycleViewAdapter recycleViewAdapter;
    private GoodsListInfo listBean;
    private SweetAlertDialog pdialog;
    private int goods_type_id;
    private String goods_type_name;
    private TextView goods_list_typeName,goods_list_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodlist);
        init();
        requestQueue.add(requestlist);
        goods_list_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pdialog.show();
    }

    private void init() {
        goods_list_typeName = (TextView) findViewById(R.id.goods_list_typeName);
        goods_list_back = (TextView) findViewById(R.id.goods_list_back);
        Intent intent = getIntent();
        goods_type_id = intent.getIntExtra("goods_type_id", -1);
        goods_type_name = intent.getStringExtra("goods_type_name");
        if(goods_type_name == null)
        {}else
        {
            goods_list_typeName.setText(goods_type_name);
        }

        requestQueue = MyApplication.getRequestQueue();
        recyclerView = (RecyclerView) findViewById(R.id.goods_list_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        pdialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        initdate();
    }

    private void initdate() {
        requestlist = new StringRequest(Request.Method.POST, url + "/api/Goods/ReceiveGoodsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Gson gson = new Gson();
                Log.i("woaicaojing", url + "/api/Goods/ReceiveGoodsList");
                Log.i("woaicaojing", response);
                listBean = gson.fromJson(response, GoodsListInfo.class);
                recycleViewAdapter = new RecycleViewAdapter(listBean,GoodsListActivity.this);
                recyclerView.setAdapter(recycleViewAdapter);
                recycleViewAdapter.setOnItemClickListener(GoodsListActivity.this);
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicaojing", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if(goods_type_id == -1)
                {}else {
                    map.put("GoodsTypeId", String.valueOf(goods_type_id));
                }
                map.put("APPToken", APPToken);
                map.put("CurrentPage", "1");
                map.put("PageSize", "200");
                return map;
            }
        };
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        int goods_id = listBean.getResult().getGoodsList().get(position).getGoodsId();
        Intent intent = new Intent(this,GoodsDetailActivity.class);
        intent.putExtra("goods_id", goods_id);
        String  transitionName = "detail";
        ActivityOptionsCompat   transitionActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(GoodsListActivity.this,view,transitionName);
        startActivity(intent,transitionActivityOptionsCompat.toBundle());
//        startActivity(intent);
    }
}