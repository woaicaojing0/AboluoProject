package com.aboluo.com.WebActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.com.R;
import com.aboluo.model.BaseModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/12/23.
 */

public class FeedBackActivity extends Activity {
    private WebView feedback_webiview;
    private ImageView feedback_back;
    private Button btn_fedback_submit;
    private EditText et_content_fedback,et_phone_fedback;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
//        feedback_webiview = (WebView) findViewById(R.id.feedback_webiview);
//        feedback_webiview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        feedback_webiview.setVerticalScrollBarEnabled(false);
//        feedback_webiview.setVerticalScrollbarOverlay(false);
//        feedback_webiview.setHorizontalScrollBarEnabled(false);
//        feedback_webiview.setHorizontalScrollbarOverlay(false);
//        //end
//        WebSettings webviewsetting = feedback_webiview.getSettings();
//        webviewsetting.setDomStorageEnabled(true);
//        webviewsetting.setJavaScriptEnabled(true);
//        webviewsetting.setUseWideViewPort(true);//关键点
//        webviewsetting.setLoadWithOverviewMode(true);
//        webviewsetting.setLoadWithOverviewMode(true);
//        //http://t.back.aboluomall.com/Moblie/FeedBack?memberId=486
//        Log.i("feedbackurl>>", CommonUtils.GetValueByKey(this, "backUrl")+"/Moblie/FeedBack?memberId=" + MemberId);
//        feedback_webiview.loadUrl(CommonUtils.GetValueByKey(this, "backUrl")+"/Moblie/FeedBack?memberId=" + MemberId);
//        feedback_webiview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//        });
        init();
        feedback_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_fedback_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content_fedback.getText().toString();
                String phone = et_content_fedback.getText().toString();
                if(content.equals("")||phone.equals(""))
                {
                    Toast.makeText(FeedBackActivity.this, "请填写相关内容", Toast.LENGTH_SHORT).show();
                }else
                {
                    initData(phone,content);
                }
            }
        });
    }
    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        feedback_back = (ImageView) findViewById(R.id.feedback_back);
        btn_fedback_submit = (Button) findViewById(R.id.btn_fedback_submit);
        et_content_fedback = (EditText) findViewById(R.id.et_content_fedback);
        et_phone_fedback = (EditText) findViewById(R.id.et_phone_fedback);
    }
    private void initData(final String ContactPhoneNumber, final String Content) {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL+"/api/ActiveApi/ReceiveQuestion", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\","");
                response = response.substring(1,response.length()-1);
                BaseModel baseModel = new BaseModel();
                baseModel = gson.fromJson(response,BaseModel.class);
                if(baseModel.isIsSuccess())
                {
                    finish();
                    Toast.makeText(FeedBackActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(FeedBackActivity.this, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("Content", Content);
                map.put("ContactPhoneNumber", ContactPhoneNumber);
                map.put("LoginPhone", "123");
                map.put("LoginCheckToken", "123");
                map.put("APPToken", APPToken);
                return map;
            };
        };
        requestQueue.add(stringRequest);
    }
}
