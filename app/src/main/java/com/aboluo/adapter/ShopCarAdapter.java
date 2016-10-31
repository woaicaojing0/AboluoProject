package com.aboluo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.text.Editable;
import android.text.Layout;
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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.aboluo.com.R.id.btnDecrease;

/**
 * Created by CJ on 2016/9/27.
 */

public class ShopCarAdapter extends BaseAdapter{
    public void setMlist(List<GoodsShoppingCartListBean> mlist) {
        this.mlist = mlist;
    }

    private List<GoodsShoppingCartListBean> mlist;
    private Context mcontext;
    //表示用户是否在编辑状态
private Picasso picasso;
    public int getStates() {
        return states;
    }

    public void setstates(int isedit) {
        this.states = isedit;
    }
private LayoutInflater minfLayoutInflater;

    //8是view.gone,0是view.visiable
    private int states;
    //选中状态
    private View.OnClickListener mbtnDecrease;
    private View.OnClickListener mbtnIncrease;
    private View.OnClickListener mshopcar_image;
    private View.OnClickListener mck_by_linelayout;


    private View.OnFocusChangeListener metAmount;


    private View.OnClickListener mhopcar_standards;
    private textchage mtextchage;
 private ArrayList<Boolean> mckckisselected;
    public void setMbtnDecrease(View.OnClickListener mbtnDecrease) {
        this.mbtnDecrease = mbtnDecrease;
    }

    public void setMbtnIncrease(View.OnClickListener mbtnIncrease) {
        this.mbtnIncrease = mbtnIncrease;
    }
    public void setMshopcar_image(View.OnClickListener mshopcar_image) {
        this.mshopcar_image = mshopcar_image;
    }

    public void setMck_by_linelayout(View.OnClickListener mck_by_linelayout) {
        this.mck_by_linelayout = mck_by_linelayout;
    }


    public void setMtextchage(textchage mtextchage) {
        this.mtextchage = mtextchage;
    }

    public void setMhopcar_standards(View.OnClickListener mhopcar_standards) {
        this.mhopcar_standards = mhopcar_standards;
    }
    public void setMetAmount(View.OnFocusChangeListener metAmount) {
        this.metAmount = metAmount;
    }

    public ShopCarAdapter(List<GoodsShoppingCartListBean> list, Context context,ArrayList<Boolean> ckckisselected) {
        this.mcontext = context;
        this.mlist = list;
        this.mckckisselected = ckckisselected;
        initDate();
        minfLayoutInflater = LayoutInflater.from(mcontext);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(100, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(100, TimeUnit.SECONDS);
//        picasso = new Picasso.Builder(mcontext)
//                .downloader(new OkHttpDownloader(okHttpClient))
//                .build();
    }
private String ImgeURL = null;
    // 初始化isSelected的数据
    private void initDate() {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = minfLayoutInflater.inflate(R.layout.shopcar_listview_item, null);
            holder = new ViewHolder();
            holder.textName = (TextView) convertView.findViewById(R.id.shopcar_txt_buyName);
            holder.ck_buy = (CheckBox) convertView.findViewById(R.id.shopcar_ck_buy);
            holder.ck_by_linelayout = (LinearLayout) convertView.findViewById(R.id.ck_by_linelayout);
            holder.moeny = (TextView) convertView.findViewById(R.id.shopcar_txt_itemmoney);
            holder.etAmount = (EditText) convertView.findViewById(R.id.etAmount);
            holder.btnDecrease = (Button) convertView.findViewById(btnDecrease);
            holder.btnIncrease = (Button) convertView.findViewById(R.id.btnIncrease);
            holder.maxnum = (TextView) convertView.findViewById(R.id.maxnum);
            holder.shopcar_standards = (TextView) convertView.findViewById(R.id.shopcar_standards);
            holder.shopcar_old_price = (TextView) convertView.findViewById(R.id.shopcar_old_price);
            holder.shopcar_image = (ImageView) convertView.findViewById(R.id.shopcar_image);

            holder.ck_by_linelayout.setOnClickListener(mck_by_linelayout);

            holder.btnDecrease.setOnClickListener(mbtnDecrease);

            holder.btnIncrease.setOnClickListener(mbtnIncrease);
            holder.shopcar_standards.setOnClickListener(mhopcar_standards);


            holder.shopcar_image.setOnClickListener(mshopcar_image);
            holder.etAmount.setOnFocusChangeListener(metAmount);
//            holder.etAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                 EditText editText =    (EditText)v;
//                    String result =editText.getText();
//                }
//            });

//            final ViewHolder finalHolder1 = holder;
//            holder.etAmount.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    mtextchage.textchage(position,s.toString());
//                }
//            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String result = String.valueOf(mlist.get(position).getGoodsCount());
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
//        Log.i("woaicaojingshopimg",ImgeURL+imges[0]);
        Picasso.with(mcontext).load(ImgeURL+imges[0]).placeholder(mcontext.getResources().getDrawable(R.drawable.imagviewloading))
                .error(mcontext.getResources().getDrawable(R.drawable.imageview_error))
                .into(holder.shopcar_image);
        final ViewHolder finalHolder = holder;
        holder.ck_buy.setChecked(mckckisselected.get(position));
        holder.ck_by_linelayout.setTag(position);
        holder.btnDecrease.setTag(position);
        holder.btnIncrease.setTag(position);
        holder.etAmount.setTag(position);
        holder.shopcar_image.setTag(position);
        holder.shopcar_standards.setTag(position);
        return convertView;
    }



    public class ViewHolder {
        public TextView textName;
        public CheckBox ck_buy;
        public AmountView numberButton;
        public LinearLayout ck_by_linelayout,linearLayout_standard;
        public TextView moeny, maxnum, shopcar_standards,shopcar_old_price;
        public EditText etAmount;
        private Button btnDecrease;
        private Button btnIncrease;
        private ImageView shopcar_image;
    }
    public  interface  textchage
    {
        void   textchage(int postion,String num);
    }
}
