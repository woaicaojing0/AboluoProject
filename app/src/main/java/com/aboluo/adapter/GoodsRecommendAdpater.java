package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.GoodsListInfo;
import com.aboluo.model.GoodsRecommend;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2017/3/4.
 */

public class GoodsRecommendAdpater extends BaseAdapter {
    private List<GoodsRecommend.ListResultBean> mgoodGoodsListBean;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Picasso picasso;
    private static String ImgeURL = null;

    public GoodsRecommendAdpater(Context context, List<GoodsRecommend.ListResultBean> goodsListInfo) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mgoodGoodsListBean = goodsListInfo;
        this.picasso = Picasso.with(mContext);
        ImgeURL = CommonUtils.GetValueByKey(context, "ImgUrl");
    }

    @Override
    public int getCount() {
        return mgoodGoodsListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return mgoodGoodsListBean.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.index_goodlist_item2, null);
            holder.indexbottom_new_money = (TextView) convertView.findViewById(R.id.indexbottom_new_money);
            holder.indexbottom_old_money = (TextView) convertView.findViewById(R.id.indexbottom_old_money);
            holder.indexbottom_goods_name = (TextView) convertView.findViewById(R.id.indexbottom_goods_name);
            holder.indexbottom_goods_sub = (TextView) convertView.findViewById(R.id.indexbottom_goods_sub);
            holder.indexbottom_goods_quantity = (TextView) convertView.findViewById(R.id.indexbottom_goods_quantity);
            holder.indexbottom_goods_list_cardview = (CardView) convertView.findViewById(R.id.indexbottom_goods_list_cardview);
            holder.indexbottom_goods_image = (ImageView) convertView.findViewById(R.id.indexbottom_goods_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.indexbottom_goods_name.setText(mgoodGoodsListBean.get(position).getGoodsName());
        holder.indexbottom_old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.indexbottom_old_money.setText(String.valueOf(mgoodGoodsListBean.get(position).getGoodsPrice()));
        holder.indexbottom_goods_quantity.setText("数量:" + mgoodGoodsListBean.get(position).getGoodsQuantity() + "件");
        String imageurl = mgoodGoodsListBean.get(position).getGoodsLogo();
        if (imageurl == null) {
        } else {
            String[] imageurls = imageurl.split(";");
            for (int i = 0; i < imageurls.length; i++) {
                imageurls[i] = ImgeURL + imageurls[i].toString();
            }
            Log.i("woaicaojing", imageurls[0].toString());
            Picasso.with(mContext).load(imageurls[0]).placeholder(mContext.getResources().getDrawable(R.drawable.imagviewloading))
                    .error(mContext.getResources().getDrawable(R.drawable.imageview_error))
                    .into(holder.indexbottom_goods_image);

        }
        holder.indexbottom_new_money.setText(mgoodGoodsListBean.get(position).getHyPrice() + "");
        holder.indexbottom_goods_sub.setText(mgoodGoodsListBean.get(position).getGoodsSub() + "");
        return convertView;
    }

    class ViewHolder {
        public TextView indexbottom_goods_name, indexbottom_new_money, indexbottom_old_money,
                indexbottom_goods_sub, indexbottom_goods_quantity;
        public CardView indexbottom_goods_list_cardview;
        public ImageView indexbottom_goods_image;
    }
}
