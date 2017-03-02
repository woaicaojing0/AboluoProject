package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.InvitationInfoBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj34920 on 2017/2/22.
 */

public class InvitationInfoAdapter extends BaseAdapter {
    private List<InvitationInfoBean.ResultBean> mlist;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Picasso picasso;

    public InvitationInfoAdapter(Context context, List<InvitationInfoBean.ResultBean> list) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mlist = list;
        this.picasso = Picasso.with(mContext);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position).getAddTime();
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
            convertView = mLayoutInflater.inflate(R.layout.activity_invitationinfo_item, null);
            holder.iv_headImage = (ImageView) convertView.findViewById(R.id.iv_invitationinfo_item_headimg);
            holder.tv_nickName = (TextView) convertView.findViewById(R.id.invitationinfo_item_nickName);
            holder.tv_sex = (TextView) convertView.findViewById(R.id.invitationinfo_item_sex);
            holder.tv_registerTime = (TextView) convertView.findViewById(R.id.invitationinfo_item_registertime);
            holder.invitationinfo_item_hehuoren = (TextView) convertView.findViewById(R.id.invitationinfo_item_hehuoren);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        picasso.load(mlist.get(position).getWechatLogoUrl()).placeholder(R.drawable.image_placeholder)
                .error(R.drawable.imageview_error).into(holder.iv_headImage);
        holder.tv_nickName.setText(mlist.get(position).getWechatNickName() + " 用户编号10" +
                mlist.get(position).getMemberId());
        if (0 == mlist.get(position).getMemberSex()) {
            holder.tv_sex.setText("女");
        } else {
            holder.tv_sex.setText("男");
        }
        if (0 == mlist.get(position).getIsDealer()) {
            holder.invitationinfo_item_hehuoren.setText("普通会员");
        } else {
            holder.invitationinfo_item_hehuoren.setText("合伙人");
        }
        holder.tv_registerTime.setText(mlist.get(position).getAddTime().replace("T", " "));
        return convertView;
    }

    class ViewHolder {
        ImageView iv_headImage;
        TextView tv_nickName, tv_sex, tv_registerTime, invitationinfo_item_hehuoren;
    }
}
