package com.aboluo.adapter.index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.com.R;
import com.squareup.picasso.Picasso;

/**
 * Created by CJ on 2017/2/16.
 */

public class HotSaleGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private String[] images;
    private Picasso picasso;

    public HotSaleGridViewAdapter(Context mContext, String[] images) {
        this.mContext = mContext;
        this.images = images;
        this.picasso = Picasso.with(mContext);
    }


    @Override
    public int getCount() {
        return images.length;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.gridview_brand_item, null);
            holder.imageView = (ImageView) view.findViewById(R.id.img_brand_gridview_item);
            int screenWidth = ScreenUtils.getScreenWidth(mContext);
            holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 4));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        picasso.load(images[i])
                .placeholder(mContext.getResources().getDrawable(R.drawable.imagviewloading))
                .into(holder.imageView);
        return view;
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}
