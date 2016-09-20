package com.aboluo.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CJ on 2016/9/20.
 */

public class ViewPagerAdapter extends PagerAdapter {
    //界面列表
    private List<View> views;

    public ViewPagerAdapter(List<View> views){
        this.views = views;
    }

    //销毁arg1位置的界面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    //获得当前界面数
    @Override
    public int getCount() {
        if (views != null)
        {
            return views.size();
        }

        return 0;
    }


    //初始化arg1位置的界面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);

        return views.get(position);
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}
