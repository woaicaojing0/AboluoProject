package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
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
import com.aboluo.model.ShopCarBean;
import com.aboluo.model.UnaryDetailBean;
import com.aboluo.model.UnaryListBean;
import com.aboluo.widget.VerticalScrollView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by CJ on 2016/12/3.
 * 一元详情
 */

public class UnaryDetailActivity extends Activity implements View.OnClickListener {
    private UnaryListBean.ListResultBean listResultBean;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private RollPagerView unarydetail_view_pager;
    private TextView unary_need_txt, unary_shengyu_txt, unary_goods_title, unary_goods_sub;
    private LinearLayout unary_startgoods; //商品状态，是否已经公布答案
    private BannerAdapter bannerAdapter;
    private LinearLayout unary_buy_now;
    private int goodsstatus = 0;
    private ArrayList<ShopCarBean.ResultBean.GoodsShoppingCartListBean> goodsShoppingCartListBean; //传入下订单的信息
    private WebView unary_goodsdetail_webview;
    private int height;
    private VerticalScrollView unary_detail_scollview;
    private TextView unarydetail_text_title;
    private Toolbar unary_toolbar;
    private ImageView unarydetail_text_back;
    private String MemberId;
    private UnaryDetailBean unaryDetailBean;
    private TextView tv_last_winner, tv_nickName, tv_addTimes, tv_now_winner, tv_onetimes;
    private RelativeLayout relative_farther;
    private LinearLayout unary_detail_percent_child;
    private TextView tv_percentNum, unary_detail_record;
    private int type;
    private ImageView unary_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_unarydetail);
        if (CommonUtils.IsLogin(UnaryDetailActivity.this)) {
            init();
            if (type == 1) {
                unary_buy_now.setEnabled(false);
                unary_buy_now.setBackgroundColor(Color.parseColor("#d6d3d6"));
            }
            else {
                unary_buy_now.setOnClickListener(this);
            }
            ViewTreeObserver vto = unarydetail_view_pager.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    unarydetail_view_pager.getViewTreeObserver().removeGlobalOnLayoutListener(
                            this);
                    height = unarydetail_view_pager.getHeight();
                    unary_detail_scollview.setScrollViewListener(new VerticalScrollView.ScrollViewListener() {
                        @Override
                        public void onScrollChanged(VerticalScrollView scrollView, int x, int y, int oldx, int oldy) {
                            //		Log.i("TAG","y--->"+y+"    height-->"+height);
                            if (y <= height) {
                                float scale = (float) y / height;
                                float alpha = (255 * scale);
                                Log.i("TAG", "alpha--->" + alpha);
                                unarydetail_text_title.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                                //只是layout背景透明(仿知乎滑动效果)
                                unary_toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                            }
                        }
                    });
                }
            });
            unarydetail_text_back.setOnClickListener(this);
            unarydetail_text_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            unary_detail_record.setOnClickListener(this);
            unary_share.setOnClickListener(this);
        } else {
            finish();
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(UnaryDetailActivity.this, LoginActivity.class);
            startActivity(intent1);
        }

    }

    private void init() {
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl3");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        MemberId = CommonUtils.GetMemberId(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        listResultBean = bundle.getParcelable("data");
        type = intent.getIntExtra("type", 0);
        goodsstatus = bundle.getInt("GoodsStatus");
        unarydetail_view_pager = (RollPagerView) findViewById(R.id.unarydetail_view_pager);
        unary_need_txt = (TextView) findViewById(R.id.unary_need_txt);
        unary_shengyu_txt = (TextView) findViewById(R.id.unary_shengyu_txt);
        unary_goods_title = (TextView) findViewById(R.id.unary_goods_title);
        unary_goods_sub = (TextView) findViewById(R.id.unary_goods_sub);
        unarydetail_text_title = (TextView) findViewById(R.id.unarydetail_text_title);
        tv_last_winner = (TextView) findViewById(R.id.tv_last_winner);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        tv_addTimes = (TextView) findViewById(R.id.tv_addTimes);
        tv_now_winner = (TextView) findViewById(R.id.tv_now_winner);
        tv_onetimes = (TextView) findViewById(R.id.tv_onetimes);
        unary_detail_record = (TextView) findViewById(R.id.unary_detail_record);
        unary_toolbar = (Toolbar) findViewById(R.id.unary_toolbar);
        unary_detail_scollview = (VerticalScrollView) findViewById(R.id.unary_detail_scollview);
        unary_startgoods = (LinearLayout) findViewById(R.id.unary_startgoods);
        unary_buy_now = (LinearLayout) findViewById(R.id.unary_buy_now);
        unarydetail_text_back = (ImageView) findViewById(R.id.unarydetail_text_back);
        unary_goodsdetail_webview = (WebView) findViewById(R.id.unary_goodsdetail_webview);
        relative_farther = (RelativeLayout) findViewById(R.id.relative_farther);
        unary_detail_percent_child = (LinearLayout) findViewById(R.id.unary_detail_percent_child);
        tv_percentNum = (TextView) findViewById(R.id.tv_percentNum);
        unary_share = (ImageView) findViewById(R.id.unary_share);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        unarydetail_view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth));

        initDetailData();
        goodsShoppingCartListBean = new ArrayList<>();
        initData();
        initUnaryProgress();
    }

    private void initWebview() {
        String detailurl = CommonUtils.GetValueByKey(UnaryDetailActivity.this, "backUrl")
                + "/moblie/Index?productId=" + listResultBean.getGoodsId();
        Log.i("woaicaojing", detailurl);
        //详情地址
        //解决了webview 头部空了一片白的问题
        unary_goodsdetail_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        unary_goodsdetail_webview.setVerticalScrollBarEnabled(false);
        unary_goodsdetail_webview.setVerticalScrollbarOverlay(false);
        unary_goodsdetail_webview.setHorizontalScrollBarEnabled(false);
        unary_goodsdetail_webview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = unary_goodsdetail_webview.getSettings();
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        unary_goodsdetail_webview.loadUrl(detailurl);
        unary_goodsdetail_webview.setWebViewClient(new WebViewClient());
    }

    private void initDetailData() {
        if (listResultBean == null) {
        } else {
            String[] imagesurl = listResultBean.getGoodsPicture().toString().split(";");
            for (int i = 0; i < imagesurl.length; i++) {
                imagesurl[i] = ImageUrl + imagesurl[i];
            }
            initWebview();
            //头部滚动banner
            bannerAdapter = new BannerAdapter(UnaryDetailActivity.this, imagesurl, unarydetail_view_pager);
            unarydetail_view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
            unarydetail_view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
            unarydetail_view_pager.setFocusable(false);
        }
        unary_need_txt.setText(String.valueOf(listResultBean.getNeedPersonCount()));
        unary_shengyu_txt.setText(String.valueOf(listResultBean.getNeedPersonCount() - listResultBean.getJoinCount()));
        unary_goods_title.setText(String.valueOf(listResultBean.getGoodsName()));
        unary_goods_sub.setText(String.valueOf(listResultBean.getGoodsSub()));
        if (goodsstatus == 0) {
            unary_startgoods.setVisibility(View.GONE);
        } else {
            unary_startgoods.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 场次编号、参与人次、期数
     */
    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/OnePurchaseApi/ReceiveOnePurchaseDetail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("unaryDetailActivity", response);
                unaryDetailBean = gson.fromJson(response, UnaryDetailBean.class);
                if (unaryDetailBean.isIsSuccess()) {
                    tv_last_winner.setText(unaryDetailBean.getLastWinLotteryNumber());
                    tv_nickName.setText(unaryDetailBean.getMemberNickName());
                    tv_addTimes.setText(String.valueOf(unaryDetailBean.getAddTotalCount()));
                    tv_now_winner.setText(String.valueOf(unaryDetailBean.getThisWinLotteryNumber()));
                    tv_onetimes.setText(String.valueOf(unaryDetailBean.getOneTimes()));
                }
                //Toast.makeText(UnaryDetailActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytes = error.networkResponse.data;
                Log.i("unaryDetailActivity", new String(bytes));
                Toast.makeText(UnaryDetailActivity.this, new String(bytes), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("OnePurchaseId", String.valueOf(listResultBean.getId()));
                map.put("APPToken", APPToken);
                map.put("LoginPhone", "123");
                map.put("LoginCheckToken", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unary_buy_now:
                if (CommonUtils.IsLogin(UnaryDetailActivity.this)) {
                    UnaryBuyNow();
                } else {
                    Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(UnaryDetailActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.unary_detail_record:
                Intent intent = new Intent(UnaryDetailActivity.this, UnaryRecordActivity.class);
                intent.putExtra("PurchaseId", listResultBean.getId());
                startActivity(intent);
                break;
            case R.id.unary_share:
                ShareSDKGoodsDetail();
                break;
            default:
                break;
        }
    }

    public void UnaryBuyNow() {
        goodsShoppingCartListBean.clear();
//        ShopCarBean.ResultBean.GoodsShoppingCartListBean goodsShoppingCartListBean2 = new ShopCarBean.ResultBean.GoodsShoppingCartListBean(
//                goodsDetailInfo.getResult().getGoodsInfo().getGoodsId(),
//                colorid,
//                color,
//                standardsid,
//                standards,
//                Integer.valueOf(etAmount.getText().toString()),
//                (new Double(goodsDetailInfo.getResult().getGoodsInfo().getYunfei())).intValue(),
//                goodsDetailInfo.getResult().getGoodsInfo().getGoodsName(),
//                goodsDetailInfo.getResult().getGoodsInfo().getGoodsLogo(),
//                hyprice
//        );
        ShopCarBean.ResultBean.GoodsShoppingCartListBean goodsShoppingCartListBean2 = new ShopCarBean.ResultBean.GoodsShoppingCartListBean(
                listResultBean.getGoodsId(), listResultBean.getGoodsColorId(),
                listResultBean.getColorName() == null ? "0" : listResultBean.getColorName().toString(),
                listResultBean.getGoodsStandId(), listResultBean.getColorName() == null ? "0" : listResultBean.getColorName().toString(),
                1, 0, listResultBean.getGoodsName(), listResultBean.getGoodsLogo().toString(), 1
        );
        goodsShoppingCartListBean.add(goodsShoppingCartListBean2);
        Intent intent1 = new Intent(UnaryDetailActivity.this, MakeOrderActivity.class);
        intent1.putExtra("allmoney", 1);
        intent1.putExtra("data", goodsShoppingCartListBean);
        intent1.putExtra("payfrom", "4"); //代表从一元购结算的
        intent1.putExtra("OnePurchaseId", listResultBean.getId()); //代表从一元购结算的
        startActivity(intent1);
    }

    /**
     * 一元购 进度条
     */
    private void initUnaryProgress() {
        ViewTreeObserver vto2 = relative_farther.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relative_farther.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams para = unary_detail_percent_child.getLayoutParams();
                int all = listResultBean.getNeedPersonCount();
                int i = listResultBean.getJoinCount();
                if (i == 0) {
                    para.width = 0;
                    tv_percentNum.setTextColor(Color.BLACK);
                    tv_percentNum.setText("0%");
                } else {
                    para.width = ((relative_farther.getWidth()) * i) / all;
                    java.text.DecimalFormat df = new java.text.DecimalFormat("#");
                    double num = (100 * i) / all;
                    String percentNum = df.format(num);
                    if (num < 50) {
                        tv_percentNum.setTextColor(Color.BLACK);
                    } else {
                        tv_percentNum.setTextColor(Color.WHITE);
                    }
                    tv_percentNum.setText(percentNum + "%");
                }
                unary_detail_percent_child.setLayoutParams(para);
            }
        });
    }

    private void ShareSDKGoodsDetail() {
        String detailurl0 = CommonUtils.GetValueByKey(UnaryDetailActivity.this, "backUrl")
                + "/moblie/ShareProducts?productId=" + listResultBean.getGoodsId();
        String imgurl = listResultBean.getGoodsLogo();
        if (imgurl == null) {
        } else {
            String[] imageurls = imgurl.split(";");
            for (int i = 0; i < imageurls.length; i++) {
                imageurls[i] = ImageUrl + imageurls[i].toString();
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
            oks.setText("快来和我一起夺宝"+listResultBean.getGoodsName());
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
