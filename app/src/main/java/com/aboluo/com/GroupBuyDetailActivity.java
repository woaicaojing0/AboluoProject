package com.aboluo.com;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.BaseModel;
import com.aboluo.model.GoodsDetailInfo;
import com.aboluo.model.GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsColorStandardListBean;
import com.aboluo.model.GoodsDetailInfo.ResultBean.GoodsInfoBean.GoodsStandardsBean;
import com.aboluo.model.GroupBuyBean;
import com.aboluo.model.ShopCarBean.ResultBean.GoodsShoppingCartListBean;
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
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by cj on 2017/4/26.
 */

public class GroupBuyDetailActivity extends Activity implements View.OnClickListener {
    //类型页面中的数量增加和减少
    private Button btnDecrease, btnIncrease;
    //数量中间的编辑框
    private EditText etAmount;
    private RollPagerView rollPagerView;  //首部banner元素
    private String[] imgurl = null;    //首部banner中绑定的图片
    private BannerAdapter bannerAdapter;
    //商品详情和评价按钮
    private RelativeLayout goods_pingjia_layout_btn1, goods_detail_layout_btn1;
    //商品详情和评价按钮下面的横线
    private View id_goods_detail_view, id_goods_pingjia_view;
    //显示详情和评价的webview
    private WebView goods_detail_webview, goods_pingjia_webview;
    //首页最右边显示更多操作、首页最上面的购物车，收藏按钮
    private ImageView detail_more, detail_goods, iv_menu_hot;

    //popwindows中的第一个布局 商品sub布局
    private LinearLayout layout_txt_goods_sub;
    //详情返回、商品类型弹出xml中的关闭、商品类型中的图片
    private ImageView goods_detail_text_back, goods_type_pop_close, goods_detail_type_imageview;
    //商品类型、名称、会员价、原价、数量、商品列表的价格和数量,商品副标题,积分,商品头部的商品详情
    private TextView txt_goods_type, txt_goods_name, txt_new_money, txt_old_money, txt_goods_num,
            goods_detail_type_txtmoney, goods_detail_type_txtnum, txt_goods_sub,
            goods_detail_jifen, goods_detail_top_txt;
    private int fHeight; //父容器的高度
    private int sHeight;  //商品类型的高度
    //父容器、自容器、商品列表颜色布局、尺寸布局
    private LinearLayout firstView, secondView, all_color, all_standards;
    private static int goods_id = 0; //商品的ID
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static String URL = null, ImgUrl = null;
    private static String APPToken = null;
    private GoodsDetailInfo goodsDetailInfo;
    private static int popwith; //获取当前屏幕的宽度
    private static boolean isshowtype = false;  //当前商品类型是否显示
    private LinearLayout index_bottom_shopcar, goods_type_addshopcart, index_bottom_kefu; //1底部加入购车按钮 商品列表中的加入购物车
    private LinearLayout goods_type_selected, goods_type_ok; // 1 有加入购物车和立即购买按钮。 2 只有确定按钮\
    private LinearLayout goodsdetail_btn_buynow, pop_goodsdetail_btn_buynow;
    private Boolean hascolor = false, hasstandards = false;
    private SweetAlertDialog pdialog;
    private static String goods_type_imgeurl; //需要放大图片的地址
    private VerticalScrollView contentscrollView; // 需要监听滑动的scrollview
    private Toolbar toolbar; //渐变显示的toolbar
    private int height;
    private String MemberId;
    private ArrayList<GoodsShoppingCartListBean> goodsShoppingCartListBean; //传入下订单的信息
    private GroupBuyBean.ListResultBean listResultBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuy_detail);
        MemberId = CommonUtils.GetMemberId(GroupBuyDetailActivity.this);
        init();
        goods_pingjia_layout_btn1.setOnClickListener(this);
        goods_detail_layout_btn1.setOnClickListener(this);
        detail_more.setOnClickListener(this);
        detail_goods.setOnClickListener(this);
        goods_detail_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        index_bottom_shopcar.setOnClickListener(this);
        index_bottom_kefu.setOnClickListener(this);
        goodsdetail_btn_buynow.setOnClickListener(this);
        getgoods_detail();
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
                        if (y <= height) {
                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            Log.i("TAG", "alpha--->" + alpha);
                            goods_detail_top_txt.setTextColor(Color.argb((int) alpha, 0, 0, 0));
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
        //解决了webview 头部空了一片白的问题
        goods_detail_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        goods_detail_webview.setVerticalScrollBarEnabled(false);
        goods_detail_webview.setVerticalScrollbarOverlay(false);
        goods_detail_webview.setHorizontalScrollBarEnabled(false);
        goods_detail_webview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = goods_detail_webview.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
//        webviewsetting.setLoadWithOverviewMode(true);
        goods_detail_webview.loadUrl(detailurl);
        goods_detail_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这个是一定要加上那个的,配合scrollView和WebView的height=wrap_content属性使用
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                goods_detail_webview.measure(w, h);
            }
        });
        goods_detail_webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    contentscrollView.requestDisallowInterceptTouchEvent(false);
//                } else {
//                    contentscrollView.requestDisallowInterceptTouchEvent(true);
//                }
                return true;
            }
        });

//        //评价地址
//        WebSettings webviewsetting2 = goods_pingjia_webview.getSettings();
//        webviewsetting2.setJavaScriptEnabled(true);
//        webviewsetting2.setUseWideViewPort(true);//关键点
//        webviewsetting2.setLoadWithOverviewMode(true);
//        goods_pingjia_webview.loadUrl("http://t.back.aboluomall.com/Moblie/ShowEvaluationList");
//        goods_pingjia_webview.setWebViewClient(new WebViewClient());
    }

    /**
     * 加载头部banner(获取数据之后)
     *
     * @param imges 图片地址(多个)
     */
    private void initrollPagerView(final String[] imges) {
        final ArrayList<String> listimage = new ArrayList<String>();
        for (int i = 0; i < imges.length; i++) {
            imges[i] = ImgUrl + imges[i].toString();
            listimage.add(imges[i]);
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
                Intent intent = new Intent(GroupBuyDetailActivity.this, GoodsDetailImageActivity.class);
                intent.putStringArrayListExtra("imgeurl", listimage);
                intent.putExtra("position", position);
                String transitionName = "images";
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(GroupBuyDetailActivity.this, rollPagerView, transitionName);
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        rollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager_detail);
        goods_pingjia_layout_btn1 = (RelativeLayout) findViewById(R.id.goods_pingjia_layout_btn1);
        goods_detail_layout_btn1 = (RelativeLayout) findViewById(R.id.goods_detail_layout_btn1);
        id_goods_detail_view = (View) findViewById(R.id.id_goods_detail_view);
        id_goods_pingjia_view = (View) findViewById(R.id.id_goods_pingjia_view);
        goods_detail_webview = (WebView) findViewById(R.id.goods_detail_webview);
        goods_pingjia_webview = (WebView) findViewById(R.id.goods_pingjia_webview);
        detail_more = (ImageView) findViewById(R.id.detail_more);
        detail_goods = (ImageView) findViewById(R.id.detail_goods);
        iv_menu_hot = (ImageView) findViewById(R.id.iv_menu_hot);
        goods_detail_text_back = (ImageView) findViewById(R.id.goods_detail_text_back);
        goods_detail_type_imageview = (ImageView) findViewById(R.id.goods_detail_type_imageview);
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
        all_color = (LinearLayout) findViewById(R.id.all_color);
        all_standards = (LinearLayout) findViewById(R.id.all_standards);
        index_bottom_shopcar = (LinearLayout) findViewById(R.id.index_bottom_shopcar);
        index_bottom_kefu = (LinearLayout) findViewById(R.id.index_bottom_kefu);
        goods_type_addshopcart = (LinearLayout) findViewById(R.id.goods_type_addshopcart);
        goods_type_selected = (LinearLayout) findViewById(R.id.goods_type_selected);
        goods_type_ok = (LinearLayout) findViewById(R.id.goods_type_ok);
        layout_txt_goods_sub = (LinearLayout) findViewById(R.id.layout_txt_goods_sub);
        goodsdetail_btn_buynow = (LinearLayout) findViewById(R.id.goodsdetail_btn_buynow);
        pop_goodsdetail_btn_buynow = (LinearLayout) findViewById(R.id.pop_goodsdetail_btn_buynow);
        contentscrollView = (VerticalScrollView) findViewById(R.id.contentscrollView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.setTitleText("请稍等");
        Intent intent = getIntent();
        listResultBean=(GroupBuyBean.ListResultBean)intent.getSerializableExtra("groupBuyBean");
        goods_id = listResultBean.getGoodsId();
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        ImgUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        goodsShoppingCartListBean = new ArrayList<>();

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
                Log.i("groupBuyDetailInfo", response);
                Gson gson = new Gson();
                goodsDetailInfo = gson.fromJson(response, GoodsDetailInfo.class);
                txt_goods_name.setText(goodsDetailInfo.getResult().getGoodsInfo().getGoodsName());
                txt_new_money.setText(String.valueOf(goodsDetailInfo.getResult().getGoodsInfo().getHyPrice()));
                if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsSub() == null) {
                    layout_txt_goods_sub.setVisibility(View.GONE);
                } else {
                    txt_goods_sub.setText(goodsDetailInfo.getResult().getGoodsInfo().getGoodsSub().toString());
                }
                //有些商品没有图片，但肯定有logo图片
                if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsPicture() == null) {
                    if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo() == null) {
                    } else {
                        imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo().split(";");
                    }
                } else {
                    imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsPicture().split(";");
                }
                if (imgurl == null) {
                } else {
                    initrollPagerView(imgurl);
                }
                String detailurl = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
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
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.IsLogin(GroupBuyDetailActivity.this)) {
            switch (v.getId()) {
                case R.id.goods_detail_layout_btn1: //商品详情按钮
                    id_goods_detail_view.setVisibility(View.VISIBLE);
                    id_goods_pingjia_view.setVisibility(View.GONE);
                    String detailurl0 = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
                    initwebview(detailurl0, null);
                    break;
                case R.id.goods_pingjia_layout_btn1: //商品评价按钮Moblie/ShowEvaluation?goodsId
                    id_goods_detail_view.setVisibility(View.GONE);
                    id_goods_pingjia_view.setVisibility(View.VISIBLE);
                    String detailurl = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/Moblie/ShowEvaluation?goodsId=" + goods_id;
                    //String detailurl = "http://back.aboluomall.com/Moblie/ShowEvaluation?goodsId=12";
                    initwebview(detailurl, null);
                    break;
                case R.id.detail_goods: //首部购物车
                    Intent intent = new Intent(GroupBuyDetailActivity.this, MainActivity.class);
                    intent.putExtra("id", 3);
                    startActivity(intent);
                    break;
                case R.id.detail_more: //分享按钮
                    ShareSDKGoodsDetail();
                    break;
                case R.id.goodsdetail_btn_buynow:  //详情底部的立即购买
                    if (CommonUtils.IsLogin(GroupBuyDetailActivity.this)) {
                        BuyNow();
                    } else {
                        Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
                        startActivity(intent1);
                    }
                    break;
                case R.id.iv_menu_hot:
                    AddFavor(goodsDetailInfo.getResult().getGoodsInfo().getGoodsId());
                    break;

                case R.id.index_bottom_kefu:
//                    if (checkApkExist(this, "com.tencent.mobileqq")) {
//                        startActivity(new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="
//                                        + CommonUtils.GetValueByKey(GoodsDetailActivity.this, "QQNum") + "&version=1")));
//                    } else {
//                        Toast.makeText(this, "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
//                    }
                    String title = "商品详情";
                    // 设置访客来源，标识访客是从哪个页面发起咨询的，用于客服了解用户是从什么页面进入三个参数分别为来源页面的url，来源页面标题，来源页面额外信息（可自由定义）
                    // 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
                    String detailurladdress = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
                    ConsultSource source = new ConsultSource(detailurladdress, "商品详情", "memberid" + MemberId);
                    // 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable(), 如果返回为false，该接口不会有任何动作
                    Unicorn.openServiceActivity(GroupBuyDetailActivity.this, // 上下文
                            title, // 聊天窗口的标题
                            source // 咨询的发起来源，包括发起咨询的url，title，描述信息等
                    );
                    break;

            }
        } else {
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(GroupBuyDetailActivity.this, LoginActivity.class);
            startActivity(intent1);
        }
    }

    //立即购买操作
    public void BuyNow() {
        goodsShoppingCartListBean.clear();
        int colorid = 0;
        String color = null;
        int standardsid = 0;
        String standards = null;
        double hyprice = goodsDetailInfo.getResult().getGoodsInfo().getHyPrice(); //默认是会员价，如果有规，会根据规格去找价格
        final RadioButton radioButton =null;
        final RadioButton radioButton2 = null;
        if (radioButton != null) {
            color = radioButton.getText().toString();
            colorid = radioButton.getId();
        } else {
            color = "无";
        }
        if (radioButton2 != null) {
            standards = radioButton2.getText().toString();
            standardsid = radioButton2.getId();
            List<GoodsStandardsBean> goodsStandardsBean = goodsDetailInfo.getResult().getGoodsInfo().getGoodsStandards();
            for (int i = 0; i < goodsStandardsBean.size(); i++) { //根据规格id寻找价格
                if (goodsStandardsBean.get(i).getGoodsStandardId() == standardsid) {
                    hyprice = goodsStandardsBean.get(i).getHyPrice();

                }
            }
        } else {
            standards = "无";
        }
        GoodsShoppingCartListBean goodsShoppingCartListBean2 = new GoodsShoppingCartListBean(
                goodsDetailInfo.getResult().getGoodsInfo().getGoodsId(),
                colorid,
                color,
                standardsid,
                standards,
                Integer.valueOf(etAmount.getText().toString()),
                (new Double(goodsDetailInfo.getResult().getGoodsInfo().getYunfei())).intValue(),
                goodsDetailInfo.getResult().getGoodsInfo().getGoodsName(),
                goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo(),
                hyprice
        );
        goodsShoppingCartListBean.add(goodsShoppingCartListBean2);
        Intent intent1 = new Intent(GroupBuyDetailActivity.this, MakeOrderActivity.class);
        intent1.putExtra("allmoney", String.valueOf(hyprice *
                Integer.valueOf(etAmount.getText().toString())));
        intent1.putExtra("data", goodsShoppingCartListBean);
        intent1.putExtra("payfrom", "1"); //代表从购物车结算的
        startActivity(intent1);
    }

    //添加收藏夹
    private void AddFavor(final int goods_id) {
        pdialog.show();
        StringRequest addrequestshopcar = new StringRequest(Request.Method.POST, URL + "/api/CollectApi/ReceiveAddGoodsCollect", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pdialog.dismiss();
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Gson gson = new Gson();
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                Toast.makeText(GroupBuyDetailActivity.this, baseModel.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                Toast.makeText(GroupBuyDetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("woaicaojingAddshopcar", error.toString());
                Toast.makeText(GroupBuyDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("GoodsId", String.valueOf(goods_id));
                map.put("APPToken", APPToken);
                return map;

            }
        };
        requestQueue.add(addrequestshopcar);
    }

    private void ShareSDKGoodsDetail() {
        String detailurl0 = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl")
                + "/moblie/ShareProducts?productId=" + goods_id;
        String imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo();
        if (imgurl == null) {
        } else {
            String[] imageurls = imgurl.split(";");
            for (int i = 0; i < imageurls.length; i++) {
                imageurls[i] = ImgUrl + imageurls[i].toString();
            }
            Log.i("ShareSDKImageUrl", imageurls[0].toString());
            Log.i("ShareSDKURLDetail", detailurl0);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle("阿波罗分享");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(detailurl0);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(goodsDetailInfo.getResult().getGoodsInfo().getGoodsName().toString());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath(imageurls[0].toString());//确保SDcard下面存在此张图片
            oks.setImageUrl(imageurls[0].toString());
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(detailurl0);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("这个商品不错哦");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(detailurl0);
            // 启动分享GUI
            oks.show(this);
        }
    }

}
