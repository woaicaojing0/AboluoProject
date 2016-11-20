package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.OrderSureListViewAdapter;
import com.aboluo.model.AddressDefaultBean;
import com.aboluo.model.BaseModel;
import com.aboluo.model.OrderInfoBean;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/11/17.
 */
public class MakeOrderActivity extends Activity implements View.OnClickListener {
    private OrderSureListViewAdapter orderSureListViewAdapter;
    private ArrayList<GoodsShoppingCartListBean> goodsShoppingCartListBean;
    private MyListview OrderList;
    private TextView txt_allmoney, goods_smallallmoeny, txt_goods_allnum;
    private TextView address_detailaddress, address_phone, address_name;
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
    private int requsetcode = 1;
    private String moeny;
    private  TextView order_yunfei;
    private double yunfei=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_makeorder);
        OrderList = (MyListview) findViewById(R.id.orderlist);
        init();
        orderSureListViewAdapter = new OrderSureListViewAdapter(goodsShoppingCartListBean, this);
        OrderList.setAdapter(orderSureListViewAdapter);
        for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
            yunfei =yunfei+goodsShoppingCartListBean.get(i).getYunfei();
        }
        order_yunfei.setText("运费："+String.valueOf(yunfei));
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
        order_yunfei = (TextView) findViewById(R.id.order_yunfei);
        requestQueue = MyApplication.getRequestQueue();
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        goodsShoppingCartListBean = bundle.getParcelableArrayList("data");
        moeny =  bundle.get("allmoney").toString();
        txt_allmoney.setText("￥" + bundle.get("allmoney").toString() + "元");
        goods_smallallmoeny.setText("￥" + bundle.get("allmoney").toString());
        txt_goods_allnum.setText("共计" + goodsShoppingCartListBean.size() + "件商品");
        Submit_Order.setOnClickListener(this);
        initData();
    }

    private void initData() {
        pdialog.setTitleText("加载中");
        pdialog.show();
        requestlist = new StringRequest(Request.Method.POST, url + "/api/Order/GetDefaultMemberAddressByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                addressDefaultBean = gson.fromJson(response, AddressDefaultBean.class);
                AddressDefaultBean addressDefaultBean2 = addressDefaultBean;
                if (addressDefaultBean.isIsSuccess()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    if (addressDefaultBean.getResult().getProvince().toString().indexOf("省") == -1) {
                    } else {
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
                } else {
                    Toast.makeText(MakeOrderActivity.this, "获取默认地址出错，请重试！", Toast.LENGTH_SHORT).show();
                }
                Log.i("woaicaojingpay",addressDefaultBean.getResult().toString());
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakeOrderActivity.this, "获取默认地址出错，请重试！" + error.toString(), Toast.LENGTH_SHORT).show();
                pdialog.dismiss();

                Log.i("woaicaojingpay",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(requestlist);
    }

    private void SubmitOrder() {
        final String lastmoney = String.valueOf(Double.valueOf(moeny) +yunfei);
        List<OrderInfoBean> bean = new ArrayList<>();
        for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
            OrderInfoBean bean1 = new OrderInfoBean();
            bean1.setGoodsId(goodsShoppingCartListBean.get(i).getGoodsId());
            bean1.setGoodsColor(goodsShoppingCartListBean.get(i).getGoodsColor());
            bean1.setGoodsColorId(goodsShoppingCartListBean.get(i).getGoodsColorId());
            bean1.setGoodsStandardId(goodsShoppingCartListBean.get(i).getGoodsStandardId());
            bean1.setGoodsStandard(goodsShoppingCartListBean.get(i).getGoodsStandard());
            bean1.setGoodsQuantity(goodsShoppingCartListBean.get(i).getGoodsCount());
            bean1.setFreight(goodsShoppingCartListBean.get(i).getYunfei());
            bean.add(bean1);
        }
        final Gson gson = new Gson();
        final String Products = gson.toJson(bean);
        pdialog.setTitleText("提交中");
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/Order/SubOrder", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                BaseModel baseModel = new BaseModel();
                baseModel = gson.fromJson(response,BaseModel.class);
                if(baseModel.isIsSuccess())
                {
                    Intent intent = new Intent(MakeOrderActivity.this, OrderPayActivity.class);
                    intent.putExtra("payMoney", lastmoney);
                    intent.putExtra("OrderNum", baseModel.getOrderSerialId().toString());
                    startActivityForResult(intent, requsetcode);
                }else {}

               Toast.makeText(MakeOrderActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MakeOrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                byte[] htmlBodyBytes = error.networkResponse.data;
                Log.i("woaicaojingeorror",new String(htmlBodyBytes));
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1");
                map.put("TotalPrice", moeny);
                map.put("AddressId", String.valueOf(addressDefaultBean.getResult().getId()));
                map.put("Remark", edit_remark.getText().toString());
                map.put("Products", Products);
                map.put("APPToken",APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Submit_Order:
                SubmitOrder();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data ==null)
        {}else {
            String backactivity = data.getStringExtra("back");

            if (backactivity.equals("OrderPay")) {
                this.finish();
            }
        }
    }
}
