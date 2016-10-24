package com.aboluo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.GoodsDetailActivity;
import com.aboluo.com.R;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
import com.aboluo.widget.AmountView;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2016/9/27.
 */

public class ShopCarAdapter extends BaseAdapter {
    public void setMlist(List<GoodsShoppingCartListBean> mlist) {
        this.mlist = mlist;
    }

    private List<GoodsShoppingCartListBean> mlist;
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

    public ShopCarAdapter(List<GoodsShoppingCartListBean> list, Context context, Callback callback) {
        this.mcontext = context;
        this.mlist = list;
        this.mCallback = callback;
        ckisselected = new ArrayList<>();
        initDate();
    }
private String ImgeURL = null;
    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < mlist.size(); i++) {
            ckisselected.add(i, false);
        }
        states = 8;
        ImgeURL = CommonUtils.GetValueByKey(mcontext,"ImgUrl");
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
            holder.shopcar_standards = (TextView) convertView.findViewById(R.id.shopcar_standards);
            holder.shopcar_old_price = (TextView) convertView.findViewById(R.id.shopcar_old_price);
            holder.shopcar_image = (ImageView) convertView.findViewById(R.id.shopcar_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.etAmount.setText(String.valueOf(mlist.get(position).getGoodsCount()));
        holder.maxnum.setText("100");
        holder.textName.setText(mlist.get(position).getGoodsName().toString());
        holder.moeny.setText(String.valueOf(mlist.get(position).getHyPrice()));
        String guige = null;
        if (mlist.get(position).getGoodsStandard() != null) {
            guige = "尺寸：" + mlist.get(position).getGoodsStandard();
        }
        if (mlist.get(position).getGoodsColor() != null) {
            guige = guige+" "+"颜色：" + mlist.get(position).getGoodsColor();
        }
        if (guige == null) {
            holder.shopcar_standards.setText("无");
        } else {
            holder.shopcar_standards.setText(guige);
        }
        //   holder.old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.shopcar_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.shopcar_old_price.setText("￥"+String.valueOf(mlist.get(position).getGoodsPrice()));
       String [] imges= mlist.get(position).getGoodsLogo().toString().split(";");
        if(ImgeURL == null)
        {ImgeURL = CommonUtils.GetValueByKey(mcontext,"ImgeURL");}else {}
        Log.i("woaicaojingshopimg",ImgeURL+imges[0]);
        Picasso.with(mcontext).load(ImgeURL+imges[0]).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                .error(mcontext.getResources().getDrawable(R.drawable.imageview_error))
                .into(holder.shopcar_image);
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
        holder.shopcar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int goods_id =  mlist.get(position).getGoodsId();
                Intent intent = new Intent(mcontext,GoodsDetailActivity.class);
                intent.putExtra("goods_id", goods_id);
                mcontext.startActivity(intent);
            }
        });
        return convertView;
    }


    public class ViewHolder {
        public TextView textName;
        public CheckBox ck_buy;
        public AmountView numberButton;
        public LinearLayout ck_by_linelayout;
        public TextView moeny, maxnum, shopcar_standards,shopcar_old_price;
        public EditText etAmount;
        private Button btnDecrease;
        private Button btnIncrease;
        private ImageView shopcar_image;
    }

    public interface Callback {
        public void click(View v, int Amount, int postion);
    }
}
