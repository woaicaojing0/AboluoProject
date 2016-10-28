package com.aboluo.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.testAdapter;
import com.aboluo.com.GoodsDetailActivity;
import com.aboluo.com.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

/**
 * Created by cj34920 on 2016/10/28.
 */

public class testActivity extends Activity{
    private RollPagerView rollPagerView;
    private testAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_acticvity);
        rollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager);
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
//        int screenWidth = ScreenUtils.getScreenWidth(this);
//        rollPagerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth));
        rollPagerView.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
        adapter = new testAdapter(this, rollPagerView);
        rollPagerView.setAdapter(adapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
        rollPagerView.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
        rollPagerView.setFocusable(false);
    }
}
