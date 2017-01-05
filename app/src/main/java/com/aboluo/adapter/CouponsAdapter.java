package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.CouponsBean;
import com.aboluo.model.CouponsBean.MemberCouponListBean;

import java.util.List;

/**
 * Created by cj34920 on 2017/1/5.
 * 优惠券的适配器
 */

public class CouponsAdapter extends BaseAdapter {
    private Context mcontext;
    private List<MemberCouponListBean> mlist;
    private LayoutInflater layoutInflater;

    public CouponsAdapter(Context context, List<MemberCouponListBean> listbean) {
        this.mcontext = context;
        this.mlist = listbean;
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
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.activity_coupons_item, null);
            holder.tv_coupons_CouponMoney = (TextView) convertView.findViewById(R.id.tv_coupons_CouponMoney);
            holder.tv_coupons_remark = (TextView) convertView.findViewById(R.id.tv_coupons_remark);
            holder.tv_coupons_starttime = (TextView) convertView.findViewById(R.id.tv_coupons_starttime);
            holder.tv_coupons_endtime = (TextView) convertView.findViewById(R.id.tv_coupons_endtime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_coupons_CouponMoney.setText("￥" + mlist.get(position).getCouponMoney());
        holder.tv_coupons_remark.setText(mlist.get(position).getRemark());
        holder.tv_coupons_starttime.setText(mlist.get(position).getStartTime());
        holder.tv_coupons_endtime.setText(mlist.get(position).getEndTime());
        return convertView;
    }

    class ViewHolder {
        public TextView tv_coupons_CouponMoney, tv_coupons_remark,
                tv_coupons_starttime, tv_coupons_endtime;
    }
}
