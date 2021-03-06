package com.aboluo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.ShopCarAdapter;
import com.aboluo.com.GoodsDetailActivity;
import com.aboluo.com.GoodsListActivity;
import com.aboluo.com.MakeOrderActivity;
import com.aboluo.com.R;
import com.aboluo.model.BaseModel;
import com.aboluo.model.GoodsDetailInfo;
import com.aboluo.model.GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean;
import com.aboluo.model.GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorStandardListBean;
import com.aboluo.model.GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean;
import com.aboluo.model.ShopCarBean;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
import com.aboluo.model.UpdateShopInfoBean;
import com.aboluo.widget.MyRadioGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.tandong.bottomview.view.BottomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class ShopCarFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "UserInfoMsg";
    private View view;
    private PullToRefreshListView listView;
    private Context context;
    //点击的范围太小了，所有让linealayout获取点击事件，去触发ck_all;
    //cb_cart_all_linealayout 全选的里呢啊layout,固定底部的编辑，固定底部的结算
    private LinearLayout cb_cart_all_linealayout, linelayout_edit, linelayout_ok;
    //全选按钮
    private CheckBox cb_cart_all;
    //保存每个item的选中状态
    private ArrayList<Boolean> ckisselected;
    private ShopCarAdapter carAdapter;
    //编辑按钮
    private TextView btn_editAndok;
    //删除按钮
    private Button shopcar_btn_delete;
    //合计,存储底部弹出布局是由哪个postion点击的
    private TextView shopcar_allmoney, store_postion;
    private RequestQueue requestQueue;
    private String APPToken = null;
    private String URL = null, ImgUrl = null;
    private List<ShopCarBean.ResultBean.GoodsShoppingCartListBean> goodsShoppingCartListBean;
    private double totalpv = 0.00;
    private GoodsDetailInfo goodsDetailInfo;
    //父容器、自容器、商品列表颜色布局、尺寸布局,底部弹出类型的确定按钮
    private LinearLayout all_color, all_standards, goods_type_ok;
    private Boolean hascolor = false, hasstandards = false;
    private int popwith;
    private ImageView goods_detail_type_imageview;
    private static String goods_type_imgeurl; //需要放大图片的地址
    //颜色和尺寸的单选组
    private MyRadioGroup goodsdetail_type_color, goodsdetail_type_standards;
    private View bottom_view;
    private BottomView bottomView;
    private SweetAlertDialog sweetAlertDialog;
    private RelativeLayout no_shopcar, shop_content;
    private Button go_shoipping; //购物车没有信息是，去逛逛按钮
    private Button btn_push; //结算按钮
    private ArrayList<GoodsShoppingCartListBean> OrderSureList; //最终到下单页的数据
    private TextView car_goods_detail_type_txtmoney; //修改bottomview 中的商品价格
    private String MemberId;
    private static boolean isnormal;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shopcar, null);
        } else {
        }
        isnormal = true;
        MemberId = CommonUtils.GetMemberId(ShopCarFragment.this.getContext());
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
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshBase refreshView) {
                initshopcar();
            }
        });
        return view;
    }

    private void init() {
        OkHttpClient okHttpClient = new OkHttpClient();
        context = ShopCarFragment.this.getActivity();
        listView = (PullToRefreshListView) view.findViewById(R.id.shopcar_listview);
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
        popwith = ScreenUtils.getScreenWidth(context);
        popwith = popwith - CommonUtils.dip2px(context, 60);
        ImgUrl = CommonUtils.GetValueByKey(context, "ImgUrl");
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("加载中");
        no_shopcar = (RelativeLayout) view.findViewById(R.id.no_shopcar);
        shop_content = (RelativeLayout) view.findViewById(R.id.shop_content);
        go_shoipping = (Button) view.findViewById(R.id.go_shoipping);
        btn_push = (Button) view.findViewById(R.id.btn_push);
        go_shoipping.setOnClickListener(this);
        btn_push.setOnClickListener(this);
        initshopcar();
        OrderSureList = new ArrayList<>();
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
            case R.id.shopcar_btn_delete: // 删除按钮
                final Gson gson = new Gson();
                boolean selectnum = false;
                String ids1 = "";
                final List<Integer> listxuhao = new ArrayList<>();
                for (int i = 0; i < ckisselected.size(); i++) {
                    if (ckisselected.get(i)) {
                        final int id = goodsShoppingCartListBean.get(i).getId();
                        final int goodsid = goodsShoppingCartListBean.get(i).getGoodsId();
                        selectnum = true;
                        final int finalI = i;
                        listxuhao.add(i);
                        if (ids1 == "") {
                            ids1 = ids1 + String.valueOf(id);
                        } else {
                            ids1 = ids1 + "," + String.valueOf(id);
                        }
                    }
                }
                final String ids = ids1;
                if (!selectnum) {
                    Toast.makeText(context, "请选择需要删除的商品", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveDeleteGoodsShoppingCart", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response = response.replace("\\", "");
                            response = response.substring(1, response.length() - 1);
                            BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                            if (baseModel.isIsSuccess()) {
                                Iterator<ShopCarBean.ResultBean.GoodsShoppingCartListBean> it2 = goodsShoppingCartListBean.iterator();
                                int xuhao = -1;
                                while (it2.hasNext()) {
                                    it2.next();
                                    xuhao++;
                                    for (int i = 0; i < listxuhao.size(); i++) {
                                        if (xuhao == listxuhao.get(i)) {
                                            it2.remove();
                                        }
                                    }
                                }
                                Iterator<Boolean> it = ckisselected.iterator();
                                while (it.hasNext()) {
                                    boolean x = it.next();
                                    if (x) {
                                        it.remove();
                                    }
                                }
                                carAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("Ids", ids);
                            map.put("MemberId", MemberId);
                            map.put("APPToken", APPToken);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                break;
            case R.id.car_goods_type_ok: //修改商品规格按钮
                if (bottom_view == null) {
                } else {
                    final int position = Integer.valueOf(store_postion.getText().toString());
                    final RadioButton radioColor = (RadioButton) bottom_view.findViewById(goodsdetail_type_color.getCheckedRadioButtonId());
                    final RadioButton radioStandards = (RadioButton) bottom_view.findViewById(goodsdetail_type_standards.getCheckedRadioButtonId());
                    final int id = goodsShoppingCartListBean.get(position).getId();
                    final int goodsid = goodsShoppingCartListBean.get(position).getGoodsId();
                    final String count = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount());
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveAddOrUpdateGoodsShoppingCart", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response = response.replace("\\", "");
                            response = response.substring(1, response.length() - 1);
                            Log.i("woaicaojingupdate", response);
                            Gson gson = new Gson();
                            UpdateShopInfoBean updateShopInfoBean = gson.fromJson(response, UpdateShopInfoBean.class);
                            if (updateShopInfoBean.isIsSuccess()) {
                                if (radioColor == null) {
                                    goodsShoppingCartListBean.get(position).setGoodsStandard(radioStandards.getText().toString());
                                    goodsShoppingCartListBean.get(position).setGoodsStandardId(radioStandards.getId());
                                } else if (radioStandards == null) {
                                    goodsShoppingCartListBean.get(position).setGoodsColor(radioColor.getText().toString());
                                    goodsShoppingCartListBean.get(position).setGoodsColorId(radioColor.getId());
                                } else {
                                    goodsShoppingCartListBean.get(position).setGoodsStandard(radioStandards.getText().toString());
                                    goodsShoppingCartListBean.get(position).setGoodsColor(radioColor.getText().toString());
                                    goodsShoppingCartListBean.get(position).setGoodsStandardId(radioStandards.getId());
                                    goodsShoppingCartListBean.get(position).setGoodsStandardId(radioColor.getId());
                                }
                                List<GoodsStandardsBean> listStandard = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
                                for (int i = 0; i < listStandard.size(); i++) {
                                    if (listStandard.get(i).getGoodsStandardId() == radioStandards.getId()) {
                                        goodsShoppingCartListBean.get(position).setHyPrice(Double.valueOf(listStandard.get(i).getHyPrice()));

                                    }
                                }
                                carAdapter.notifyDataSetChanged();
                                bottomView.dismissBottomView();
                                Toast.makeText(context, updateShopInfoBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, updateShopInfoBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            bottomView.dismissBottomView();
                            Toast.makeText(context, "修改失败" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("Id", String.valueOf(id));
                            map.put("goodsId", String.valueOf(goodsid));
                            if (radioColor == null) {
                                map.put("goodsStandard", radioStandards.getText().toString());
                                map.put("goodsStandardId", String.valueOf(radioStandards.getId()));
                                map.put("goodsColor", "");
                            } else if (radioStandards == null) {
                                map.put("goodsColor", radioColor.getText().toString());
                                map.put("goodsColorId", String.valueOf(radioColor.getId()));
                                map.put("goodsStandard", "");
                            } else {
                                map.put("goodsStandard", radioStandards.getText().toString());
                                map.put("goodsStandardId", String.valueOf(radioStandards.getId()));
                                map.put("goodsColor", radioColor.getText().toString());
                                map.put("goodsColorId", String.valueOf(radioColor.getId()));
                            }
                            map.put("goodsCount", String.valueOf(count));
                            map.put("memberId", MemberId);
                            map.put("shopId", "1");
                            map.put("APPToken", APPToken);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest2);

                }
                break;
            case R.id.go_shoipping:
                Intent intent1 = new Intent(getActivity(), GoodsListActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_push:
                OrderSureList.clear();
                Toast.makeText(context, "开始结算啦", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MakeOrderActivity.class);
                for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
                    boolean check = ckisselected.get(i);
                    if (check) {
                        OrderSureList.add(goodsShoppingCartListBean.get(i));
                    }
                }
                if (OrderSureList.size() == 0) {
                    Toast.makeText(context, "请选择需要结算的商品", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra("allmoney", shopcar_allmoney.getText().toString());
                    intent.putExtra("data", OrderSureList);
                    intent.putExtra("payfrom", "1"); //代表从购物车结算的
                    startActivity(intent);
                }
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
            btn_push.setText("结算(" + String.valueOf(totalCheckNumber()) + ")");
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
        MemberId = CommonUtils.GetMemberId(ShopCarFragment.this.getContext());
        sweetAlertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveGoodsShoppingCartList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicaojingshopcar", response);
                Gson gson = new Gson();
                ShopCarBean shopCarBean = gson.fromJson(response, ShopCarBean.class);
                if (shopCarBean.isIsSuccess()) {
                    if (shopCarBean.getResult().getGoodsShoppingCartList().size() == 0) {
                        no_shopcar.setVisibility(View.VISIBLE);
                        shop_content.setVisibility(View.GONE);
                    } else {
                        no_shopcar.setVisibility(View.GONE);
                        shop_content.setVisibility(View.VISIBLE);
                        goodsShoppingCartListBean = shopCarBean.getResult().getGoodsShoppingCartList();
                        ckisselected.clear();
                        for (int i = 0; i < goodsShoppingCartListBean.size(); i++) {
                            ckisselected.add(i, false);
                        }
                        carAdapter = new ShopCarAdapter(goodsShoppingCartListBean, context, ckisselected);
                        listView.setAdapter(carAdapter);
                        carAdapter.setMbtnDecrease(listener);
                        carAdapter.setMbtnIncrease(listener);
                        carAdapter.setMck_by_linelayout(listener);
                        carAdapter.setMhopcar_standards(listener);
                        carAdapter.setMetAmount(focusChangeListener);
                        carAdapter.setMshopcar_image(listener);
                        carAdapter.registerDataSetObserver(AdapterDataSetObserver);
                    }
                } else {
                }
                sweetAlertDialog.dismiss();
                listView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicaojingshopcar", error.toString());
                sweetAlertDialog.dismiss();
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
                        final int id = goodsShoppingCartListBean.get(position).getId();
                        final int goodsid = goodsShoppingCartListBean.get(position).getGoodsId();
                        final String goodscolor = goodsShoppingCartListBean.get(position).getGoodsColor();
                        final String goodscolorid = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsColorId());
                        final String goodsstandards = goodsShoppingCartListBean.get(position).getGoodsStandard();
                        final String goodsstandardsid = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsStandardId());
                        final String count = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount());
                        final int finalNum = num;
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveAddOrUpdateGoodsShoppingCart", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replace("\\", "");
                                response = response.substring(1, response.length() - 1);
                                Log.i("woaicaojingupdate", response);
                                Gson gson = new Gson();
                                UpdateShopInfoBean updateShopInfoBean = gson.fromJson(response, UpdateShopInfoBean.class);
                                if (updateShopInfoBean.isIsSuccess()) {
                                    goodsShoppingCartListBean.get(position).setGoodsCount(finalNum);
                                    carAdapter.notifyDataSetChanged();
                                    Toast.makeText(context, updateShopInfoBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, updateShopInfoBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "修改失败" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("Id", String.valueOf(id));
                                map.put("goodsId", String.valueOf(goodsid));
                                map.put("goodsColor", goodscolor);
                                map.put("goodsColorId", goodscolorid);
                                map.put("goodsStandard", goodsstandards);
                                map.put("goodsStandardId", goodsstandardsid);
                                map.put("goodsCount", String.valueOf(finalNum));
                                map.put("memberId", MemberId);
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
                        final int position = (Integer) tag;
                        //更改集合的数据
                        int num = Double.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount()).intValue();
                        if (num > 1) {
                            num--;
                            final int id = goodsShoppingCartListBean.get(position).getId();
                            final int goodsid = goodsShoppingCartListBean.get(position).getGoodsId();
                            final String goodscolor = goodsShoppingCartListBean.get(position).getGoodsColor();
                            final String goodscolorid = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsColorId());
                            final String goodsstandards = goodsShoppingCartListBean.get(position).getGoodsStandard();
                            final String goodsstandardsid = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsStandardId());
                            final String count = String.valueOf(goodsShoppingCartListBean.get(position).getGoodsCount());
                            final int finalNum = num;
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveAddOrUpdateGoodsShoppingCart", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    response = response.replace("\\", "");
                                    response = response.substring(1, response.length() - 1);
                                    Log.i("woaicaojingupdate", response);
                                    Gson gson = new Gson();
                                    UpdateShopInfoBean updateShopInfoBean = gson.fromJson(response, UpdateShopInfoBean.class);
                                    if (updateShopInfoBean.isIsSuccess()) {
                                        goodsShoppingCartListBean.get(position).setGoodsCount(finalNum);
                                        carAdapter.notifyDataSetChanged();
                                        Toast.makeText(context, updateShopInfoBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, updateShopInfoBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "修改失败" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("Id", String.valueOf(id));
                                    map.put("goodsId", String.valueOf(goodsid));
                                    map.put("goodsColor", goodscolor);
                                    map.put("goodsColorId", goodscolorid);
                                    map.put("goodsStandard", goodsstandards);
                                    map.put("goodsStandardId", goodsstandardsid);
                                    map.put("goodsCount", String.valueOf(finalNum));
                                    map.put("memberId", MemberId);
                                    map.put("shopId", "1");
                                    map.put("APPToken", APPToken);
                                    return map;
                                }
                            };
                            requestQueue.add(stringRequest2);


                            goodsShoppingCartListBean.get(position).setGoodsCount(num); //修改集合中商品数量
                            carAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "当前商品不能再少啦", Toast.LENGTH_SHORT).show();
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
                        String color = null, standard = null;
                        int position = (Integer) tag;
                        if (goodsShoppingCartListBean.get(position).getGoodsColor() == null) {
                        } else {
                            color = goodsShoppingCartListBean.get(position).getGoodsColor().toString();
                            Toast.makeText(context, goodsShoppingCartListBean.get(position).getGoodsColor().toString(), Toast.LENGTH_SHORT).show();
                        }
                        if (goodsShoppingCartListBean.get(position).getGoodsStandard() == null) {
                        } else {
                            standard = goodsShoppingCartListBean.get(position).getGoodsStandard().toString();
                            Toast.makeText(context, goodsShoppingCartListBean.get(position).getGoodsStandard().toString(), Toast.LENGTH_SHORT).show();
                        }
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                        bottomView = new BottomView(getActivity(), R.style.BottomViewTheme_Defalut, R.layout.fragment_shopcar_update_type);
                        bottomView.setAnimation(R.style.BottomToTopAnim);
                        bottomView.showBottomView(true);
                        bottom_view = bottomView.getView();
                        inittype(bottomView.getView(), goodsShoppingCartListBean.get(position).getGoodsId(), color, standard, position);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // 此处为得到焦点时的处理内容
            if (hasFocus) {
            } else {
                EditText editText = (EditText) v;
                Toast.makeText(context, editText.getText().toString() + "暂时不能修改", Toast.LENGTH_SHORT).show();
            }
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
     * 计算当前的选中个数
     *
     * @author cjr
     * QQ1206067690
     * 2016年10月11日
     * @version 1.0
     */
    public int totalCheckNumber() {
        int cnumber = 0;
        for (int i = 0; i < ckisselected.size(); i++) {
            if (ckisselected.get(i)) {
                cnumber++;
            }
        }
        return cnumber;
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
                OrderSureList.add(goodsShoppingCartListBean.get(i));
            }
            double item = count * choose * itempv;
            totalpv += item;
        }
        return totalpv;
    }

    /**
     * 加载更新商品类别
     *
     * @param view 底部弹出的布局
     */
    private void inittype(View view, int goods_id, String color, String standards, int postion) {
        all_color = (LinearLayout) view.findViewById(R.id.car_all_color);
        all_standards = (LinearLayout) view.findViewById(R.id.car_all_standards);
        goods_detail_type_imageview = (ImageView) view.findViewById(R.id.car_goods_detail_type_imageview);
        goodsdetail_type_color = (MyRadioGroup) view.findViewById(R.id.car_goodsdetail_type_color);
        goodsdetail_type_standards = (MyRadioGroup) view.findViewById(R.id.car_goodsdetail_type_standards);
        goods_type_ok = (LinearLayout) view.findViewById(R.id.car_goods_type_ok);
        goods_type_ok.setOnClickListener(this);
        store_postion = (TextView) view.findViewById(R.id.store_postion);
        car_goods_detail_type_txtmoney = (TextView) view.findViewById(R.id.car_goods_detail_type_txtmoney);
        store_postion.setText(String.valueOf(postion));
        getgoods_detail(goods_id, color, standards);
    }


    /**
     * 获取商品详情的数据，在这个方法里加载initrollPagerView、initwebview
     */
    private void getgoods_detail(final int goods_id, final String color, final String standards) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Goods/ReceiveGoodsById", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicoajing", response);
                Gson gson = new Gson();
                goodsDetailInfo = gson.fromJson(response, GoodsDetailInfo.class);
                List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean> listcolor = goodsDetailInfo.getResult().getGoodsInfo().getGoodsColor();
                List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> listStandard = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
                List<GoodsColorStandardListBean> listcolorstandards = goodsDetailInfo.getResult().getGoodsInfo().getGoodsColorStandardList();
                String imageurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo();
                if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo() == null) {
                } else {
                    String[] imageurls = imageurl.split(";");
                    Log.i("woaicaojinggoodstype", imageurls[0]);
                }

                if (listcolor == null) {
                    all_color.setVisibility(View.GONE);
                } else {
                    if (listcolor.size() == 0) {
                        all_color.setVisibility(View.GONE);
                    } else {
                        hascolor = true;
                        CreateColor(listcolor, color, listcolorstandards, listStandard);
                    }
                }
                if (listStandard == null) {
                    all_standards.setVisibility(View.GONE);
                } else {
                    if (listStandard.size() == 0) {
                        all_standards.setVisibility(View.GONE);
                    } else {
                        hasstandards = true;
                        CreateStandards(listStandard, listcolorstandards, listcolor, standards);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicoajing", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("GoodsId", String.valueOf(goods_id));
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 创建color radiobutton
     *
     * @param listcolor List<GoodsColorBean></>
     */
    private void CreateColor(final List<GoodsColorBean> listcolor, String color, final List<GoodsColorStandardListBean> listcolorstandard, final List<GoodsStandardsBean> liststandards) {

        int num = listcolor.size() / 5;
        int yushu = listcolor.size() % 5;
        if (num == 0) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.dip2px(context, 54)));
            for (int i2 = 0; i2 < listcolor.size(); i2++) {
                RadioButton button = new RadioButton(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(context, 50));
                layoutParams.setMargins(CommonUtils.dip2px(context, 10), 0, 0, 0);
                button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                Bitmap a = null;
                button.setButtonDrawable(new BitmapDrawable(a));
                button.setGravity(Gravity.CENTER);
                ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                button.setTextColor(csl);
                button.setLayoutParams(layoutParams);
                button.setText(listcolor.get(i2).getColor());
                button.setId(listcolor.get(i2).getGoodsColorId());
                final int finalI = i2;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean ischeck = false;
                        if (v.getTag() == null) {
                            v.setTag(true);
                        } else {
                            ischeck = (boolean) v.getTag();
                        }
                        int colorid = v.getId();
                        goods_type_imgeurl = ImgUrl + listcolor.get(finalI).getColorImg();
                        Log.i("woaicaojing", goods_type_imgeurl);
                        Log.i("woaicaojing + picURl", ImgUrl + listcolor.get(finalI).getColorImg());
                        Picasso.with(context).load(ImgUrl + listcolor.get(finalI).getColorImg()).placeholder(getResources().getDrawable(R.drawable.imagviewloading)).into(goods_detail_type_imageview);
                        RadioButton radioButton = (RadioButton) bottom_view.findViewById(v.getId());
                        Toast.makeText(context, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                        if (listcolorstandard == null) {
                        } else {
                            RadioButton checkRadioButton = (RadioButton) bottom_view.findViewById(goodsdetail_type_standards.getCheckedRadioButtonId());
                            List<Integer> listStandard = new ArrayList<Integer>();
                            for (int i = 0; i < listcolorstandard.size(); i++) {
                                if (listcolorstandard.get(i).getGoodsColorId() == colorid) {
                                    listStandard.add(listcolorstandard.get(i).getGoodsStandardId());
                                }
                            }
                            for (int i = 0; i < liststandards.size(); i++) {
                                boolean isshow = false;
                                for (int i1 = 0; i1 < listStandard.size(); i1++) {
                                    if (liststandards.get(i).getGoodsStandardId() == listStandard.get(i1)) {
                                        isshow = true;
                                    }
                                }
                                if (!isshow) {
                                    RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(liststandards.get(i).getGoodsStandardId());
                                    radioButton1.setEnabled(false);
                                } else {
                                    RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(liststandards.get(i).getGoodsStandardId());
                                    radioButton1.setEnabled(true);
                                }
                            }
                        }

//                        if(ischeck ==false)
//                        {
//                            ((RadioButton) v).setChecked(true);
//                            v.setTag(true);
//                        }else
//                        {
//                            ((RadioButton) v).setChecked(false);
//                            v.setTag(false);
//                        }

                    }
                });
                linearLayout.addView(button);
                if (listcolor.get(i2).getColor().equals(color)) {
                    button.setChecked(true);
                } else {
                }
            }
            goodsdetail_type_color.addView(linearLayout);
        } else {
            if (yushu == 0) {
                for (int i = 0; i < num; i++) {
                    CreateRadioButtonToColor(i, 5, listcolor, listcolorstandard, liststandards, color);
                }

            } else {
                num = num + 1;
                for (int i = 0; i < num; i++) {
                    if (i == (num - 1)) {
                        CreateRadioButtonToColor(i, yushu, listcolor, listcolorstandard, liststandards, color);

                    } else {
                        CreateRadioButtonToColor(i, 5, listcolor, listcolorstandard, liststandards, color);
                    }

                }
            }

        }
    }

    /**
     * 创建尺寸 radiobutton
     *
     * @param liststandards List<GoodsStandardsBean></>
     */
    private void CreateStandards(final List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> liststandards, final List<GoodsColorStandardListBean> listcolorstandard, final List<GoodsColorBean> listcolor, String standards) {

        int num = liststandards.size() / 5;
        int yushu = liststandards.size() % 5;
        if (num == 0) {
            final LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.dip2px(context, 54)));
            for (int i2 = 0; i2 < liststandards.size(); i2++) {
                final RadioButton button = new RadioButton(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(context, 50));
                layoutParams.setMargins(CommonUtils.dip2px(context, 10), 0, 0, 0);
                button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                Bitmap a = null;
                button.setButtonDrawable(new BitmapDrawable(a));
                button.setGravity(Gravity.CENTER);
                ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                button.setTextColor(csl);
                button.setLayoutParams(layoutParams);
                button.setText(liststandards.get(i2).getStandard());
                button.setId(liststandards.get(i2).getGoodsStandardId());
                final int finalI = i2;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int standardsId = v.getId();
                        RadioButton checkRadioButton = (RadioButton) bottom_view.findViewById(goodsdetail_type_color.getCheckedRadioButtonId());
                        List<Integer> listcolorInt = new ArrayList<Integer>();
                        if (listcolorstandard == null) {
                        } else {
                            for (int i = 0; i < listcolorstandard.size(); i++) {
                                if (listcolorstandard.get(i).getGoodsStandardId() == standardsId) {
                                    listcolorInt.add(listcolorstandard.get(i).getGoodsColorId());
                                }
                            }
                            for (int i = 0; i < listcolor.size(); i++) {
                                boolean isshow = false;
                                for (int i1 = 0; i1 < listcolorInt.size(); i1++) {
                                    if (listcolor.get(i).getGoodsColorId() == listcolorInt.get(i1)) {
                                        isshow = true;
                                    }
                                }
                                if (!isshow) {
                                    RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(listcolor.get(i).getGoodsColorId());
                                    radioButton1.setEnabled(false);
                                } else {
                                    RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(listcolor.get(i).getGoodsColorId());
                                    ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                                    radioButton1.setTextColor(csl);
                                    radioButton1.setEnabled(true);
                                }
                            }
                            List<GoodsStandardsBean> goodsStandardsBean = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
                            for (int i = 0; i < goodsStandardsBean.size(); i++) {
                                if (goodsStandardsBean.get(i).getGoodsStandardId() == standardsId) {
                                    car_goods_detail_type_txtmoney.setText("会员价：￥" + String.valueOf(goodsStandardsBean.get(i).getHyPrice()) + "元");
                                }
                            }
                        }

                    }
                });
                linearLayout.addView(button);
                if (liststandards.get(i2).getStandard().equals(standards)) {
                    button.setChecked(true);
                } else {
                }
            }
            goodsdetail_type_standards.addView(linearLayout);
        } else {
            if (yushu == 0) {
                for (int i = 0; i < num; i++) {
                    CreateRadioButtonToStandards(i, 5, liststandards, listcolorstandard, listcolor, standards);
                }

            } else {
                num = num + 1;
                for (int i = 0; i < num; i++) {
                    if (i == (num - 1)) {
                        CreateRadioButtonToStandards(i, yushu, liststandards, listcolorstandard, listcolor, standards);

                    } else {
                        CreateRadioButtonToStandards(i, 5, liststandards, listcolorstandard, listcolor, standards);
                    }

                }
            }

        }
    }

    private void CreateRadioButtonToColor(int i, int num, final List<GoodsColorBean> listcolor, final List<GoodsColorStandardListBean> listcolorstandard, final List<GoodsStandardsBean> liststandards, final String color) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(context, 54)));
        for (int i2 = 0; i2 < num; i2++) {
            RadioButton button = new RadioButton(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(context, 50));
            layoutParams.setMargins(CommonUtils.dip2px(context, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            Bitmap a = null;
            button.setButtonDrawable(new BitmapDrawable(a));
            ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
            button.setTextColor(csl);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(layoutParams);
            button.setId((listcolor.get((5 * i) + i2).getGoodsColorId()));
            int i22 = (5 * i) + i2;
            button.setText(listcolor.get((5 * i) + i2).getColor());
            final int finalI = i22;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean ischeck = false;
                    if (v.getTag() == null) {
                        v.setTag(true);
                    } else {
                        ischeck = (boolean) v.getTag();
                    }
                    int colorid = v.getId();
                    goods_type_imgeurl = ImgUrl + listcolor.get(finalI).getColorImg();
                    Log.i("woaicaojing", goods_type_imgeurl);
                    Log.i("woaicaojing + picURl", ImgUrl + listcolor.get(finalI).getColorImg());
                    Picasso.with(context).load(ImgUrl + listcolor.get(finalI).getColorImg()).placeholder(getResources().getDrawable(R.drawable.imagviewloading)).into(goods_detail_type_imageview);
                    RadioButton radioButton = (RadioButton) bottom_view.findViewById(v.getId());
                    Toast.makeText(context, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (listcolorstandard == null) {
                    } else {
                        RadioButton checkRadioButton = (RadioButton) bottom_view.findViewById(goodsdetail_type_standards.getCheckedRadioButtonId());
                        List<Integer> listStandard = new ArrayList<Integer>();
                        for (int i = 0; i < listcolorstandard.size(); i++) {
                            if (listcolorstandard.get(i).getGoodsColorId() == colorid) {
                                listStandard.add(listcolorstandard.get(i).getGoodsStandardId());
                            }
                        }
                        for (int i = 0; i < liststandards.size(); i++) {
                            boolean isshow = false;
                            for (int i1 = 0; i1 < listStandard.size(); i1++) {
                                if (liststandards.get(i).getGoodsStandardId() == listStandard.get(i1)) {
                                    isshow = true;
                                }
                            }
                            if (!isshow) {
                                RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(liststandards.get(i).getGoodsStandardId());
                                radioButton1.setEnabled(false);
                            } else {
                                RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(liststandards.get(i).getGoodsStandardId());
                                radioButton1.setEnabled(true);
                            }
                        }
                    }

//                        if(ischeck ==false)
//                        {
//                            ((RadioButton) v).setChecked(true);
//                            v.setTag(true);
//                        }else
//                        {
//                            ((RadioButton) v).setChecked(false);
//                            v.setTag(false);
//                        }
                }
            });
            linearLayout.addView(button);
            if (listcolor.get((5 * i) + i2).getColor().equals(color)) {
                button.setChecked(true);
            } else {
            }
        }
        goodsdetail_type_color.addView(linearLayout);
    }

    private void CreateRadioButtonToStandards(int i, int num, final List<GoodsStandardsBean> liststandards, final List<GoodsColorStandardListBean> listcolorstandard, final List<GoodsColorBean> listcolor, String standards) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(context, 54)));
        for (int i2 = 0; i2 < num; i2++) {
            RadioButton button = new RadioButton(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(context, 50));
            layoutParams.setMargins(CommonUtils.dip2px(context, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            Bitmap a = null;
            button.setButtonDrawable(new BitmapDrawable(a));
            ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
            button.setTextColor(csl);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(layoutParams);
            button.setId(liststandards.get((5 * i) + i2).getGoodsStandardId());
            int i22 = (5 * i) + i2;
            button.setText(liststandards.get((5 * i) + i2).getStandard());
            final int finalI = i22;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int standardsId = v.getId();
                    RadioButton checkRadioButton = (RadioButton) bottom_view.findViewById(goodsdetail_type_color.getCheckedRadioButtonId());
                    List<Integer> listcolorInt = new ArrayList<Integer>();
                    if (listcolorstandard == null) {
                    } else {
                        for (int i = 0; i < listcolorstandard.size(); i++) {
                            if (listcolorstandard.get(i).getGoodsStandardId() == standardsId) {
                                listcolorInt.add(listcolorstandard.get(i).getGoodsColorId());
                            }
                        }
                        for (int i = 0; i < listcolor.size(); i++) {
                            boolean isshow = false;
                            for (int i1 = 0; i1 < listcolorInt.size(); i1++) {
                                if (listcolor.get(i).getGoodsColorId() == listcolorInt.get(i1)) {
                                    isshow = true;
                                }
                            }
                            if (!isshow) {
                                RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(listcolor.get(i).getGoodsColorId());
                                radioButton1.setEnabled(false);
                            } else {
                                RadioButton radioButton1 = (RadioButton) bottom_view.findViewById(listcolor.get(i).getGoodsColorId());
                                ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                                radioButton1.setTextColor(csl);
                                radioButton1.setEnabled(true);
                            }
                        }
                        List<GoodsStandardsBean> goodsStandardsBean = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
                        for (int i = 0; i < goodsStandardsBean.size(); i++) {
                            if (goodsStandardsBean.get(i).getGoodsStandardId() == standardsId) {
                                car_goods_detail_type_txtmoney.setText("会员价：￥" + String.valueOf(goodsStandardsBean.get(i).getHyPrice()) + "元");

                            }
                        }
                    }
                }
            });
            if (standards.equals(liststandards.get((5 * i) + i2).getStandard())) {
                button.setChecked(true);
            } else {
            }
            linearLayout.addView(button);
        }
        goodsdetail_type_standards.addView(linearLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(context, "暂停结束了", Toast.LENGTH_SHORT).show();
        if (isnormal) {
            isnormal = false;
        } else {
            initshopcar();
            if (ckisselected.size() != 0) {
                for (int i = 0; i < ckisselected.size(); i++) {
                    ckisselected.set(i, false);
                }
                if (carAdapter != null) {
                    carAdapter.notifyDataSetChanged();
                }
                cb_cart_all.setChecked(false);
            } else {
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            initshopcar();
        }
    }
}
