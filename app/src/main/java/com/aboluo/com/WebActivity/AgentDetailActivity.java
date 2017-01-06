package com.aboluo.com.WebActivity;

import android.app.Activity;
import android.content.Intent;
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

public class AgentDetailActivity extends Activity {
    private WebView agentdetail_webiview;
    private String MemberId;
    private ImageView agentdetail_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_detail);
        MemberId = CommonUtils.GetMemberId(this);
        agentdetail_webiview = (WebView) findViewById(R.id.agentdetail_webiview);
        agentdetail_back = (ImageView) findViewById(R.id.agentdetail_back);
        agentdetail_webiview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        agentdetail_webiview.setVerticalScrollBarEnabled(false);
        agentdetail_webiview.setHorizontalScrollBarEnabled(false);
        //end
        WebSettings webviewsetting = agentdetail_webiview.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        agentdetail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        int i = intent.getIntExtra("AgentDetailLevel", 1);
        agentdetail_webiview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        AgentLevel(i);
    }

    //http://t.back.aboluomall.com/Moblie/ShowPersonReferents?memberid=1976&referrerId=1&pageindex=1
    private void AgentLevel(int level) {
        switch (level) {
            case 1:
                Log.i("agentdetail>>" + level,
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 1 + "&pageindex=1");
                agentdetail_webiview.loadUrl(
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 1 + "&pageindex=1");
                break;
            case 2:
                Log.i("agentdetail>>" + level,
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 2 + "&pageindex=1");
                agentdetail_webiview.loadUrl(
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 2 + "&pageindex=1");
                break;
            case 3:
                Log.i("agentdetail>>" + level,
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 3 + "&pageindex=1");
                agentdetail_webiview.loadUrl(
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 3 + "&pageindex=1");
                break;
            default:
                Log.d("AgentDetailActivity", "当前代理等级" + level + "");
                break;
        }
    }
}
