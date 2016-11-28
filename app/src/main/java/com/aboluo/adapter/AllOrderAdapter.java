package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.SearchOrderBean.ResultBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/11/22.
 */

public class AllOrderAdapter extends BaseAdapter {
    private List<ResultBean> mlist;
    private Context context;
    private LayoutInflater layoutInflater;
    private Picasso picasso;
    private String ImageURL;
    private View.OnClickListener itemOnclickListener;
    private View.OnClickListener FindGoodsOnclickListener;
    private View.OnClickListener PayOnclickListener;
    private View.OnClickListener SureOkOnclickListener;
    private View.OnClickListener CancelOrderOnclickListener;

    public void setItemOnclickListener(View.OnClickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    public void setFindGoodsOnclickListener(View.OnClickListener findGoodsOnclickListener) {
        FindGoodsOnclickListener = findGoodsOnclickListener;
    }

    public void setPayOnclickListener(View.OnClickListener payOnclickListener) {
        PayOnclickListener = payOnclickListener;
    }

    public void setSureOkOnclickListener(View.OnClickListener sureOkOnclickListener) {
        SureOkOnclickListener = sureOkOnclickListener;
    }

    public void setCancelOrderOnclickListener(View.OnClickListener cancelOrderOnclickListener) {
        CancelOrderOnclickListener = cancelOrderOnclickListener;
    }

    public AllOrderAdapter(Context context, List<ResultBean> list) {
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
        View contentView = null;
        ViewHolder2 holder2 = null;
        if (convertView == null) {

            holder2 = new ViewHolder2();
            convertView = layoutInflater.inflate(R.layout.fragment_allorder_listview_item, null);
            holder2.content_view = (LinearLayout) convertView.findViewById(R.id.content_view);
            holder2.btn_findgoods = (Button) convertView.findViewById(R.id.btn_findgoods);
            holder2.btn_ok = (Button) convertView.findViewById(R.id.btn_ok);
            holder2.btn_cancelorder = (Button) convertView.findViewById(R.id.btn_cancelorder);
            holder2.btn_payorder = (Button) convertView.findViewById(R.id.btn_payorder);
            holder2.txt_order_status = (TextView) convertView.findViewById(R.id.txt_order_status);
            convertView.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) convertView.getTag();
        }
        //这段代码很重要，如果没有 你可以试试看，滑动listview之后，每次都会添加商品 导致一个订单号对应很多商品
        if (holder2.content_view.getChildCount() > 0) {
            holder2.content_view.removeAllViews();
        }
        //end
        for (int i = 0; i < mlist.get(position).getOrderItemList().size(); i++) {   //for 循环商品的数量，动态添加
            ViewHolder holder = null;
            if (holder == null) {
                holder = new ViewHolder();
                contentView = layoutInflater.inflate(R.layout.fragment_allorder_content_item, null); // 商品显示的view
                holder.image_order_goodsimage = (ImageView) contentView.findViewById(R.id.image_order_goodsimage);
                holder.txt_order_goodsName = (TextView) contentView.findViewById(R.id.txt_order_goodsName);
                holder.txt_order_standardandcolor = (TextView) contentView.findViewById(R.id.txt_order_standardandcolor);
                holder.txt_order_hyprice = (TextView) contentView.findViewById(R.id.txt_order_hyprice);
                holder.txt_order_yuanprice = (TextView) contentView.findViewById(R.id.txt_order_yuanprice);
                holder.txt_order_goods_num = (TextView) contentView.findViewById(R.id.txt_order_goods_num);
                contentView.setTag(holder);
            } else {
                holder = (ViewHolder) contentView.getTag();
            }
            picasso.load(ImageURL + "/" + mlist.get(position).getOrderItemList().get(i).getGoodsLogoUrl())
                    .placeholder(context.getResources().getDrawable(R.drawable.imagviewloading))
                    .into(holder.image_order_goodsimage);
            Log.i("woaaicaojingallorder", ImageURL + "/" + mlist.get(position).getOrderItemList().get(i).getGoodsLogoUrl());
            holder.txt_order_goodsName.setText(String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsName()));
            holder.txt_order_standardandcolor.setText(String.valueOf("颜色分类：" + mlist.get(position).getOrderItemList().get(i).getGoodsColor() +
                    " 规格分类：" + mlist.get(position).getOrderItemList().get(i).getGoodsStandard()));
            holder.txt_order_hyprice.setText("￥" + String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsPrice()));
            holder.txt_order_yuanprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_order_yuanprice.setText("￥" + String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsPrice()));
            holder.txt_order_goods_num.setText(String.valueOf("X" + String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsQuantity())));
            contentView.setOnClickListener(itemOnclickListener);
            contentView.setTag(position);
            holder2.txt_order_status.setText(OrderStatus(mlist.get(position).getOrderStatus(),holder2));
            holder2.btn_findgoods.setOnClickListener(FindGoodsOnclickListener);
            holder2.btn_ok.setOnClickListener(SureOkOnclickListener);
            holder2.btn_cancelorder.setOnClickListener(CancelOrderOnclickListener);
            holder2.btn_payorder.setOnClickListener(PayOnclickListener);
            holder2.btn_findgoods.setTag(position);
            holder2.btn_ok.setTag(position);
            holder2.btn_cancelorder.setTag(position);
            holder2.btn_payorder.setTag(position);
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
        public Button btn_findgoods, btn_ok, btn_cancelorder, btn_payorder;
        private TextView txt_order_status;
    }

    public String OrderStatus(int orderStatus,ViewHolder2 holder2) {
        String result;
        switch (orderStatus) {
            case 10:
                result = "待付款";
                holder2.btn_cancelorder.setVisibility(View.VISIBLE);
                holder2.btn_payorder.setVisibility(View.VISIBLE);
                break;
            case 20:
                result = "待发货";
                break;
            case 30:
                result = "卖家已发货";
                holder2.btn_findgoods.setVisibility(View.VISIBLE);
                holder2.btn_ok.setVisibility(View.VISIBLE);
                break;
            case 40:
                result = "已完成";
                holder2.btn_findgoods.setVisibility(View.VISIBLE);
                break;
            default:
                result = "暂无信息";
                break;
        }
        return result;
    }
}
