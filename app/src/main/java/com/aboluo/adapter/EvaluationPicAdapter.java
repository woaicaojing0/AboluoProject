package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.com.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class EvaluationPicAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> images;
    private Picasso picasso;

    public EvaluationPicAdapter(Context mContext, ArrayList<String> images) {
        this.mContext = mContext;
        this.images = images;
        this.picasso = Picasso.with(mContext);
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.gridview_evaluation_item, null);
            holder.imageView = (ImageView) view.findViewById(R.id.gridview_evaluation_iamge);
            int screenWidth = ScreenUtils.getScreenWidth(mContext);
            int width = (ScreenUtils.getScreenWidth(mContext) - CommonUtils.dip2px(mContext, 12)) / 4;
            holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        picasso.load(new File(images.get(i).toString()))
                .placeholder(mContext.getResources().getDrawable(R.drawable.imagviewloading))
                .into(holder.imageView);
        return view;
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}
