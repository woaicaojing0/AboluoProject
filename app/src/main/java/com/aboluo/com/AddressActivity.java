package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.AddressAdapter;
import com.aboluo.model.AddressInfoBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddressActivity extends Activity {
    private ListView address_listview;
    private List<String> list;
    private LinearLayout shopcar_bottom_add_address;
    private RelativeLayout no_address_show;
private String url;
    private String APPToken;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Gson gson;
    private AddressInfoBean addressInfoBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        List<String> list2 = new ArrayList<>();
//        if(list2.size() == 0)
//        {
//            no_address_show.setVisibility(View.VISIBLE);
//        }
//        else {
//            no_address_show.setVisibility(View.GONE);
//            AddressAdapter addressAdapter = new AddressAdapter(list2, AddressActivity.this);
//            address_listview.setAdapter(addressAdapter);
//        }
        shopcar_bottom_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });


    }

    private void init() {
        address_listview = (ListView) findViewById(R.id.address_listview);
        shopcar_bottom_add_address = (LinearLayout) findViewById(R.id.shopcar_bottom_add_address);
        no_address_show = (RelativeLayout) findViewById(R.id.no_address_show);
        url= CommonUtils.GetValueByKey(this,"apiurl");
        APPToken= CommonUtils.GetValueByKey(this,"APPToken");
        requestQueue = MyApplication.getRequestQueue();
        addressInfoBean = new AddressInfoBean();
        gson = new Gson();
        initdata();
    }
    private void initdata()
    {
        stringRequest =new StringRequest(Request.Method.POST, url+"/api/Order/GetMemberAddressListByMemberId",  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                Log.i("woaicaojingaddres",response);
                addressInfoBean =gson.fromJson(response,AddressInfoBean.class);
                int memsize =addressInfoBean.getResult().getMemberAddressList().size();
                if(memsize == 0)
                {
                    no_address_show.setVisibility(View.VISIBLE);
                }
                else {
                    no_address_show.setVisibility(View.GONE);
                    AddressAdapter addressAdapter = new AddressAdapter(addressInfoBean.getResult().getMemberAddressList(), AddressActivity.this);
                    address_listview.setAdapter(addressAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=  new HashMap<>();
                map.put("MemberId","1");
                map.put("APPToken",APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
