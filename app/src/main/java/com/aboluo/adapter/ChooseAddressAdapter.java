package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.AddressInfoBean;

import java.util.List;
import com.aboluo.model.AddressInfoBean.ResultBean.MemberAddressListBean;
/**
 * Created by CJ on 2016/11/21.
 */

public class ChooseAddressAdapter extends BaseAdapter {
    private List<MemberAddressListBean> mlist;
    private Context  mcontext;
    private LayoutInflater layoutInflater;
    public ChooseAddressAdapter(Context context,List<MemberAddressListBean> list)
    {
        this.mlist = list;
        this.mcontext = context;
        layoutInflater = LayoutInflater.from(mcontext);
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
        ViewHolder  holder =null;
        if(convertView ==null)
        {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.activity_make_sure_choose_address_item,null);
            holder.txt_choose_address = (TextView) convertView.findViewById(R.id.txt_choose_address);
            holder.txt_choose_phone = (TextView) convertView.findViewById(R.id.txt_choose_phone);
            holder.txt_choose_name = (TextView) convertView.findViewById(R.id.txt_choose_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (mlist.get(position).getProvince().toString().indexOf("省") == -1) {
        } else {
            stringBuffer.append(mlist.get(position).getProvince().toString());
        }
        stringBuffer.append(mlist.get(position).getCity().toString());
        stringBuffer.append(mlist.get(position).getRegion().toString());
        stringBuffer.append(mlist.get(position).getStreet().toString());
        stringBuffer.append(mlist.get(position).getAddress().toString());
        holder.txt_choose_address.setText(stringBuffer.toString());
        holder.txt_choose_name.setText(mlist.get(position).getReceiver().toString());
        holder.txt_choose_phone.setText(mlist.get(position).getMobile().toString());
        return convertView;
    }
   class ViewHolder
    {
        public TextView txt_choose_name,txt_choose_phone,txt_choose_address;
    }
}
