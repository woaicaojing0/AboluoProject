package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.OrderDetailInfo.ResultBean.OrderExpressListBean;

import java.util.List;

/**
 * Created by CJ on 2017/2/13.
 * 待评价代付款已付款中的商品详情中多个物流的adapter
 */

public class ExpressListAdapter extends BaseAdapter {
    private List<OrderExpressListBean> mlist;
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public  ExpressListAdapter(List<OrderExpressListBean> list, Context mcontext) {
        this.mlist = list;
        this.mcontext = mcontext;
        this.layoutInflater = LayoutInflater.from(mcontext);
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.expresslist_item, null);
            holder = new ViewHolder();
            holder.orderdetail_express_status = (TextView) convertView.findViewById(R.id.orderdetail_express_status);
            holder.orderdetail_express_time = (TextView) convertView.findViewById(R.id.orderdetail_express_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mlist.get(position).getLocalInfo() == null)
        {}else {
            holder.orderdetail_express_status.setText(mlist.get(position).getLocalInfo().get(0).getStatus().toString());
            holder.orderdetail_express_time.setText(mlist.get(position).getLocalInfo().get(0).getTime().toString());
        }
        return convertView;
    }

    class ViewHolder {
        private TextView orderdetail_express_status, orderdetail_express_time;
    }
}
