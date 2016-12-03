package com.aboluo.com;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.OrderTabAdapter;
import com.aboluo.fragment.UnaryFragment.IntroduceFragment;
import com.aboluo.fragment.UnaryFragment.MoodsFragment;
import com.aboluo.fragment.UnaryFragment.NewFragment;
import com.aboluo.fragment.UnaryFragment.SurplusFragment;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.widget.CustomViewPager1;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJ on 2016/12/2.
 */

public class UnaryActivity extends FragmentActivity {
    private TabLayout tablayout_unary_title;
    private CustomViewPager1 vp_unart_pager;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private Fragment introduceFragment, moodsFragment, newFragment, surplusFragment;
    private OrderTabAdapter fAdapter;
    private int selected, status;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Context mcontext;
    private Picasso picasso;
    private Gson gson;
    private BaseConfigBean unaryConfigBean;
    private BannerAdapter bannerAdapter;
    private RollPagerView unary__view_pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unary_index);
        init();
        vp_unart_pager.removeOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vp_unart_pager.resetHeight(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_unart_pager.resetHeight(0);


    }

    private void init() {
        mcontext= this;
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
        URL = CommonUtils.GetValueByKey(mcontext, "apiurl");
        APPToken = CommonUtils.GetValueByKey(mcontext, "APPToken");
        gson = new Gson();
        picasso = Picasso.with(mcontext);
        tablayout_unary_title = (TabLayout) findViewById(R.id.tablayout_unary_title);
        vp_unart_pager = (CustomViewPager1) findViewById(R.id.vp_unart_pager);
        unary__view_pager = (RollPagerView) findViewById(R.id.unary__view_pager);
        initTabLayoutAndViewPage();
        initBannerImage();
    }

    private void initTabLayoutAndViewPage() {
        //初始化fragment
        introduceFragment = new IntroduceFragment(vp_unart_pager);
        moodsFragment = new MoodsFragment(vp_unart_pager);
        newFragment = new NewFragment(vp_unart_pager);
        surplusFragment = new SurplusFragment(vp_unart_pager);
        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(moodsFragment);
        list_fragment.add(surplusFragment);
        list_fragment.add(newFragment);
        list_fragment.add(introduceFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("人气");
        list_title.add("剩余");
        list_title.add("最新");
        list_title.add("玩法介绍");
        //设置TabLayout的模式
        tablayout_unary_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tablayout_unary_title.addTab(tablayout_unary_title.newTab().setText(list_title.get(0)), false);
        tablayout_unary_title.addTab(tablayout_unary_title.newTab().setText(list_title.get(1)), false);
        tablayout_unary_title.addTab(tablayout_unary_title.newTab().setText(list_title.get(2)), false);
        tablayout_unary_title.addTab(tablayout_unary_title.newTab().setText(list_title.get(3)), true);
        fAdapter = new OrderTabAdapter(getSupportFragmentManager(), list_fragment, list_title);
        //viewpager加载adapter
        vp_unart_pager.setAdapter(fAdapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tablayout_unary_title.setupWithViewPager(vp_unart_pager);
        //tab_FindFragment_title.set
        vp_unart_pager.setCurrentItem(selected);
    }

    private void initBannerImage()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (unaryConfigBean.isIsSuccess()) {
                    String[] arrString = new String[unaryConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < unaryConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + unaryConfigBean.getAppConfigList().get(i).getImage();
                    }
                    //头部滚动banner
                    bannerAdapter = new BannerAdapter(mcontext, arrString, unary__view_pager);
                    unary__view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                    unary__view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                    unary__view_pager.setFocusable(false);
                } else {
                    Toast.makeText(mcontext, unaryConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("ConfigModule","8");
                map.put("APPToken",APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void initNewOpen()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (unaryConfigBean.isIsSuccess()) {
                    String[] arrString = new String[unaryConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < unaryConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + unaryConfigBean.getAppConfigList().get(i).getImage();
                    }
                    //头部滚动banner
                    bannerAdapter = new BannerAdapter(mcontext, arrString, unary__view_pager);
                    unary__view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                    unary__view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                    unary__view_pager.setFocusable(false);
                } else {
                    Toast.makeText(mcontext, unaryConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("ConfigModule","8");
                map.put("APPToken",APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
