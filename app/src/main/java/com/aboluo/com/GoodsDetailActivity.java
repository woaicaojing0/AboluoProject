package com.aboluo.com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.AddShopCarBean;
import com.aboluo.model.GoodsDetailInfo;
import com.aboluo.widget.MyRadioGroup;
import com.aboluo.widget.VerticalScrollView;
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
 * 该页面是商品详情页对应的activitiy，包含2各部分：1 显示详情的页面 2选择商品类型的页面(默认是隐藏的)
 */

public class GoodsDetailActivity extends Activity implements View.OnClickListener {
    //类型页面中的数量增加和减少
    private Button btnDecrease, btnIncrease;
    //数量中间的编辑框
    private EditText etAmount;
    private RollPagerView rollPagerView;  //首部banner元素
    private String[] imgurl = null;    //首部banner中绑定的图片
    private BannerAdapter bannerAdapter;
    //商品详情和评价按钮
    private RelativeLayout goods_pingjia_layout_btn, goods_detail_layout_btn;
    //商品详情和评价按钮下面的横线
    private View id_goods_detail_view, id_goods_pingjia_view;
    //显示详情和评价的webview
    private WebView goods_detail_webview, goods_pingjia_webview;
    //首页最右边显示更多操作、首页最上面的购物车
    private ImageView detail_more, detail_goods;
    //最右边的popwindows
    private PopupWindow goods_popwindow;
    //popwindows中的第一个布局 商品sub布局
    private LinearLayout pop_01, layout_txt_goods_sub;
    //详情返回、商品类型弹出xml中的关闭、商品类型中的图片
    private ImageView goods_detail_text_back, goods_type_pop_close, goods_detail_type_imageview;
    //商品类型、名称、会员价、原价、数量、商品列表的价格和数量,商品副标题,积分,商品头部的商品详情
    private TextView txt_goods_type, txt_goods_name, txt_new_money, txt_old_money, txt_goods_num, goods_detail_type_txtmoney, goods_detail_type_txtnum, txt_goods_sub, goods_detail_jifen,goods_detail_top_txt;
    private int fHeight; //父容器的高度
    private int sHeight;  //商品类型的高度
    //父容器、自容器、商品列表颜色布局、尺寸布局
    private LinearLayout firstView, secondView, all_color, all_standards;
    //颜色和尺寸的单选组
    private MyRadioGroup goodsdetail_type_color, goodsdetail_type_standards;
    private static int goods_id = 0; //商品的ID
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static String URL = null, ImgUrl = null;
    private static String APPToken = null;
    private GoodsDetailInfo goodsDetailInfo;
    private static int popwith; //获取当前屏幕的宽度
    private static boolean isshowtype = false;  //当前商品类型是否显示
    private LinearLayout index_bottom_shopcar, goods_type_addshopcart; //1底部加入购车按钮 商品列表中的加入购物车
    private LinearLayout goods_type_selected, goods_type_ok; // 1 有加入购物车和立即购买按钮。 2 只有确定按钮
    private Boolean hascolor = false, hasstandards = false;
    private SweetAlertDialog pdialog;
    private static String goods_type_imgeurl; //需要放大图片的地址
    private VerticalScrollView contentscrollView; // 需要监听滑动的scrollview
    private Toolbar toolbar; //渐变显示的toolbar
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetail);
        init();
        goods_pingjia_layout_btn.setOnClickListener(this);
        goods_detail_layout_btn.setOnClickListener(this);
        detail_more.setOnClickListener(this);
        detail_goods.setOnClickListener(this);
        goods_detail_text_back.setOnClickListener(this);
        txt_goods_type.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        index_bottom_shopcar.setOnClickListener(this);
        goods_type_ok.setOnClickListener(this);
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
                if (s.toString().equals("")) {
                } else {
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
        goods_detail_type_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsDetailActivity.this, GoodsDetailImageActivity.class);
                intent.putExtra("imgeurl", goods_type_imgeurl);
                String transitionName = "images";
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(GoodsDetailActivity.this, v, transitionName);
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        ViewTreeObserver vto = rollPagerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rollPagerView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = rollPagerView.getHeight();
                contentscrollView.setScrollViewListener(new VerticalScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(VerticalScrollView scrollView, int x, int y, int oldx, int oldy) {
                        //		Log.i("TAG","y--->"+y+"    height-->"+height);
                        if(y<=height){
                            float scale =(float) y /height;
                            float alpha =  (255 * scale);
                            Log.i("TAG","alpha--->"+alpha);
                            goods_detail_top_txt.setTextColor(Color.argb((int)alpha,0,0,0));
                            //只是layout背景透明(仿知乎滑动效果)
                            toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        }
                    }
                });
            }
        });

    }

    /**
     * 初始化底部webivew（获取数据之后）
     *
     * @param detailurl 详情地址
     * @param pingjia   评价地址
     */
    private void initwebview(String detailurl, String pingjia) {
        //详情地址
        WebSettings webviewsetting = goods_detail_webview.getSettings();
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        goods_detail_webview.loadUrl(detailurl);
        goods_detail_webview.setWebViewClient(new WebViewClient());
        //评价地址
        WebSettings webviewsetting2 = goods_pingjia_webview.getSettings();
        webviewsetting2.setJavaScriptEnabled(true);
        webviewsetting2.setUseWideViewPort(true);//关键点
        webviewsetting2.setLoadWithOverviewMode(true);
        goods_pingjia_webview.loadUrl(null);
        goods_pingjia_webview.setWebViewClient(new WebViewClient());
    }

    /**
     * 加载头部banner(获取数据之后)
     *
     * @param imges 图片地址(多个)
     */
    private void initrollPagerView(String[] imges) {
        for (int i = 0; i < imges.length; i++) {
            imges[i] = ImgUrl + imges[i].toString();
        }
        //窗口的宽度
        int screenWidth = ScreenUtils.getScreenWidth(this);
        rollPagerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth));
        rollPagerView.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
        bannerAdapter = new BannerAdapter(this, imges, rollPagerView);
        rollPagerView.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
        rollPagerView.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
        rollPagerView.setFocusable(false);
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(GoodsDetailActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
        goods_type_imgeurl = imges[0];
        Picasso.with(GoodsDetailActivity.this).load(imges[0])
                .placeholder(getResources().getDrawable(R.drawable.imagviewloading))
                .error(getResources().getDrawable(R.drawable.imageview_error))
                .into(goods_detail_type_imageview);
    }

    /**
     * 初始化
     */
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
        goods_detail_text_back = (ImageView) findViewById(R.id.goods_detail_text_back);
        goods_detail_type_imageview = (ImageView) findViewById(R.id.goods_detail_type_imageview);
        txt_goods_type = (TextView) findViewById(R.id.txt_goods_type);
        txt_goods_name = (TextView) findViewById(R.id.txt_goods_name);
        txt_new_money = (TextView) findViewById(R.id.txt_new_money);
        txt_old_money = (TextView) findViewById(R.id.txt_old_money);
        txt_goods_num = (TextView) findViewById(R.id.txt_goods_num);
        txt_goods_sub = (TextView) findViewById(R.id.txt_goods_sub);
        goods_detail_jifen = (TextView) findViewById(R.id.goods_detail_jifen);
        goods_detail_type_txtmoney = (TextView) findViewById(R.id.goods_detail_type_txtmoney);
        goods_detail_type_txtnum = (TextView) findViewById(R.id.goods_detail_type_txtnum);
        goods_detail_top_txt = (TextView) findViewById(R.id.goods_detail_top_txt);
        secondView = (LinearLayout) findViewById(R.id.second_view);
        firstView = (LinearLayout) findViewById(R.id.first_view);
        goodsdetail_type_color = (MyRadioGroup) findViewById(R.id.goodsdetail_type_color);
        goodsdetail_type_standards = (MyRadioGroup) findViewById(R.id.goodsdetail_type_standards);
        all_color = (LinearLayout) findViewById(R.id.all_color);
        all_standards = (LinearLayout) findViewById(R.id.all_standards);
        goods_type_pop_close = (ImageView) findViewById(R.id.goods_type_pop_close);
        goods_type_pop_close.setOnClickListener(this);
        firstView.setOnClickListener(this);
        index_bottom_shopcar = (LinearLayout) findViewById(R.id.index_bottom_shopcar);
        goods_type_addshopcart = (LinearLayout) findViewById(R.id.goods_type_addshopcart);
        goods_type_selected = (LinearLayout) findViewById(R.id.goods_type_selected);
        goods_type_ok = (LinearLayout) findViewById(R.id.goods_type_ok);
        layout_txt_goods_sub = (LinearLayout) findViewById(R.id.layout_txt_goods_sub);
        contentscrollView = (VerticalScrollView) findViewById(R.id.contentscrollView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.setTitleText("请稍等");
        Intent intent = getIntent();
        goods_id = intent.getIntExtra("goods_id", 0);
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        ImgUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");

    }

    /**
     * 获取商品详情的数据，在这个方法里加载initrollPagerView、initwebview
     */
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
                txt_new_money.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getHyPrice()));
                txt_old_money.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsPrice()));
                txt_old_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                txt_goods_num.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsQuantity()));
                goods_detail_type_txtmoney.setText("会员价：￥" + String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getHyPrice()) + "元");
                goods_detail_type_txtnum.setText("库存：" + String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getGoodsQuantity()) + "件");
                if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsSub() == null) {
                    layout_txt_goods_sub.setVisibility(View.GONE);
                } else {
                    txt_goods_sub.setText(goodsDetailInfo.getResult().getGoodsInfo().getGoodsSub().toString());
                }
                //有些商品没有图片，但肯定有logo图片
                if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsPicture() == null) {
                    imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo().split(";");
                } else {
                    imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsPicture().split(";");
                }
                initrollPagerView(imgurl);
                List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean> listcolor = goodsDetailInfo.getResult().getGoodsInfo().getGoodsColor();
                List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> listStandard = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
                String imageurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo();
                String[] imageurls = imageurl.split(";");
                Log.i("woaicaojinggoodstype", imageurls[0]);

                if (listcolor == null) {
                    all_color.setVisibility(View.GONE);
                } else {
                    if (listcolor.size() == 0) {
                        all_color.setVisibility(View.GONE);
                    } else {
                        hascolor = true;
                        CreateColor(listcolor);
                    }
                }
                if (listStandard == null) {
                    all_standards.setVisibility(View.GONE);
                } else {
                    if (listStandard.size() == 0) {
                        all_standards.setVisibility(View.GONE);
                    } else {
                        hasstandards = true;
                        CreateStandards(listStandard);
                    }
                }
                String detailurl = CommonUtils.GetValueByKey(GoodsDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
                Log.i("woaicaojing", detailurl);
                initwebview(detailurl, null);

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

    /**
     * 创建color radiobutton
     *
     * @param listcolor List<GoodsColorBean></>
     */
    private void CreateColor(final List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorBean> listcolor) {

        int num = listcolor.size() / 5;
        int yushu = listcolor.size() % 5;
        if (num == 0) {
            LinearLayout linearLayout = new LinearLayout(GoodsDetailActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.dip2px(GoodsDetailActivity.this, 54)));
            for (int i2 = 0; i2 < listcolor.size(); i2++) {
                RadioButton button = new RadioButton(GoodsDetailActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 50));
                layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
                button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                Bitmap a = null;
                button.setButtonDrawable(new BitmapDrawable(a));
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
                        goods_type_imgeurl = ImgUrl + listcolor.get(finalI).getColorImg();
                        Log.i("woaicaojing", goods_type_imgeurl);
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

    /**
     * 创建尺寸 radiobutton
     *
     * @param liststandards List<GoodsStandardsBean></>
     */
    private void CreateStandards(List<GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean> liststandards) {

        int num = liststandards.size() / 5;
        int yushu = liststandards.size() % 5;
        if (num == 0) {
            LinearLayout linearLayout = new LinearLayout(GoodsDetailActivity.this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.dip2px(GoodsDetailActivity.this, 54)));
            for (int i2 = 0; i2 < liststandards.size(); i2++) {
                RadioButton button = new RadioButton(GoodsDetailActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 50));
                layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
                button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                Bitmap a = null;
                button.setButtonDrawable(new BitmapDrawable(a));
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
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(GoodsDetailActivity.this, 54)));
        for (int i2 = 0; i2 < num; i2++) {
            RadioButton button = new RadioButton(GoodsDetailActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 50));
            layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            Bitmap a = null;
            button.setButtonDrawable(new BitmapDrawable(a));
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
                    goods_type_imgeurl = ImgUrl + listcolor.get(finalI).getColorImg();
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
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(GoodsDetailActivity.this, 54)));
        for (int i2 = 0; i2 < num; i2++) {
            RadioButton button = new RadioButton(GoodsDetailActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(popwith / 5, CommonUtils.dip2px(GoodsDetailActivity.this, 50));
            layoutParams.setMargins(CommonUtils.dip2px(GoodsDetailActivity.this, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            Bitmap a = null;
            button.setButtonDrawable(new BitmapDrawable(a));
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
            case R.id.goods_detail_layout_btn: //商品详情按钮
                id_goods_detail_view.setVisibility(View.VISIBLE);
                id_goods_pingjia_view.setVisibility(View.GONE);
                goods_detail_webview.setVisibility(View.VISIBLE);
                goods_pingjia_webview.setVisibility(View.GONE);
                break;
            case R.id.goods_pingjia_layout_btn: //商品评价按钮
                id_goods_detail_view.setVisibility(View.GONE);
                id_goods_pingjia_view.setVisibility(View.VISIBLE);
                goods_detail_webview.setVisibility(View.GONE);
                goods_pingjia_webview.setVisibility(View.VISIBLE);
                break;
            case R.id.detail_goods: //首部购物车
                break;
            case R.id.detail_more: //首部更多
                showinfopopupwindow();
                break;
            case R.id.goods_detail_text_back: //返回
                finish();
                break;
            case R.id.txt_goods_type: //选择商品类型
                goods_type_ok.setVisibility(View.GONE);
                goods_type_selected.setVisibility(View.VISIBLE);
                initShowAnim();
                isshowtype = true;
                break;
            case R.id.goods_type_pop_close:  //关闭商品类型
                initHiddenAnim();
                setChooseType();
                break;
            case R.id.first_view: //点击外部view
                initHiddenAnim();
                setChooseType();
                break;
            case R.id.btnDecrease: //数量减少按钮
                if (Integer.valueOf(etAmount.getText().toString()) <= 1) {
                } else {
                    etAmount.setText(String.valueOf(Integer.valueOf(etAmount.getText().toString()) - 1));

                }
                break;
            case R.id.btnIncrease: //数量增加按钮
                if (Integer.valueOf(etAmount.getText().toString()) < goodsDetailInfo.getResult().getGoodsInfo().getBuyQuantity()) {
                } else {
                    etAmount.setText(String.valueOf(Integer.valueOf(etAmount.getText().toString()) + 1));

                }
                break;
            case R.id.goods_type_ok:

                final RadioButton radioButton = (RadioButton) findViewById(goodsdetail_type_color.getCheckedRadioButtonId());
                final RadioButton radioButton2 = (RadioButton) findViewById(goodsdetail_type_standards.getCheckedRadioButtonId());
//                if (hasstandards) {
//                    if (radioButton2 == null) {
//                        Toast.makeText(GoodsDetailActivity.this, "请选择商品尺寸", Toast.LENGTH_SHORT).show();
//                    } else {
//                    }
//                }
//                if (hascolor) {
//                    if (radioButton == null) {
//                        Toast.makeText(GoodsDetailActivity.this, "请选择商品颜色", Toast.LENGTH_SHORT).show();
//                    }
//                    else{}
//                }
                boolean isok = false;
                if (radioButton == null && radioButton2 == null) {
                    if (hasstandards) {
                        if (hascolor) {
                            Toast.makeText(GoodsDetailActivity.this, "请选择商品尺寸和颜色", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GoodsDetailActivity.this, "请选择商品尺寸", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (hascolor) {
                            Toast.makeText(GoodsDetailActivity.this, "请选择商品颜色", Toast.LENGTH_SHORT).show();
                        } else {
                            isok = true;
                        }
                    }
                } else if (radioButton == null) {
                    if (hascolor) {
                        Toast.makeText(GoodsDetailActivity.this, "请选择商品颜色", Toast.LENGTH_SHORT).show();
                    } else {
                        isok = true;
                    }
                } else if (radioButton2 == null) {
                    if (hasstandards) {
                        Toast.makeText(GoodsDetailActivity.this, "请选择商品尺寸", Toast.LENGTH_SHORT).show();
                    } else {
                        isok = true;
                    }
                } else {
                    isok = true;
                }
                if (isok) {
                    pdialog.show();
                    StringRequest addrequestshopcar = new StringRequest(Request.Method.POST, URL + "/api/GoodsShoppingCart/ReceiveAddOrUpdateGoodsShoppingCart", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pdialog.dismiss();
                            response = response.replace("\\", "");
                            response = response.substring(1, response.length() - 1);
                            AddShopCarBean bean = new AddShopCarBean();
                            Gson gson = new Gson();
                            bean = gson.fromJson(response, AddShopCarBean.class);
                            Log.i("woaicaojingAddshopcar", response);
                            Toast.makeText(GoodsDetailActivity.this, bean.getMessage(), Toast.LENGTH_LONG).show();
                            initHiddenAnim();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pdialog.dismiss();
                            Toast.makeText(GoodsDetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("woaicaojingAddshopcar", error.toString());
                            Toast.makeText(GoodsDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("Id", "0");
                            map.put("goodsId", String.valueOf(goods_id));
                            if (radioButton != null) {
                                map.put("goodsColor", radioButton.getText().toString());
                            } else {
                                map.put("goodsColor", "无");
                            }
                            if (radioButton2 != null) {
                                map.put("goodsStandard", radioButton2.getText().toString());
                            } else {
                                map.put("goodsStandard", "无");
                            }
                            map.put("goodsCount", etAmount.getText().toString());
                            map.put("memberId", "6");
                            map.put("shopId", "1");
                            map.put("APPToken", APPToken);
                            return map;

                        }
                    };
                    requestQueue.add(addrequestshopcar);
                } else {
                }
                break;
            case R.id.goods_type_addshopcart:
                break;
            case R.id.index_bottom_shopcar:
                goods_type_ok.setVisibility(View.VISIBLE);
                goods_type_selected.setVisibility(View.GONE);
                initShowAnim();


        }
    }

    /**
     * 设置选择的类型
     */
    private void setChooseType() {
        isshowtype = false;
        RadioButton radioButton = (RadioButton) findViewById(goodsdetail_type_color.getCheckedRadioButtonId());
        if (radioButton == null) {
        } else {
            Log.i("woaicaojing", radioButton.getText().toString());
            txt_goods_type.setText(radioButton.getText().toString());
        }
        RadioButton radioButton2 = (RadioButton) findViewById(goodsdetail_type_standards.getCheckedRadioButtonId());
        if (radioButton2 == null) {
        } else {
            Log.i("woaicaojing", radioButton2.getText().toString());
            txt_goods_type.setText(radioButton2.getText().toString());
        }
        if (radioButton != null && radioButton2 != null) {
            txt_goods_type.setText(radioButton.getText().toString() + "  " + radioButton2.getText().toString());
        }

    }

    /**
     * 显示右上角的popwindows
     */
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

//    private void showgoodstypepopupwindow() {
//        View view = LayoutInflater.from(this).inflate(R.layout.goods_type_popupwindow, null);
//        view.measure(0, 0);
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int height = metric.heightPixels;     // 屏幕宽度（像素）
//        int lastheight = (int) Math.ceil((height / 5) * 3);
//        goods_type_popupwindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, lastheight, true);
//        goods_type_popupwindow.setContentView(view);
//        goods_type_popupwindow.setAnimationStyle(R.style.goods_type_anim);
//        goods_type_popupwindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        goods_type_popupwindow.setOutsideTouchable(true);
//        Farther_view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.activity_goodsdetail, null);
//        goods_type_popupwindow.showAtLocation(Farther_view, Gravity.BOTTOM, 0, 0);
//        goods_type_pop_close = (ImageView) view.findViewById(R.id.goods_type_pop_close);
//        goods_type_pop_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goods_type_popupwindow.dismiss();
//            }
//        });
//
//    }

    //firstView是主视图,secondView是popopWindow 显示动画
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

    //隐藏动画
    private void initHiddenAnim() {
        isshowtype = false;
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
