package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.BankCardBean;
import com.aboluo.model.BaseModel;
import com.aboluo.model.BindMsgBean;
import com.aboluo.model.ExtractBean;
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
    private ImageView iv_brankImage;
    private LinearLayout ll_extract;
    private TextView tv_extract_history;
    private static int type = 1;
    private Button btn_extract_apply;

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
        tv_extract_history.setOnClickListener(this);
        ll_extract.setOnClickListener(this);
        btn_extract_apply.setOnClickListener(this);
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
        initData();
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
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.setCancelable(false);
        spinner_extract = (Spinner) findViewById(R.id.spinner_extract);
        tv_extract_text = (TextView) findViewById(R.id.tv_extract_text);
        et_extract_money = (EditText) findViewById(R.id.et_extract_money);
        et_extract_bankCard = (EditText) findViewById(R.id.et_extract_bankCard);
        et_extract_bankName = (EditText) findViewById(R.id.et_extract_bankName);
        et_extract_username = (EditText) findViewById(R.id.et_extract_username);
        et_extract_authcode = (EditText) findViewById(R.id.et_extract_authcode);
        btn_validate_bank = (Button) findViewById(R.id.btn_validate_bank);
        btn_extract_apply = (Button) findViewById(R.id.btn_extract_apply);
        btn_getinfo = (Button) findViewById(R.id.btn_getinfo);
        iv_brankImage = (ImageView) findViewById(R.id.iv_brankImage);
        ll_extract = (LinearLayout) findViewById(R.id.ll_extract);
        tv_extract_history = (TextView) findViewById(R.id.tv_extract_history);
        mspinnerList = new ArrayList<>();
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象register_edit_auth.addTextChangedListener(this);
    }

    private void initSpinner() {
        if ("0".equals(email)) {
            if ("0".equals(phone)) {
                mspinnerList.add("请先绑定邮箱或者手机号");
                btn_extract_apply.setEnabled(false);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/WithdrawApi/ReceiveMemberWithdrawInfoByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                ExtractBean extractBean = gson.fromJson(response, ExtractBean.class);
                if (extractBean.isIsSuccess() && null != extractBean.getResult()) {
                    et_extract_username.setText(extractBean.getResult().getWithdrawalsInfo().getRealName());
                    et_extract_bankCard.setText(extractBean.getResult().getWithdrawalsInfo().getBankCard());
                    et_extract_bankName.setText(extractBean.getResult().getWithdrawalsInfo().getBankCardName());
                    picasso.load(extractBean.getResult().getWithdrawalsInfo().getBankLogo()).error(R.drawable.imageview_error)
                            .placeholder(R.drawable.image_placeholder).into(iv_brankImage);
                } else {
                }
                initSpinner();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] bytecode = error.networkResponse.data;
//                String s = new String(bytecode);
//                Toast.makeText(ExtractMoneyActivity.this, s, Toast.LENGTH_SHORT).show();
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
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_validate_bank:
                if (et_extract_bankCard.length() > 0) {
                    getBankInfo(et_extract_bankCard.getText().toString());
                } else {
                    Toast.makeText(this, "请先输入银行卡号", Toast.LENGTH_SHORT).show();
                }
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
            case R.id.tv_extract_history:
                Intent intent = new Intent(ExtractMoneyActivity.this, ExtractMoneyDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_extract:
                finish();
                break;
            case R.id.btn_extract_apply:
                applyForExtract();
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
                    picasso.load(bankCardBean.getResult().getLogo()).error(R.drawable.imageview_error)
                            .placeholder(R.drawable.image_placeholder).into(iv_brankImage);
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
        type = 1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/WithdrawApi/ReceiveMemberWithdrawValid",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", response);
                        Gson gson = new Gson();
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        messageInfo = gson.fromJson(response, BindMsgBean.class);
                        if (messageInfo.isIsSuccess()) {
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
                map.put("ValidType", "1");
                map.put("Email", "");
                map.put("Mobile", number);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetEmailAuth(final String email) {
        type = 2;
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
                map.put("ValidType", "1");
                map.put("Email", email);
                map.put("Mobile", "");
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

    private void applyForExtract() {
        final String realName = et_extract_username.getText().toString();
        String bankNum = et_extract_bankCard.getText().toString();
        String bankName = et_extract_bankName.getText().toString();
        final String extraMoney = et_extract_money.getText().toString();
        final String valiatecode = et_extract_authcode.getText().toString();
        final String emailorphpne = tv_extract_text.getText().toString();
        if (realName.length() > 0 && bankNum.length() > 0 && bankName.length() > 0 && extraMoney.length() > 0
                && valiatecode.length() > 0) {
            if (null != bankCardBean && bankCardBean.getStatus().equals("0")) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        URL + "/api/WithdrawApi/ReceiveMemberWithdrawSubmit", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace("\\", "");
                        response = response.substring(1, response.length() - 1);
                        BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                        if (baseModel.isIsSuccess()) {
                            finish();
                        } else {
                        }
                        Toast.makeText(ExtractMoneyActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("memberId", MemberId);
                        map.put("bankCard", bankCardBean.getResult().getBankcard());
                        map.put("bankCardName", bankCardBean.getResult().getName());
                        map.put("bankProvince", bankCardBean.getResult().getProvince());
                        map.put("bankCity", bankCardBean.getResult().getCity());
                        map.put("bankType", bankCardBean.getResult().getType());
                        map.put("bankCardLength", bankCardBean.getResult().getLen());
                        map.put("bankName", bankCardBean.getResult().getBank());
                        map.put("bankLogo", bankCardBean.getResult().getLogo());
                        map.put("bankTel", bankCardBean.getResult().getTel());
                        map.put("bankWebsite", bankCardBean.getResult().getWebsite());
                        map.put("mobile", emailorphpne);
                        map.put("price", extraMoney);
                        map.put("email", emailorphpne);
                        map.put("realName", realName);
                        if (spinner_extract.getSelectedItem().equals("手机号码验证")) {
                            map.put("validType", "1");
                        } else if (spinner_extract.getSelectedItem().equals("邮箱验证")) {
                            map.put("validType", "2");
                        }
                        map.put("validType", String.valueOf(type));
                        map.put("validCode", valiatecode);
                        map.put("APPToken", APPToken);
                        return map;
                    }
                };
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(this, "银行信息有误，请退出重试", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
        }
    }
}
