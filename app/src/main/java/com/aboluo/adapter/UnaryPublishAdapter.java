package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.UnaryListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/12/9.
 */

public class UnaryPublishAdapter extends BaseAdapter {

    private Context mcontext;
    private Picasso picasso;
    private List<UnaryListBean.ListResultBean> list;
    private LayoutInflater layoutInflater;
    private String ImageUrl;

    public UnaryPublishAdapter(Context context, List<UnaryListBean.ListResultBean> list, String imageurl) {
        this.mcontext = context;
        this.picasso = Picasso.with(context);
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
        this.ImageUrl = imageurl;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = layoutInflater.inflate(R.layout.activity_unary_publish_item, null);
            holder.unary_publish_item_title = (TextView) convertView.findViewById(R.id.unary_publish_item_title);
            holder.unary_publish_item_price = (TextView) convertView.findViewById(R.id.unary_publish_item_price);
            holder.unary_publish_item_name = (TextView) convertView.findViewById(R.id.unary_publish_item_name);
            holder.unary_publish_item_count = (TextView) convertView.findViewById(R.id.unary_publish_item_count);
            holder.unary_publish_item_time = (TextView) convertView.findViewById(R.id.unary_publish_item_time);
            holder.unary_publish_item_image = (ImageView) convertView.findViewById(R.id.unary_publish_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.unary_publish_item_title.setText(list.get(position).getGoodsName() == null ? "" : list.get(position).getGoodsName());
        holder.unary_publish_item_price.setText(String.valueOf(list.get(position).getGoodsPrice()));
        holder.unary_publish_item_name.setText("暂无中奖人数据");
        holder.unary_publish_item_count.setText(String.valueOf(list.get(position).getNeedPersonCount()));
        holder.unary_publish_item_time.setText(list.get(position).getStartTime().toString());
        picasso.load(ImageUrl + list.get(position).getGoodsLogo()).placeholder(mcontext.getResources().getDrawable(
                R.drawable.imagviewloading)).error(mcontext.getResources().getDrawable(R.drawable.imageview_error))
                .into(holder.unary_publish_item_image);
        return convertView;
    }

    class ViewHolder {
        public TextView unary_publish_item_time, unary_publish_item_name, unary_publish_item_count,
                unary_publish_item_title, unary_publish_item_price;
        private ImageView unary_publish_item_image;
    }
}
