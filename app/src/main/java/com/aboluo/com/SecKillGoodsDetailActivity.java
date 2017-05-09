package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
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
import com.aboluo.XUtils.ConstUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.XUtils.TimeUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.model.SecKillDetailInfo.SeckillListBean;
import com.aboluo.model.ShopCarBean;
import com.aboluo.widget.VerticalScrollView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;

import cn.iwgang.countdownview.CountdownView;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private ArrayList<ShopCarBean.ResultBean.GoodsShoppingCartListBean> goodsShoppingCartListBean; //传入下订单的信息
    private RelativeLayout goods_pingjia_layout_btn, goods_detail_layout_btn;
    //商品详情和评价按钮下面的横线
    private View seckill_goods_detail_view, seckill_goods_pingjia_view;
    private ImageView seckill_share;

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
        goods_detail_layout_btn.setOnClickListener(this);
        seckillgoods_detail_image_back.setOnClickListener(this);
        goods_pingjia_layout_btn.setOnClickListener(this);
        seckill_share.setOnClickListener(this);

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
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
//        webviewsetting.setLoadWithOverviewMode(true);
        kill_goods_detail_webview.loadUrl(detailurl);
        kill_goods_detail_webview.setWebViewClient(new WebViewClient() {
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
                kill_goods_detail_webview.measure(w, h);
            }
        });
        kill_goods_detail_webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
    private void initrollPagerView(String[] imges) {
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
                Intent intent = new Intent(SecKillGoodsDetailActivity.this, GoodsDetailImageActivity.class);
                intent.putStringArrayListExtra("imgeurl", listimage);
                intent.putExtra("position", position);
                String transitionName = "images";
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SecKillGoodsDetailActivity.this, rollPagerView, transitionName);
                startActivity(intent, activityOptionsCompat.toBundle());
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
        goods_detail_layout_btn = (RelativeLayout) findViewById(R.id.goods_detail_layout_btn);
        goods_pingjia_layout_btn = (RelativeLayout) findViewById(R.id.goods_pingjia_layout_btn);
        seckill_goods_detail_view = (View) findViewById(R.id.seckill_goods_detail_view);
        seckill_goods_pingjia_view = (View) findViewById(R.id.seckill_goods_pingjia_view);
        seckill_share = (ImageView) findViewById(R.id.seckill_share);
        Bundle bundle = getIntent().getExtras();
        seckillListBean = bundle.getParcelable("data");
        Seckill_endtime = bundle.getString("endtime");
        GetSreverTime();
        initPageViewAndWebView(seckillListBean);
        goodsShoppingCartListBean = new ArrayList<>();
    }

    private void initPageViewAndWebView(SeckillListBean seckillListBean) {
        if (seckillListBean.getGoodsPicture() == null) {
            Toast.makeText(this, "请先添加商品的banner图片", Toast.LENGTH_SHORT).show();
        } else {
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
                if (CommonUtils.IsLogin(SecKillGoodsDetailActivity.this)) {
                    Toast.makeText(this, "开始付款啦", Toast.LENGTH_SHORT).show();
                    UnaryBuyNow();
                } else {
                    Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SecKillGoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.goods_detail_layout_btn:
                seckill_goods_detail_view.setVisibility(View.VISIBLE);
                seckill_goods_pingjia_view.setVisibility(View.GONE);
                String detailurl0 = CommonUtils.GetValueByKey(SecKillGoodsDetailActivity.this, "backUrl")
                        + "/moblie/ShareProducts?productId=" + seckillListBean.getGoodsId();
                initwebview(detailurl0, null);
                break;
            case R.id.goods_pingjia_layout_btn:
                seckill_goods_detail_view.setVisibility(View.GONE);
                seckill_goods_pingjia_view.setVisibility(View.VISIBLE);
                String detailurl = CommonUtils.GetValueByKey(SecKillGoodsDetailActivity.this, "backUrl")
                        + "/Moblie/ShowEvaluation?goodsId=" + seckillListBean.getGoodsId();
                //String detailurl = "http://back.aboluomall.com/Moblie/ShowEvaluation?goodsId=12";
                initwebview(detailurl, null);
                break;
            case R.id.seckill_share:
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
                seckillListBean.getGoodsId(), seckillListBean.getGoodsColorId(),
                seckillListBean.getGoodsColor() == null ? "0" : seckillListBean.getGoodsColor().toString(),
                seckillListBean.getGoodsStandardId(), seckillListBean.getGoodsStandard() == null ? "0" : seckillListBean.getGoodsStandard().toString(),
                1, 0, seckillListBean.getGoodsName(), seckillListBean.getGoodsLogo().toString(),
                seckillListBean.getSeckillPrice()
        );
        goodsShoppingCartListBean.add(goodsShoppingCartListBean2);
        Intent intent1 = new Intent(SecKillGoodsDetailActivity.this, MakeOrderActivity.class);
        intent1.putExtra("allmoney", seckillListBean.getSeckillPrice());
        intent1.putExtra("data", goodsShoppingCartListBean);
        intent1.putExtra("payfrom", "3"); //代表从秒杀结算的
        intent1.putExtra("SeckillId", seckillListBean.getSeckillId()); //代表秒杀商品的id
        startActivity(intent1);
    }

    private void ShareSDKGoodsDetail() {
        String detailurl0 = CommonUtils.GetValueByKey(SecKillGoodsDetailActivity.this, "backUrl")
                + "/moblie/ShareProducts?productId=" + seckillListBean.getGoodsId();
        String imgurl = seckillListBean.getGoodsLogo();
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
            oks.setText(seckillListBean.getGoodsName().toString());
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
