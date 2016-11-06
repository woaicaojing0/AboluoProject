package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
    private View.OnClickListener editeOnclickListener;
    private View.OnClickListener deleteOnclickListener;
    private View.OnClickListener ck_defaultOnClickListener;

    public AddressAdapter(List<MemberAddressListBean> list, Context context) {
        this.mlist = list;
        this.mContext = context;
    }

    public void setEditeOnclickListener(View.OnClickListener editeOnclickListener) {
        this.editeOnclickListener = editeOnclickListener;
    }

    public void setDeleteOnclickListener(View.OnClickListener deleteOnclickListener) {
        this.deleteOnclickListener = deleteOnclickListener;
    }

    public void setCk_defaultOnClickListener(View.OnClickListener ck_defaultOnClickListener) {
        this.ck_defaultOnClickListener = ck_defaultOnClickListener;
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
            viewHolder.address_edit = (LinearLayout) convertView.findViewById(R.id.address_edit);
            viewHolder.address_delete = (LinearLayout) convertView.findViewById(R.id.address_delete);
            viewHolder.ck_defalut = (CheckBox) convertView.findViewById(R.id.checkBox);
            viewHolder.address_edit.setTag(position);
            viewHolder.address_delete.setTag(position);
            viewHolder.ck_defalut.setTag(position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String address = mlist.get(position).getProvince()+mlist.get(position).getCity()+mlist.get(position).getRegion()
                +mlist.get(position).getStreet()+mlist.get(position).getAddress();
        viewHolder.address_listview_address.setText(address);
        viewHolder.address_listview_name.setText(mlist.get(position).getReceiver().toString());
        viewHolder.address_listview_phone.setText(mlist.get(position).getMobile().toString());
        viewHolder.address_edit.setOnClickListener(editeOnclickListener);
        viewHolder.address_delete.setOnClickListener(deleteOnclickListener);
        viewHolder.ck_defalut.setOnClickListener(ck_defaultOnClickListener);
        if(mlist.get(position).getIsDefault() ==0)
        { viewHolder.ck_defalut.setChecked(false);}else {
            viewHolder.ck_defalut.setChecked(true);
        }
        return convertView;

    }

    public class ViewHolder {
        private TextView address_listview_address, address_listview_phone, address_listview_name;
        private LinearLayout address_edit,address_delete;
        private CheckBox ck_defalut;
    }
}
