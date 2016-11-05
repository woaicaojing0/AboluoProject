package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.ProvinceBean;

import java.util.List;

/**
 * Created by CJ on 2016/11/5.
 */

public class ProvinceAdapter extends BaseAdapter {
    private ProvinceBean mlist;
    private Context mcontext;
    private LayoutInflater mlaLayoutInflater;

    public ProvinceAdapter(ProvinceBean list, Context context) {
        this.mcontext = context;
        this.mlist = list;
        mlaLayoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return mlist.getResult().getCounryAreaList().size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.getResult().getCounryAreaList().get(position).getAreaName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mlaLayoutInflater.inflate(R.layout.activity_choose_address_item,null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_province);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mlist.getResult().getCounryAreaList().get(position).getAreaName().toString());
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
    }
}
