package com.aboluo.com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.adapter.BannerAdapter;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

/**
 * Created by CJ on 2016/10/1.
 */

public class GoodsDetailActivity extends Activity implements View.OnClickListener {

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
    private ImageView my_info_text_back,goods_type_pop_close;
    private TextView txt_goods_type;
    private View Farther_view;

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
    }

    private void init() {
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
        txt_goods_type = (TextView) findViewById(R.id.txt_goods_type);

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
                showgoodstypepopupwindow();
                break;

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
}
