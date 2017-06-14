package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.MemberPointBean;
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

/**
 * Created by cj on 2017/6/13.
 * 点击合伙人进入的身份说明页面
 */

public class IdentityIntroduceActivity extends Activity {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private MemberPointBean memberPointBean;
    private String webUrl;
    private RelativeLayout relative_farther;
    private LinearLayout linelayout_child;
    private TextView tv_group_value, tv_introduce_groupup, tv_need_groupup, tv_next_level;
    private ImageView iv_level1, iv_level2, iv_level3, iv_level4, iv_level5, iv_level6, iv_level7;
    private String nickName, userNum;
    private TextView tv_user_name, tv_user_id, tv_my_huiyuan, tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identityintroduce);
        init();
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl3");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        relative_farther = (RelativeLayout) findViewById(R.id.relative_farther);
        linelayout_child = (LinearLayout) findViewById(R.id.linelayout_child);
        tv_group_value = (TextView) findViewById(R.id.tv_group_value);
        tv_need_groupup = (TextView) findViewById(R.id.tv_need_groupup);
        tv_next_level = (TextView) findViewById(R.id.tv_next_level);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_id = (TextView) findViewById(R.id.tv_user_id);
        tv_my_huiyuan = (TextView) findViewById(R.id.tv_my_huiyuan);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_introduce_groupup = (TextView) findViewById(R.id.tv_introduce_groupup);
        iv_level1 = (ImageView) findViewById(R.id.mv_level1);
        iv_level2 = (ImageView) findViewById(R.id.mv_level2);
        iv_level3 = (ImageView) findViewById(R.id.mv_level3);
        iv_level4 = (ImageView) findViewById(R.id.mv_level4);
        iv_level5 = (ImageView) findViewById(R.id.mv_level5);
        iv_level6 = (ImageView) findViewById(R.id.mv_level6);
        iv_level7 = (ImageView) findViewById(R.id.mv_level7);

        initData();
        tv_introduce_groupup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webUrl == null) {
                    Toast.makeText(IdentityIntroduceActivity.this, "请退出重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(IdentityIntroduceActivity.this, IdentityWebIntroduceActivity.class);
                intent.putExtra("url", webUrl);
                startActivity(intent);
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/RecieveMemberGrowInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                memberPointBean = gson.fromJson(response, MemberPointBean.class);
                webUrl = memberPointBean.getGrowUrlAddress();
                ViewTreeObserver vto2 = relative_farther.getViewTreeObserver();
                vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        relative_farther.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        ViewGroup.LayoutParams para = linelayout_child.getLayoutParams();
                        double all = memberPointBean.getCommonLevelNeed();
                        double i = memberPointBean.getCommonLevelScroes();
                        para.width = (int) (((relative_farther.getWidth()) * i) / all);
                        tv_group_value.setText(String.valueOf((int) i) + "/" + String.valueOf((int) all));
                        linelayout_child.setLayoutParams(para);
                    }
                });
                double shengyu = memberPointBean.getCommonLevelNeed() - memberPointBean.getCommonLevelScroes();
                tv_need_groupup.setText(String.valueOf((int) shengyu));
                tv_user_name.setText(memberPointBean.getMemberNickName());
                tv_user_id.setText(memberPointBean.getMemberId());
                String userIdentity;
                switch (memberPointBean.getMemberLevel()) {
                    case 0:
                        userIdentity = "普通会员";
                        break;
                    case 1:
                        userIdentity = "合伙人";
                        break;
                    case 2:
                        userIdentity = "金牌合伙人";
                        break;
                    default:
                        userIdentity = "普通会员";
                        break;
                }
                tv_next_level.setText(userIdentity);
                tv_my_huiyuan.setText(userIdentity);
                setImageSrc(memberPointBean.getMemberGrade());
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                Toast.makeText(IdentityIntroduceActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    private void setImageSrc(int id) {
        switch (id) {
            case 0:
                tv_next_level.setText("V1");
                break;
            case 1:
                tv_next_level.setText("V2");
                iv_level2.setImageResource(R.drawable.v1_selected);
                break;
            case 2:
                tv_next_level.setText("S1");
                iv_level2.setImageResource(R.drawable.v1_selected);
                iv_level3.setImageResource(R.drawable.v2_selected);
                break;
            case 3:
                tv_next_level.setText("S2");
                iv_level2.setImageResource(R.drawable.v1_selected);
                iv_level3.setImageResource(R.drawable.v2_selected);
                iv_level4.setImageResource(R.drawable.s1_selected);
                break;
            case 4:
                tv_next_level.setText("S3");
                iv_level2.setImageResource(R.drawable.v1_selected);
                iv_level3.setImageResource(R.drawable.v2_selected);
                iv_level4.setImageResource(R.drawable.s1_selected);
                iv_level5.setImageResource(R.drawable.s2_selected);
                break;
            case 5:
                tv_next_level.setText("S4");
                iv_level2.setImageResource(R.drawable.v1_selected);
                iv_level3.setImageResource(R.drawable.v2_selected);
                iv_level4.setImageResource(R.drawable.s1_selected);
                iv_level5.setImageResource(R.drawable.s2_selected);
                iv_level6.setImageResource(R.drawable.s3_selected);
                break;
            case 6:
                tv_next_level.setText("最高级");
                iv_level2.setImageResource(R.drawable.v1_selected);
                iv_level3.setImageResource(R.drawable.v2_selected);
                iv_level4.setImageResource(R.drawable.s1_selected);
                iv_level5.setImageResource(R.drawable.s2_selected);
                iv_level6.setImageResource(R.drawable.s3_selected);
                iv_level7.setImageResource(R.drawable.s4_selected);
                break;
            case 7:
                break;
            default:
                break;

        }
    }
}
