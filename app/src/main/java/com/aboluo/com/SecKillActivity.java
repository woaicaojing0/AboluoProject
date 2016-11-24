package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.aboluo.adapter.SecKillDetailAdapter;
import com.aboluo.fragment.SecKillFragment.CenterFragment;
import com.aboluo.fragment.SecKillFragment.LeftFragment;
import com.aboluo.fragment.SecKillFragment.RightFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/11/24.
 */

public class SecKillActivity extends FragmentActivity {
    private TabLayout tabLayout_seckilldetail;
    private ViewPager viewpage_seckilldetail;
    private Fragment leftfragment, rightfragment, centerfragment;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;
    private SecKillDetailAdapter fAdapter;
    private int selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seckill);
        Intent intent = getIntent();
        selected = intent.getIntExtra("TAG", 0);
        init();;
    }
    private void init() {
        tabLayout_seckilldetail = (TabLayout) findViewById(R.id.tabLayout_seckilldetail);
        viewpage_seckilldetail = (ViewPager) findViewById(R.id.vp_seckilldetail);
        //初始化fragment
        leftfragment = new LeftFragment();
        rightfragment = new RightFragment();
        centerfragment = new CenterFragment();
        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(leftfragment);
        list_fragment.add(rightfragment);
        list_fragment.add(centerfragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("11:00");
        list_title.add("12:00");
        list_title.add("13:00");
        //设置TabLayout的模式
        tabLayout_seckilldetail.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabLayout_seckilldetail.addTab(tabLayout_seckilldetail.newTab().setText(list_title.get(0)), false);
        tabLayout_seckilldetail.addTab(tabLayout_seckilldetail.newTab().setText(list_title.get(1)), false);
        tabLayout_seckilldetail.addTab(tabLayout_seckilldetail.newTab().setText(list_title.get(2)), false);
        fAdapter = new SecKillDetailAdapter(getSupportFragmentManager(), list_fragment, list_title);
        //viewpager加载adapter
        viewpage_seckilldetail.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabLayout_seckilldetail.setupWithViewPager(viewpage_seckilldetail);
        //tab_FindFragment_title.set
        viewpage_seckilldetail.setCurrentItem(selected);
    }
}
