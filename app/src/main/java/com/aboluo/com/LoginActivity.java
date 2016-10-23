package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.LoginInfo;
import com.aboluo.model.RegisterInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/9/21.
 */

public class LoginActivity extends Activity  implements View.OnClickListener,TextWatcher{
   private TextView txt_register; //注册点击
    private TextView txt_retrivepwd;  //忘记密码
    private EditText edit_username,edit_userpwd;
    private Button btn_login;
    private RequestQueue requestQueue;
    private SweetAlertDialog pDialog;
    private static  String URL =null;
    private static  String APPToken = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        init();
        txt_register.setOnClickListener(this);
        txt_retrivepwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        edit_userpwd.addTextChangedListener(this);
        edit_username.addTextChangedListener(this);
    }
    private  void init()
    {
        txt_register = (TextView) findViewById(R.id.txt_register);
        txt_retrivepwd = (TextView) findViewById(R.id.txt_retrivepwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_userpwd = (EditText) findViewById(R.id.edit_userpwd);
        requestQueue = MyApplication.getRequestQueue();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("登录中");
        pDialog.setCancelable(true);
        URL =CommonUtils.GetValueByKey(this,"apiurl");
        APPToken =CommonUtils.GetValueByKey(this,"APPToken");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_retrivepwd:
                Intent intent2 = new Intent(this,RetrievepwdActivity.class);
                startActivity(intent2);
                break;
            case  R.id.btn_login:
                pDialog.show();
                final String pwd = edit_userpwd.getText().toString();
                final String name = edit_username.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"/api/Login/UserLogin", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG",response);
                        response=response.replace("\\", "");//去掉'/'
                        response=response.substring(1, response.length()-1); //去掉头尾引号。
                        Gson gson = new Gson();
                        LoginInfo loginInfo =  gson.fromJson(response, LoginInfo.class);
                        Log.i("woaicoajing",response);
                        pDialog.dismiss();
                        Toast.makeText(LoginActivity.this, loginInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("UserLoginNumber", name);
                        map.put("UserLoginPass", CommonUtils.getMD5(pwd));
                        map.put("UserLoginIP", "192.168.0.150");
                        map.put("LoginChannel","1");
                        map.put("APPToken", APPToken);
                        map.put("LoginCheckToken", "");
                        map.put("LoginPhone", name);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
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
        if(edit_username.length()>0)
        {
            if(edit_userpwd.length()>0)
            {isok = true;

            }
        }
        btn_login.setEnabled(isok);

    }
}
