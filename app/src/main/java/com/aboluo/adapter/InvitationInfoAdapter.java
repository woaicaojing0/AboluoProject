package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;

/**
 * Created by cj34920 on 2017/2/22.
 */

public class InvitationInfoAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public void InvitationInfoAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.activity_invitationinfo_item, parent);
            holder.iv_headImage = (ImageView) convertView.findViewById(R.id.iv_invitationinfo_item_headimg);
            holder.tv_nickName = (TextView) convertView.findViewById(R.id.invitationinfo_item_nickName);
            holder.tv_sex = (TextView) convertView.findViewById(R.id.invitationinfo_item_sex);
            holder.tv_registerTime = (TextView) convertView.findViewById(R.id.invitationinfo_item_registertime);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_headImage;
        TextView tv_nickName, tv_sex, tv_registerTime;
    }
}
