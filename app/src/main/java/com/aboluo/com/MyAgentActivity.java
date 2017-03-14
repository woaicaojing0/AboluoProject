package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ProgressWebView;
import com.aboluo.com.WebActivity.AgentDetailActivity;


/**
 * Created by cj34920 on 2016/12/28.
 * 我的代理包括一级代理、二级代理、三级代理
 */

public class MyAgentActivity extends Activity implements View.OnClickListener {
    private LinearLayout agent_one, agent_two,agent_three;
    private ImageView agent_back,iv_agent_three,iv_agent_two,iv_agent_one;
    private ProgressWebView wv_agent_detail;
    private TextView tv_one,tv_two,tv_three;
    private String MemberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_agent);
        init();
        agent_back.setOnClickListener(this);
        agent_one.setOnClickListener(this);
        agent_two.setOnClickListener(this);
        agent_three.setOnClickListener(this);
        wv_agent_detail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_agent_detail.setVerticalScrollBarEnabled(false);
        wv_agent_detail.setHorizontalScrollBarEnabled(false);
        //end
        WebSettings webviewsetting = wv_agent_detail.getSettings();
        webviewsetting.setDomStorageEnabled(true);
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        AgentLevel(1);
        cleanPic();
        iv_agent_one.setImageResource( R.drawable.agent_bg);
        tv_one.setTextColor(Color.parseColor("#f63995"));

    }

    private void init() {
        agent_one = (LinearLayout) findViewById(R.id.agent_one);
        agent_two = (LinearLayout) findViewById(R.id.agent_two);
        agent_three = (LinearLayout) findViewById(R.id.agent_three);
        agent_back = (ImageView) findViewById(R.id.agent_back);
        iv_agent_three = (ImageView) findViewById(R.id.iv_agent_three);
        iv_agent_two = (ImageView) findViewById(R.id.iv_agent_two);
        iv_agent_one = (ImageView) findViewById(R.id.iv_agent_one);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        wv_agent_detail = (ProgressWebView) findViewById(R.id.wv_agent_detail);
        MemberId = CommonUtils.GetMemberId(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agent_one:
                cleanPic();
                iv_agent_one.setImageResource( R.drawable.agent_bg);
                tv_one.setTextColor(Color.parseColor("#f63995"));
                AgentLevel(1);
                break;
            case R.id.agent_two:
                cleanPic();
                iv_agent_two.setImageResource(R.drawable.agent_bg);
                tv_two.setTextColor(Color.parseColor("#f63995"));
                AgentLevel(2);
                break;
            case R.id.agent_three:
                cleanPic();
                iv_agent_three.setImageResource(R.drawable.agent_bg);
                tv_three.setTextColor(Color.parseColor("#f63995"));
                AgentLevel(3);
                break;
            case R.id.agent_back:
                finish();
                break;
        }
    }

    private void AgentLevel(int level) {
        switch (level) {
            case 1:
                Log.i("agentdetail>>" + level,
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 1 + "&pageindex=1");
                wv_agent_detail.loadUrl(
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 1 + "&pageindex=1");
                break;
            case 2:
                Log.i("agentdetail>>" + level,
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 2 + "&pageindex=1");
                wv_agent_detail.loadUrl(
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 2 + "&pageindex=1");
                break;
            case 3:
                Log.i("agentdetail>>" + level,
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 3 + "&pageindex=1");
                wv_agent_detail.loadUrl(
                        CommonUtils.GetValueByKey(this, "backUrl") + "/Moblie/ShowPersonReferents?memberid=" + MemberId
                                + "&referrerId=" + 3 + "&pageindex=1");
                break;
            default:
                Log.d("AgentDetailActivity", "当前代理等级" + level + "");
                break;
        }
    }

    private  void cleanPic()
    {
        iv_agent_three.setImageResource(R.drawable.agent_bg_normal);
        iv_agent_two.setImageResource(R.drawable.agent_bg_normal);
        iv_agent_one.setImageResource(R.drawable.agent_bg_normal);
        tv_two.setTextColor(Color.parseColor("#ACACB6"));
        tv_one.setTextColor(Color.parseColor("#ACACB6"));
        tv_three.setTextColor(Color.parseColor("#ACACB6"));
    }
}
