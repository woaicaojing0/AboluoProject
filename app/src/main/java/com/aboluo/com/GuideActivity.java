package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aboluo.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2016/9/21.
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private RelativeLayout btn_enter_main;
    private SharedPreferences preferences;
    //引导图片资源
    private static final int[] pics = { R.drawable.guide_01,
            R.drawable.guide_02, R.drawable.guide_03};
    //记录当前选中位置
    private int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
preferences = getSharedPreferences("abolu",MODE_PRIVATE);
        boolean isFristIn = preferences.getBoolean("isFristIn",false);
        //如果是第一次进入，进入引导界面；如果不是第一次进入，直接跳到主页面
        if(isFristIn)
        {
            Intent intent = new Intent(this,LaunchActivity.class);
            startActivity(intent);
            finish();
        }else {
            Init();
            btn_enter_main.setOnClickListener(this);
            //初始化Adapter
            vpAdapter = new ViewPagerAdapter(views);
            vp.setAdapter(vpAdapter);
            vp.addOnPageChangeListener(this);
        }

    }

    private void Init()
    {
        views = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        btn_enter_main = (RelativeLayout) findViewById(R.id.btn_enter_main);
        vp = (ViewPager) findViewById(R.id.viewpager);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Toast.makeText(this, "跳转页面了" +position, Toast.LENGTH_SHORT).show();
        btn_enter_main.setVisibility(position == views.size() - 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFristIn",true);
        editor.commit();
       // Toast.makeText(this, "跳转页面了", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}