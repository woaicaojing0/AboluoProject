package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.SecKillDetailInfo.SeckillListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/11/26.
 */

public class SeckillAdapter extends BaseAdapter {

    private Context mcontext;
    private List<SeckillListBean> mlist;
    private LayoutInflater layoutInflater;
    private Picasso picasso;
    private String ImageURl;
    private int status;

    public SeckillAdapter(Context context, List<SeckillListBean> list, int status) {
        this.mcontext = context;
        this.mlist = list;
        this.layoutInflater = LayoutInflater.from(context);
        this.status = status;
        picasso = Picasso.with(context);
        ImageURl = CommonUtils.GetValueByKey(context, "ImgUrl");
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
            convertView = layoutInflater.inflate(R.layout.seckill_listview_item, null);
            holder.seckill_goods_name = (TextView) convertView.findViewById(R.id.seckill_goods_name);
            holder.seckill_goods_stand = (TextView) convertView.findViewById(R.id.seckill_goods_stand);
            holder.seckill_goods_num = (TextView) convertView.findViewById(R.id.seckill_goods_num);
            holder.seckill_goods_price = (TextView) convertView.findViewById(R.id.seckill_goods_price);
            holder.seckill_btn_go = (Button) convertView.findViewById(R.id.seckill_btn_go);
            holder.seckill_goods_image = (ImageView) convertView.findViewById(R.id.seckill_goods_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.seckill_goods_name.setText(mlist.get(position).getGoodsName());
        holder.seckill_goods_stand.setText(mlist.get(position).getGoodsColorStandardName());
        holder.seckill_goods_num.setText(String.valueOf(mlist.get(position).getSeckCount()));
        holder.seckill_goods_price.setText(String.valueOf(mlist.get(position).getSeckillPrice()));
        picasso.load(ImageURl + mlist.get(position).getGoodsLogo()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading)
        ).error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.seckill_goods_image);
        holder.seckill_btn_go.setTag(mlist.get(position).getSeckillStatus());
        //State : 0,1,2 //(0-未开启，1-已开启，2-准备中，3-已结束）
        if (status == 0 || status == 2) {
            holder.seckill_btn_go.setText("未开始");
        } else if (status == 1) {
        } else {
            holder.seckill_btn_go.setText("已结束");
        }
        return convertView;
    }

    class ViewHolder {
        public TextView seckill_goods_name, seckill_goods_stand, seckill_goods_num, seckill_goods_price;
        private Button seckill_btn_go;
        private ImageView seckill_goods_image;
    }
}
