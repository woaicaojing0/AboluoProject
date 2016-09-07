package com.aboluo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aboluo.com.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class BannerAdapter extends StaticPagerAdapter {
    private int[] imgs ={
            R.drawable.banner,
            R.mipmap.ic_launcher,
    };
    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view =new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        return view;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }
}
