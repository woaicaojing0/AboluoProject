package com.aboluo.com.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.ProvinceAdapter;
import com.aboluo.com.R;
import com.aboluo.model.ProvinceBean;
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
 * Created by CJ on 2016/11/5.
 */

public class ProvinceAddressActivity  extends Activity{
    private LinearLayout back_last_choose;
    private ListView choose_address;
    private String url, APPToken;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ProvinceBean provinceBean;
    private Gson  gson;
    private ProvinceAdapter provinceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        init();
        choose_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProvinceAddressActivity.this,CityAddressActivity.class);
                intent.putExtra("id",provinceBean.getResult().getCounryAreaList().get(position).getId());
                intent.putExtra("name",provinceBean.getResult().getCounryAreaList().get(position).getAreaName().toString());
                startActivity(intent);
            }
        });
    }

    private void init() {
       back_last_choose = (LinearLayout) findViewById(R.id.back_last_choose);
        choose_address = (ListView) findViewById(R.id.choose_address);
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        requestQueue = MyApplication.getRequestQueue();
        provinceBean = new ProvinceBean();
        gson = new Gson();
        initdata();
    }

    private void initdata() {
        stringRequest = new StringRequest(Request.Method.POST, url + "/api/CountryArea/GetProvinceList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicoajingaddress",response);
                provinceBean = gson.fromJson(response,ProvinceBean.class);
                provinceAdapter = new ProvinceAdapter(provinceBean,ProvinceAddressActivity.this);
                choose_address.setAdapter(provinceAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
