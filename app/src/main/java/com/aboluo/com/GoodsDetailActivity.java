package com.aboluo.com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.GoodsDetailInfo;
import com.aboluo.widget.MyRadioGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/10/1.
 */

public class GoodsDetailActivity extends Activity implements View.OnClickListener {

    private Button btnDecrease, btnIncrease;
    private EditText etAmount;
    private RollPagerView rollPagerView;
    private String[] imgurl = {"http://img4.imgtn.bdimg.com/it/u=2408370625,380818695&fm=21&gp=0.jpg", "" +
            "http://pic24.nipic.com/20121025/10444819_041559015351_2.jpg"};
    private BannerAdapter bannerAdapter;
    private RelativeLayout goods_pingjia_layout_btn, goods_detail_layout_btn;
    private View id_goods_detail_view, id_goods_pingjia_view;
    private WebView goods_detail_webview, goods_pingjia_webview;
    private ImageView detail_more, detail_goods;
    private PopupWindow goods_popwindow, goods_type_popupwindow;
    private LinearLayout pop_01;
    private ImageView my_info_text_back, goods_type_pop_close, goods_detail_type_imageview;
    private TextView txt_goods_type, txt_goods_name, txt_new_money, txt_old_money, txt_goods_num, goods_detail_type_txtmoney, goods_detail_type_txtnum;
    private View Farther_view;
    private int fHeight;
    private int sHeight;
    private LinearLayout firstView, secondView, all_color, all_standards;
    private MyRadioGroup goodsdetail_type_color, goodsdetail_type_standards;
    private static int goods_id = 0; //商品的ID
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static String URL = null, ImgUrl = null;
    private static String APPToken = null;
    private GoodsDetailInfo goodsDetailInfo;
    private static int popwith;
    private static boolean isshowtype = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetail);
        init();
        rollPagerView.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
        bannerAdapter = new BannerAdapter(this, imgurl, rollPagerView);
        rollPagerView.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
        rollPagerView.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
        rollPagerView.setFocusable(false);
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(GoodsDetailActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
        goods_pingjia_layout_btn.setOnClickListener(this);
        goods_detail_layout_btn.setOnClickListener(this);
        detail_more.setOnClickListener(this);
        detail_goods.setOnClickListener(this);
        my_info_text_back.setOnClickListener(this);
        txt_goods_type.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        WebSettings webviewsetting = goods_detail_webview.getSettings();
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        goods_detail_webview.loadUrl("http://www.51cto.com");
        goods_detail_webview.setWebViewClient(new WebViewClient());
        goods_pingjia_webview.loadUrl("http://www.baidu.com");
        goods_pingjia_webview.setWebViewClient(new WebViewClient());
        WebSettings webviewsetting2 = goods_pingjia_webview.getSettings();
        webviewsetting2.setJavaScriptEnabled(true);
        webviewsetting2.setUseWideViewPort(true);//关键点
        firstView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                fHeight = firstView.getHeight();
                firstView.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this);
            }
        });
        secondView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                sHeight = secondView.getHeight();
                popwith = secondView.getWidth();
                popwith = popwith - CommonUtils.dip2px(GoodsDetailActivity.this, 60);
                secondView.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this);
            }
        });
        getgoods_detail();
        requestQueue.add(stringRequest);
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(""))
                {}else {
                    if (Integer.valueOf(s.toString()) > goodsDetailInfo.getResult().getGoodsInfo().getGoodsQuantity()) {
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GoodsDetailActivity.this, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setCanceledOnTouchOutside(true);
                        sweetAlertDialog.setTitleText("购买数量超过当前库存");
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                etAmount.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsQuantity()));
                            }
                        });
                        sweetAlertDialog.show();
                    } else {
                    }
                }
            }
        });
    }

    private void init() {
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);
        etAmount = (EditText) findViewById(R.id.etAmount);
        rollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager);
        goods_pingjia_layout_btn = (RelativeLayout) findViewById(R.id.goods_pingjia_layout_btn);
        goods_detail_layout_btn = (RelativeLayout) findViewById(R.id.goods_detail_layout_btn);
        id_goods_detail_view = (View) findViewById(R.id.id_goods_detail_view);
        id_goods_pingjia_view = (View) findViewById(R.id.id_goods_pingjia_view);
        goods_detail_webview = (WebView) findViewById(R.id.goods_detail_webview);
        goods_pingjia_webview = (WebView) findViewById(R.id.goods_pingjia_webview);
        detail_more = (ImageView) findViewById(R.id.detail_more);
        detail_goods = (ImageView) findViewById(R.id.detail_goods);
        my_info_text_back = (ImageView) findViewById(R.id.my_info_text_back);
        goods_detail_type_imageview = (ImageView) findViewById(R.id.goods_detail_type_imageview);
        txt_goods_type = (TextView) findViewById(R.id.txt_goods_type);
        txt_goods_name = (TextView) findViewById(R.id.txt_goods_name);
        txt_new_money = (TextView) findViewById(R.id.txt_new_money);
        txt_old_money = (TextView) findViewById(R.id.txt_old_money);
        txt_goods_num = (TextView) findViewById(R.id.txt_goods_num);
        goods_detail_type_txtmoney = (TextView) findViewById(R.id.goods_detail_type_txtmoney);
        goods_detail_type_txtnum = (TextView) findViewById(R.id.goods_detail_type_txtnum);
        secondView = (LinearLayout) findViewById(R.id.second_view);
        firstView = (LinearLayout) findViewById(R.id.first_view);
        goodsdetail_type_color = (MyRadioGroup) findViewById(R.id.goodsdetail_type_color);
        goodsdetail_type_standards = (MyRadioGroup) findViewById(R.id.goodsdetail_type_standards);
        all_color = (LinearLayout) findViewById(R.id.all_color);
        all_standards = (LinearLayout) findViewById(R.id.all_standards);
        goods_type_pop_close = (ImageView) findViewById(R.id.goods_type_pop_close);
        goods_type_pop_close.setOnClickListener(this);
        firstView.setOnClickListener(this);
        Intent intent = getIntent();
        goods_id = intent.getIntExtra("goods_id", 0);
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        ImgUrl = CommonUtils.GetValueByKey(this, "ImgUrl");

        APPToken = CommonUtils.GetValueByKey(this, "APPToken");

    }

    private void getgoods_detail() {
        stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Goods/ReceiveGoodsById", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicoajing", response);
                Gson gson = new Gson();
                goodsDetailInfo = gson.fromJson(response, GoodsDetailInfo.class);
                txt_goods_name.setText(goodsDetailInfo.getResult().getGoodsInfo().getGoodsName());
                txt_new_money.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsPrice()));
                txt_old_money.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getHyPrice()));
                txt_old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                txt_goods_num.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsQuantity()));
                goods_detail_type_txtmoney.setText("会员价：￥" + String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getHyPrice()) + "元");
                goods_detail_type_txtnum.setText("库存：" + String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsQuantity()) + "件");

                List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean> listcolor = goodsDetailInfo.getResult().getGoodsInfo().getGoodsColor();
                List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> listStandard = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
                if (listcolor == null || listcolor.size() == 0) {
                    all_color.setVisibility(View.GONE);
                } else {
                    Picasso.with(GoodsDetailActivity.this).load(ImgUrl + listcolor.get(0).getColorImg())
                            .placeholder(getResources().getDrawable(R.drawable.imagviewloading))
                            .error(getResources().getDrawable(R.drawable.imageview_error))
                            .into(goods_detail_type_imageview);
                    CreateColor(listcolor);
                }
                if (listStandard == null || listcolor.size() == 0) {
                    all_standards.setVisibility(View.GONE);
                } else {
                    CreateStandards(listStandard);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicoajing", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("GoodsId", String.valueOf(goods_id));
                map.put("APPToken", APPToken);
                return map;
            }
        };
    }

    private void CreateColor(final List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean> listcolor) {

        int num = listcolor.size() / 5;
        int yushu = listcolor.size() % 5;
        if (num == 0) {
            LinearLayout linearLayout = new LinearLayout(GoodsDetailActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.dip2px(GoodsDetailActivity.this, 40)));
            for (int i2 = 0; i2 < listcolor.size(); i2++) {
                RadioButton button = new RadioButton(GoodsDetailActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 30));
                layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
                button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                button.setButtonDrawable(null);
                button.setGravity(Gravity.CENTER);
                ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                button.setTextColor(csl);
                button.setLayoutParams(layoutParams);
                button.setText(listcolor.get(i2).getColor());
                button.setId(i2);
                final int finalI = i2;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("woaicaojing + picURl", ImgUrl + listcolor.get(finalI).getColorImg());
                        Picasso.with(GoodsDetailActivity.this).load(ImgUrl + listcolor.get(finalI).getColorImg()).placeholder(getResources().getDrawable(R.drawable.imagviewloading)).into(goods_detail_type_imageview);
                    }
                });
                linearLayout.addView(button);
            }
            goodsdetail_type_color.addView(linearLayout);
        } else {
            if (yushu == 0) {
                for (int i = 0; i < num; i++) {
                    CreateRadioButtonToColor(i, 5, listcolor);
                }

            } else {
                num = num + 1;
                for (int i = 0; i < num; i++) {
                    if (i == (num - 1)) {
                        CreateRadioButtonToColor(i, yushu, listcolor);

                    } else {
                        CreateRadioButtonToColor(i, 5, listcolor);
                    }

                }
            }

        }
    }

    private void CreateStandards(List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> liststandards) {

        int num = liststandards.size() / 5;
        int yushu = liststandards.size() % 5;
        if (num == 0) {
            LinearLayout linearLayout = new LinearLayout(GoodsDetailActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.dip2px(GoodsDetailActivity.this, 40)));
            for (int i2 = 0; i2 < liststandards.size(); i2++) {
                RadioButton button = new RadioButton(GoodsDetailActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 30));
                layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
                button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                button.setButtonDrawable(null);
                button.setGravity(Gravity.CENTER);
                ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                button.setTextColor(csl);
                button.setLayoutParams(layoutParams);
                button.setText(liststandards.get(i2).getStandard());
                button.setId(100 + i2);
                final int finalI = i2;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                linearLayout.addView(button);
            }
            goodsdetail_type_standards.addView(linearLayout);
        } else {
            if (yushu == 0) {
                for (int i = 0; i < num; i++) {
                    CreateRadioButtonToStandards(i, 5, liststandards);
                }

            } else {
                num = num + 1;
                for (int i = 0; i < num; i++) {
                    if (i == (num - 1)) {
                        CreateRadioButtonToStandards(i, yushu, liststandards);

                    } else {
                        CreateRadioButtonToStandards(i, 5, liststandards);
                    }

                }
            }

        }
    }

    private void CreateRadioButtonToColor(int i, int num, final List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean> listcolor) {
        LinearLayout linearLayout = new LinearLayout(GoodsDetailActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(GoodsDetailActivity.this, 40)));
        for (int i2 = 0; i2 < num; i2++) {
            RadioButton button = new RadioButton(GoodsDetailActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 30));
            layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            button.setButtonDrawable(null);
            ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
            button.setTextColor(csl);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(layoutParams);
            button.setId((5 * i) + i2);
            int i22 = (5 * i) + i2;
            button.setText(listcolor.get((5 * i) + i2).getColor());
            final int finalI = i22;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("woaicaojing + picURl", ImgUrl + listcolor.get(finalI).getColorImg());
                    Toast.makeText(GoodsDetailActivity.this, finalI + "", Toast.LENGTH_SHORT).show();
                    Picasso.with(GoodsDetailActivity.this).load(ImgUrl + listcolor.get(finalI).getColorImg()).placeholder(getResources().getDrawable(R.drawable.imagviewloading)).into(goods_detail_type_imageview);
                }
            });
            linearLayout.addView(button);
        }
        goodsdetail_type_color.addView(linearLayout);
    }

    private void CreateRadioButtonToStandards(int i, int num, final List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> liststandards) {
        LinearLayout linearLayout = new LinearLayout(GoodsDetailActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(GoodsDetailActivity.this, 40)));
        for (int i2 = 0; i2 < num; i2++) {
            RadioButton button = new RadioButton(GoodsDetailActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 30));
            layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            button.setButtonDrawable(null);
            ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
            button.setTextColor(csl);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(layoutParams);
            button.setId(100 + (5 * i) + i2);
            int i22 = (5 * i) + i2;
            button.setText(liststandards.get((5 * i) + i2).getStandard());
            final int finalI = i22;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GoodsDetailActivity.this, finalI + "", Toast.LENGTH_SHORT).show();
                    goods_detail_type_txtmoney.setText("会员价：￥" + String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getHyPrice()) + "元");
                }
            });
            linearLayout.addView(button);
        }
        goodsdetail_type_standards.addView(linearLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_detail_layout_btn:
                id_goods_detail_view.setVisibility(View.VISIBLE);
                id_goods_pingjia_view.setVisibility(View.GONE);
                goods_detail_webview.setVisibility(View.VISIBLE);
                goods_pingjia_webview.setVisibility(View.GONE);
                break;
            case R.id.goods_pingjia_layout_btn:
                id_goods_detail_view.setVisibility(View.GONE);
                id_goods_pingjia_view.setVisibility(View.VISIBLE);
                goods_detail_webview.setVisibility(View.GONE);
                goods_pingjia_webview.setVisibility(View.VISIBLE);
                break;
            case R.id.detail_goods:
                break;
            case R.id.detail_more:
                showinfopopupwindow();
                break;
            case R.id.my_info_text_back:
                finish();
                break;
            case R.id.txt_goods_type:
                initShowAnim();
                isshowtype = true;
                break;
            case R.id.goods_type_pop_close:
                initHiddenAnim();
                setChooseType();
                break;
            case R.id.first_view:
                initHiddenAnim();
                setChooseType();
                break;
            case R.id.btnDecrease:
                if (Integer.valueOf(etAmount.getText().toString()) < 1) {
                } else {
                    etAmount.setText(String.valueOf(Integer.valueOf(etAmount.getText().toString()) - 1));

                }
                break;
            case R.id.btnIncrease:
                if (Integer.valueOf(etAmount.getText().toString()) < goodsDetailInfo.getResult().getGoodsInfo().getBuyQuantity()) {
                } else {
                    etAmount.setText(String.valueOf(Integer.valueOf(etAmount.getText().toString())+ 1));

                }
                break;

        }
    }

    private void setChooseType() {
        isshowtype = false;
        RadioButton radioButton = (RadioButton) findViewById(goodsdetail_type_color.getCheckedRadioButtonId());
        if (radioButton == null) {
        } else {
            Log.i("woaicaojing", radioButton.getText().toString());
        }
        RadioButton radioButton2 = (RadioButton) findViewById(goodsdetail_type_standards.getCheckedRadioButtonId());
        if (radioButton2 == null) {
        } else {
            Log.i("woaicaojing", radioButton2.getText().toString());
        }
        if (radioButton != null && radioButton2 != null) {
            txt_goods_type.setText(radioButton.getText().toString() + "  " + radioButton2.getText().toString());
        }

    }

    private void showinfopopupwindow() {
        View detail_pop = LayoutInflater.from(this).inflate(R.layout.goods_popupwindow, null);
        //测量布局的大小
        detail_pop.measure(0, 0);
        goods_popwindow = new PopupWindow(detail_pop, detail_pop.getMeasuredWidth(), detail_pop.getMeasuredHeight(), true);
        goods_popwindow.setContentView(detail_pop);
        pop_01 = (LinearLayout) detail_pop.findViewById(R.id.pop_01);
        pop_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GoodsDetailActivity.this, "1111", Toast.LENGTH_SHORT).show();
                goods_popwindow.dismiss();
            }
        });
        int popwith = goods_popwindow.getWidth();
        int image = detail_more.getWidth();
        goods_popwindow.setAnimationStyle(R.style.AnimationPreview);
        goods_popwindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        goods_popwindow.setOutsideTouchable(true);
        goods_popwindow.showAsDropDown(detail_more, -(popwith - image), 12);

    }

    private void showgoodstypepopupwindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.goods_type_popupwindow, null);
        view.measure(0, 0);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;     // 屏幕宽度（像素）
        int lastheight = (int) Math.ceil((height / 5) * 3);
        goods_type_popupwindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, lastheight, true);
        goods_type_popupwindow.setContentView(view);
        goods_type_popupwindow.setAnimationStyle(R.style.goods_type_anim);
        goods_type_popupwindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        goods_type_popupwindow.setOutsideTouchable(true);
        Farther_view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.activity_goodsdetail, null);
        goods_type_popupwindow.showAtLocation(Farther_view, Gravity.BOTTOM, 0, 0);
        goods_type_pop_close = (ImageView) view.findViewById(R.id.goods_type_pop_close);
        goods_type_pop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goods_type_popupwindow.dismiss();
            }
        });

    }

    //firstView是主视图,secondView是popopWindow
    private void initShowAnim() {
        ObjectAnimator fViewScaleXAnim = ObjectAnimator.ofFloat(firstView, "scaleX", 1.0f, 0.8f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim = ObjectAnimator.ofFloat(firstView, "scaleY", 1.0f, 0.8f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim = ObjectAnimator.ofFloat(firstView, "alpha", 1.0f, 0.5f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(firstView, "translationY", 0, -0.1f * fHeight);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(secondView, "translationY", sHeight, 0);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                secondView.setVisibility(View.VISIBLE);
            }
        });
        AnimatorSet showAnim = new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim, fViewRotationXAnim, fViewResumeAnim, fViewTransYAnim, fViewAlphaAnim, fViewScaleYAnim, sViewTransYAnim);
        showAnim.start();
    }

    private void initHiddenAnim() {
        ObjectAnimator fViewScaleXAnim = ObjectAnimator.ofFloat(firstView, "scaleX", 0.8f, 1.0f);
        fViewScaleXAnim.setDuration(350);
        ObjectAnimator fViewScaleYAnim = ObjectAnimator.ofFloat(firstView, "scaleY", 0.8f, 1.0f);
        fViewScaleYAnim.setDuration(350);
        ObjectAnimator fViewAlphaAnim = ObjectAnimator.ofFloat(firstView, "alpha", 0.5f, 1.0f);
        fViewAlphaAnim.setDuration(350);
        ObjectAnimator fViewRotationXAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 0f, 10f);
        fViewRotationXAnim.setDuration(200);
        ObjectAnimator fViewResumeAnim = ObjectAnimator.ofFloat(firstView, "rotationX", 10f, 0f);
        fViewResumeAnim.setDuration(150);
        fViewResumeAnim.setStartDelay(200);
        ObjectAnimator fViewTransYAnim = ObjectAnimator.ofFloat(firstView, "translationY", -0.1f * fHeight, 0);
        fViewTransYAnim.setDuration(350);
        ObjectAnimator sViewTransYAnim = ObjectAnimator.ofFloat(secondView, "translationY", 0, sHeight);
        sViewTransYAnim.setDuration(350);
        sViewTransYAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                secondView.setVisibility(View.INVISIBLE);
            }
        });
        AnimatorSet showAnim = new AnimatorSet();
        showAnim.playTogether(fViewScaleXAnim, fViewRotationXAnim, fViewResumeAnim, fViewTransYAnim, fViewAlphaAnim, fViewScaleYAnim, sViewTransYAnim);
        showAnim.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isshowtype) {
                setChooseType();
                initHiddenAnim();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
