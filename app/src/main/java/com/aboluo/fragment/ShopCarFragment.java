package com.aboluo.fragment;

import android.content.Context;
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
import com.aboluo.com.R;
import com.aboluo.model.ShopCarBean;
import com.aboluo.model.ShopCarInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class ShopCarFragment extends Fragment implements View.OnClickListener, ShopCarAdapter.Callback {
    private static String TAG = "UserInfoMsg";
    private View view;
    private ListView listView;
    private List<ShopCarInfo> list;
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
    private String APPToken =null;
    private String URL =null;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shopcar, null);
        } else {
        }
        init();

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShopCarInfo carInfo = new ShopCarInfo();
            carInfo.setGoodsName(i + "奋斗奋斗奋斗方法打发打发斯蒂芬斯蒂芬地方倒萨发送方");
            carInfo.setNum(i);
            carInfo.setMoney(180 + i);
            list.add(carInfo);
        }

        cb_cart_all_linealayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_cart_all.isChecked()) {
                    cb_cart_all.setChecked(false);
                    for (int i = 0; i < carAdapter.getCount(); i++) {
                        ck_checked.set(i, cb_cart_all.isChecked());
                    }
                } else {
                    cb_cart_all.setChecked(true);
                    for (int i = 0; i < carAdapter.getCount(); i++) {
                        ck_checked.add(i, cb_cart_all.isChecked());
                    }
                }

                carAdapter.setckisselected(ck_checked);
                carAdapter.notifyDataSetChanged();
            }
        });
        btn_editAndok.setOnClickListener(this);
        shopcar_btn_delete.setOnClickListener(this);
        return view;
    }

    private void init() {
        ck_checked = new ArrayList<>();
        context = ShopCarFragment.this.getActivity();
        listView = (ListView) view.findViewById(R.id.shopcar_listview);
        cb_cart_all = (CheckBox) view.findViewById(R.id.cb_cart_all);
        cb_cart_all_linealayout = (LinearLayout) view.findViewById(R.id.cb_cart_all_linealayout);
        btn_editAndok = (TextView) view.findViewById(R.id.btn_editAndok);
        linelayout_edit = (LinearLayout) view.findViewById(R.id.linelayout_edit);
        linelayout_ok = (LinearLayout) view.findViewById(R.id.linelayout_ok);
        shopcar_btn_delete = (Button) view.findViewById(R.id.shopcar_btn_delete);
        shopcar_allmoney = (TextView) view.findViewById(R.id.shopcar_allmoney);
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(context,"apiurl");
        APPToken = CommonUtils.GetValueByKey(context,"APPToken");
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
                int num = carAdapter.getckisselected().size();
                ArrayList<Boolean> temp_isSelected = new ArrayList<>();
                List<ShopCarInfo> temp_info = new ArrayList<>();
                int j = 0;
                for (int i = 0; i < num; i++) {
                    if (!carAdapter.getckisselected().get(i)) {
                        temp_isSelected.add(j, false);
                        temp_info.add(j++, list.get(i));
                    }
                }
                list = temp_info;
//                carAdapter.setMlist(temp_info);
                carAdapter.setckisselected(temp_isSelected);
                carAdapter.notifyDataSetChanged();
                break;
        }
    }

    private DataSetObserver AdapterDataSetObserver = new DataSetObserver() {
        /**
         * 当Adapter的notifyDataSetChanged方法执行时被调用
         */
        @Override
        public void onChanged() {
            double money = 0;
            int num = carAdapter.getckisselected().size();
            for (int i = 0; i < num; i++) {
                if (carAdapter.getckisselected().get(i)) {
                    int count = list.get(i).getNum();
                    money = money + (list.get(i).getMoney() * count);
                }
            }
            Toast.makeText(context, String.valueOf(money), Toast.LENGTH_SHORT).show();
            shopcar_allmoney.setText(String.valueOf(money));
        }

        /**
         * 当Adapter 调用 notifyDataSetInvalidate方法执行时被调用
         */
        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };


    @Override
    public void click(View v, int Amount, int postion) {
        Log.i(TAG, "当前数量" + Amount + ">>>>>>>>>>>点击的第" + postion + "条");
//        Toast.makeText(context, Amount +">>>>>>>>>>>"+postion+"", Toast.LENGTH_SHORT).show();
        list.get(postion).setNum(Amount);
    }

    private void initshopcar() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/GoodsShoppingCart/ReceiveGoodsShoppingCartList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                Log.i("woaicaojingshopcar",response);
                Gson gson = new Gson();
                ShopCarBean shopCarBean = gson.fromJson(response,ShopCarBean.class);
                carAdapter = new ShopCarAdapter(shopCarBean.getResult().getGoodsShoppingCartList(), context, ShopCarFragment.this);
                listView.setAdapter(carAdapter);
                carAdapter.registerDataSetObserver(AdapterDataSetObserver);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicaojingshopcar",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> map = new HashMap<>();
                map.put("MemberId","6");
                map.put("APPToken",APPToken);
                return  map;
            }
        };
       requestQueue.add(stringRequest);

    }
}
