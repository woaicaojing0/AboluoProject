package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public AllOrderAdapter(Context context, List<OrderListBean> list) {
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
        View contentView =null;
        ViewHolder2 holder2 = null;
        if (convertView == null) {

            holder2 = new ViewHolder2();
            convertView = layoutInflater.inflate(R.layout.fragment_allorder_listview_item, null);
            holder2.content_view = (LinearLayout) convertView.findViewById(R.id.content_view);
            convertView.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) convertView.getTag();
        }
        //这段代码很重要，如果没有 你可以试试看，滑动listview之后，每次都会添加商品 导致一个订单号对应很多商品
        if(holder2.content_view.getChildCount() >0)
        {
            holder2.content_view.removeAllViews();
        }
        //end
        for (int i = 0; i < mlist.size(); i++) {   //for 循环商品的数量，动态添加
            ViewHolder holder = null;
            if(holder ==null){
                holder = new ViewHolder();
                contentView = layoutInflater.inflate(R.layout.fragment_allorder_content_item, null); // 商品显示的view
                holder.image_order_goodsimage = (ImageView) contentView.findViewById(R.id.image_order_goodsimage);
                holder.txt_order_goodsName = (TextView) contentView.findViewById(R.id.txt_order_goodsName);
                holder.txt_order_standardandcolor = (TextView) contentView.findViewById(R.id.txt_order_standardandcolor);
                holder.txt_order_hyprice = (TextView) contentView.findViewById(R.id.txt_order_hyprice);
                holder.txt_order_yuanprice = (TextView) contentView.findViewById(R.id.txt_order_yuanprice);
                holder.txt_order_goods_num = (TextView) contentView.findViewById(R.id.txt_order_goods_num);
                contentView.setTag(holder);
            }else{
                holder = (ViewHolder) contentView.getTag();
            }
            picasso.load(ImageURL + "/" + mlist.get(i).getGoodsLogoUrl())
                    .placeholder(context.getResources().getDrawable(R.drawable.imagviewloading))
                    .into(holder.image_order_goodsimage);
            Log.i("woaaicaojingallorder", ImageURL + "/" + mlist.get(i).getGoodsLogoUrl());
            holder.txt_order_goodsName.setText(String.valueOf(mlist.get(i).getGoodsName()));
            holder.txt_order_standardandcolor.setText(String.valueOf("颜色分类：" + mlist.get(i).getGoodsColor() +
                    " 规格分类：" + mlist.get(i).getGoodsStandard()));
            holder.txt_order_hyprice.setText("￥" + String.valueOf(mlist.get(i).getGoodsPrice()));
            holder.txt_order_yuanprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_order_yuanprice.setText("￥" + String.valueOf(mlist.get(i).getGoodsPrice()));
            holder.txt_order_goods_num.setText(String.valueOf("X" + String.valueOf(mlist.get(i).getGoodsQuantity())));
            holder2.content_view.addView(contentView);  //
        }

        return convertView;
    }
    //商品
    class ViewHolder {
        public TextView txt_order_goodsName, txt_order_hyprice, txt_order_yuanprice, txt_order_standardandcolor, txt_order_goods_num;
        public ImageView image_order_goodsimage;
    }
    //主listview
    class ViewHolder2 {
        public LinearLayout content_view;
    }
}
