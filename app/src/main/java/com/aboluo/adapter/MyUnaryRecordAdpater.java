package com.aboluo.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aboluo.XUtils.Utils;
import com.aboluo.com.R;
import com.aboluo.model.UnaryRecordBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cj34920 on 2017/5/18.
 */

public class MyUnaryRecordAdpater extends RecyclerView.Adapter<MyUnaryRecordAdpater.MyUnaryRecordViewHolder> {
    private Context mcontext;
    private List<UnaryRecordBean.ListResultBean> mlist;
    private Picasso mpicasso;

    public MyUnaryRecordAdpater(Context context, List<UnaryRecordBean.ListResultBean> list) {
        this.mcontext = context;
        this.mlist = list;
        this.mpicasso = Picasso.with(context);
    }

    @Override
    public MyUnaryRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_my_unaryrecord_item, null, false);
        MyUnaryRecordViewHolder myUnaryRecordAdpater = new MyUnaryRecordViewHolder(view);
        return myUnaryRecordAdpater;
    }

    @Override
    public void onBindViewHolder(MyUnaryRecordViewHolder holder, int position) {
        holder.tv_my_unary_nickName.setText(mlist.get(position).getWechatNickName() == null ? "暂无" : mlist.get(position).getWechatNickName());
        holder.tv_my_unary_joinTime.setText(mlist.get(position).getJoinTime().replace("T", " "));
        holder.tv_unary_num.setText(mlist.get(position).getLotteryNumber() == null ? "暂无" : mlist.get(position).getLotteryNumber());
        holder.tv_my_unary_id.setText(mlist.get(position).getPurchaseId() + "");
        holder.tv_my_unary_TotalBuyCount.setText(String.valueOf(mlist.get(position).getTotalBuyCount()));
        String logUrl = mlist.get(position).getWechatLogoUrl();
        if (mlist.get(position).getIsWinLottery() == 1) {
            holder.rl_winlottery.setVisibility(View.VISIBLE);
        }
        if (logUrl == null || logUrl.length() <= 0) {
            mpicasso.load(R.drawable.imagviewloading).into(holder.iv_my_unary_profile_image);
        } else {
            mpicasso.load(mlist.get(position).getWechatLogoUrl()).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                    .resize(Utils.dp2px(mcontext, 250), Utils.dp2px(mcontext, 250))
                    .error(mcontext.getResources().getDrawable(R.drawable.imageview_error)).into(holder.iv_my_unary_profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyUnaryRecordViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_my_unary_nickName, tv_my_unary_id,
                tv_my_unary_TotalBuyCount, tv_my_unary_joinTime, tv_unary_num;
        private ImageView iv_my_unary_profile_image;
        private RelativeLayout rl_winlottery;

        public MyUnaryRecordViewHolder(View itemView) {
            super(itemView);
            tv_my_unary_nickName = (TextView) itemView.findViewById(R.id.tv_my_unary_nickName);
            tv_my_unary_id = (TextView) itemView.findViewById(R.id.tv_my_unary_id);
            tv_unary_num = (TextView) itemView.findViewById(R.id.tv_unary_num);
            tv_my_unary_TotalBuyCount = (TextView) itemView.findViewById(R.id.tv_my_unary_TotalBuyCount);
            tv_my_unary_joinTime = (TextView) itemView.findViewById(R.id.tv_my_unary_joinTime);
            iv_my_unary_profile_image = (ImageView) itemView.findViewById(R.id.iv_my_unary_profile_image);
            rl_winlottery = (RelativeLayout) itemView.findViewById(R.id.rl_winlottery);
        }
    }
}
