package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ConstUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.XUtils.TimeUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.SecKillDetailInfo.SeckillListBean;
import com.aboluo.widget.VerticalScrollView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by CJ on 2016/10/1.
 * 该页面是秒杀商品详情页对应的activitiy，包含2各部分：1 显示详情的页面 2选择商品类型的页面(默认是隐藏的)
 */

public class SecKillGoodsDetailActivity extends Activity implements View.OnClickListener {
    private static String URL = null, ImgUrl = null;
    private static String APPToken = null;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private RollPagerView rollPagerView;  //首部banner元素
    private int height;
    private VerticalScrollView seckill_contentscrollView; // 需要监听滑动的scrollview
    private TextView seckillgoods_detail_top_txt, txt_seckilldetailgoods_price, txt_seckilldetailgoods_num, txt_seckilldetailgoods_name, txt_seckill_goodsdetail_stands;
    private Toolbar seckill_toolbar;
    private BannerAdapter bannerAdapter;
    //显示详情和评价的webview
    private WebView kill_goods_detail_webview, seckill_goods_pingjia_webview;
    private CountdownView cv_seckillgoodsdetail;
    private LinearLayout seckill_goodsdetail_bottom;
    private SeckillListBean seckillListBean;
    private String Seckill_endtime;
    private ImageView seckillgoods_detail_image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seckillgoodsdetail);
        init();
        ViewTreeObserver vto = rollPagerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rollPagerView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = rollPagerView.getHeight();
                seckill_contentscrollView.setScrollViewListener(new VerticalScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(VerticalScrollView scrollView, int x, int y, int oldx, int oldy) {
                        //		Log.i("TAG","y--->"+y+"    height-->"+height);
                        if (y <= height) {
                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            Log.i("TAG", "alpha--->" + alpha);
                            seckillgoods_detail_top_txt.setTextColor(Color.argb((int) alpha, 0, 0, 0));
                            //只是layout背景透明(仿知乎滑动效果)
                            seckill_toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                        }
                    }
                });
            }
        });
        seckill_goodsdetail_bottom.setOnClickListener(this);
        seckillgoods_detail_image_back.setOnClickListener(this);

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
        kill_goods_detail_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        kill_goods_detail_webview.setVerticalScrollBarEnabled(false);
        kill_goods_detail_webview.setVerticalScrollbarOverlay(false);
        kill_goods_detail_webview.setHorizontalScrollBarEnabled(false);
        kill_goods_detail_webview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = kill_goods_detail_webview.getSettings();
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        kill_goods_detail_webview.loadUrl(detailurl);
        kill_goods_detail_webview.setWebViewClient(new WebViewClient());
        //评价地址
        WebSettings webviewsetting2 = seckill_goods_pingjia_webview.getSettings();
        webviewsetting2.setJavaScriptEnabled(true);
        webviewsetting2.setUseWideViewPort(true);//关键点
        webviewsetting2.setLoadWithOverviewMode(true);
        seckill_goods_pingjia_webview.loadUrl(null);
        seckill_goods_pingjia_webview.setWebViewClient(new WebViewClient());
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
                Toast.makeText(SecKillGoodsDetailActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        requestQueue = MyApplication.getRequestQueue();
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        ImgUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        rollPagerView = (RollPagerView) findViewById(R.id.sekilldetail_roll_view_pager);
        seckill_contentscrollView = (VerticalScrollView) findViewById(R.id.seckill_contentscrollView);
        txt_seckill_goodsdetail_stands = (TextView) findViewById(R.id.txt_seckill_goodsdetail_stands);
        txt_seckilldetailgoods_price = (TextView) findViewById(R.id.txt_seckilldetailgoods_price);
        txt_seckilldetailgoods_name = (TextView) findViewById(R.id.txt_seckilldetailgoods_name);
        txt_seckilldetailgoods_num = (TextView) findViewById(R.id.txt_seckilldetailgoods_num);
        seckillgoods_detail_top_txt = (TextView) findViewById(R.id.seckillgoods_detail_top_txt);
        seckill_goodsdetail_bottom = (LinearLayout) findViewById(R.id.seckill_goodsdetail_bottom);
        seckillgoods_detail_image_back = (ImageView) findViewById(R.id.seckillgoods_detail_image_back);
        seckill_toolbar = (Toolbar) findViewById(R.id.seckill_toolbar);
        kill_goods_detail_webview = (WebView) findViewById(R.id.kill_goods_detail_webview);
        seckill_goods_pingjia_webview = (WebView) findViewById(R.id.seckill_goods_pingjia_webview);
        cv_seckillgoodsdetail = (CountdownView) findViewById(R.id.cv_seckillgoodsdetail);
        Bundle bundle = getIntent().getExtras();
        seckillListBean = bundle.getParcelable("data");
        Seckill_endtime = bundle.getString("endtime");
        GetSreverTime();
        initPageViewAndWebView(seckillListBean);
    }

    private void initPageViewAndWebView(SeckillListBean seckillListBean) {
        if(seckillListBean.getGoodsPicture() ==null)
        {
            Toast.makeText(this, "请先添加商品的banner图片", Toast.LENGTH_SHORT).show();
        }else {
            String[] imgesurl = seckillListBean.getGoodsPicture().split(";");
            initrollPagerView(imgesurl);
            String detailurl = CommonUtils.GetValueByKey(this, "backUrl") + "/moblie/Index?productId=" + seckillListBean.getGoodsId();
            Log.i("woaicaojing", detailurl);
            initwebview(detailurl, null);
        }
    }

    private void initdata(SeckillListBean seckillListBean) {
        txt_seckill_goodsdetail_stands.setText(seckillListBean.getGoodsColorStandardName().toString());
        txt_seckilldetailgoods_price.setText(String.valueOf(seckillListBean.getSeckillPrice()));
        txt_seckilldetailgoods_name.setText(seckillListBean.getGoodsName());
        txt_seckilldetailgoods_num.setText("库存：" + String.valueOf(seckillListBean.getSeckCount()) + " 件");
    }

    private void GetSreverTime() {
        //(0-未开启，1-已开启，2-准备中，3-已结束）
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/api/ConfigApi/ReceiveServerTime", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(2, response.length() - 2);
                long time = 0;
                time = TimeUtils.getIntervalTime(response, Seckill_endtime, ConstUtils.TimeUnit.MSEC);
                cv_seckillgoodsdetail.start(time);
                initdata(seckillListBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecKillGoodsDetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seckillgoods_detail_image_back:
                this.finish();
                break;
            case R.id.seckill_goodsdetail_bottom:
                Toast.makeText(this, "开始付款啦", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
