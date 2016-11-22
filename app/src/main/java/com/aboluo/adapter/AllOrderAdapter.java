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
import com.aboluo.model.SearchOrderBean.ResultBean.OrderListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/11/22.
 */

public class AllOrderAdapter extends BaseAdapter {
    private List<OrderListBean> mlist;
    private Context context;
    private LayoutInflater layoutInflater;
    private Picasso picasso;
    private String ImageURL;

    public  AllOrderAdapter(Context context, List<OrderListBean> list) {
        this.context = context;
        this.mlist = list;
        this.layoutInflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
        ImageURL = CommonUtils.GetValueByKey(context, "ImgUrl");
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
            convertView = layoutInflater.inflate(R.layout.fragment_allorder_listview_item, null);
            holder.image_order_goodsimage = (ImageView) convertView.findViewById(R.id.image_order_goodsimage);
            holder.txt_order_goodsName = (TextView) convertView.findViewById(R.id.txt_order_goodsName);
            holder.txt_order_standardandcolor = (TextView) convertView.findViewById(R.id.txt_order_standardandcolor);
            holder.txt_order_hyprice = (TextView) convertView.findViewById(R.id.txt_order_hyprice);
            holder.txt_order_yuanprice = (TextView) convertView.findViewById(R.id.txt_order_yuanprice);
            holder.txt_order_goods_num = (TextView) convertView.findViewById(R.id.txt_order_goods_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        picasso.load(ImageURL + "/" + mlist.get(position).getGoodsLogoUrl())
                .placeholder(context.getResources().getDrawable(R.drawable.imagviewloading))
                .into(holder.image_order_goodsimage);
        Log.i("woaaicaojingallorder",ImageURL + "/" + mlist.get(position).getGoodsLogoUrl());
        holder.txt_order_goodsName.setText(String.valueOf(mlist.get(position).getGoodsName()));
        holder.txt_order_standardandcolor.setText(String.valueOf("颜色分类:" + mlist.get(position).getGoodsColor() +
                "规格分类："+mlist.get(position).getGoodsStandard()));
        holder.txt_order_hyprice.setText(String.valueOf(mlist.get(position).getGoodsPrice()));
        holder.txt_order_yuanprice.setText(String.valueOf(mlist.get(position).getGoodsPrice()));
        holder.txt_order_goods_num.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txt_order_goods_num.setText(String.valueOf("X"+String.valueOf(mlist.get(position).getGoodsQuantity())));
        return convertView;
    }

    class ViewHolder {
        public TextView txt_order_goodsName, txt_order_hyprice, txt_order_yuanprice, txt_order_standardandcolor, txt_order_goods_num;
        public ImageView image_order_goodsimage;
    }
}
