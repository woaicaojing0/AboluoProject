package com.aboluo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aboluo.com.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class testAdapter extends LoopPagerAdapter {
    private Context mcontext;
    private String[] imgsurl;
    private Picasso picasso;
    public testAdapter(Context context, RollPagerView viewPager)
    {
        super(viewPager);
        this.mcontext = context;
        this.imgsurl = imgsurl;
        picasso =  Picasso.with(mcontext); // 加载网络图片
    }
    @Override
    public View getView(ViewGroup container, int position) {
         ImageView view =new ImageView(container.getContext());
//        Log.i("woaiocajingimgs",imgsurl[position]);
        picasso.load(R.drawable.goods_detailsmail)
                .placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                .into(view);
        if(position==1)
        {
            picasso.load(R.drawable.goodsdetail)
                    .placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .into(view);
        }
        if(position == 2)
        {picasso.load(R.drawable.goodsdetailsmail2)
                .placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                .into(view);

        }
       view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getRealCount() {
        return 4;
    }
}
