package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;

import org.w3c.dom.Text;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context mContext;

    public GridViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private Integer[] images = {
            //九宫格图片的设置
            R.drawable.icon_01,
            R.drawable.icon_02,
            R.drawable.icon_03,
            R.drawable.icon_04,
            R.drawable.icon_05,
            R.drawable.icon_06,
            R.drawable.icon_07,
            R.drawable.icon_08,
    };

    private String[] texts = {
            //九宫格图片下方文字的设置
            "合伙人商品",
            "夺宝",
            "拼团购",
            "分类",
            "秒杀",
            "签到送积分",
            "尽请期待...",
            "尽请期待...",
    };

    @Override
    public int getCount() {
        return texts.length;
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
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imageView.setImageResource(images[i]);
        holder.textView.setText(texts[i]);
        return view;
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
}
