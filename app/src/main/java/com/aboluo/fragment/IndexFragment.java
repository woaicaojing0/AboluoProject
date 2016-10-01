package com.aboluo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.GridViewAdapter;
import com.aboluo.com.GoodsDetailActivity;
import com.aboluo.com.MainActivity;
import com.aboluo.com.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class IndexFragment extends Fragment {
    private LinearLayout linearLayout;
    private EditText top_editsearch;
    private RollPagerView rollPagerView;
    private GridView mid_gridview;
    private MarqueeView marqueeView;
    private View view;
    private String[] imgurl = {"http://img4.imgtn.bdimg.com/it/u=2408370625,380818695&fm=21&gp=0.jpg", "" +
            "http://pic24.nipic.com/20121025/10444819_041559015351_2.jpg"};
    private PullToRefreshScrollView pullToRefreshScrollView;
  private BannerAdapter bannerAdapter;
    private ImageView ceshi_imgeview;
    private CountdownView mCvCountdownView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
        } else {}
            view = inflater.inflate(R.layout.fragment_index, null);
            init(view);
            linearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    linearLayout.setFocusable(true);
                    linearLayout.setFocusableInTouchMode(true);
                    linearLayout.requestFocus();
                    return false;
                }
            });
            //edittext 中的图标
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            drawable.setBounds(0, 0, 80, 80);
            top_editsearch.setCompoundDrawables(drawable, null, drawable, null);
            //头部滚动banner
            rollPagerView.setHintView(new ColorPointHintView(this.getActivity(), Color.RED, Color.WHITE));
            bannerAdapter = new BannerAdapter(this.getActivity(), imgurl, rollPagerView);
            rollPagerView.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
            rollPagerView.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
            rollPagerView.setFocusable(false);
            rollPagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(IndexFragment.this.getActivity(), "1", Toast.LENGTH_SHORT).show();
                }
            });
            mid_gridview.setAdapter(new GridViewAdapter(this.getActivity()));
            mid_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(IndexFragment.this.getActivity(), position+"", Toast.LENGTH_SHORT).show();
                }
            });
            final List<String> info = new ArrayList<>();
            info.add("1. 大家好，我是曹晶 \n 2.欢迎大家关注我哦！");
            info.add("2. 欢迎大家关注我哦！");
            info.add("3. GitHub帐号：woaicaojing0");
            info.add("4. 新浪微博：曹晶微博");
            info.add("5. 个人博客：caojing.com");
            info.add("6. 微信公众号：woaiocajing0");
            marqueeView.startWithList(info);
            marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                @Override
                public void onItemClick(int position, TextView textView) {
                    Toast.makeText(IndexFragment.this.getActivity(), info.get(position).toString() + textView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                    bannerAdapter.notifyDataSetChanged();
                    MyApplication.getRequestQueue().add(getInfo());
                }
            });
            Picasso.with(this.getActivity()).load("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1309/05/c5/25283777_1378352004384_800x600.jpg").into(ceshi_imgeview);
            Picasso.with(this.getActivity()).setIndicatorsEnabled(true);
            mCvCountdownView.start(24000); // 毫秒
            mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    cv.setVisibility(View.GONE);
                }
            });
            mCvCountdownView.setOnCountdownIntervalListener(3000, new CountdownView.OnCountdownIntervalListener() {
                @Override
                public void onInterval(CountdownView cv, long remainTime) {
                    Toast.makeText(IndexFragment.this.getActivity(), "1", Toast.LENGTH_SHORT).show();
                }
            });
        ceshi_imgeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("info","这是从首页中的图片跳转的");
                startActivity(intent);

            }
        });
        return view;
    }
    private void init(View view) {
        linearLayout = (LinearLayout) view.findViewById(R.id.ALLFather);
        top_editsearch = (EditText) view.findViewById(R.id.top_editsearch);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        mid_gridview = (GridView) view.findViewById(R.id.mid_gridview);
        mid_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pullToRefresh);
        ceshi_imgeview = (ImageView) view.findViewById(R.id.ceshi_imgeview);
        mCvCountdownView = (CountdownView)view.findViewById(R.id.cv_countdownViewTest1);

    }
    private StringRequest getInfo()
    {
            StringRequest stringRequest  = new StringRequest(Request.Method.POST, "http://msoa.weidustudio.com/api/MachineManage/ReceiveProductListByCategoryId", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(IndexFragment.this.getActivity(), response, Toast.LENGTH_SHORT).show();
                    pullToRefreshScrollView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(IndexFragment.this.getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    pullToRefreshScrollView.onRefreshComplete();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("ProductCategoryId", "1");
                    map.put("PageIndex", "1");
                    return map;
                }
            };
        return  stringRequest;
    }
}
