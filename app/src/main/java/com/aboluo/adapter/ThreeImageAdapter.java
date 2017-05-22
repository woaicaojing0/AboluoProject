package com.aboluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aboluo.Interface.OnRecyclerViewItemClickListener;
import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.com.R;
import com.aboluo.model.UnaryListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj on 2017/5/4.
 */

public class ThreeImageAdapter extends RecyclerView.Adapter<ThreeImageAdapter.ThreeImageViewHolder> implements View.OnClickListener {
    private static String ImgeURL = null;
    private List<UnaryListBean.ListResultBean> mlist;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private Context mcontext;
    private Picasso mpicasso;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public ThreeImageAdapter(Context context, List<UnaryListBean.ListResultBean> list) {
        this.mlist = list;
        this.mcontext = context;
        this.mpicasso = Picasso.with(mcontext);
        ImgeURL = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
    }

    @Override
    public ThreeImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_threeimage_item, null, false);
        ThreeImageViewHolder threeImageViewHolder = new ThreeImageViewHolder(view);
        return threeImageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ThreeImageViewHolder holder, final int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mcontext) / 3,
                LinearLayout.LayoutParams.MATCH_PARENT);
        holder.rl_farther.setLayoutParams(params);
        try {
            if (mlist.get(position).getGoodsLogo() == null || mlist.get(position).getGoodsLogo().equals("")) {
                mpicasso.load(R.drawable.imagviewloading).into(holder.unary_threeimage);
            } else {
                mpicasso.load(ImgeURL + mlist.get(position).getGoodsLogo())
                        .placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                        .error(mcontext.getResources().getDrawable(R.drawable.imageview_error))
                        .into(holder.unary_threeimage);
            }
            if (mlist.get(position).getState() == 2) {
                holder.unary_threetext.setText("正在揭晓");
            } else if (mlist.get(position).getState() == 3 || mlist.get(position).getState() == 4) {
                holder.unary_threetext.setText("已开奖");
            } else {
                holder.unary_threetext.setText("正在揭晓");
            }

            holder.rl_farther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClick(v, position);
                }
            });
        } catch (IndexOutOfBoundsException e) {

        }


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View v) {

    }

    class ThreeImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView unary_threeimage;
        public TextView unary_threetext;
        public RelativeLayout rl_farther;

        public ThreeImageViewHolder(View itemView) {
            super(itemView);
            unary_threeimage = (ImageView) itemView.findViewById(R.id.unary_threeimage);
            unary_threetext = (TextView) itemView.findViewById(R.id.unary_threetext);
            rl_farther = (RelativeLayout) itemView.findViewById(R.id.rl_farther);
        }
    }
}
