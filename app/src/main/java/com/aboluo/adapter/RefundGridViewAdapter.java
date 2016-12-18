package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aboluo.com.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by cj34920 on 2016/9/7.
 */
public class RefundGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> images;
    private Picasso picasso;
    private View.OnClickListener DeleteOnclickListener;
    private View.OnClickListener ShowOnclickListener;

    public RefundGridViewAdapter(Context mContext, ArrayList<String> images) {
        this.mContext = mContext;
        this.images = images;
        this.picasso = Picasso.with(mContext);
    }

    public void setDeleteOnclickListener(View.OnClickListener deleteOnclickListener) {
        DeleteOnclickListener = deleteOnclickListener;
    }

    public void setShowOnclickListener(View.OnClickListener showOnclickListener) {
        ShowOnclickListener = showOnclickListener;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.gridview_refund_item, null);
            holder.imageViewshow = (ImageView) view.findViewById(R.id.refund_img_show);
            holder.imageviewdelete = (ImageView) view.findViewById(R.id.refund_img_delete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        picasso.load(new File(images.get(i).toString()))
                .placeholder(mContext.getResources().getDrawable(R.drawable.imagviewloading))
                .into(holder.imageViewshow);
        holder.imageViewshow.setTag(i);
        holder.imageViewshow.setOnClickListener(ShowOnclickListener);
        holder.imageviewdelete.setTag(i);
        holder.imageviewdelete.setOnClickListener(DeleteOnclickListener);
        return view;
    }

    public class ViewHolder {
        public ImageView imageViewshow, imageviewdelete;
    }
}
