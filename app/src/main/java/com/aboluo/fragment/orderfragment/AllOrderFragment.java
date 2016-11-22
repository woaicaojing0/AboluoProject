package com.aboluo.fragment.orderfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.AllOrderAdapter;
import com.aboluo.com.R;
import com.aboluo.model.SearchOrderBean;
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
 * Created by cj34920 on 2016/9/30.
 */

public class AllOrderFragment extends Fragment{
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String APPToken;
    private String ImageURL;
    private String URL;
    private View view;
    private ListView allorder_listview;
    private SearchOrderBean orderBean;
    private Gson gson;
    private AllOrderAdapter allOrderAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allorder,null);
        init();
        return view;
    }
    private void init()
    {
        allorder_listview = (ListView) view.findViewById(R.id.allorder_listview);
        requestQueue= MyApplication.getRequestQueue();
        APPToken = CommonUtils.GetValueByKey(AllOrderFragment.this.getActivity(),"APPToken");
        URL = CommonUtils.GetValueByKey(AllOrderFragment.this.getActivity(),"apiurl");
        ImageURL = CommonUtils.GetValueByKey(AllOrderFragment.this.getActivity(),"ImgUrl");
        orderBean= new SearchOrderBean();
        gson = new Gson();
        GetInfo();
    }
    private void GetInfo()
    {
        stringRequest = new StringRequest(Request.Method.POST, URL+"/api/Order/GetOrderListByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                Log.i("woaicaojingallorder",response);
                orderBean =gson.fromJson(response,SearchOrderBean.class);
                allOrderAdapter =new AllOrderAdapter(AllOrderFragment.this.getContext(),orderBean.getResult().getOrderList());
                allorder_listview.setAdapter(allOrderAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] htmlBodyBytes = error.networkResponse.data;
                String result = new String(htmlBodyBytes);
                Log.i("woaicaojingallorder",result);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1");
                map.put("OrderStatus", "0");
                map.put("CurrentPage", "1");
                map.put("PageSize", "10");
                map.put("NCount", "5");
                map.put("APPToken", APPToken);
                return  map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
