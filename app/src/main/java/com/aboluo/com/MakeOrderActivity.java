package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.MenuGridviewAdapter;
import com.aboluo.adapter.OrderExpendListViewAdapter;
import com.aboluo.adapter.OrderSureListViewAdapter;
import com.aboluo.model.AddressDefaultBean;
import com.aboluo.model.AddressInfoBean;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
import com.aboluo.widget.MyListview;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  com.aboluo.model.AddressInfoBean.ResultBean.MemberAddressListBean;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/11/17.
 */

public class MakeOrderActivity extends Activity  implements View.OnClickListener{
    private OrderSureListViewAdapter orderSureListViewAdapter;
    private ArrayList<GoodsShoppingCartListBean> goodsShoppingCartListBean;
    private MyListview OrderList;
    private double allmoney;
private TextView txt_allmoney,goods_smallallmoeny,txt_goods_allnum;
    private TextView address_detailaddress,address_phone,address_name;
    private Volley volley;
    private String url;
    private static String APPToken;
    private StringRequest requestlist;
    private RequestQueue requestQueue;
    private Gson gson;
    private AddressDefaultBean addressDefaultBean;
    private SweetAlertDialog pdialog;
    private EditText edit_remark;
    private Button Submit_Order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_makeorder);
        OrderList = (MyListview) findViewById(R.id.orderlist);
        init();
        orderSureListViewAdapter = new OrderSureListViewAdapter(goodsShoppingCartListBean, this);
        OrderList.setAdapter(orderSureListViewAdapter);

//        initialData();
//        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewdemo);
//        expandableListAdapter = new OrderExpendListViewAdapter(dataset,this,parentList);
//        expandableListView.setAdapter(expandableListAdapter);
//        for (int i = 0; i < parentList.length; i++) {
//            expandableListView.expandGroup(i);
//        }
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                return true;
//            }
//        });

    }
//    private void initialData() {
//        childrenList1.add(parentList[0] + "-" + "first");
//        childrenList1.add(parentList[0] + "-" + "second");
//        childrenList1.add(parentList[0] + "-" + "third");
//        childrenList2.add(parentList[1] + "-" + "first");
//        childrenList2.add(parentList[1] + "-" + "second");
//        childrenList2.add(parentList[1] + "-" + "third");
//        childrenList3.add(parentList[2] + "-" + "first");
//        childrenList3.add(parentList[2] + "-" + "second");
//        childrenList3.add(parentList[2] + "-" + "third");
//        dataset.put(parentList[0], childrenList1);
//        dataset.put(parentList[1], childrenList2);
//        dataset.put(parentList[2], childrenList3);
//    }

    private void init() {
        txt_allmoney = (TextView) findViewById(R.id.txt_allmoney);
        goods_smallallmoeny = (TextView) findViewById(R.id.goods_smallallmoeny);
        txt_goods_allnum = (TextView) findViewById(R.id.txt_goods_allnum);
        address_detailaddress = (TextView) findViewById(R.id.address_detailaddress);
        address_phone = (TextView) findViewById(R.id.address_phone);
        address_name = (TextView) findViewById(R.id.address_name);
        edit_remark = (EditText) findViewById(R.id.edit_remark);
        Submit_Order = (Button) findViewById(R.id.Submit_Order);
        requestQueue = MyApplication.getRequestQueue();
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        gson= new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        goodsShoppingCartListBean  =bundle.getParcelableArrayList("data");
        txt_allmoney.setText("￥"+bundle.get("allmoney").toString()+"元");
        goods_smallallmoeny.setText("￥"+bundle.get("allmoney").toString());
        txt_goods_allnum.setText("共计"+goodsShoppingCartListBean.size()+"件商品");
        Submit_Order.setOnClickListener(this);
        initData();
    }
    private  void initData()
    {
        pdialog.setTitleText("加载中");
        pdialog.show();
        requestlist =  new StringRequest(Request.Method.POST, url+"/api/Order/GetDefaultMemberAddressByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1, response.length()-1);
                addressDefaultBean = gson.fromJson(response,AddressDefaultBean.class);
                AddressDefaultBean addressDefaultBean2 = addressDefaultBean;
                if(addressDefaultBean.isIsSuccess())
                {
                    StringBuffer stringBuffer =new StringBuffer();
                    if(addressDefaultBean.getResult().getProvince().toString().indexOf("省")==-1)
                    {
                    }else {
                        stringBuffer.append(addressDefaultBean.getResult().getProvince().toString());
                    }
                    stringBuffer.append(addressDefaultBean.getResult().getCity().toString());
                    stringBuffer.append(addressDefaultBean.getResult().getRegion().toString());
                    stringBuffer.append(addressDefaultBean.getResult().getStreet().toString());
                    stringBuffer.append(addressDefaultBean.getResult().getAddress().toString());
                    address_detailaddress.setText(stringBuffer.toString());
                    address_name.setText(addressDefaultBean.getResult().getReceiver().toString());
                    address_phone.setText(addressDefaultBean.getResult().getMobile().toString());
//                    addressInfoBean.getResult().getMemberAddressList().get(0).get
                }else {
                    Toast.makeText(MakeOrderActivity.this, "获取默认地址出错，请重试！", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakeOrderActivity.this, "获取默认地址出错，请重试！"+error.toString(), Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map = new HashMap<>();
                map.put("MemberId","1");
                map.put("APPToken",APPToken);
                return  map;
            }
        };
        requestQueue.add(requestlist);
    }

    private void SubmitOrder()
    {
        pdialog.setTitleText("提交中");
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("MemberId","1");
                map.put("TotalPrice",txt_allmoney.getText().toString());
                map.put("AddressId",String.valueOf(addressDefaultBean.getResult().getId()));
                map.put("Remark",edit_remark.getText().toString());
                map.put("Products","");
                return  map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Submit_Order:
//                SubmitOrder();
                Intent intent = new Intent(this,OrderPayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
