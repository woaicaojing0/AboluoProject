package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.ShopCarInfo;

import java.util.ArrayList;
import java.util.List;

import ren.qinc.numberbutton.NumberButton;

/**
 * Created by CJ on 2016/9/27.
 */

public class ShopCarAdapter extends BaseAdapter {
    private List<ShopCarInfo> mlist;
    private Context mcontext;
    //表示用户是否在编辑状态

    public boolean isedit() {
        return isedit;
    }

    public void setIsedit(boolean isedit) {
        this.isedit = isedit;
    }

    public ArrayList<Boolean> getckisselected() {
        return ckisselected;
    }

    public void setckisselected(ArrayList<Boolean> ckisselected) {
        this.ckisselected = ckisselected;
    }

    private boolean isedit;
    //选中状态
    private ArrayList<Boolean> ckisselected;

    public ShopCarAdapter(List<ShopCarInfo> list, Context context) {
        this.mcontext = context;
        this.mlist = list;
        ckisselected = new ArrayList<>();
        initDate();
    }
    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < mlist.size(); i++) {
            ckisselected.add(i, false);
        }
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position).getGoodsName();
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.shopcar_listview_item, null);
            holder.textName = (TextView) convertView.findViewById(R.id.shopcar_txt_buyName);
            holder.ck_buy = (CheckBox) convertView.findViewById(R.id.shopcar_ck_buy);
            holder.ck_by_linelayout = (LinearLayout) convertView.findViewById(R.id.ck_by_linelayout);
            holder.numberButton = (NumberButton) convertView.findViewById(R.id.number_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textName.setText(mlist.get(position).getGoodsName().toString());
        final ViewHolder finalHolder = holder;
        final ViewHolder finalHolder1 = holder;
        holder.ck_buy.setChecked(ckisselected.get(position));
        holder.ck_by_linelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalHolder1.ck_buy.isChecked()) {
                    finalHolder.ck_buy.setChecked(false);
                } else {
                    finalHolder.ck_buy.setChecked(true);
                }
            }
        });
        holder.numberButton.setBuyMax(position + 1).setCurrentNumber(1);
        return convertView;
    }

    public class ViewHolder {
        public TextView textName;
        public CheckBox ck_buy;
        public NumberButton numberButton;
        public LinearLayout ck_by_linelayout;
    }
}
