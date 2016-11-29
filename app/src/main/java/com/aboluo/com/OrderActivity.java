package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.aboluo.adapter.OrderTabAdapter;
import com.aboluo.fragment.orderfragment.AllOrderFragment;
import com.aboluo.fragment.orderfragment.AssessmentFragment;
import com.aboluo.fragment.orderfragment.NoPayFragment;
import com.aboluo.fragment.orderfragment.NoSendFragment;
import com.aboluo.fragment.orderfragment.NoreceiveFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/9/30.
 */

public class OrderActivity extends FragmentActivity {
    private TabLayout tab_FindFragment_title;
    private ViewPager vp_FindFragment_pager;
    private Fragment allOrderfragment, assessmentfragment, nopayfragment, noreceivefragment, nosendfragment;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private OrderTabAdapter fAdapter;
    private int selected,status;

    public OrderActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        selected = intent.getIntExtra("TAG", 0);
        status = intent.getIntExtra("status", 0);
        init();
    }

    private void init() {
        tab_FindFragment_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        //初始化fragment
        allOrderfragment = new AllOrderFragment();
        assessmentfragment = new AssessmentFragment();
        nopayfragment = new NoPayFragment();
        noreceivefragment = new NoreceiveFragment();
        nosendfragment = new NoSendFragment();
        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(allOrderfragment);
        list_fragment.add(nopayfragment);
        list_fragment.add(nosendfragment);
        list_fragment.add(noreceivefragment);
        list_fragment.add(assessmentfragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("待付款");
        list_title.add("待发货");
        list_title.add("待收货");
        list_title.add("待评价");
        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)), false);
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)), false);
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)), false);
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(3)), true);
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(4)), false);
        fAdapter = new OrderTabAdapter(getSupportFragmentManager(), list_fragment, list_title);
        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
        //tab_FindFragment_title.set
        vp_FindFragment_pager.setCurrentItem(selected);
    }
}
