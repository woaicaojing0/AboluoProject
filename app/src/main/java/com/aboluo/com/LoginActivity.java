package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.LoginInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CJ on 2016/9/21.
 */

public class LoginActivity extends Activity implements View.OnClickListener, TextWatcher {
    private TextView txt_register; //注册点击
    private TextView txt_retrivepwd;  //忘记密码
    private EditText edit_username, edit_userpwd;
    private Button btn_login;
    private RequestQueue requestQueue;
    private SweetAlertDialog pDialog;
    private static String URL = null;
    private static String APPToken = null;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final int registercode = 1;
    private LinearLayout weixin_login;
    public static final String APP_ID = "wxf933769a912e1313";
    private IWXAPI api;
    private CircleImageView iv_login_touxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        init();
        txt_register.setOnClickListener(this);
        txt_retrivepwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        weixin_login.setOnClickListener(this);
        edit_userpwd.addTextChangedListener(this);
        edit_username.addTextChangedListener(this);
        api = WXAPIFactory.createWXAPI(LoginActivity.this, APP_ID, true);
        api.registerApp(APP_ID);
        Intent intent = getIntent();
        if (intent.getExtras() == null) {
        } else {
            String status = intent.getStringExtra("status");
            if (status.equals("OK")) {
                LoginActivity.this.finish();
            } else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        MyApplication myApplication = new MyApplication(this);
        txt_register = (TextView) findViewById(R.id.txt_register);
        txt_retrivepwd = (TextView) findViewById(R.id.txt_retrivepwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_userpwd = (EditText) findViewById(R.id.edit_userpwd);
        weixin_login = (LinearLayout) findViewById(R.id.weixin_login);
        iv_login_touxiang = (CircleImageView) findViewById(R.id.iv_login_touxiang);
        requestQueue = MyApplication.getRequestQueue();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("登录中");
        pDialog.setCancelable(true);
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        preferences = getSharedPreferences("aboluoInfo", MODE_PRIVATE);
        editor = preferences.edit();
        Picasso.with(this).load(CommonUtils.GetLoginImageURl(this)).error(R.drawable.appstart)
                .into(iv_login_touxiang);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_retrivepwd:
                Intent intent2 = new Intent(this, RetrievePwd0Activity.class);
                startActivity(intent2);
                break;
            case R.id.btn_login:
                pDialog.show();
                final String pwd = edit_userpwd.getText().toString();
                final String name = edit_username.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Login/UserLogin", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        Gson gson = new Gson();
                        LoginInfo loginInfo = gson.fromJson(response, LoginInfo.class);
                        Log.i("woaicoajing", response);
                        pDialog.dismiss();
                        Toast.makeText(LoginActivity.this, loginInfo.getMessage(), Toast.LENGTH_SHORT).show();
                        if (loginInfo.isIsSuccess()) {
                            if (CommonUtils.Login(LoginActivity.this,
                                    name, pwd,
                                    String.valueOf(loginInfo.getResult().getMemberEntity().getMemberId()),
                                    String.valueOf(loginInfo.getResult().getMemberEntity().getIsDealer()))) {
                                if (CommonUtils.LoginImageURl(LoginActivity.this, loginInfo.getResult()
                                        .getMemberEntity().getWechatLogoUrl())) {
                                } else {
                                    Toast.makeText(LoginActivity.this, "头像获取失败", Toast.LENGTH_SHORT).show();
                                }
                                LoginActivity.this.finish();
                            } else {

                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        byte[] ss = error.networkResponse.data;
                        Toast.makeText(LoginActivity.this, new String(ss), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("UserLoginNumber", name);
                        map.put("UserLoginPass", CommonUtils.getMD5(pwd));
                        map.put("UserLoginIP", "192.168.0.150");
                        map.put("LoginChannel", "1");
                        map.put("APPToken", APPToken);
                        map.put("LoginCheckToken", "");
                        map.put("LoginPhone", name);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
                break;
            case R.id.weixin_login:
                // send oauth request
                if (!api.isWXAppInstalled()) {
                    Toast.makeText(LoginActivity.this, "请先安装微信应用", Toast.LENGTH_SHORT).show();
                } else {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "com.aboluo.com";
                    api.sendReq(req);
                    pDialog.show();
                }
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean isok = false;
        if (edit_username.length() > 0) {
            if (edit_userpwd.length() > 0) {
                isok = true;

            }
        }
        btn_login.setEnabled(isok);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case registercode:
                if (data == null) {
                } else {
                    String loginName = data.getStringExtra("LoginName");
                    String LoginPwd = data.getStringExtra("LoginPwd");
                    if (loginName != null && LoginPwd != null) {
                        edit_username.setText(loginName);
                        edit_userpwd.setText(LoginPwd);
                        btn_login.setEnabled(true);
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() == null) {
        } else {
            String status = intent.getStringExtra("status");
            if (status.equals("OK")) {
                pDialog.dismiss();
                this.finish();
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
