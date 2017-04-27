package com.aboluo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cj34920 on 2017/4/27.
 */

public class GroupBuyRecordAdapter extends RecyclerView.Adapter<GroupBuyRecordAdapter.GroupBuyRecordViewHolder> {


    @Override
    public GroupBuyRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(GroupBuyRecordViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class GroupBuyRecordViewHolder extends RecyclerView.ViewHolder {

        public GroupBuyRecordViewHolder(View itemView) {
            super(itemView);
        }
    }
}
