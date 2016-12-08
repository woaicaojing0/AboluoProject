package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.ChooseAddressAdapter;
import com.aboluo.model.AddressInfoBean;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.aboluo.model.AddressInfoBean.ResultBean.MemberAddressListBean;

/**
 * Created by CJ on 2016/11/21.
 */

public class ChooseAddressActivtiy extends Activity implements View.OnClickListener {
    private ListView listview_choose_address;
    private SweetAlertDialog pdialog;
    private String url;
    private static String APPToken;
    private StringRequest requestlist;
    private RequestQueue requestQueue;
    private AddressInfoBean addressInfoBean;
    private Gson gson;
    private ChooseAddressAdapter chooseAddressAdapter;
    private String MemberId;
    private TextView detail_more;
    private ImageView choose_address_text_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sure_choose_address);
        MemberId = CommonUtils.GetMemberId(ChooseAddressActivtiy.this);
        init();
        listview_choose_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemberAddressListBean memberAddressListBean = addressInfoBean.getResult().getMemberAddressList().get(position);
                Intent intent = new Intent();
                ArrayList<MemberAddressListBean> memberAddressListBeanlist = new ArrayList<MemberAddressListBean>();
                memberAddressListBeanlist.add(memberAddressListBean);
                intent.putExtra("data", memberAddressListBeanlist);
                setResult(2, intent);
                finish();
            }
        });
        detail_more.setOnClickListener(this);
        choose_address_text_back.setOnClickListener(this);
    }

    private void init() {
        listview_choose_address = (ListView) findViewById(R.id.listview_choose_address);
        detail_more = (TextView) findViewById(R.id.detail_more);
        choose_address_text_back = (ImageView) findViewById(R.id.choose_address_text_back);
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        requestQueue = MyApplication.getRequestQueue();
        addressInfoBean = new AddressInfoBean();
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
    }

    private void initData() {
        pdialog.setTitleText("加载中");
        pdialog.show();
        requestlist = new StringRequest(Request.Method.POST, url + "/api/Order/ReceiveMemberAddressListByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                addressInfoBean = gson.fromJson(response, AddressInfoBean.class);
                if (addressInfoBean.isIsSuccess()) {
                    chooseAddressAdapter = new ChooseAddressAdapter(ChooseAddressActivtiy.this, addressInfoBean.getResult().getMemberAddressList());
                    listview_choose_address.setAdapter(chooseAddressAdapter);
                } else {
                    Toast.makeText(ChooseAddressActivtiy.this, "获取默认地址出错，请重试！", Toast.LENGTH_SHORT).show();
                    Log.i("woaicaojingpay", addressInfoBean.getResult().toString());
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChooseAddressActivtiy.this, "获取默认地址出错，请重试！" + error.toString(), Toast.LENGTH_SHORT).show();
                pdialog.dismiss();

                Log.i("woaicaojingpay", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(requestlist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_more:
                Intent intent = new Intent(ChooseAddressActivtiy.this, AddAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.choose_address_text_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
