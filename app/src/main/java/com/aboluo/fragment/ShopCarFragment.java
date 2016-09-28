package com.aboluo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aboluo.adapter.ShopCarAdapter;
import com.aboluo.com.R;
import com.aboluo.model.ShopCarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class ShopCarFragment extends Fragment {
    private View view;
    private ListView listView;
    private List<ShopCarInfo> list;
    private Context context;
    //点击的范围太小了，所有让linealayout获取点击事件，去触发ck_all;
    private LinearLayout cb_cart_all_linealayout;
    private CheckBox cb_cart_all;
    private ArrayList<Boolean> ck_checked;
    private ShopCarAdapter carAdapter;

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
            list.add(carInfo);
        }
        carAdapter = new ShopCarAdapter(list, context);
        listView.setAdapter(carAdapter);
        cb_cart_all_linealayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_cart_all.isChecked())
                {
                    cb_cart_all.setChecked(false);
                }
                else {
                    cb_cart_all.setChecked(true);
                }
                for (int i = 0; i < carAdapter.getCount(); i++) {
                    ck_checked.add(i, cb_cart_all.isChecked());
                }
                carAdapter.setckisselected(ck_checked);
                carAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private void init() {
        ck_checked = new ArrayList<>();
        context = ShopCarFragment.this.getActivity();
        listView = (ListView) view.findViewById(R.id.shopcar_listview);
        cb_cart_all = (CheckBox) view.findViewById(R.id.cb_cart_all);
        cb_cart_all_linealayout = (LinearLayout) view.findViewById(R.id.cb_cart_all_linealayout);
    }
}
