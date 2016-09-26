package com.aboluo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aboluo.adapter.ShopCarAdapter;
import com.aboluo.com.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class ShopCarFragment extends Fragment {
    private View view;
  private ListView listView;
    private List<String> list;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shopcar, null);
        } else {
        }
        init();
list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i,i+"奋斗奋斗奋斗方法打发打发斯蒂芬斯蒂芬地方倒萨发送方");
        }
        ShopCarAdapter carAdapter = new ShopCarAdapter(list,context);
        listView.setAdapter(carAdapter);
        return view;
    }
    private void init()
    {
        context = ShopCarFragment.this.getActivity();
        listView = (ListView) view.findViewById(R.id.shopcar_listview);
    }
}
