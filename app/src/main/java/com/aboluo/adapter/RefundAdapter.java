package com.aboluo.adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/11/22.
 */

public class RefundAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private Picasso picasso;
    private String ImageURL;
    private View.OnClickListener itemOnclickListener;
    private View.OnClickListener ReFundDetailOnClickListener;

    public void setReFundDetailOnClickListener(View.OnClickListener reFundDetailOnClickListener) {
        ReFundDetailOnClickListener = reFundDetailOnClickListener;
    }

    public void setItemOnclickListener(View.OnClickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }


    public RefundAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
        ImageURL = CommonUtils.GetValueByKey(context, "ImgUrl");
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return 3;
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
            convertView = layoutInflater.inflate(R.layout.refund_listview_item, null);
            holder2.refund_content_view = (LinearLayout) convertView.findViewById(R.id.refund_content_view);
            holder2.refund_status = (TextView) convertView.findViewById(R.id.refund_status);
            holder2.refund_money = (TextView) convertView.findViewById(R.id.refund_money);
            holder2.pay_money = (TextView) convertView.findViewById(R.id.pay_money);
            holder2.txt_refund_detail = (TextView) convertView.findViewById(R.id.txt_refund_detail);
            convertView.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) convertView.getTag();
        }
        //这段代码很重要，如果没有 你可以试试看，滑动listview之后，每次都会添加商品 导致一个订单号对应很多商品
        if (holder2.refund_content_view.getChildCount() > 0) {
            holder2.refund_content_view.removeAllViews();
        }
        //end
        for (int i = 0; i < 3; i++) {   //for 循环商品的数量，动态添加
            ViewHolder holder = null;
            if (holder == null) {
                holder = new ViewHolder();
                contentView = layoutInflater.inflate(R.layout.refund_content_item, null); // 商品显示的view
                holder.refund_goodsimage = (ImageView) contentView.findViewById(R.id.refund_goodsimage);
                holder.refund_goodsName = (TextView) contentView.findViewById(R.id.refund_goodsName);
                holder.refund_standardandcolor = (TextView) contentView.findViewById(R.id.refund_standardandcolor);
                contentView.setTag(holder);
            } else {
                holder = (ViewHolder) contentView.getTag();
            }
            //判断该商品是否有规格和颜色

            holder2.refund_content_view.setOnClickListener(itemOnclickListener);
            holder2.txt_refund_detail.setOnClickListener(ReFundDetailOnClickListener);
            holder2.refund_content_view.setTag(position);
            holder2.txt_refund_detail.setTag(position);
            holder2.refund_content_view.addView(contentView);  //
        }

        return convertView;
    }

    //商品
    class ViewHolder {
        public TextView refund_goodsName, refund_standardandcolor;
        public ImageView refund_goodsimage;

    }

    //主listview
    class ViewHolder2 {
        public LinearLayout refund_content_view;
        public TextView refund_status, refund_money, pay_money, txt_refund_detail;
    }

    public String OrderStatus(int orderStatus, ViewHolder2 holder2) {
        String result;
        switch (orderStatus) {
            case 10:
                result = "退款中";
                holder2.txt_refund_detail.setVisibility(View.VISIBLE);
                break;
            default:
                result = "退款成功";
                break;
        }
        return result;
    }
}
