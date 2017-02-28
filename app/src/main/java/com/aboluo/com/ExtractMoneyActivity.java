package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.BankCardBean;
import com.aboluo.model.BindMsgBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2017/2/28.
 * 提现界面
 */

public class ExtractMoneyActivity extends Activity implements View.OnClickListener {
    private String email, phone;
    private double money;
    private Spinner spinner_extract;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private String AppkeyBank;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private ArrayAdapter<String> adapter;
    private List<String> mspinnerList;
    private TextView tv_extract_text;
    private EditText et_extract_money, et_extract_bankCard,
            et_extract_bankName, et_extract_username, et_extract_authcode;
    private Button btn_validate_bank, btn_getinfo;
    private BankCardBean bankCardBean;
    private CountDownTimer time;
    private BindMsgBean messageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extractmoney);
        Bundle bundle = getIntent().getExtras();
        email = (bundle.get("email") == null ? "0" : bundle.get("email").toString());
        phone = (bundle.get("phone") == null ? "0" : bundle.get("phone").toString());
        money = (bundle.get("money") == null ? 0 : (double) bundle.get("money"));
        init();
        btn_validate_bank.setOnClickListener(this);
        btn_getinfo.setOnClickListener(this);
        spinner_extract.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mspinnerList.get(position).equals("手机号码验证")) {
                    Toast.makeText(ExtractMoneyActivity.this, phone + "", Toast.LENGTH_SHORT).show();
                    tv_extract_text.setText(phone);
                } else if (mspinnerList.get(position).equals("邮箱验证")) {
                    Toast.makeText(ExtractMoneyActivity.this, email + "", Toast.LENGTH_SHORT).show();
                    tv_extract_text.setText(email);
                } else {
                    Toast.makeText(ExtractMoneyActivity.this, "请先绑定手机号或者邮箱", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_extract_money.setHint("当前最多可以提现 ￥" + Math.floor(money));
        et_extract_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (Double.valueOf(s.toString()) > Math.floor(money)) {
                        Toast.makeText(ExtractMoneyActivity.this, "超出提现额度", Toast.LENGTH_SHORT).show();
                        et_extract_money.setText(String.valueOf(Math.floor(money)));
                        et_extract_money.requestFocus();
                    }
                } else {
                    Toast.makeText(ExtractMoneyActivity.this, "请输入提现金额", Toast.LENGTH_SHORT).show();
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
        AppkeyBank = CommonUtils.GetValueByKey(this, "AppkeyBank");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        spinner_extract = (Spinner) findViewById(R.id.spinner_extract);
        tv_extract_text = (TextView) findViewById(R.id.tv_extract_text);
        et_extract_money = (EditText) findViewById(R.id.et_extract_money);
        et_extract_bankCard = (EditText) findViewById(R.id.et_extract_bankCard);
        et_extract_bankName = (EditText) findViewById(R.id.et_extract_bankName);
        et_extract_username = (EditText) findViewById(R.id.et_extract_username);
        et_extract_authcode = (EditText) findViewById(R.id.et_extract_authcode);
        btn_validate_bank = (Button) findViewById(R.id.btn_validate_bank);
        btn_getinfo = (Button) findViewById(R.id.btn_getinfo);
        mspinnerList = new ArrayList<>();
        initSpinner();
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象register_edit_auth.addTextChangedListener(this);
    }

    private void initSpinner() {
        if ("0".equals(email)) {
            if ("0".equals(phone)) {
                mspinnerList.add("请先绑定邮箱或者手机号");
            } else {
                mspinnerList.add("手机号码验证");
            }
        } else {
            mspinnerList.add("邮箱验证");
            if ("0".equals(phone)) {
            } else {
                mspinnerList.add("手机号码验证");
            }
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mspinnerList);
        spinner_extract.setAdapter(adapter);
        spinner_extract.setSelection(0);
    }

    private void initData() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_validate_bank:
                getBankInfo(et_extract_bankCard.getText().toString());
                break;
            case R.id.btn_getinfo:
                if (spinner_extract.getSelectedItem().equals("手机号码验证")) {
                    GetPhoneAuth(phone);
                } else if (spinner_extract.getSelectedItem().equals("邮箱验证")) {
                    GetEmailAuth(email);
                } else {
                    Toast.makeText(ExtractMoneyActivity.this, "请先绑定手机号或者邮箱", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getBankInfo(String bankCard) {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://api.jisuapi.com/bankcard/query?appkey=" + AppkeyBank
                        + "&bankcard=" + bankCard, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                bankCardBean = gson.fromJson(response, BankCardBean.class);
                if (bankCardBean.getStatus().equals("0")) {
                    et_extract_bankName.setText(bankCardBean.getResult().getBank().toString());
                } else {
                    et_extract_bankName.setText(bankCardBean.getMsg().toString());
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }

    //通过手机号码获取验证码
    private void GetPhoneAuth(final String number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/MemberApi/BindPhoneNumberSendMsg",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, BindMsgBean.class);
                        if (messageInfo.isIsSuccess()) {
                            //发送短信成功后，将发送的短信的手机号存入到shareperferences中，防止用户退出应用
                            //重新进入app进行注册，退出了应用，如果不存本地，无法判断。
                            //SharedPreferences.Editor editor = preferences.edit();
                            //editor.putString("bindPhoneNumber", messageInfo.getResult().getSendPhoneNumber().toString());
                            //editor.commit();
                            time.start();
                            btn_getinfo.setEnabled(false);
                        } else {
                        }
                        Toast.makeText(ExtractMoneyActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] htmlBodyBytes = error.networkResponse.data;
//                //Toast.makeText(AddAddressActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
                Toast.makeText(ExtractMoneyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                map.put("MemberId", MemberId);
                map.put("BindPhoneNumber", number);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetEmailAuth(final String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL
                + "/api/MemberApi/BindEmailNumberSendMsg",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, BindMsgBean.class);
                        if (messageInfo.isIsSuccess()) {
                            btn_getinfo.setEnabled(false);
                            time.start();
                        } else {
                        }
                        Toast.makeText(ExtractMoneyActivity.this, messageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ExtractMoneyActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                map.put("MemberId", MemberId);
                map.put("BindEmailAccount", email);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_getinfo.setText("重新验证");
            btn_getinfo.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_getinfo.setEnabled(false);
            btn_getinfo.setText(millisUntilFinished / 1000 + "秒重新发送");
        }
    }

}
