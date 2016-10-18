package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.GoodsBigType;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/9/25.
 */

public class MenuGridviewAdapter extends BaseAdapter {
    public void setMlistinfo(GoodsBigType.ResultBean mlistinfo) {
        this.mlistinfo = mlistinfo;
    }

    private GoodsBigType.ResultBean mlistinfo;
    private Context mcontext;
    public MenuGridviewAdapter(GoodsBigType.ResultBean listinfo, Context context)
    {
        this.mlistinfo = listinfo;
        this.mcontext = context;
    }
    @Override
    public int getCount() {
        return mlistinfo.getGoodsTypeList().size();
    }

    @Override
    public Object getItem(int position) {
        return mlistinfo.getGoodsTypeList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null)
        {
             holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.menu_gridview_item,null);
            holder.img_info = (ImageView) convertView.findViewById(R.id.men_gridview_item_img);
            holder.txt_info = (TextView) convertView.findViewById(R.id.men_gridview_item_txt);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_info.setText(mlistinfo.getGoodsTypeList().get(position).getGoodsTypeName());
        Picasso.with(mcontext).load("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1309/05/c5/25283777_1378352004384_800x600.jpg").into(holder.img_info);
        return convertView;
    }
    class  ViewHolder
    {
        public TextView txt_info;
        public ImageView img_info;
    }
}
