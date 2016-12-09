package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.FavorBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/12/9.
 */

public class FavorAdapter extends BaseAdapter {

    private Context mcontext;
    private Picasso picasso;
    private List<FavorBean.ResultBean> list;
    private LayoutInflater layoutInflater;
    private String ImageUrl;

    public void setSetOnMoreItemClickListener(View.OnClickListener setOnMoreItemClickListener) {
        SetOnMoreItemClickListener = setOnMoreItemClickListener;
    }

    private View.OnClickListener SetOnMoreItemClickListener;

    public FavorAdapter(Context context, List<FavorBean.ResultBean> list, String imageurl) {
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
            convertView = layoutInflater.inflate(R.layout.activity_favor_item, null);
            holder.favor_item_image = (ImageView) convertView.findViewById(R.id.favor_item_image);
            holder.favor_item_more = (LinearLayout) convertView.findViewById(R.id.favor_item_more);
            holder.favor_item_title = (TextView) convertView.findViewById(R.id.favor_item_title);
            holder.favor_item_price = (TextView) convertView.findViewById(R.id.favor_item_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        picasso.load(ImageUrl + list.get(position).getGoodsLogo()).placeholder(mcontext.getResources()
                .getDrawable(R.drawable.imagviewloading)).error(mcontext.getResources().getDrawable(R.drawable.imageview_error))
                .into(holder.favor_item_image);
        holder.favor_item_more.setOnClickListener(SetOnMoreItemClickListener);
        holder.favor_item_price.setText("￥" + String.valueOf(list.get(position).getHyPrice()));
        holder.favor_item_title.setText(String.valueOf(list.get(position).getGoodsName()));
        holder.favor_item_more.setTag(position);
        return convertView;
    }

    class ViewHolder {
        public TextView favor_item_title, favor_item_price;
        public ImageView favor_item_image;
        public LinearLayout favor_item_more; //imageview相应的控件太小了，换个大的
    }
}
