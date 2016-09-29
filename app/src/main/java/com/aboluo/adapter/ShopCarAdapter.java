package com.aboluo.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.com.R;
import com.aboluo.model.ShopCarInfo;
import com.aboluo.widget.AmountView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2016/9/27.
 */

public class ShopCarAdapter extends BaseAdapter {
    private List<ShopCarInfo> mlist;
    private Callback mCallback;
    private Context mcontext;
    //表示用户是否在编辑状态

    public int getStates() {
        return states;
    }

    public void setstates(int isedit) {
        this.states = isedit;
    }

    public ArrayList<Boolean> getckisselected() {
        return ckisselected;
    }

    public void setckisselected(ArrayList<Boolean> ckisselected) {
        this.ckisselected = ckisselected;
    }

    //8是view.gone,0是view.visiable
    private int states;
    //选中状态
    private ArrayList<Boolean> ckisselected;

    public ShopCarAdapter(List<ShopCarInfo> list, Context context, Callback callback) {
        this.mcontext = context;
        this.mlist = list;
        this.mCallback = callback;
        ckisselected = new ArrayList<>();
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < mlist.size(); i++) {
            ckisselected.add(i, false);
        }
        states = 8;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.shopcar_listview_item, null);
            holder.textName = (TextView) convertView.findViewById(R.id.shopcar_txt_buyName);
            holder.ck_buy = (CheckBox) convertView.findViewById(R.id.shopcar_ck_buy);
            holder.ck_by_linelayout = (LinearLayout) convertView.findViewById(R.id.ck_by_linelayout);
            holder.moeny = (TextView) convertView.findViewById(R.id.shopcar_txt_itemmoney);
            holder.etAmount = (EditText) convertView.findViewById(R.id.etAmount);
            holder.btnDecrease = (Button) convertView.findViewById(R.id.btnDecrease);
            holder.btnIncrease = (Button) convertView.findViewById(R.id.btnIncrease);
            holder.maxnum = (TextView) convertView.findViewById(R.id.maxnum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.etAmount.setText(mlist.get(position).getNum() + "");
        holder.maxnum.setText("10");
        holder.textName.setText(mlist.get(position).getGoodsName().toString());
        holder.moeny.setText(String.valueOf(mlist.get(position).getMoney()));
        final ViewHolder finalHolder = holder;
        holder.ck_buy.setChecked(ckisselected.get(position));
        holder.ck_by_linelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalHolder.ck_buy.isChecked()) {
                    finalHolder.ck_buy.setChecked(false);
                    ckisselected.set(position, false);
                } else {
                    finalHolder.ck_buy.setChecked(true);
                    ckisselected.set(position, true);
                }
                Toast.makeText(mcontext, position + "", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });
        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(finalHolder.etAmount.getText().toString()) < 1) {
                } else {
                    finalHolder.etAmount.setText(String.valueOf(Integer.valueOf(finalHolder.etAmount.getText().toString()) - 1));

                }
                mCallback.click(v, Integer.valueOf(finalHolder.etAmount.getText().toString()), position);
                notifyDataSetChanged();
            }
        });
        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(finalHolder.etAmount.getText().toString()) >= Integer.valueOf(finalHolder.maxnum.getText().toString())) {
                } else {
                    finalHolder.etAmount.setText(String.valueOf(Integer.valueOf(finalHolder.etAmount.getText().toString()) + 1));
                }
                mCallback.click(v, Integer.valueOf(finalHolder.etAmount.getText().toString()), position);
                notifyDataSetChanged();
            }
        });
        holder.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //先去服务器更新，如更新成功才会执行一下过程
                int amount = Integer.valueOf(s.toString());
                if (s.toString().isEmpty())
                    return;
                if (amount > Integer.valueOf(finalHolder.maxnum.getText().toString())) {
                    finalHolder.etAmount.setText(finalHolder.maxnum.getText().toString());
                    return;
                }
            }

        });
        return convertView;
    }


    public class ViewHolder {
        public TextView textName;
        public CheckBox ck_buy;
        public AmountView numberButton;
        public LinearLayout ck_by_linelayout;
        public TextView moeny, maxnum;
        public EditText etAmount;
        private Button btnDecrease;
        private Button btnIncrease;
    }

    public interface Callback {
        public void click(View v, int Amount, int postion);
    }
}
