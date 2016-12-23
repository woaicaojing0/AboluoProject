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

public class FeedBackActivity extends Activity {
    private WebView feedback_webiview;
    private String MemberId;
    private ImageView feedback_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        MemberId = CommonUtils.GetMemberId(this);
        feedback_webiview = (WebView) findViewById(R.id.feedback_webiview);
        feedback_back = (ImageView) findViewById(R.id.feedback_back);
        feedback_webiview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        feedback_webiview.setVerticalScrollBarEnabled(false);
        feedback_webiview.setVerticalScrollbarOverlay(false);
        feedback_webiview.setHorizontalScrollBarEnabled(false);
        feedback_webiview.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = feedback_webiview.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        webviewsetting.setLoadWithOverviewMode(true);
        //http://t.back.aboluomall.com/Moblie/FeedBack?memberId=486
        Log.i("feedbackurl>>", CommonUtils.GetValueByKey(this, "backUrl")+"/Moblie/FeedBack?memberId=" + MemberId);
        feedback_webiview.loadUrl(CommonUtils.GetValueByKey(this, "backUrl")+"/Moblie/FeedBack?memberId=" + MemberId);
        feedback_webiview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        feedback_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
