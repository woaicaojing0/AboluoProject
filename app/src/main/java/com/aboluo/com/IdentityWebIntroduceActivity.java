package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aboluo.XUtils.ProgressWebView;

/**
 * Created by cj on 2017/6/14.
 * 会员积分介绍界面webiview
 */

public class IdentityWebIntroduceActivity extends Activity {
    private ProgressWebView wv_group_introduce;
    private ImageView iv_group_introduce_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identitywebintroduce);
        Intent intent = getIntent();
        String address = intent.getStringExtra("url");
        wv_group_introduce = (ProgressWebView) findViewById(R.id.wv_group_introduce);
        iv_group_introduce_back = (ImageView) findViewById(R.id.iv_group_introduce_back);
        wv_group_introduce.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_group_introduce.setVerticalScrollBarEnabled(false);
        wv_group_introduce.setHorizontalScrollBarEnabled(false);
        //end
        wv_group_introduce.setWebViewClient(new WebViewClient()
        {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webviewsetting = wv_group_introduce.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        wv_group_introduce.loadUrl(address);
        iv_group_introduce_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
