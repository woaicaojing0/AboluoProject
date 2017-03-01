package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.ExtractHisotoryBean;

import java.util.List;

/**
 * Created by CJ on 2017/3/1.
 */

public class ExtractMoneyAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mlayoutInflater;
    private List<ExtractHisotoryBean.ResultBean.WithdrawalsListBean> mlist;

    public ExtractMoneyAdapter(Context context, List<ExtractHisotoryBean.ResultBean.WithdrawalsListBean> list) {
        this.mContext = context;
        this.mlayoutInflater = LayoutInflater.from(mContext);
        this.mlist = list;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mlayoutInflater.inflate(R.layout.activity_extractmoney__detail_item, null);
            holder.tv_extractdetail_item_extra = (TextView) convertView.findViewById(R.id.tv_extractdetail_item_extra);
            holder.tv_extractdetail_item_status = (TextView) convertView.findViewById(R.id.tv_extractdetail_item_status);
            holder.tv_extractdetail_item_money = (TextView) convertView.findViewById(R.id.tv_extractdetail_item_money);
            holder.tv_extractdetail_item_time = (TextView) convertView.findViewById(R.id.tv_extractdetail_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_extractdetail_item_extra.setText("提现");
        if (mlist.get(position).getAuditStatus() == 0) {
            holder.tv_extractdetail_item_status.setText("审核中");
        } else {
            holder.tv_extractdetail_item_status.setText("审核通过");
        }
        holder.tv_extractdetail_item_money.setText("￥" + mlist.get(position).getPrice() + "");
        holder.tv_extractdetail_item_time.setText(mlist.get(position).getCreateTime());
        return convertView;
    }

    class ViewHolder {
        public TextView tv_extractdetail_item_status, tv_extractdetail_item_money,
                tv_extractdetail_item_time, tv_extractdetail_item_extra;
    }
}
