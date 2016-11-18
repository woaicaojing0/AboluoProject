package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Window;
import android.widget.ExpandableListView;

import com.aboluo.adapter.OrderExpendListViewAdapter;
import com.aboluo.adapter.OrderSureListViewAdapter;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
import com.aboluo.widget.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cj34920 on 2016/11/17.
 */

public class MakeOrderActivity extends Activity {
    private ExpandableListView expandableListView;
    private OrderSureListViewAdapter orderSureListViewAdapter;
    private ArrayList<GoodsShoppingCartListBean> goodsShoppingCartListBean;
    private OrderExpendListViewAdapter expandableListAdapter;
    private Map<String, List<String>> dataset = new HashMap<>();
    private String[] parentList = new String[]{"first", "second", "third"};
    private List<String> childrenList1 = new ArrayList<>();
    private List<String> childrenList2 = new ArrayList<>();
    private List<String> childrenList3 = new ArrayList<>();
    private MyListview OrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_makeorder);
        OrderList = (MyListview) findViewById(R.id.orderlist);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + "");
        }
        orderSureListViewAdapter = new OrderSureListViewAdapter(strings, this);
        OrderList.setAdapter(orderSureListViewAdapter);
        init();
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        goodsShoppingCartListBean  =bundle.getParcelableArrayList("data");
    }
}
