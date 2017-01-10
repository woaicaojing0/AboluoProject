package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.GoodsListInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj34920 on 2016/12/20.
 */

public class PartnerAdpater  extends  RecyclerView.Adapter<PartnerAdpater.MyViewHolder>  {
    private static String ImgeURL = null;
    private List<GoodsListInfo.ResultBean.GoodsListBean> mgoodGoodsListBean;
    private PartnerAdpater.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private Context mcontext;

    public PartnerAdpater(List<GoodsListInfo.ResultBean.GoodsListBean> goodsListInfo, Context context) {
        this.mgoodGoodsListBean = goodsListInfo;
        this.mcontext = context;
        ImgeURL = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
    }

    @Override
    public PartnerAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PartnerAdpater.MyViewHolder myViewHolder = null;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_partner_item, parent, false);
            myViewHolder = new PartnerAdpater.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(PartnerAdpater.MyViewHolder holder, final int position) {
        holder.goods_name.setText(mgoodGoodsListBean.get(position).getGoodsName());
        holder.old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.old_money.setText("￥"+mgoodGoodsListBean.get(position).getGoodsPrice());
        holder.goods_quantity.setText("数量:" + mgoodGoodsListBean.get(position).getGoodsQuantity() + "件");
        String imageurl = mgoodGoodsListBean.get(position).getGoodsLogo();
        if (imageurl == null) {
        } else {
            String[] imageurls = imageurl.split(";");
            for (int i = 0; i < imageurls.length; i++) {
                imageurls[i] = ImgeURL + imageurls[i].toString();
            }
            Log.i("woaicaojing", imageurls[0].toString());
            Picasso.with(mcontext).load(imageurls[0]).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.goods_image);

        }
        holder.new_money.setText("￥"+mgoodGoodsListBean.get(position).getHyPrice() + "元");
        holder.goods_sub.setText(mgoodGoodsListBean.get(position).getGoodsSub() + "");
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
        return mgoodGoodsListBean.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView goods_name, new_money, old_money, goods_sub, goods_quantity;
        public CardView goods_list_cardview;
        public ImageView goods_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            new_money = (TextView) itemView.findViewById(R.id.parnter_new_money);
            old_money = (TextView) itemView.findViewById(R.id.parnter_old_money);
            goods_name = (TextView) itemView.findViewById(R.id.parnter_goods_name);
            goods_sub = (TextView) itemView.findViewById(R.id.parnter_goods_sub);
            goods_quantity = (TextView) itemView.findViewById(R.id.parnter_goods_quantity);
            goods_list_cardview = (CardView) itemView.findViewById(R.id.parnter_goods_list_cardview);
            goods_image = (ImageView) itemView.findViewById(R.id.parnter_goods_image);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(PartnerAdpater.OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }

}
