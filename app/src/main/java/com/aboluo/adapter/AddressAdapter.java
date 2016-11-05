package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.AddressInfoBean.ResultBean.MemberAddressListBean;
import java.util.List;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddressAdapter extends BaseAdapter {
    private List<MemberAddressListBean> mlist;
    private Context mContext;

    public AddressAdapter(List<MemberAddressListBean> list, Context context) {
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

        String address = mlist.get(position).getProvince()+mlist.get(position).getCity()+mlist.get(position).getRegion()
                +mlist.get(position).getStreet()+mlist.get(position).getAddress();
        viewHolder.address_listview_address.setText(address);
        viewHolder.address_listview_name.setText(mlist.get(position).getReceiver().toString());
        viewHolder.address_listview_phone.setText(mlist.get(position).getMobile().toString());
        return convertView;

    }

    public class ViewHolder {
        private TextView address_listview_address, address_listview_phone, address_listview_name;
    }
}
