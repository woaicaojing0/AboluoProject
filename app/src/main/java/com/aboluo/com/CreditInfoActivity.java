package com.aboluo.com;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class CreditInfoActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private CreditInfoBean creditInfoBean;
    private TextView totalmoney, memberlevel, totalscore, freeMoney, CanUserMoney, tv_totalTurnover,
            CanUserScore, cred_nickName, tv_parnter_month_money, tv_my_month_money;
    private CircleImageView cred_userImage;
    private LinearLayout creditinfo_individual, creditinfo_capital, ll_back, ll_tixian;
    private AlertDialog.Builder builder;
    private View cunstomView;
    private LinearLayout ll_showtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditinfo);
        init();
        creditinfo_individual.setOnClickListener(this);
        creditinfo_capital.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        ll_tixian.setOnClickListener(this);
        ll_showtitle.setOnClickListener(this);
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
        tv_totalTurnover = (TextView) findViewById(R.id.tv_totalTurnover);
        CanUserScore = (TextView) findViewById(R.id.CanUserScore);
        cred_nickName = (TextView) findViewById(R.id.cred_nickName);
        ll_showtitle = (LinearLayout) findViewById(R.id.ll_showtitle);
        tv_parnter_month_money = (TextView) findViewById(R.id.tv_parnter_month_money);
        tv_my_month_money = (TextView) findViewById(R.id.tv_my_month_money);
        cred_userImage = (CircleImageView) findViewById(R.id.cred_userImage);
        creditinfo_individual = (LinearLayout) findViewById(R.id.creditinfo_individual);
        creditinfo_capital = (LinearLayout) findViewById(R.id.creditinfo_capital);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_tixian = (LinearLayout) findViewById(R.id.ll_tixian);
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
                    switch (creditInfoBean.getIsLeader()) {
                        case 0:
                            memberlevel.setText("普通会员");
                            break;
                        case 1:
                            memberlevel.setText("合伙人");
                            break;
                        case 2:
                            memberlevel.setText("金牌合伙人");
                            break;
                        default:
                            memberlevel.setText("普通会员");
                            break;
                    }
                    totalscore.setText("￥" + creditInfoBean.getTotalScore());
                    freeMoney.setText("￥" + creditInfoBean.getFreeMoney());
                    CanUserMoney.setText("￥" + creditInfoBean.getCanUseMoney());
                    tv_totalTurnover.setText("￥" + creditInfoBean.getTotalTurnover());
                    CanUserScore.setText("￥" + creditInfoBean.getCanUseScore());
                    tv_parnter_month_money.setText("￥" + creditInfoBean.getPartnersMonthlyTurnover());
                    tv_my_month_money.setText("￥" + creditInfoBean.getPersonalMonthlyTurnover());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tixian:
                cunstomView = LayoutInflater.from(this).inflate(R.layout.customtitle, null);
                final Bundle bundle = getIntent().getExtras();
                if ("0".equals(bundle.get("email")) && "0".equals(bundle.get("phone"))) {
                    Toast.makeText(this, "请先至个人信息绑定手机号或者邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                builder = new AlertDialog.Builder(CreditInfoActivity.this);
                //    指定下拉列表的显示数据
                final String[] cities = {"银行卡", "支付宝"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(CreditInfoActivity.this, ExtractMoneyActivity.class);
                                intent.putExtra("fromType", 0);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(CreditInfoActivity.this, ExtractMoneyActivity.class);
                                intent1.putExtra("fromType", 1);
                                intent1.putExtras(bundle);
                                startActivity(intent1);
                                break;
                            //dialog.dismiss();
                        }
                        //Toast.makeText(MyInfoAcitvity.this, "选择的城市为：" + cities[which], Toast.LENGTH_SHORT).show();
                    }
                }).setCustomTitle(cunstomView);
                builder.show();
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.creditinfo_individual:
                Intent intent = new Intent(CreditInfoActivity.this, IntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.creditinfo_capital:
                Intent intent2 = new Intent(CreditInfoActivity.this, CapitalActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_showtitle:
                Toast.makeText(this, "合伙人商品月营业额", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
