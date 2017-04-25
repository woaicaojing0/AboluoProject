package com.aboluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aboluo.XUtils.Utils;
import com.aboluo.com.R;
import com.aboluo.model.GroupBuyBean.ListResultBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj on 2017/4/25.
 */

public class GroupBuyAdapter extends RecyclerView.Adapter<GroupBuyAdapter.MyViewHolder> {
    private List<ListResultBean> mlist;
    private Context mcontext;
    private Picasso picasso;

    public GroupBuyAdapter(List<ListResultBean> list, Context context) {
        this.mcontext = context;
        this.mlist = list;
        picasso = Picasso.with(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_groupbuy_item, parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.groupbuy_item_name.setText(mlist.get(position).getGoodsName());
        holder.groupbuy_item_sub.setText(mlist.get(position).getRemarks() == null ? "暂无" : mlist.get(position).getRemarks().toString());
        holder.groupbuy_item_quantity.setText("团购价：￥" + String.valueOf(mlist.get(position).getTeamPrice()));
        Picasso.with(mcontext).load(mlist.get(position).getGoodsLogo()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                .resize(Utils.dp2px(mcontext, 250), Utils.dp2px(mcontext, 250))
                .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.groupbuy_item_image);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView groupbuy_item_name, groupbuy_item_sub, groupbuy_item_quantity;
        private RelativeLayout relative_farther;
        private LinearLayout linelayout_child;
        private ImageView groupbuy_item_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            groupbuy_item_image = (ImageView) itemView.findViewById(R.id.groupbuy_item_image);
            groupbuy_item_name = (TextView) itemView.findViewById(R.id.groupbuy_item_name);
            groupbuy_item_sub = (TextView) itemView.findViewById(R.id.groupbuy_item_sub);
            groupbuy_item_quantity = (TextView) itemView.findViewById(R.id.groupbuy_item_quantity);
            relative_farther = (RelativeLayout) itemView.findViewById(R.id.relative_farther);
            linelayout_child = (LinearLayout) itemView.findViewById(R.id.linelayout_child);
        }
    }
}
