package com.aboluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.Utils;
import com.aboluo.com.R;
import com.aboluo.model.GroupBuyRecordBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj34920 on 2017/4/27.
 */

public class GroupBuyRecordAdapter extends RecyclerView.Adapter<GroupBuyRecordAdapter.GroupBuyRecordViewHolder> {

    private Context mcontext;
    private List<GroupBuyRecordBean.GroupBuyRecordItemBean> mlist;
    private Picasso mpicasso;

    public GroupBuyRecordAdapter(Context context, List<GroupBuyRecordBean.GroupBuyRecordItemBean> list) {
        this.mcontext = context;
        this.mlist = list;
        this.mpicasso = Picasso.with(context);
    }

    @Override
    public GroupBuyRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_groupbuyrecord_item, null, false);
        GroupBuyRecordViewHolder groupBuyRecordViewHolder = new GroupBuyRecordViewHolder(view);
        return groupBuyRecordViewHolder;
    }

    @Override
    public void onBindViewHolder(GroupBuyRecordViewHolder holder, int position) {
        holder.tv_nickName.setText(mlist.get(position).getMemberNick());
        holder.tv_joinTime.setText(mlist.get(position).getPayTime().replace("T", " "));
        switch (mlist.get(position).getIsDealer()) {
            case 0:
                holder.tv_level.setText("普通会员");
                break;
            case 1:
                holder.tv_level.setText("合伙人");
                break;
            case 2:
                holder.tv_level.setText("金牌合伙人");
                break;
            default:
                holder.tv_level.setText("普通会员");
                break;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(mlist.get(position).getProvince()
                + mlist.get(position).getCity() +
                mlist.get(position).getMemberNick());
        if (mlist.get(position).getMemberSex() == 0) {
            stringBuffer.append("女士");
        } else {
            stringBuffer.append("男士");
        }
        holder.tv_item_useraddress.setText(stringBuffer.toString());
        String logUrl = mlist.get(position).getMemberLogo();
        if (logUrl == null || logUrl.length() <= 0) {
            mpicasso.load(R.drawable.imagviewloading).into(holder.iv_profile_image);
        } else {
            mpicasso.load(mlist.get(position).getMemberLogo()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .resize(Utils.dp2px(mcontext, 250), Utils.dp2px(mcontext, 250))
                    .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.iv_profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class GroupBuyRecordViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nickName, tv_item_useraddress, tv_level, tv_joinTime;
        private ImageView iv_profile_image;

        public GroupBuyRecordViewHolder(View itemView) {
            super(itemView);
            tv_nickName = (TextView) itemView.findViewById(R.id.tv_nickName);
            tv_item_useraddress = (TextView) itemView.findViewById(R.id.tv_item_useraddress);
            tv_level = (TextView) itemView.findViewById(R.id.tv_level);
            tv_joinTime = (TextView) itemView.findViewById(R.id.tv_joinTime);
            iv_profile_image = (ImageView) itemView.findViewById(R.id.iv_profile_image);
        }
    }
}
