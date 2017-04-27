package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aboluo.Interface.OnRecyclerViewItemClickListener;
import com.aboluo.XUtils.Utils;
import com.aboluo.com.R;
import com.aboluo.model.GroupBuyBean.ListResultBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj on 2017/4/25.
 */

public class GroupBuyAdapter extends RecyclerView.Adapter<GroupBuyAdapter.MyViewHolder> implements View.OnClickListener{
    private List<ListResultBean> mlist;
    private Context mcontext;
    private Picasso picasso;
    public GroupBuyAdapter(List<ListResultBean> list, Context context) {
        this.mcontext = context;
        this.mlist = list;
        picasso = Picasso.with(context);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_groupbuy_item, null, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ViewTreeObserver vto2 = holder.relative_farther.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                holder.relative_farther.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //  textView.append("\n\n"+imageView.getHeight()+","+imageView.getWidth());
                ViewGroup.LayoutParams para = holder.linelayout_child.getLayoutParams();
                int with = holder.relative_farther.getWidth();
                int all = mlist.get(position).getNeedPerson();
                int i = mlist.get(position).getBuyedPerson();
                if (i == 0) {
                    para.width = 0;
                    holder.tv_percentNum.setTextColor(Color.BLACK);
                    holder.tv_percentNum.setText("0%");
                } else {
                    para.width = ((holder.relative_farther.getWidth()) * i) / all;
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    double num = (100 * i) / all;
                    String percentNum = df.format(num);
                    if (num < 50) {
                        holder.tv_percentNum.setTextColor(Color.BLACK);
                    } else {
                        holder.tv_percentNum.setTextColor(Color.WHITE);
                        holder.tv_percentNum.setText(percentNum + "%");
                    }
                }
                holder.linelayout_child.setLayoutParams(para);
            }
        });
        holder.itemView.setTag(position);
        holder.groupbuy_item_name.setText(mlist.get(position).getGoodsName());
        holder.groupbuy_item_sub.setText(mlist.get(position).getRemarks() == null ? "暂无" : mlist.get(position).getRemarks().toString());
        holder.groupbuy_item_price.setText("团购价：￥" + String.valueOf(mlist.get(position).getTeamPrice()));
        if (mlist.get(position).getGoodsLogo() == null || mlist.get(position).getGoodsLogo() == "") {
            Picasso.with(mcontext).load(R.drawable.imageview_error).into(holder.groupbuy_item_image);
            return;
        }
        Picasso.with(mcontext).load(mlist.get(position).getGoodsLogo()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                .resize(Utils.dp2px(mcontext, 250), Utils.dp2px(mcontext, 250))
                .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.groupbuy_item_image);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView groupbuy_item_name, groupbuy_item_sub, groupbuy_item_price, tv_percentNum;
        private RelativeLayout relative_farther;
        private LinearLayout linelayout_child;
        private ImageView groupbuy_item_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            groupbuy_item_image = (ImageView) itemView.findViewById(R.id.groupbuy_item_image);
            groupbuy_item_name = (TextView) itemView.findViewById(R.id.groupbuy_item_name);
            groupbuy_item_sub = (TextView) itemView.findViewById(R.id.groupbuy_item_sub);
            tv_percentNum = (TextView) itemView.findViewById(R.id.tv_percentNum);
            groupbuy_item_price = (TextView) itemView.findViewById(R.id.groupbuy_item_price);
            relative_farther = (RelativeLayout) itemView.findViewById(R.id.relative_farther);
            linelayout_child = (LinearLayout) itemView.findViewById(R.id.linelayout_child);
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
