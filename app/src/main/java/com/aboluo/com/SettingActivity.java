package com.aboluo.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CleanUtils;
import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2017/1/15.
 */

public class SettingActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_setting_aboutus, rl_setting_cleancache, rl_setting_update;
    private ImageView iv_setting_back;
    private TextView tv_cache_size;
    private SweetAlertDialog pdialog;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private String MemberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settting);
        init();
        rl_setting_aboutus.setOnClickListener(this);
        rl_setting_cleancache.setOnClickListener(this);
        iv_setting_back.setOnClickListener(this);
        try {
            tv_cache_size.setText(CleanUtils.getTotalCacheSize(SettingActivity.this).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("清理中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        rl_setting_aboutus = (RelativeLayout) findViewById(R.id.rl_setting_aboutus);
        rl_setting_cleancache = (RelativeLayout) findViewById(R.id.rl_setting_cleancache);
        rl_setting_update = (RelativeLayout) findViewById(R.id.rl_setting_update);
        iv_setting_back = (ImageView) findViewById(R.id.iv_setting_back);
        tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_aboutus:
                Intent intent = new Intent(SettingActivity.this, CompanyIntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_cleancache:
                pdialog.show();
                if (CleanUtils.cleanInternalCache()) {
                    Toast.makeText(this, "清理缓存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "清理缓存失败", Toast.LENGTH_SHORT).show();
                }
                try {
                    tv_cache_size.setText(CleanUtils.getTotalCacheSize(SettingActivity.this).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pdialog.dismiss();
                break;
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.rl_setting_update:
                getVersionInfo();
                break;
        }

    }

    ;

    private void getVersionInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1975");
                map.put("OrderId", "508");
                map.put("ExpressId", "1");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 返回当前程序版本名
     */
    @Nullable
    public static PackageInfo getAppVersionName(Context context) {
        int versioncode = 0;
        String versionName = "";
        PackageInfo pi = null;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName; //版本名称
            versioncode = pi.versionCode; //版本id
            if (versionName == null || versionName.length() <= 0) {
                return null;
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return pi;
    }

    /**
     * 打开浏览器下载
     */
    public void UpdateAPP(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}
