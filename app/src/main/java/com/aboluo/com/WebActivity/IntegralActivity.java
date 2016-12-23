package com.aboluo.com.WebActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.R;

/**
 * Created by CJ on 2016/12/23.
 */

public class IntegralActivity extends Activity {
    private WebView integral_webiview;
    private String MemberId;
    private ImageView integral_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        MemberId = CommonUtils.GetMemberId(this);
        integral_webiview = (WebView) findViewById(R.id.integral_webiview);
        integral_back = (ImageView) findViewById(R.id.integral_back);
        integral_webiview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        integral_webiview.setVerticalScrollBarEnabled(false);
        integral_webiview.setVerticalScrollbarOverlay(false);
        integral_webiview.setHorizontalScrollBarEnabled(false);
        integral_webiview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = integral_webiview.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        webviewsetting.setLoadWithOverviewMode(true);
        Log.i("integral>>", CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/MoneyLog?memberId=" + MemberId);
        integral_webiview.loadUrl(CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/MoneyLog?memberId=" + MemberId);
        integral_webiview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        integral_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
