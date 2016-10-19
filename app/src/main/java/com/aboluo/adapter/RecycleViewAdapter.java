package com.aboluo.adapter;

import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.GoodsListInfo;

import java.util.List;

/**
 * Created by cj34920 on 2016/10/19.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private GoodsListInfo mgoodsListInfo;
    private List<GoodsListInfo.ResultBean.GoodsListBean> mgoodGoodsListBean;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public RecycleViewAdapter(GoodsListInfo goodsListInfo) {
        this.mgoodsListInfo = goodsListInfo;
        this.mgoodGoodsListBean = goodsListInfo.getResult().getGoodsList();
    }

    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_goodlist_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.goods_name.setText(mgoodGoodsListBean.get(position).getGoodsName());
        holder.old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.old_money.setText(mgoodGoodsListBean.get(position).getGoodsPrice() + "元");
        holder.goods_quantity.setText("数量:" + mgoodGoodsListBean.get(position).getGoodsQuantity() + "件");

        holder.new_money.setText(mgoodGoodsListBean.get(position).getHyPrice() + "元");
        holder.goods_sub.setText(mgoodGoodsListBean.get(position).getGoodsSub() + "商品描述");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onItemClick(v, position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mgoodsListInfo.getResult().getGoodsList().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView goods_name, new_money, old_money, goods_sub, goods_quantity;
        public CardView goods_list_cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            new_money = (TextView) itemView.findViewById(R.id.new_money);
            old_money = (TextView) itemView.findViewById(R.id.old_money);
            goods_name = (TextView) itemView.findViewById(R.id.goods_name);
            goods_sub = (TextView) itemView.findViewById(R.id.goods_sub);
            goods_quantity = (TextView) itemView.findViewById(R.id.goods_quantity);
            goods_list_cardview = (CardView) itemView.findViewById(R.id.goods_list_cardview);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

}
