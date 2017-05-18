package com.aboluo.adapter;

import android.content.Context;
import android.media.Image;
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
 * Created by cj34920 on 2017/5/18.
 */

public class MyGroupBuyRecordAdapter extends RecyclerView.Adapter<MyGroupBuyRecordAdapter.MyGroupBuyRecordViewHolder> {

    private Context mcontext;
    private List<GroupBuyRecordBean.GroupBuyRecordItemBean> mlist;
    private Picasso mpicasso;

    public MyGroupBuyRecordAdapter(Context context, List<GroupBuyRecordBean.GroupBuyRecordItemBean> list) {
        this.mcontext = context;
        this.mlist = list;
        this.mpicasso = Picasso.with(context);
    }

    @Override
    public MyGroupBuyRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_my_group_record_item, null, false);
        MyGroupBuyRecordViewHolder myGroupBuyRecordAdapter = new MyGroupBuyRecordViewHolder(view);
        return myGroupBuyRecordAdapter;
    }

    @Override
    public void onBindViewHolder(MyGroupBuyRecordViewHolder holder, int position) {
        holder.tv_my_group_nickName.setText(mlist.get(position).getMemberNick());
        holder.tv_my_group_id.setText(mlist.get(position).getTeamBuyId() + "");
        holder.tv_my_group_joinTime.setText(mlist.get(position).getPayTime().replace("T", ""));
        String logUrl = mlist.get(position).getMemberLogo();
        if (logUrl == null || logUrl.length() <= 0) {
            mpicasso.load(R.drawable.imagviewloading).into(holder.iv_my_group_profile_image);
        } else {
            mpicasso.load(mlist.get(position).getMemberLogo()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .resize(Utils.dp2px(mcontext, 250), Utils.dp2px(mcontext, 250))
                    .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.iv_my_group_profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyGroupBuyRecordViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_my_group_nickName, tv_my_group_id, tv_my_group_joinTime;
        private ImageView iv_my_group_profile_image;

        public MyGroupBuyRecordViewHolder(View itemView) {
            super(itemView);
            tv_my_group_nickName = (TextView) itemView.findViewById(R.id.tv_my_group_nickName);
            tv_my_group_id = (TextView) itemView.findViewById(R.id.tv_my_group_id);
            tv_my_group_joinTime = (TextView) itemView.findViewById(R.id.tv_my_group_joinTime);
            iv_my_group_profile_image = (ImageView) itemView.findViewById(R.id.iv_my_group_profile_image);
        }
    }
}
