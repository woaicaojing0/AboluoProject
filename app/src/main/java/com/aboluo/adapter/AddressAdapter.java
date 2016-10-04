package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;

import java.util.List;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddressAdapter extends BaseAdapter {
    private List<String> mlist;
    private Context mContext;

    public AddressAdapter(List<String> list, Context context) {
        this.mlist = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {

        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.address_list_item, null);
            viewHolder.address_listview_address = (TextView) convertView.findViewById(R.id.address_listview_address);
            viewHolder.address_listview_name = (TextView) convertView.findViewById(R.id.address_listview_name);
            viewHolder.address_listview_phone = (TextView) convertView.findViewById(R.id.address_listview_phone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.address_listview_address.setText(mlist.get(position) + "江苏省淮安市枚乘东路1号 老校区:淮阴工学院北京路校区北京北路89号");
        viewHolder.address_listview_name.setText(mlist.get(position) + "这是姓名");
        viewHolder.address_listview_phone.setText(mlist.get(position) + "122332222");
        return convertView;

    }

    public class ViewHolder {
        private TextView address_listview_address, address_listview_phone, address_listview_name;
    }
}
