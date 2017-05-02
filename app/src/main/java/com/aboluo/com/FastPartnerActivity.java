package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ProgressWebView;
import com.aboluo.model.FastParnterBean;
import com.aboluo.model.FastParnterMakeOrderBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2017/2/17.
 */

public class FastPartnerActivity extends Activity {
    Button btn_fastpartner;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private FastParnterBean fastParnterBean;
    private FastParnterMakeOrderBean fastParnterMakeOrderBean;
    private TextView tv_Amount, fast_parnter_title;
    private ImageView iv_fastpartner, iv_fastpartner_back;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private ProgressWebView wv_fastParnter;
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastpartner);
        init();
        btn_fastpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastParnterBean == null) {
                    Toast.makeText(FastPartnerActivity.this, "请刷新重试", Toast.LENGTH_SHORT).show();
                } else {
                    fastParnterMakeOrder();
                }
            }
        });
        iv_fastpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FastPartnerActivity.this, GoodsDetailImageActivity.class);
                ArrayList<String> listimage = new ArrayList<String>();
                listimage.add(String.valueOf(R.drawable.fastparnter));
                intent.putStringArrayListExtra("imgeurl", listimage);
                intent.putExtra("position", 0);
                String transitionName = "images";
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(FastPartnerActivity.this, v, transitionName);
                startActivity(intent, activityOptionsCompat.toBundle());
            }
        });
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
            }
        });
        iv_fastpartner_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        webUrl = CommonUtils.GetValueByKey(this, "backUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl2");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        btn_fastpartner = (Button) findViewById(R.id.btn_fastpartner);
        tv_Amount = (TextView) findViewById(R.id.tv_Amount);
        fast_parnter_title = (TextView) findViewById(R.id.fast_parnter_title);
        iv_fastpartner = (ImageView) findViewById(R.id.iv_fastpartner);
        iv_fastpartner_back = (ImageView) findViewById(R.id.iv_fastpartner_back);
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.sv_fastparnter);
        wv_fastParnter = (ProgressWebView) findViewById(R.id.wv_fastParnter);
        initData();
        initWebView(webUrl + "/Moblie/ShowHelpCenter?helpMenuId=10");
    }

    private void initData() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL +
                "/api/PartnerApi/ReceivePartner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                fastParnterBean = gson.fromJson(response, FastParnterBean.class);
                if (fastParnterBean.isIsSuccess()) {
                    btn_fastpartner.setText(fastParnterBean.getTypeName());
                    //fast_parnter_title.setText(fastParnterBean.getTypeName());
                    tv_Amount.setText(fastParnterBean.getAmount() + "");
                    Toast.makeText(FastPartnerActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FastPartnerActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
                pullToRefreshScrollView.onRefreshComplete();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] htmlBodyBytes = error.networkResponse.data;
//                //Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
                //sweetAlertDialog.dismiss();
                pdialog.dismiss();
                pullToRefreshScrollView.onRefreshComplete();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    private void fastParnterMakeOrder() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/PartnerApi/PartnerOrder", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                fastParnterMakeOrderBean = gson.fromJson(response, FastParnterMakeOrderBean.class);
                //fastParnterBean = gson.fromJson(response, FastParnterBean.class);
                if (fastParnterMakeOrderBean.isIsSuccess()) {
                    Toast.makeText(FastPartnerActivity.this, fastParnterMakeOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FastPartnerActivity.this, OrderPayActivity.class);
                    intent.putExtra("payMoney", String.valueOf(fastParnterMakeOrderBean.getAmount()));
                    intent.putExtra("OrderNum", String.valueOf(fastParnterMakeOrderBean.getSerialId().toString()));
                    intent.putExtra("payfrom", "6");
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(FastPartnerActivity.this, fastParnterMakeOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] htmlBodyBytes = error.networkResponse.data;
                //Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
//                //sweetAlertDialog.dismiss();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("Amount", String.valueOf(fastParnterBean.getAmount()));
                map.put("PartnerType", String.valueOf(fastParnterBean.getPartnerType()));
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        } else {
            finish();
        }
    }

    /**
     * 初始化底部webivew（获取数据之后）
     */
    private void initWebView(String detailUrl) {
        //详情地址
        //解决了webview 头部空了一片白的问题
        wv_fastParnter.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_fastParnter.setVerticalScrollBarEnabled(false);
        wv_fastParnter.setHorizontalScrollBarEnabled(false);
        //end
        WebSettings webViewSetting = wv_fastParnter.getSettings();
        webViewSetting.setDomStorageEnabled(true);
        webViewSetting.setJavaScriptEnabled(true);
        webViewSetting.setUseWideViewPort(true);//关键点
        webViewSetting.setLoadWithOverviewMode(true);
        wv_fastParnter.loadUrl(detailUrl);
        wv_fastParnter.setWebViewClient(new WebViewClient() {
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
                wv_fastParnter.measure(w, h);
            }
        });

    }
}
