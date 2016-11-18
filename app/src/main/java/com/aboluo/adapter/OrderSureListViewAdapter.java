package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;

import java.util.List;

/**
 * Created by CJ on 2016/11/18.
 */

public class OrderSureListViewAdapter extends BaseAdapter {
    private List<String> ListDemo;
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public OrderSureListViewAdapter(List<String> listDemo, Context context) {
        this.mcontext = context;
        this.ListDemo = listDemo;
        layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return ListDemo.size();
    }

    @Override
    public Object getItem(int position) {
        return ListDemo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.orderlist_item,null);
            holder.order_image = (ImageView) convertView.findViewById(R.id.order_image);
            holder.order_txt_desc = (TextView) convertView.findViewById(R.id.order_txt_desc);
            holder.order_txt_colorStandards = (TextView) convertView.findViewById(R.id.order_txt_colorStandards);
            holder.order_txt_num = (TextView) convertView.findViewById(R.id.order_txt_num);
            holder.order_txt_money = (TextView) convertView.findViewById(R.id.order_txt_money);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        TextView order_txt_desc, order_txt_colorStandards, order_txt_num, order_txt_money;
        ImageView order_image;
    }
}
