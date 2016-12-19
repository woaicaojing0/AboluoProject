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
import com.aboluo.model.SearchOrderBean.ResultBean;
import com.bigkoo.convenientbanner.holder.Holder;
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
    private View.OnClickListener CuicuiOrderOnClickListener;
    private View.OnClickListener EvaluateOrderOnClickListener;
    private View.OnClickListener AfterSaleOrderOnClickListener;

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

    public void setCuicuiOrderOnClickListener(View.OnClickListener cuicuiOrderOnClickListener) {
        CuicuiOrderOnClickListener = cuicuiOrderOnClickListener;
    }

    public void setEvaluateOrderOnClickListener(View.OnClickListener evaluateOrderOnClickListener) {
        EvaluateOrderOnClickListener = evaluateOrderOnClickListener;
    }

    public void setAfterSaleOrderOnClickListener(View.OnClickListener afterSaleOrderOnClickListener) {
        AfterSaleOrderOnClickListener = afterSaleOrderOnClickListener;
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
        View contentView = null;    //中间内容的布局
        ViewHolder2 holder2 = null;
        if (convertView == null) {

            holder2 = new ViewHolder2();
            convertView = layoutInflater.inflate(R.layout.fragment_allorder_listview_item, null);
            holder2.content_view = (LinearLayout) convertView.findViewById(R.id.content_view);
            holder2.txt_findgoods = (TextView) convertView.findViewById(R.id.txt_findgoods);
            holder2.txt_ok = (TextView) convertView.findViewById(R.id.txt_ok);
            holder2.txt_cancelorder = (TextView) convertView.findViewById(R.id.txt_cancelorder);
            holder2.txt_payorder = (TextView) convertView.findViewById(R.id.txt_payorder);
            holder2.txt_cuicui = (TextView) convertView.findViewById(R.id.txt_cuicui);
            holder2.txt_order_status = (TextView) convertView.findViewById(R.id.txt_order_status);
            holder2.order_allmoney = (TextView) convertView.findViewById(R.id.order_allmoney);
            holder2.order_goodsnum = (TextView) convertView.findViewById(R.id.order_goodsnum);
            holder2.txt_after_sale = (TextView) convertView.findViewById(R.id.txt_after_sale);
            holder2.txt_evaluate = (TextView) convertView.findViewById(R.id.txt_evaluate);
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
            String imageurl = mlist.get(position).getOrderItemList().get(i).getGoodsLogoUrl().toString();
            if (imageurl == null) {
            } else {
                String[] imageurls = imageurl.split(";");
                for (int i0 = 0; i0 < imageurls.length; i0++) {
                    imageurls[i0] = ImageURL + imageurls[i0].toString();
                }
                Log.i("woaicaojing", imageurls[0].toString());
                picasso.load(imageurls[0].toString()).placeholder(context.getResources()
                        .getDrawable(R.drawable.imagviewloading)).error(context.getResources().getDrawable(R.drawable.imageview_error))
                        .into(holder.image_order_goodsimage);
            }
            Log.i("woaaicaojingallorder", ImageURL + "/" + mlist.get(position).getOrderItemList().get(i).getGoodsLogoUrl());
            holder.txt_order_goodsName.setText(String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsName()));
            //判断该商品是否有规格和颜色
            if (mlist.get(position).getOrderItemList().get(i).getGoodsColorId() == 0) {
                if (mlist.get(position).getOrderItemList().get(i).getGoodsStandardId() == 0) {
                    holder.txt_order_standardandcolor.setText("");
                } else {
                    holder.txt_order_standardandcolor.setText(String.valueOf(
                            " 规格分类：" + mlist.get(position).getOrderItemList().get(i).getGoodsStandard()));
                }
            } else {
                if (mlist.get(position).getOrderItemList().get(i).getGoodsStandardId() == 0) {
                    holder.txt_order_standardandcolor.setText(String.valueOf("颜色分类：" +
                            mlist.get(position).getOrderItemList().get(i).getGoodsColor()));
                } else {
                    holder.txt_order_standardandcolor.setText(String.valueOf("颜色分类：" + mlist.get(position).getOrderItemList().get(i).getGoodsColor() +
                            " 规格分类：" + mlist.get(position).getOrderItemList().get(i).getGoodsStandard()));
                }
            }
            holder.txt_order_hyprice.setText("￥" + String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsPrice()));
            holder.txt_order_yuanprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txt_order_yuanprice.setText("￥" + String.valueOf(mlist.get(position).getOrderItemList().get(i).getPrice()));
            holder.txt_order_goods_num.setText(String.valueOf("X" + String.valueOf(mlist.get(position).getOrderItemList().get(i).getGoodsQuantity())));

            holder2.content_view.setOnClickListener(itemOnclickListener);
            holder2.content_view.setTag(position);
            holder2.txt_order_status.setText(OrderStatus(mlist.get(position).getOrderStatus(), holder2));
            holder2.order_allmoney.setText(String.valueOf("￥" + String.valueOf(mlist.get(position).getTotalPrice())));
            holder2.order_goodsnum.setText(String.valueOf(String.valueOf(mlist.get(position).getOrderItemList().size())));
            holder2.txt_findgoods.setOnClickListener(FindGoodsOnclickListener);
            holder2.txt_ok.setOnClickListener(SureOkOnclickListener);
            holder2.txt_cancelorder.setOnClickListener(CancelOrderOnclickListener);
            holder2.txt_payorder.setOnClickListener(PayOnclickListener);
            holder2.txt_cuicui.setOnClickListener(CuicuiOrderOnClickListener);
            holder2.txt_after_sale.setOnClickListener(AfterSaleOrderOnClickListener);
            holder2.txt_evaluate.setOnClickListener(EvaluateOrderOnClickListener);
            holder2.txt_findgoods.setTag(position);
            holder2.txt_ok.setTag(position);
            holder2.txt_cancelorder.setTag(position);
            holder2.txt_payorder.setTag(position);
            holder2.txt_cuicui.setTag(position);
            holder2.txt_after_sale.setTag(position);
            holder2.txt_evaluate.setTag(position);
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
        public TextView txt_findgoods, txt_ok, txt_cancelorder, txt_payorder, txt_cuicui,
                txt_after_sale, txt_evaluate;
        public TextView txt_order_status, order_allmoney, order_goodsnum;
    }

    public String OrderStatus(int orderStatus, ViewHolder2 holder2) {
        String result;
        clean(holder2);
        switch (orderStatus) {
            case 10:
                result = "待付款";
                holder2.txt_cancelorder.setVisibility(View.VISIBLE);
                holder2.txt_payorder.setVisibility(View.VISIBLE);
                break;
            case 20:
                result = "待发货";
                holder2.txt_cuicui.setVisibility(View.VISIBLE);
                break;
            case 30:
                result = "卖家已发货";
                holder2.txt_findgoods.setVisibility(View.VISIBLE);
                holder2.txt_ok.setVisibility(View.VISIBLE);
                break;
            case 40:
                result = "交易成功";
                holder2.txt_evaluate.setVisibility(View.VISIBLE);
                break;
            default:
                result = "暂无信息";
                break;
        }
        return result;
    }
    private  void clean(ViewHolder2 holder2)
    {
        holder2.txt_cancelorder.setVisibility(View.GONE);
        holder2.txt_payorder.setVisibility(View.GONE);
        holder2.txt_cuicui.setVisibility(View.GONE);
        holder2.txt_findgoods.setVisibility(View.GONE);
        holder2.txt_ok.setVisibility(View.GONE);
        holder2.txt_after_sale.setVisibility(View.GONE);
        holder2.txt_evaluate.setVisibility(View.GONE);
    }
}
