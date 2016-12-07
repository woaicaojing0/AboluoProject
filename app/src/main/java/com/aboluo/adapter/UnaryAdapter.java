package com.aboluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;
import com.aboluo.model.UnaryListBean.ListResultBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CJ on 2016/12/6.
 */

public class UnaryAdapter extends RecyclerView.Adapter<UnaryAdapter.MyviewHolder> {

    private List<ListResultBean> mlist;
    private Context context;
    private Picasso picasso;
    private String ImageURL;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;

    public UnaryAdapter(List<ListResultBean> list, Context context) {
        this.mlist = list;
        this.context = context;
        this.picasso = Picasso.with(context);
        this.ImageURL = CommonUtils.GetValueByKey(context, "ImgUrl");
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyviewHolder holder = new MyviewHolder(LayoutInflater.from(context).
                inflate(R.layout.activity_unary_listview_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, final int position) {
        ViewTreeObserver vto2 = holder.relative_farther.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                holder.relative_farther.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //  textView.append("\n\n"+imageView.getHeight()+","+imageView.getWidth());
                ViewGroup.LayoutParams para = holder.linelayout_child.getLayoutParams();
                int with = holder.relative_farther.getWidth();
                int all = mlist.get(position).getNeedPersonCount();
                int i = mlist.get(position).getJoinCount();
                if (i == 0) {
                    para.width = 0;
                } else {
                    para.width = ((holder.relative_farther.getWidth())* i)/all;
                }
                holder.linelayout_child.setLayoutParams(para);
            }
        });
        String result = CommonUtils.Getpercent(mlist.get(position).getJoinCount(),
                mlist.get(position).getNeedPersonCount());
        holder.textView.setText(mlist.get(position).getGoodsName());
        holder.uanry_listview_item_percent.setText(result);
        holder.unary_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerViewItemClickListener != null) {
                    onRecyclerViewItemClickListener.onItemClick(v, position);
                }
            }
        });
        picasso.load(ImageURL + mlist.get(position).getGoodsLogo()).placeholder(R.drawable.imagviewloading)
                .error(R.drawable.imageview_error).into(holder.unary_listview_image);
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder {
        public TextView textView, uanry_listview_item_percent, unary_begin;
        public ImageView unary_listview_image;
        public RelativeLayout relative_farther;
        public LinearLayout linelayout_child;

        public MyviewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.unary_listview_title);
            unary_begin = (TextView) itemView.findViewById(R.id.unary_begin);
            uanry_listview_item_percent = (TextView) itemView.findViewById(R.id.uanry_listview_item_percent);
            unary_listview_image = (ImageView) itemView.findViewById(R.id.unary_listview_image);
            relative_farther = (RelativeLayout) itemView.findViewById(R.id.relative_farther);
            linelayout_child = (LinearLayout) itemView.findViewById(R.id.linelayout_child);
            //-----------------------------------------------方法三
            ViewTreeObserver vto2 = unary_listview_image.getViewTreeObserver();
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    unary_listview_image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    //  textView.append("\n\n"+imageView.getHeight()+","+imageView.getWidth());
                    ViewGroup.LayoutParams para = unary_listview_image.getLayoutParams();
                    para.height = unary_listview_image.getWidth();
                    para.width = unary_listview_image.getWidth();
                    unary_listview_image.setLayoutParams(para);
                }
            });
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onRecyclerViewItemClickListener = listener;
    }
}
