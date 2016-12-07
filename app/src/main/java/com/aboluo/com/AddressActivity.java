package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.AddressAdapter;
import com.aboluo.model.AddressInfoBean;
import com.aboluo.model.BaseModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddressActivity extends Activity implements View.OnClickListener {
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
    private SweetAlertDialog sweetAlertDialog;
    private SweetAlertDialog baseaAlertDialog;
    private AddressAdapter addressAdapter;
    private List<AddressInfoBean.ResultBean.MemberAddressListBean> listbean;
    private boolean IsPause = false;
    private String MemberId;
    private ImageView all_address_text_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        MemberId = CommonUtils.GetMemberId(this);
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
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });
        all_address_text_back.setOnClickListener(this);

    }

    private void init() {
        address_listview = (ListView) findViewById(R.id.address_listview);
        shopcar_bottom_add_address = (LinearLayout) findViewById(R.id.shopcar_bottom_add_address);
        no_address_show = (RelativeLayout) findViewById(R.id.no_address_show);
        all_address_text_back = (ImageView) findViewById(R.id.all_address_text_back);
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        requestQueue = MyApplication.getRequestQueue();
        addressInfoBean = new AddressInfoBean();
        gson = new Gson();
        baseaAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        baseaAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        baseaAlertDialog.setTitleText("加载中......");
        baseaAlertDialog.setCancelable(false);
        initdata();
    }

    private void initdata() {
        baseaAlertDialog.show();
        stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/ReceiveMemberAddressListByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicaojingaddres", response);
                addressInfoBean = gson.fromJson(response, AddressInfoBean.class);
                int memsize = addressInfoBean.getResult().getMemberAddressList().size();
                if (memsize == 0) {
                    no_address_show.setVisibility(View.VISIBLE);
                } else {
                    no_address_show.setVisibility(View.GONE);
                    listbean = addressInfoBean.getResult().getMemberAddressList();
                    addressAdapter = new AddressAdapter(listbean, AddressActivity.this);
                    addressAdapter.setEditeOnclickListener(AddressActivity.this);
                    addressAdapter.setDeleteOnclickListener(AddressActivity.this);
                    addressAdapter.setCk_defaultOnClickListener(AddressActivity.this);
                    address_listview.setAdapter(addressAdapter);
                }
                baseaAlertDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                baseaAlertDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        final Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.address_edit:
                final int model = addressInfoBean.getResult().getMemberAddressList().get((Integer) tag).getId();
                if (tag != null && tag instanceof Integer) {
                    Toast.makeText(this, tag + "edit", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                    intent.putExtra("model", model);
                    startActivity(intent);
                } else {
                }
                break;
            case R.id.address_delete:
                if (tag != null && tag instanceof Integer) {
                    Toast.makeText(this, tag + "delete", Toast.LENGTH_SHORT).show();
                    sweetAlertDialog = new SweetAlertDialog(AddressActivity.this)
                            .setCancelText("取消").setTitleText("您确定要删除本地址么")
                            .setConfirmText("确定")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    baseaAlertDialog.show();
                                    deletedata((Integer) tag);
                                }
                            });
                    sweetAlertDialog.show();
                } else {
                }
                break;
            case R.id.checkBox:
                if (tag != null && tag instanceof Integer) {
                    CheckBox checkBox = (CheckBox) v;
                    boolean ischeked = checkBox.isChecked();
                    if (checkBox.isChecked()) {
                        ChageDefaultAddress((Integer) tag, 1);

                    } else {
                        ChageDefaultAddress((Integer) tag, 0);
                    }
                } else {
                }
                break;
            case R.id.all_address_text_back:
                finish();
                break;
        }
    }

    private void ChageDefaultAddress(final int id, final int model) {
        final SweetAlertDialog sweetAlertDialog2 = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog2.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog2.setCancelable(false);
        sweetAlertDialog2.setTitleText("修改中......");
        sweetAlertDialog2.show();
        final int last_id = addressInfoBean.getResult().getMemberAddressList().get(id).getId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/SetDefaultMemberAddress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel baseModel = new BaseModel();
                baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.isIsSuccess()) {
                    for (int i = 0; i < listbean.size(); i++) {
                        listbean.get(i).setIsDefault(0);
                    }
                    listbean.get(id).setIsDefault(model);
                } else {
                }
                sweetAlertDialog2.dismiss();
                Toast.makeText(AddressActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                addressAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sweetAlertDialog2.dismiss();
                Toast.makeText(AddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Id", String.valueOf(last_id));
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void deletedata(final int id) {
        final int deleteid = addressInfoBean.getResult().getMemberAddressList().get(id).getId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/DeleteMemberAddressById", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel baseModel = new BaseModel();
                baseModel = gson.fromJson(response, BaseModel.class);
                if (baseModel.isIsSuccess()) {
                    Toast.makeText(AddressActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    addressInfoBean.getResult().getMemberAddressList().remove(id);
                    addressAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddressActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                sweetAlertDialog.dismiss();
                baseaAlertDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                sweetAlertDialog.dismiss();
                baseaAlertDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Id", String.valueOf(deleteid));
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (IsPause) {
            initdata();
        }else {}
    }

    @Override
    protected void onPause() {
        super.onPause();
        IsPause=true;
    }
}
