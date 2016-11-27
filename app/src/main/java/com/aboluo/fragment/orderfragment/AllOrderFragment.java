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
import java.util.List;
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
    private String MemberId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allorder,null);
        MemberId =CommonUtils.GetMemberId(AllOrderFragment.this.getContext());
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
                response ="{\n" +
                        "    \"IsSuccess\": true,\n" +
                        "    \"Message\": null,\n" +
                        "    \"Result\": {\n" +
                        "        \"OrderList\": [\n" +
                        "            {\n" +
                        "                \"orderId\": 596,\n" +
                        "                \"orderCode\": \"201611201944270003\",\n" +
                        "                \"memberId\": 1,\n" +
                        "                \"addressId\": 35,\n" +
                        "                \"expressId\": 0,\n" +
                        "                \"orderStatus\": 10,\n" +
                        "                \"payTypeId\": 0,\n" +
                        "                \"tuihuo\": \"\",\n" +
                        "                \"payTime\": \"1900-01-01T00:00:00\",\n" +
                        "                \"totalPrice\": 200,\n" +
                        "                \"orderRemarks\": null,\n" +
                        "                \"shouhuoTime\": \"1900-01-01T00:00:00\",\n" +
                        "                \"addTime\": \"2016-11-20T19:44:27\",\n" +
                        "                \"Receiver\": \"曹晶\",\n" +
                        "                \"Mobile\": \"18360733212\",\n" +
                        "                \"Address\": \"许路新村\",\n" +
                        "                \"Price\": 300,\n" +
                        "                \"orderItemId\": 706,\n" +
                        "                \"goodsId\": 3058,\n" +
                        "                \"goodsName\": \"研发测试\",\n" +
                        "                \"goodsSub\": \"研发测试\",\n" +
                        "                \"goodsColorId\": 0,\n" +
                        "                \"goodsColor\": \"无\",\n" +
                        "                \"goodsStandardId\": 45,\n" +
                        "                \"goodsStandard\": \"789\",\n" +
                        "                \"goodsPrice\": 300,\n" +
                        "                \"yunfei\": 0,\n" +
                        "                \"goodsLogoUrl\": \"e7eec70dca2047dbba1d9ccf00d05643.jpg\",\n" +
                        "                \"goodsQuantity\": 1\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"orderId\": 596,\n" +
                        "                \"orderCode\": \"201611201944270003\",\n" +
                        "                \"memberId\": 1,\n" +
                        "                \"addressId\": 35,\n" +
                        "                \"expressId\": 0,\n" +
                        "                \"orderStatus\": 10,\n" +
                        "                \"payTypeId\": 0,\n" +
                        "                \"tuihuo\": \"\",\n" +
                        "                \"payTime\": \"1900-01-01T00:00:00\",\n" +
                        "                \"totalPrice\": 200,\n" +
                        "                \"orderRemarks\": null,\n" +
                        "                \"shouhuoTime\": \"1900-01-01T00:00:00\",\n" +
                        "                \"addTime\": \"2016-11-20T19:44:27\",\n" +
                        "                \"Receiver\": \"曹晶\",\n" +
                        "                \"Mobile\": \"18360733212\",\n" +
                        "                \"Address\": \"许路新村\",\n" +
                        "                \"Price\": 300,\n" +
                        "                \"orderItemId\": 706,\n" +
                        "                \"goodsId\": 3058,\n" +
                        "                \"goodsName\": \"研发测试2\",\n" +
                        "                \"goodsSub\": \"研发测试2\",\n" +
                        "                \"goodsColorId\": 0,\n" +
                        "                \"goodsColor\": \"无\",\n" +
                        "                \"goodsStandardId\": 45,\n" +
                        "                \"goodsStandard\": \"789\",\n" +
                        "                \"goodsPrice\": 300,\n" +
                        "                \"yunfei\": 0,\n" +
                        "                \"goodsLogoUrl\": \"e7eec70dca2047dbba1d9ccf00d05643.jpg\",\n" +
                        "                \"goodsQuantity\": 1\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"orderId\": 596,\n" +
                        "                \"orderCode\": \"201611201944270003\",\n" +
                        "                \"memberId\": 1,\n" +
                        "                \"addressId\": 35,\n" +
                        "                \"expressId\": 0,\n" +
                        "                \"orderStatus\": 10,\n" +
                        "                \"payTypeId\": 0,\n" +
                        "                \"tuihuo\": \"\",\n" +
                        "                \"payTime\": \"1900-01-01T00:00:00\",\n" +
                        "                \"totalPrice\": 200,\n" +
                        "                \"orderRemarks\": null,\n" +
                        "                \"shouhuoTime\": \"1900-01-01T00:00:00\",\n" +
                        "                \"addTime\": \"2016-11-20T19:44:27\",\n" +
                        "                \"Receiver\": \"曹晶\",\n" +
                        "                \"Mobile\": \"18360733212\",\n" +
                        "                \"Address\": \"许路新村\",\n" +
                        "                \"Price\": 300,\n" +
                        "                \"orderItemId\": 706,\n" +
                        "                \"goodsId\": 3058,\n" +
                        "                \"goodsName\": \"研发测试3\",\n" +
                        "                \"goodsSub\": \"研发测试3\",\n" +
                        "                \"goodsColorId\": 0,\n" +
                        "                \"goodsColor\": \"无\",\n" +
                        "                \"goodsStandardId\": 45,\n" +
                        "                \"goodsStandard\": \"789\",\n" +
                        "                \"goodsPrice\": 300,\n" +
                        "                \"yunfei\": 0,\n" +
                        "                \"goodsLogoUrl\": \"e7eec70dca2047dbba1d9ccf00d05643.jpg\",\n" +
                        "                \"goodsQuantity\": 1\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"ListResult\": null\n" +
                        "}";
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
                map.put("MemberId", MemberId);
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
