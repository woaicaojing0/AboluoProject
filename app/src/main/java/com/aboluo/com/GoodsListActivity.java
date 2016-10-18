package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cj34920 on 2016/10/18.
 */

public class GoodsListActivity extends Activity {
    private RequestQueue requestQueue;
    private StringRequest requestlist;
    private RecyclerView recyclerView;
    private String url;
    private static String APPToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodlist);
        init();

        requestQueue.add(requestlist);
    }

    private void init() {
        requestQueue = MyApplication.getRequestQueue();
        recyclerView = (RecyclerView) findViewById(R.id.goods_list_recycleview);
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        initdate();
    }

    private void initdate() {
        requestlist = new StringRequest(Request.Method.POST, url + "/api/Goods/ReceiveGoodsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Gson gson = new Gson();
                Log.i("woaicaojing", response);
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
                map.put("APPToken", APPToken);
                map.put("CurrentPage", "1");
                map.put("PageSize", "1");
                return map;
            }
        };
    }
}
