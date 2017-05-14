package com.aboluo.com;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.BaseModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by cj on 2017/4/26.
 */

public class GroupBuyDetailActivity extends Activity implements View.OnClickListener {
    private RollPagerView rollPagerView;  //首部banner元素
    private String[] imgurl = null;    //首部banner中绑定的图片
    private BannerAdapter bannerAdapter;
    //商品详情和评价按钮
    private RelativeLayout goods_pingjia_layout_btn1, goods_detail_layout_btn1;
    //商品详情和评价按钮下面的横线
    private View id_goods_detail_view, id_goods_pingjia_view;
    //显示详情和评价的webview
    private WebView goods_detail_webview;
    //首页最右边显示更多操作、首页最上面的购物车，收藏按钮
    private ImageView groupbuy_share;
    private LinearLayout index_bottom_menu;
    private LinearLayout layout_txt_goods_sub;
    //详情返回、商品类型弹出xml中的关闭、商品类型中的图片
    private ImageView goods_detail_text_back;
    //商品类型、名称、会员价、商品副标题,商品头部的商品详情
    private TextView txt_goods_name, txt_new_money, txt_goods_sub, goods_detail_top_txt;
    private static int goods_id = 0; //商品的ID
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static String URL = null, ImgUrl = null;
    private static String APPToken = null;
    private LinearLayout index_bottom_shopcar, index_bottom_kefu; //1底部加入购车按钮 商品列表中的加入购物车
    private LinearLayout goodsdetail_btn_buynow;
    private SweetAlertDialog pdialog;

    private VerticalScrollView contentscrollView; // 需要监听滑动的scrollview
    private Toolbar toolbar; //渐变显示的toolbar
    private int height;
    private String MemberId;
    private ArrayList<GoodsShoppingCartListBean> goodsShoppingCartListBean; //传入下订单的信息
    private GroupBuyBean.ListResultBean groupBuyDetailBean;
    private TextView tv_colorAndstandard; // 团购商品规格
    private RelativeLayout relative_farther; //进度条上一层rl
    private LinearLayout goodbuy_detail_percent_child; //进度条本身
    private TextView tv_percentNum, goodbuy_detail_record;
    private int currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuy_detail);
        MemberId = CommonUtils.GetMemberId(GroupBuyDetailActivity.this);
        Intent intent = getIntent();
        groupBuyDetailBean = (GroupBuyBean.ListResultBean) intent.getSerializableExtra("groupBuyBean");
        currentState = intent.getIntExtra("currentState", 0);
        init();
        initData();
        initGroupBuyNum();
        goods_pingjia_layout_btn1.setOnClickListener(this);
        goods_detail_layout_btn1.setOnClickListener(this);
        groupbuy_share.setOnClickListener(this);
        goods_detail_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        index_bottom_shopcar.setOnClickListener(this);
//        index_bottom_kefu.setOnClickListener(this);
//        index_bottom_menu.setOnClickListener(this);
        goodsdetail_btn_buynow.setOnClickListener(this);
        goodbuy_detail_record.setOnClickListener(this);

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
        if (currentState != 2) {
            goodsdetail_btn_buynow.setEnabled(false);
            goodsdetail_btn_buynow.setBackgroundColor(Color.parseColor("#d6d3d6"));
        }

    }

    /**
     * 初始化底部webivew（获取数据之后）
     */
    private void initWebView(String detailUrl, String pingJia) {
        //详情地址
        //解决了webview 头部空了一片白的问题
        goods_detail_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        goods_detail_webview.setVerticalScrollBarEnabled(false);
        goods_detail_webview.setHorizontalScrollBarEnabled(false);
        //end
        WebSettings webViewSetting = goods_detail_webview.getSettings();
        webViewSetting.setDomStorageEnabled(true);
        webViewSetting.setJavaScriptEnabled(true);
        webViewSetting.setUseWideViewPort(true);//关键点
        webViewSetting.setLoadWithOverviewMode(true);
        goods_detail_webview.loadUrl(detailUrl);
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

    }

    /**
     * 加载头部banner(获取数据之后)
     *
     * @param imges 图片地址(多个)
     */
    private void iniTrollPagerView(final String[] imges) {
        final ArrayList<String> listimage = new ArrayList<String>();
        for (int i = 0; i < imges.length; i++) {
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
        groupbuy_share = (ImageView) findViewById(R.id.groupbuy_share);
        index_bottom_menu = (LinearLayout) findViewById(R.id.index_bottom_menu);
        goods_detail_text_back = (ImageView) findViewById(R.id.goods_detail_text_back);
        txt_goods_name = (TextView) findViewById(R.id.txt_goods_name);
        txt_new_money = (TextView) findViewById(R.id.txt_new_money);
        txt_goods_sub = (TextView) findViewById(R.id.txt_goods_sub);
        index_bottom_kefu = (LinearLayout) findViewById(R.id.index_bottom_kefu);
        goods_detail_top_txt = (TextView) findViewById(R.id.goods_detail_top_txt);
        layout_txt_goods_sub = (LinearLayout) findViewById(R.id.layout_txt_goods_sub);
        goodsdetail_btn_buynow = (LinearLayout) findViewById(R.id.goodsdetail_btn_buynow);
        index_bottom_shopcar = (LinearLayout) findViewById(R.id.index_bottom_shopcar);
        contentscrollView = (VerticalScrollView) findViewById(R.id.contentscrollView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        tv_colorAndstandard = (TextView) findViewById(R.id.tv_colorAndstandard);
        relative_farther = (RelativeLayout) findViewById(R.id.relative_farther);
        goodbuy_detail_percent_child = (LinearLayout) findViewById(R.id.goodbuy_detail_percent_child);
        tv_percentNum = (TextView) findViewById(R.id.tv_percentNum);
        goodbuy_detail_record = (TextView) findViewById(R.id.goodbuy_detail_record);
        pdialog.setTitleText("请稍等");
        goods_id = groupBuyDetailBean.getGoodsId();
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        ImgUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        goodsShoppingCartListBean = new ArrayList<>();

    }

    private void initData() {
        txt_goods_name.setText(groupBuyDetailBean.getGoodsName());
        txt_new_money.setText(String.valueOf(groupBuyDetailBean.getTeamPrice()));
        tv_colorAndstandard.setText("颜色 " + groupBuyDetailBean.getGoodsColorName() + " 规格 " + groupBuyDetailBean.getGoodsStanderName());
        if (groupBuyDetailBean.getRemarks() == null) {
            layout_txt_goods_sub.setVisibility(View.GONE);
        } else {
            txt_goods_sub.setText(groupBuyDetailBean.getRemarks().toString());
        }
        imgurl = groupBuyDetailBean.getGoodsPicture().split(";");
        iniTrollPagerView(imgurl);
        String detailurl = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
        Log.i("woaicaojing", detailurl);
        initWebView(detailurl, null);
    }
//    /**
//     * 获取商品详情的数据，在这个方法里加载initrollPagerView、initwebview
//     */
//    private void getGoods_detail() {
//        stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Goods/ReceiveGoodsById", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                response = response.replace("\\", "");
//                response = response.substring(1, response.length() - 1);
//                Log.i("groupBuyDetailInfo", response);
//                Gson gson = new Gson();
//                goodsDetailInfo = gson.fromJson(response, GoodsDetailInfo.class);
//
//                //有些商品没有图片，但肯定有logo图片
//                if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsPicture() == null) {
//                    if (goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo() == null) {
//                    } else {
//                        imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo().split(";");
//                    }
//                } else {
//                    imgurl = goodsDetailInfo.getResult().getGoodsInfo().getGoodsPicture().split(";");
//                }
//                if (imgurl == null) {
//                } else {
//                    iniTrollPagerView(imgurl);
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("woaicoajing", error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("GoodsId", String.valueOf(goods_id));
//                map.put("APPToken", APPToken);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.IsLogin(GroupBuyDetailActivity.this)) {
            switch (v.getId()) {
                case R.id.goods_detail_layout_btn1: //商品详情按钮
                    id_goods_detail_view.setVisibility(View.VISIBLE);
                    id_goods_pingjia_view.setVisibility(View.GONE);
                    String detailurl0 = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
                    initWebView(detailurl0, null);
                    break;
                case R.id.goods_pingjia_layout_btn1:
                    id_goods_detail_view.setVisibility(View.GONE);
                    id_goods_pingjia_view.setVisibility(View.VISIBLE);
                    String detailurl = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/Moblie/ShowEvaluation?goodsId=" + goods_id;
                    initWebView(detailurl, null);
                    break;
                case R.id.index_bottom_shopcar:
                    Toast.makeText(this, "团购商品请直接购买！", Toast.LENGTH_SHORT).show();
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
                case R.id.index_bottom_menu:
                    AddFavor(groupBuyDetailBean.getGoodsId());
                    break;
                case R.id.index_bottom_kefu:
                    String title = "商品详情";
                    String detailurladdress = CommonUtils.GetValueByKey(GroupBuyDetailActivity.this, "backUrl") + "/moblie/Index?productId=" + goods_id;
                    ConsultSource source = new ConsultSource(detailurladdress, "商品详情", "memberid" + MemberId);
                    Unicorn.openServiceActivity(GroupBuyDetailActivity.this,
                            title,
                            source
                    );
                    break;
                case R.id.goodbuy_detail_record: //团购详情
                    Intent intent = new Intent(GroupBuyDetailActivity.this, GroupBuyRecordActivity.class);
                    intent.putExtra("TeamBuyId", groupBuyDetailBean.getTId());
                    intent.putExtra("openTime", groupBuyDetailBean.getOpenTime());
                    startActivity(intent);
                    break;
                case R.id.groupbuy_share:
                    ShareSDKGoodsDetail();
                    break;
                default:
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
        double hyprice = groupBuyDetailBean.getTeamPrice();
        GoodsShoppingCartListBean goodsShoppingCartListBean2 = new GoodsShoppingCartListBean(
                groupBuyDetailBean.getGoodsId(),
                groupBuyDetailBean.getGoodsColorId(),
                groupBuyDetailBean.getGoodsColorName(),
                groupBuyDetailBean.getGoodsStanderId(),
                groupBuyDetailBean.getGoodsStanderName(),
                Integer.valueOf(1),
                0,
                groupBuyDetailBean.getGoodsName(),
                groupBuyDetailBean.getGoodsLogo(),
                hyprice
        );
        goodsShoppingCartListBean.add(goodsShoppingCartListBean2);
        Intent intent1 = new Intent(GroupBuyDetailActivity.this, MakeOrderActivity.class);
        intent1.putExtra("allmoney", String.valueOf(hyprice *
                Integer.valueOf(1)));
        intent1.putExtra("data", goodsShoppingCartListBean);
        intent1.putExtra("payfrom", "7"); //代表从购物车结算的
        intent1.putExtra("TeamBuyId", groupBuyDetailBean.getTId()); //拼团购的场次
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
        String imgurl = groupBuyDetailBean.getGoodsLogo(); //这个地址是全地址
        if (imgurl == null) {
        } else {
            String[] imageurls = imgurl.split(";");
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
            oks.setText("快来和我一起拼团"+groupBuyDetailBean.getGoodsName().toString());
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

    private void initGroupBuyNum() {
        ViewTreeObserver vto2 = relative_farther.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relative_farther.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams para = goodbuy_detail_percent_child.getLayoutParams();
                int all = groupBuyDetailBean.getNeedPerson();
                int i = groupBuyDetailBean.getBuyPerson();
                if (i == 0) {
                    para.width = 0;
                    tv_percentNum.setTextColor(Color.BLACK);
                    tv_percentNum.setText("0%");
                } else {
                    para.width = ((relative_farther.getWidth()) * i) / all;
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                    double num = (100 * i) / all;
                    String percentNum = CommonUtils.Getpercent(i,all);
                    if (num < 50) {
                        tv_percentNum.setTextColor(Color.BLACK);
                    } else {
                        tv_percentNum.setTextColor(Color.WHITE);
                    }
                    tv_percentNum.setText(percentNum);
                }
                goodbuy_detail_percent_child.setLayoutParams(para);
            }
        });
    }

}
