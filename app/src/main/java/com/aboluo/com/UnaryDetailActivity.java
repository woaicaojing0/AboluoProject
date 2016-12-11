package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.ShopCarBean;
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

/**
 * Created by CJ on 2016/12/3.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_unarydetail);
        init();
        unary_buy_now.setOnClickListener(this);
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

    }

    private void init() {
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Bundle bundle = getIntent().getExtras();
        listResultBean = bundle.getParcelable("data");
        goodsstatus = bundle.getInt("GoodsStatus");
        unarydetail_view_pager = (RollPagerView) findViewById(R.id.unarydetail_view_pager);
        unary_need_txt = (TextView) findViewById(R.id.unary_need_txt);
        unary_shengyu_txt = (TextView) findViewById(R.id.unary_shengyu_txt);
        unary_goods_title = (TextView) findViewById(R.id.unary_goods_title);
        unary_goods_sub = (TextView) findViewById(R.id.unary_goods_sub);
        unarydetail_text_title = (TextView) findViewById(R.id.unarydetail_text_title);
        unary_toolbar = (Toolbar) findViewById(R.id.unary_toolbar);
        unary_detail_scollview = (VerticalScrollView) findViewById(R.id.unary_detail_scollview);
        unary_startgoods = (LinearLayout) findViewById(R.id.unary_startgoods);
        unary_buy_now = (LinearLayout) findViewById(R.id.unary_buy_now);
        unary_goodsdetail_webview = (WebView) findViewById(R.id.unary_goodsdetail_webview);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        unarydetail_view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth));
        initDetailData();
        goodsShoppingCartListBean = new ArrayList<>();
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
            String[] imagesurl = listResultBean.getGoodsPicture().toString().split(",");
            for (int i = 0; i < imagesurl.length; i++) {
                imagesurl[i] = ImageUrl + imagesurl;
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

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1975");
                map.put("OrderId", "508");
                map.put("ExpressId", "1");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
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
        }
    }

    public void UnaryBuyNow() {
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
        intent1.putExtra("payfrom", "4"); //代表从购物车结算的
        startActivity(intent1);
    }
}
