package com.aboluo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.XUtils.Utils;
import com.aboluo.com.R;
import com.aboluo.model.UnaryRecordBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj34920 on 2017/4/27.
 */

public class UnaryRecordAdapter extends RecyclerView.Adapter<UnaryRecordAdapter.UnaryRecordViewHolder> {

    private Context mcontext;
    private List<UnaryRecordBean.ListResultBean> mlist;
    private Picasso mpicasso;

    public UnaryRecordAdapter(Context context, List<UnaryRecordBean.ListResultBean> list) {
        this.mcontext = context;
        this.mlist = list;
        this.mpicasso = Picasso.with(context);
    }

    @Override
    public UnaryRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_unaryrecord_item, null, false);
        UnaryRecordViewHolder unaryRecordViewHolder = new UnaryRecordViewHolder(view);
        return unaryRecordViewHolder;
    }

    @Override
    public void onBindViewHolder(UnaryRecordViewHolder holder, int position) {
        holder.tv_unary_nickName.setText(mlist.get(position).getWechatNickName() == null ? "暂无" : mlist.get(position).getWechatNickName());
        holder.tv_unary_joinTime.setText(mlist.get(position).getJoinTime().replace("T", " "));
        holder.tv_unary_num.setText(mlist.get(position).getLotteryNumber() == null ? "暂无" : mlist.get(position).getLotteryNumber());
        switch (mlist.get(position).getIsDealer()) {
            case 0:
                holder.tv_unary_level.setText("普通会员");
                break;
            case 1:
                holder.tv_unary_level.setText("合伙人");
                break;
            case 2:
                holder.tv_unary_level.setText("金牌合伙人");
                break;
            default:
                holder.tv_unary_level.setText("普通会员");
                break;
        }
        holder.tv_unary_TotalBuyCount.setText(String.valueOf(mlist.get(position).getTotalBuyCount()));
        int IsWinLottery = mlist.get(position).getIsWinLottery();
        if (IsWinLottery == 1) { ////中奖
            holder.tv_unary_nickName.setTextColor(mcontext.getResources().getColor(R.color.btn_color));
            holder.tv_unary_level.setTextColor(mcontext.getResources().getColor(R.color.btn_color));
            holder.tv_IsWinLottery.setVisibility(View.VISIBLE);
            holder.tv_unary_joinTime.setTextColor(mcontext.getResources().getColor(R.color.btn_color));
            holder.tv_unary_TotalBuyCountPrefix.setTextColor(mcontext.getResources().getColor(R.color.btn_color));
            holder.tv_unary_TotalBuyCountSuffix.setTextColor(mcontext.getResources().getColor(R.color.btn_color));
        }
        String logUrl = mlist.get(position).getWechatLogoUrl();
        if (logUrl == null || logUrl.length() <= 0) {
            mpicasso.load(R.drawable.imagviewloading).into(holder.iv_unary_profile_image);
        } else {
            mpicasso.load(mlist.get(position).getWechatLogoUrl()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .resize(Utils.dp2px(mcontext, 250), Utils.dp2px(mcontext, 250))
                    .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.iv_unary_profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class UnaryRecordViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_unary_nickName, tv_unary_TotalBuyCount,
                tv_unary_level, tv_unary_joinTime, tv_IsWinLottery,
                tv_unary_TotalBuyCountPrefix, tv_unary_TotalBuyCountSuffix, tv_unary_num;
        private ImageView iv_unary_profile_image;

        public UnaryRecordViewHolder(View itemView) {
            super(itemView);
            tv_unary_nickName = (TextView) itemView.findViewById(R.id.tv_unary_nickName);
            tv_unary_TotalBuyCount = (TextView) itemView.findViewById(R.id.tv_unary_TotalBuyCount);
            tv_unary_level = (TextView) itemView.findViewById(R.id.tv_unary_level);
            tv_unary_joinTime = (TextView) itemView.findViewById(R.id.tv_unary_joinTime);
            tv_IsWinLottery = (TextView) itemView.findViewById(R.id.tv_IsWinLottery);
            tv_unary_TotalBuyCountPrefix = (TextView) itemView.findViewById(R.id.tv_unary_TotalBuyCountPrefix);
            tv_unary_TotalBuyCountSuffix = (TextView) itemView.findViewById(R.id.tv_unary_TotalBuyCountSuffix);
            tv_unary_num = (TextView) itemView.findViewById(R.id.tv_unary_num);
            iv_unary_profile_image = (ImageView) itemView.findViewById(R.id.iv_unary_profile_image);
        }
    }
}
