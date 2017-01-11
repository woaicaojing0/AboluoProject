package com.aboluo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.com.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private String [] images;
    private Picasso picasso;
    private String ImageUrl;
    public GridViewAdapter(Context mContext,String [] mimages) {
        this.mContext = mContext;
        this.images =mimages;
        this.picasso = Picasso.with(mContext);
        ImageUrl = CommonUtils.GetValueByKey(mContext, "ImgUrl");
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
            view = LayoutInflater.from(mContext).inflate(R.layout.gridview_item,null);
            holder.imageView = (ImageView) view.findViewById(R.id.img_mid_gridview_item);
            holder.textView = (TextView) view.findViewById(R.id.txt_mid_gridview_item);
            int screenWidth = ScreenUtils.getScreenWidth(mContext);
            holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 4));
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Log.i("Mid_GridViewAdapter",images[i]);
        picasso.load(images[i]).placeholder(R.drawable.imagviewloading)
                .error(R.drawable.imageview_error).into( holder.imageView);
        return view;
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
}
