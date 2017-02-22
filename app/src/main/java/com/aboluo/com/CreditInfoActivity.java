package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.com.WebActivity.CapitalActivity;
import com.aboluo.com.WebActivity.IntegralActivity;
import com.aboluo.model.CreditInfoBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CJ on 2016/12/19.
 * 我的钱包界面
 */

public class CreditInfoActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private CreditInfoBean creditInfoBean;
    private TextView totalmoney, memberlevel, totalscore, freeMoney, CanUserMoney, totalscore2,
            CanUserScore, cred_nickName;
    private CircleImageView cred_userImage;
    private LinearLayout creditinfo_individual, creditinfo_capital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditinfo);
        init();
        creditinfo_individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreditInfoActivity.this, IntegralActivity.class);
                startActivity(intent);
            }
        });
        creditinfo_capital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreditInfoActivity.this, CapitalActivity.class);
                startActivity(intent);
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
        totalmoney = (TextView) findViewById(R.id.totalmoney);
        memberlevel = (TextView) findViewById(R.id.memberlevel);
        totalscore = (TextView) findViewById(R.id.totalscore);
        freeMoney = (TextView) findViewById(R.id.freeMoney);
        CanUserMoney = (TextView) findViewById(R.id.CanUserMoney);
        totalscore2 = (TextView) findViewById(R.id.totalscore2);
        CanUserScore = (TextView) findViewById(R.id.CanUserScore);
        cred_nickName = (TextView) findViewById(R.id.cred_nickName);
        cred_userImage = (CircleImageView) findViewById(R.id.cred_userImage);
        creditinfo_individual = (LinearLayout) findViewById(R.id.creditinfo_individual);
        creditinfo_capital = (LinearLayout) findViewById(R.id.creditinfo_capital);
        initData();
    }

    private void initData() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/ReceiveMemberAccount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                creditInfoBean = gson.fromJson(response, CreditInfoBean.class);
                if (creditInfoBean.isIsSuccess()) {
                    totalmoney.setText("￥" + creditInfoBean.getTotalMoney());
                    memberlevel.setText(creditInfoBean.getIsLeader() == 0 ? "普通用户" : "合伙人");
                    totalscore.setText("￥" + creditInfoBean.getTotalScore());
                    freeMoney.setText("￥" + creditInfoBean.getFreeMoney());
                    CanUserMoney.setText("￥" + creditInfoBean.getCanUseMoney());
                    totalscore2.setText("￥" + creditInfoBean.getTotalScore());
                    CanUserScore.setText("￥" + creditInfoBean.getCanUseScore());
                    cred_nickName.setText("亲爱的:" + creditInfoBean.getNickName() == null ? "" :
                            creditInfoBean.getNickName().toString());
                    Log.i("CreditInfoActivity", (creditInfoBean.getMemberLogo() == null ? "" :
                            creditInfoBean.getMemberLogo().toString()));
                    String url = CommonUtils.GetLoginImageURl(CreditInfoActivity.this);
                    if (url.equals("") || url.equals("0")) {
                        picasso.load(R.drawable.appstart)
                                .placeholder(R.drawable.image_placeholder)
                                .error(R.drawable.imageview_error).into(cred_userImage);
                    } else {
                        picasso.load(CommonUtils.GetLoginImageURl(CreditInfoActivity.this)).placeholder(
                                R.drawable.image_placeholder).error(R.drawable.imageview_error)
                                .into(cred_userImage);
                    }
                } else {

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
                map.put("APPToken", APPToken);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }
}
