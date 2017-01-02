package com.aboluo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/11/18.
 */

public class OrderSureListViewAdapter extends BaseAdapter {
    private String ImgeURL = null;
    private List<GoodsShoppingCartListBean> mlist;
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public OrderSureListViewAdapter(List<GoodsShoppingCartListBean> list, Context context) {
        this.mcontext = context;
        this.mlist = list;
        layoutInflater = LayoutInflater.from(mcontext);
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
            convertView = layoutInflater.inflate(R.layout.orderlist_item, null);
            holder.order_image = (ImageView) convertView.findViewById(R.id.order_image);
            holder.order_txt_desc = (TextView) convertView.findViewById(R.id.order_txt_desc);
            holder.order_txt_num = (TextView) convertView.findViewById(R.id.order_txt_num);
            holder.order_txt_money = (TextView) convertView.findViewById(R.id.order_txt_money);
            holder.order_txt_standards = (TextView) convertView.findViewById(R.id.order_txt_standards);
            holder.order_color = (TextView) convertView.findViewById(R.id.order_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String result = String.valueOf(mlist.get(position).getGoodsCount());
        holder.order_txt_num.setText("X" + String.valueOf(mlist.get(position).getGoodsCount()));
        holder.order_txt_desc.setText(mlist.get(position).getGoodsName().toString());
        holder.order_txt_money.setText("￥" + String.valueOf(mlist.get(position).getHyPrice()));
        String guige = null;
        if (mlist.get(position).getGoodsStandard() != null) {
            holder.order_txt_standards.setText("" + String.valueOf(mlist.get(position).getGoodsStandard()));
        } else {
            holder.order_txt_standards.setText("无");
        }
        if (mlist.get(position).getGoodsColor() != null) {
            holder.order_color.setText("" + String.valueOf(mlist.get(position).getGoodsColor()));
            holder.order_color.setText("无");
        } else {

        }
        if (mlist.get(position).getGoodsLogo() == null) {
        } else {
            String[] imges = mlist.get(position).getGoodsLogo().toString().split(";");
            if (ImgeURL == null) {
                ImgeURL = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
            } else {
            }
            Log.i("woaicaojingshopimg", ImgeURL + imges[0]);
            Picasso.with(mcontext).load(ImgeURL + imges[0]).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .error(mcontext.getResources().getDrawable(R.drawable.imageview_error))
                    .into(holder.order_image);
        }
        return convertView;
    }

    class ViewHolder {
        TextView order_txt_desc, order_txt_num, order_txt_money, order_txt_standards, order_color;
        ImageView order_image;
    }
}
