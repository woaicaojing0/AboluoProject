package com.aboluo.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.ShopCarAdapter;
import com.aboluo.com.GoodsDetailActivity;
import com.aboluo.com.R;
import com.aboluo.model.ShopCarBean;
import com.aboluo.widget.MyListview;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import  com.aboluo.model.ShopCarBean.ResultBean.*;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class ShopCarFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "UserInfoMsg";
    private View view;
    private MyListview listView;
    private Context context;
    //点击的范围太小了，所有让linealayout获取点击事件，去触发ck_all;
    //cb_cart_all_linealayout 全选的里呢啊layout,固定底部的编辑，固定底部的结算
    private LinearLayout cb_cart_all_linealayout, linelayout_edit, linelayout_ok;
    //全选按钮
    private CheckBox cb_cart_all;
    //保存每个item的选中状态
    private ArrayList<Boolean> ck_checked;
    private ShopCarAdapter carAdapter;
    //编辑按钮
    private TextView btn_editAndok;
    //删除按钮
    private Button shopcar_btn_delete;
    //合计
    private TextView shopcar_allmoney;
    private RequestQueue requestQueue;
    private String APPToken = null;
    private String URL = null;
    private List<ShopCarBean.ResultBean.GoodsShoppingCartListBean> goodsShoppingCartListBean;
    private ArrayList<Boolean> ckisselected;
    private double totalpv = 0.00;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shopcar, null);
        } else {
        }
        init();
        cb_cart_all_linealayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_cart_all.isChecked()) {
                    cb_cart_all.setChecked(false);
                    for (int i = 0; i < ckisselected.size(); i++) {
                        ckisselected.set(i, false);
                        carAdapter.notifyDataSetChanged();
                    }
                } else {
                    cb_cart_all.setChecked(true);
                    for (int i = 0; i < ckisselected.size(); i++) {
                        ckisselected.set(i, true);
                        carAdapter.notifyDataSetChanged();
                    }
                }
                carAdapter.notifyDataSetChanged();
            }
        });
        btn_editAndok.setOnClickListener(this);
        shopcar_btn_delete.setOnClickListener(this);
        return view;
    }

    private void init() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(1, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(1, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(1, TimeUnit.SECONDS);
        ck_checked = new ArrayList<>();
        context = ShopCarFragment.this.getActivity();
        listView = (MyListview) view.findViewById(R.id.shopcar_listview);
        cb_cart_all = (CheckBox) view.findViewById(R.id.cb_cart_all);
        cb_cart_all_linealayout = (LinearLayout) view.findViewById(R.id.cb_cart_all_linealayout);
        btn_editAndok = (TextView) view.findViewById(R.id.btn_editAndok);
        linelayout_edit = (LinearLayout) view.findViewById(R.id.linelayout_edit);
        linelayout_ok = (LinearLayout) view.findViewById(R.id.linelayout_ok);
        shopcar_btn_delete = (Button) view.findViewById(R.id.shopcar_btn_delete);
        shopcar_allmoney = (TextView) view.findViewById(R.id.shopcar_allmoney);
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(context, "apiurl");
        APPToken = CommonUtils.GetValueByKey(context, "APPToken");
        ckisselected = new ArrayList<>();
        initshopcar();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_editAndok:
                if (btn_editAndok.getText().equals("编辑")) {
                    btn_editAndok.setText("完成");
                    linelayout_edit.setVisibility(View.VISIBLE);
                    linelayout_ok.setVisibility(View.GONE);
                } else {
                    btn_editAndok.setText("编辑");
                    linelayout_edit.setVisibility(View.GONE);
                    linelayout_ok.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.shopcar_btn_delete:
                break;
        }
    }

    private DataSetObserver AdapterDataSetObserver = new DataSetObserver() {
        /**
         * 当Adapter的notifyDataSetChanged方法执行时被调用
         */
        @Override
        public void onChanged() {
            shopcar_allmoney.setText(String.valueOf(totalPv() + ""));
        }

        /**
         * 当Adapter 调用 notifyDataSetInvalidate方法执行时被调用
         */
        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };


    private void initshopcar() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveGoodsShoppingCartList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
//                Log.i("woaicaojingshopcar",response);
                Gson gson = new Gson();
                ShopCarBean shopCarBean = gson.fromJson(response, ShopCarBean.class);
                goodsShoppingCartListBean = shopCarBean.getResult().getGoodsShoppingCartList();
                for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
                    ckisselected.add(i, false);
                }
                carAdapter = new ShopCarAdapter(goodsShoppingCartListBean, context, ckisselected);
                listView.setAdapter(carAdapter);
                carAdapter.setMbtnDecrease(listener);
                carAdapter.setMbtnIncrease(listener);
                carAdapter.setMck_by_linelayout(listener);
                carAdapter.setMhopcar_standards(listener);
                carAdapter.setMtextchage(textchage);
                carAdapter.registerDataSetObserver(AdapterDataSetObserver);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicaojingshopcar", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "6");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    /**
     * 这里处理我们listview item里面的监听事件
     */
    protected View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            int key = v.getId();
            switch (key) {
                case R.id.btnIncrease: //点击添加数量按钮，执行相应的处理
                    // 获取 Adapter 中设置的 Tag
                    if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                        final int position = (Integer) tag;
                        //更改集合的数据
                        int num = Double.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount()).intValue();
                        num++;
                        final String goodscolor = goodsShoppingCartListBean.get(position).getGoodsColor();
                        final String goodsstandards = goodsShoppingCartListBean.get(position).getGoodsStandard();
                        final String count = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount());
                        final int finalNum = num;
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL + "api/GoodsShoppingCart/ReceiveAddGoodsShoppingCart", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replace("\\","");
                                response = response.substring(1,response.length()-1);
                                Log.i("woaicaojingupdate",response);
                                goodsShoppingCartListBean.get(position).setGoodsCount(finalNum);
                                carAdapter.notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("Id", "1");
                                map.put("goodsId", String.valueOf(position));
                                map.put("goodsColor", goodscolor);
                                map.put("goodsStandard", goodsstandards);
                                map.put("goodsCount", String .valueOf(finalNum));
                                map.put("memberId", "6");
                                map.put("shopId", "1");
                                map.put("APPToken", APPToken);
                                return map;
                            }
                        };
                        requestQueue.add(stringRequest2);
                        //解决问题：点击某个按钮的时候，如果列表项所需的数据改变了，如何更新UI

                    }
                    break;
                case R.id.btnDecrease: //点击减少数量按钮 ，执行相应的处理
                    // 获取 Adapter 中设置的 Tag
                    if (tag != null && tag instanceof Integer) {
                        int position = (Integer) tag;
                        //更改集合的数据
                        int num = Double.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount()).intValue();
                        if (num > 0) {
                            num--;
                            goodsShoppingCartListBean.get(position).setGoodsCount(num); //修改集合中商品数量
                            carAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case R.id.ck_by_linelayout: //处理选中时间
                    // 获取 Adapter 中设置的 Tag
                    if (tag != null && tag instanceof Integer) {
                        int position = (Integer) tag;
                        //更改集合的数据
                        boolean ischecked = ckisselected.get(position);
                        ischecked = !ischecked;
                        ckisselected.set(position, ischecked);
                        if (isTheBoxallCheck()) {
                            cb_cart_all.setChecked(true);
                        } else {
                            cb_cart_all.setChecked(false);
                        }
                        carAdapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.shopcar_image:
                    if (tag != null && tag instanceof Integer) {
                        int position = (Integer) tag;
                        int goods_id = goodsShoppingCartListBean.get(position).getGoodsId();
                        Intent intent = new Intent(context, GoodsDetailActivity.class);
                        intent.putExtra("goods_id", goods_id);
                        context.startActivity(intent);
                    } else {
                    }
                    break;
                case R.id.shopcar_standards:
                    if (tag != null && tag instanceof Integer) {
                        int position = (Integer) tag;
                        if (goodsShoppingCartListBean.get(position).getGoodsColor() == null) {
                        } else {
                            Toast.makeText(context, goodsShoppingCartListBean.get(position).getGoodsColor().toString(), Toast.LENGTH_SHORT).show();
                        }
                        if (goodsShoppingCartListBean.get(position).getGoodsStandard() == null) {
                        } else {
                            Toast.makeText(context, goodsShoppingCartListBean.get(position).getGoodsStandard().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private ShopCarAdapter.textchage textchage = new ShopCarAdapter.textchage() {
        @Override
        public void textchage(int postion, String num) {
            int amount = Integer.valueOf(num);
            if (num.isEmpty())
                goodsShoppingCartListBean.get(postion).setGoodsCount(1);
            else if (amount > Integer.valueOf("10")) {
                goodsShoppingCartListBean.get(postion).setGoodsCount(10);
            } else {
                goodsShoppingCartListBean.get(postion).setGoodsCount(amount);
            }

            carAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 判断是否全选
     *
     * @author cj
     * 2016年10月33日
     * @version 1.0
     */
    private boolean isTheBoxallCheck() {
        for (int i = 0; i < ckisselected.size(); i++) {
            if (!ckisselected.get(i)) {
                return false;
            }
            ;
        }
        return true;
    }

    /**
     * 计算当前状态的pv总数
     *
     * @author cj
     * 2016年10月33日
     * @version 1.0
     */
    public double totalPv() {
        totalpv = 0;
        for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
            int count = Double.valueOf(goodsShoppingCartListBean.get(i).getGoodsCount()).intValue();
            double itempv = goodsShoppingCartListBean.get(i).getHyPrice();
            boolean check = ckisselected.get(i);
            int choose = 0;
            if (check) {
                choose = 1;
            }
            double item = count * choose * itempv;
            totalpv += item;
        }
        return totalpv;
    }

//    private boolean updateShopcar( final List<GoodsShoppingCartListBean>goodsShoppingCartListBean, final int goodsid) {
//        final boolean[] issuccess = {false};
//        final String goodscolor = goodsShoppingCartListBean.get(goodsid).getGoodsColor();
//        final String goodsstandards = goodsShoppingCartListBean.get(goodsid).getGoodsStandard();
//        final String count = String.valueOf(goodsShoppingCartListBean.get(goodsid).getGoodsCount());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "api/GoodsShoppingCart/ReceiveAddGoodsShoppingCart", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                response = response.replace("\\","");
//                response = response.substring(1,response.length()-1);
//                Log.i("woaicaojingupdate",response);
//                if(true) {
//                    issuccess[0] = true;
//                }else {}
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                issuccess[0] = false;
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("Id", "1");
//                map.put("goodsId", String.valueOf(goodsid));
//                map.put("goodsColor", goodscolor);
//                map.put("goodsStandard", goodsstandards);
//                map.put("goodsCount", count);
//                map.put("memberId", "6");
//                map.put("shopId", "1");
//                map.put("APPToken", APPToken);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//
//    }
}
