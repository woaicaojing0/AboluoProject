package com.aboluo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.GridViewAdapter;
import com.aboluo.com.MainActivity;
import com.aboluo.com.R;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
        } else {
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
            rollPagerView.setAdapter(new BannerAdapter(this.getActivity(), imgurl, rollPagerView)); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
            rollPagerView.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
            rollPagerView.setFocusable(false);
            rollPagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(IndexFragment.this.getActivity(), "1", Toast.LENGTH_SHORT).show();
                }
            });
            mid_gridview.setAdapter(new GridViewAdapter(this.getActivity()));
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
        }
        return view;
    }


    private void init(View view) {
        linearLayout = (LinearLayout) view.findViewById(R.id.ALLFather);
        top_editsearch = (EditText) view.findViewById(R.id.top_editsearch);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        mid_gridview = (GridView) view.findViewById(R.id.mid_gridview);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
    }
}
