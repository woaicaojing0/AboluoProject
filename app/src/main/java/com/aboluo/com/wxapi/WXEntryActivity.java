package com.aboluo.com.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.LoginInfo;
import com.aboluo.model.WXTokenBean;
import com.aboluo.model.WXUserBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.paysdk.login.LoginActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.tencent.mm.sdk.constants.ConstantsAPI.COMMAND_SENDAUTH;
import static com.tencent.mm.sdk.constants.ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX;

/**
 * Created by CJ on 2016/12/20.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private IWXAPI api;
    private String APP_ID;
    private String AppSecret;
    private WXTokenBean wxTokenBean;
    private WXUserBean wxUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    private void init() {
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
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case COMMAND_SENDAUTH:
                        //登录回调,处理登录成功的逻辑
                        String code = ((SendAuth.Resp) baseResp).code; //即为所需的code
                        GetAsses_Token(code);
                        break;
                    case COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                        Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }

                break;
            default:
                finish();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(getIntent(), WXEntryActivity.this);  //
    }

    private void GetAsses_Token(final String code) {
        pdialog.show();
        APP_ID = CommonUtils.GetValueByKey(this, "APP_ID");
        AppSecret = CommonUtils.GetValueByKey(this, "AppSecret");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID
                        + "&secret=" + AppSecret
                        + "&code=" + code
                        + "&grant_type=authorization_code", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                wxTokenBean = gson.fromJson(response, WXTokenBean.class);
                if (wxTokenBean.getErrmsg() == null) {
                    GetUserInfo(wxTokenBean.getAccess_token(), wxTokenBean.getOpenid());
                } else {
                    pdialog.dismiss();
                    finish();
                    Toast.makeText(WXEntryActivity.this, wxTokenBean.getErrmsg().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                finish();
                Toast.makeText(WXEntryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void GetUserInfo(String token, String openid) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        WXUserBean wxUserBean1 = gson.fromJson(response, WXUserBean.class);
                        WXLoginIn(wxUserBean1);
                        Toast.makeText(WXEntryActivity.this, "你好：" + wxUserBean1.getNickname().toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                finish();
                Toast.makeText(WXEntryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void WXLoginIn(final WXUserBean wxUserBean) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Login/WeChatLogin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");//去掉'/'
                response = response.substring(1, response.length() - 1); //去掉头尾引号。
                LoginInfo loginInfo = gson.fromJson(response, LoginInfo.class);
                if (loginInfo.isIsSuccess()) {
                    Intent intent = new Intent(WXEntryActivity.this, com.aboluo.com.LoginActivity.class);
                    if (CommonUtils.Login(WXEntryActivity.this, "", "",
                            String.valueOf(loginInfo.getResult().getMemberEntity().getMemberId()),
                            String.valueOf(loginInfo.getResult().getMemberEntity().getIsDealer()))) {
                        //使用哪边的头像，是微信的还是七牛云的
                        if (loginInfo.getResult().getMemberEntity().getWechatLogoUrl() == null) {
                            CommonUtils.LoginImageURl(WXEntryActivity.this, wxUserBean.getHeadimgurl());
                        } else if (loginInfo.getResult().getMemberEntity().getWechatLogoUrl().length() == 0) {
                            CommonUtils.LoginImageURl(WXEntryActivity.this, wxUserBean.getHeadimgurl());
                        } else {
                            CommonUtils.LoginImageURl(WXEntryActivity.this, loginInfo.getResult().getMemberEntity()
                                    .getWechatLogoUrl());
                        }
                        intent.putExtra("status", "OK");
                        startActivity(intent);
                        finish();
                        pdialog.dismiss();
                    } else {

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                byte[] ss = error.networkResponse.data;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("OpenId", wxUserBean.getOpenid());
                map.put("NickName", wxUserBean.getNickname());
                map.put("LoginPhone", "123");
                map.put("LoginCheckToken", "123");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
