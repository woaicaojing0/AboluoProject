package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.OrderDetailInfo.ResultBean.OrderItemListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/12/11.
 */

public class OrderDetailItemAdpater extends BaseAdapter {
    private List<OrderItemListBean> mlist;
    private Context mcontext;
    private String ImageUrl;
    private Picasso picasso;
    private LayoutInflater layoutInflater;
    private int mOrderStatus;

    private View.OnClickListener EvaluationOnClickListener;
    private View.OnClickListener AfterSaleOnClickListener;

    public void setEvaluationOnClickListener(View.OnClickListener evaluationOnClickListener) {
        EvaluationOnClickListener = evaluationOnClickListener;
    }

    public void setAfterSaleOnClickListener(View.OnClickListener afterSaleOnClickListener) {
        AfterSaleOnClickListener = afterSaleOnClickListener;
    }

    public OrderDetailItemAdpater(Context context, List<OrderItemListBean> list, int OrderStatus) {
        mlist = list;
        mcontext = context;
        picasso = Picasso.with(context);
        layoutInflater = LayoutInflater.from(context);
        ImageUrl = CommonUtils.GetValueByKey(context, "ImgUrl");
        mOrderStatus = OrderStatus;
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

            convertView = layoutInflater.inflate(R.layout.orderdetail_listview_item, null);
            holder.ordetail_item_goodsname = (TextView) convertView.findViewById(R.id.ordetail_item_goodsname);
            holder.ordetail_item_hyprice = (TextView) convertView.findViewById(R.id.ordetail_item_hyprice);
            holder.tordetail_item_yuanprice = (TextView) convertView.findViewById(R.id.tordetail_item_yuanprice);
            holder.ordetail_item_standardandcolor = (TextView) convertView.findViewById(R.id.ordetail_item_standardandcolor);
            holder.ordetail_item__goods_num = (TextView) convertView.findViewById(R.id.ordetail_item__goods_num);
            holder.ordetail_item_goodsimage = (ImageView) convertView.findViewById(R.id.ordetail_item_goodsimage);
            holder.txt_orderdetail_evaluate = (TextView) convertView.findViewById(R.id.txt_orderdetail_evaluate);
            holder.txt_orderdetail_after_sale = (TextView) convertView.findViewById(R.id.txt_orderdetail_after_sale);
            if (mOrderStatus == 40) {
                holder.txt_orderdetail_evaluate.setVisibility(View.VISIBLE);
                holder.txt_orderdetail_after_sale.setVisibility(View.VISIBLE);
            } else {
                holder.txt_orderdetail_evaluate.setVisibility(View.GONE);
                holder.txt_orderdetail_after_sale.setVisibility(View.GONE);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mlist.get(position).getGoodsLogoUrl() == null) {
        } else {
            picasso.load(ImageUrl + "/" + ((mlist.get(position).getGoodsLogoUrl() == null) ? "" :
                    mlist.get(position).getGoodsLogoUrl()))
                    .placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .into(holder.ordetail_item_goodsimage);
            Log.i("woaaicaojingallorder", ImageUrl + "/" + mlist.get(position).getGoodsLogoUrl());
        }
        if (mlist.get(position).getGoodsName() == null) {
        } else {
            holder.ordetail_item_goodsname.setText("" + (mlist.get(position).getGoodsName().toString()));
        }
        //判断该商品是否有规格和颜色
        if (mlist.get(position).getGoodsColorId() == 0) {
            if (mlist.get(position).getGoodsStandardId() == 0) {
                holder.ordetail_item_standardandcolor.setText("");
            } else {
                holder.ordetail_item_standardandcolor.setText(String.valueOf(
                        " 规格分类：" + mlist.get(position).getGoodsStandard()));
            }
        } else {
            if (mlist.get(position).getGoodsStandardId() == 0) {
                holder.ordetail_item_standardandcolor.setText(String.valueOf("颜色分类：" +
                        mlist.get(position).getGoodsColor()));
            } else {
                holder.ordetail_item_standardandcolor.setText(String.valueOf("颜色分类：" + mlist.get(position).getGoodsColor() +
                        " 规格分类：" + mlist.get(position).getGoodsStandard()));
            }
        }
        holder.ordetail_item_hyprice.setText("￥" + String.valueOf(mlist.get(position).getGoodsPrice()));
        holder.tordetail_item_yuanprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tordetail_item_yuanprice.setText("￥" + String.valueOf(mlist.get(position).getGoodsPrice()));
        holder.ordetail_item__goods_num.setText(String.valueOf("X" + String.valueOf(mlist.get(position).getGoodsQuantity())));
        holder.txt_orderdetail_evaluate.setOnClickListener(EvaluationOnClickListener);
        holder.txt_orderdetail_evaluate.setTag(position);
        holder.txt_orderdetail_after_sale.setOnClickListener(AfterSaleOnClickListener);
        holder.txt_orderdetail_after_sale.setTag(position);
        return convertView;
    }

    class ViewHolder {
        public TextView ordetail_item_goodsname, ordetail_item_hyprice, tordetail_item_yuanprice,
                ordetail_item_standardandcolor, ordetail_item__goods_num, txt_orderdetail_evaluate,
                txt_orderdetail_after_sale;
        public ImageView ordetail_item_goodsimage;
    }
}
