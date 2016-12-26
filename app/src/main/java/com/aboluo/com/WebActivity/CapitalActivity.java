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

public class CapitalActivity extends Activity {
    private WebView capital_webiview;
    private String MemberId;
    private ImageView capital_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital);
        MemberId = CommonUtils.GetMemberId(this);
        capital_webiview = (WebView) findViewById(R.id.capital_webiview);
        capital_back = (ImageView) findViewById(R.id.capital_back);
        capital_webiview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        capital_webiview.setVerticalScrollBarEnabled(false);
        capital_webiview.setVerticalScrollbarOverlay(false);
        capital_webiview.setHorizontalScrollBarEnabled(false);
        capital_webiview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = capital_webiview.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        webviewsetting.setLoadWithOverviewMode(true);
        Log.i("integral>>", CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/MoneyLog?memberId=" + MemberId);
        capital_webiview.loadUrl(CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/MoneyLog?memberId=" + MemberId);
        //capital_webiview.loadUrl("http://t.back.aboluomall.com/Moblie/ScoreLog?memberId" + MemberId);
        capital_webiview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        capital_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
