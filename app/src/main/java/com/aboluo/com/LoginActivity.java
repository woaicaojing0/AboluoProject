package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJ on 2016/9/21.
 */

public class LoginActivity extends Activity  implements View.OnClickListener{
   private TextView txt_register; //注册点击
    private TextView txt_retrivepwd;  //忘记密码
    private EditText edit_username,edit_userpwd;
    private Button btn_login;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        init();
        txt_register.setOnClickListener(this);
        txt_retrivepwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }
    private  void init()
    {
        txt_register = (TextView) findViewById(R.id.txt_register);
        txt_retrivepwd = (TextView) findViewById(R.id.txt_retrivepwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_userpwd = (EditText) findViewById(R.id.edit_userpwd);
        requestQueue = MyApplication.getRequestQueue();
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
                final String pwd = edit_userpwd.getText().toString();
                final String name = edit_username.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://m.abl.weidustudio.com/api/Login/UserLogin", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG",response);
                        response=response.replace("\\", "");//去掉'/'
                        response=response.substring(1, response.length()-1); //去掉头尾引号。
                        Gson gson = new Gson();
                        LoginInfo loginInfo =  gson.fromJson(response, LoginInfo.class);
                        Toast.makeText(LoginActivity.this, loginInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("UserLoginNumber", name);
                        map.put("UserLoginPass", pwd);
                        map.put("UserLoginIP", "192.168.0.150");
                        map.put("LoginChannel", CommonUtils.getMD5(pwd));
                        map.put("APPToken", MyApplication.APPToken);
                        map.put("LoginCheckToken", "");
                        map.put("LoginPhone", name);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
        }
    }
}
