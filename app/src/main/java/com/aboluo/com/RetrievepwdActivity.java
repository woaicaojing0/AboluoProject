package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ValidateUtils;
import com.aboluo.model.MessageInfo;
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

import static com.aboluo.com.R.id.btn_getinfo;
import static com.aboluo.com.R.id.retrievepwd_edit_yzm;

/**
 * Created by CJ on 2016/9/21.
 */

public class RetrievepwdActivity  extends Activity implements View.OnClickListener,TextWatcher{
    private TextView retrievepwd_btn_close;
    private Button retrievepwd_btn_getinfo,retrievepwd_btn_next;
    private EditText retrievepwd_edit_yzm,retrievepwd_edit_phone;
    private MessageInfo messageInfo;
    private CountDownTimer time;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.retrievepwd);
        init();
        retrievepwd_btn_close = (TextView) findViewById(R.id.retrievepwd_btn_close);
        retrievepwd_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrievepwdActivity.this.finish();
            }
        });
        retrievepwd_edit_phone.addTextChangedListener(this);
        retrievepwd_edit_yzm.addTextChangedListener(this);
        retrievepwd_btn_next.setOnClickListener(this);
        retrievepwd_btn_getinfo.setOnClickListener(this);
    }
    private void  init()
    {
        retrievepwd_btn_getinfo = (Button) findViewById(R.id.retrievepwd_btn_getinfo);
        retrievepwd_btn_next = (Button) findViewById(R.id.retrievepwd_btn_next);
        retrievepwd_edit_yzm = (EditText) findViewById(R.id.retrievepwd_edit_yzm);
        retrievepwd_edit_phone = (EditText) findViewById(R.id.retrievepwd_edit_phone);
        requestQueue = MyApplication.getRequestQueue();
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
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
        if(ValidateUtils.isMobileNO(retrievepwd_edit_phone.getText().toString()))
        {
            if(retrievepwd_edit_yzm.getText().length() >5)
            {
                isok = true;
            }
        }
        retrievepwd_btn_next.setEnabled(isok);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.retrievepwd_btn_getinfo:
                final String number = retrievepwd_edit_phone.getText().toString().trim();
                final String apptoken = MyApplication.APPToken;
                if (ValidateUtils.isMobileNO(number)) {
                    time.start();
                    retrievepwd_btn_getinfo.setEnabled(false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://m.abl.weidustudio.com/api/Login/SendMessage",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("TAG",response);
                                    Gson gson = new Gson();
                                    response=response.replace("\\", "");//去掉'/'
                                    response=response.substring(1, response.length()-1); //去掉头尾引号。
                                    messageInfo=  gson.fromJson(response, MessageInfo.class);
                                    Toast.makeText(RetrievepwdActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RetrievepwdActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("UserLoginNumber", number);
                            map.put("APPToken", apptoken);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText("您输入的手机号有误!")
                            .setConfirmText("确定")
                            .show();
                }
                break;
            case R.id.retrievepwd_btn_next:
                if(ValidateUtils.isMobileNO(retrievepwd_edit_phone.getText().toString().trim()))
                {
                    if(messageInfo.isIsSuccess()) {
                        String phone = retrievepwd_edit_phone.getText().toString().trim();
                        String yzm = retrievepwd_edit_yzm.getText().toString().trim();
                        if (yzm.equals(messageInfo.getResult().getMessageCode())&&phone.equals(messageInfo.getResult().getSendPhoneNumber()))
                        {
                            Toast.makeText(this, "进行跳转", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("提示")
                                    .setContentText("您输入的验证码有误!")
                                    .setConfirmText("确定")
                                    .show();
                        }
                    }else {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("提示")
                                .setContentText("您输入的验证码有误!")
                                .setConfirmText("确定")
                                .show();
                    }
                }
                else {
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("提示")
                            .setContentText("您输入的手机号有误!")
                            .setConfirmText("确定")
                            .show();
                }

        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            retrievepwd_btn_getinfo.setText("重新验证");
            retrievepwd_btn_getinfo.setEnabled(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            retrievepwd_btn_getinfo.setEnabled(false);
            retrievepwd_btn_getinfo.setText(millisUntilFinished /1000+"秒重新发送");
        }
    }
}
