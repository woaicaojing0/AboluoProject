package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aboluo.com.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class BannerAdapter extends LoopPagerAdapter {
    private Context mcontext;
    private String[] imgsurl;
    public BannerAdapter(Context context,String []imgsurl,RollPagerView viewPager)
    {
        super(viewPager);
        this.mcontext = context;
        this.imgsurl = imgsurl;
    }
    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view =new ImageView(container.getContext());
        Picasso.with(mcontext).load(imgsurl[position]).into(view);  // 加载网络图片
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getRealCount() {
        return imgsurl.length;
    }
}
