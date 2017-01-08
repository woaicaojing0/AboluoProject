package com.aboluo.com;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.Gesture.SecondActivity;
import com.aboluo.GestureUtils.Contants;
import com.aboluo.GestureUtils.PasswordUtil;
import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.model.MyInfoBean;
import com.aboluo.model.QiNiuToken;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CJ on 2016/9/24.
 */

public class MyInfoAcitvity extends TakePhotoActivity implements View.OnClickListener {
    private TextView my_info_text_back;
    private Switch gesture;
    private SharedPreferences sharedPreferences;
    private String pwd;
    private SharedPreferences.Editor editor;
    private RelativeLayout my_info_image;
    private CustomHelper customHelper;
    private View view;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private QiNiuToken qiNiuToken;
    private AlertDialog.Builder builder;
    private CircleImageView my_info_touxiang;
    private RelativeLayout my_info_name, my_info_sex;
    private MyInfoBean myInfoBean;
    private TextView my_inf_txt_loginName, my_inf_txt_nicheng, my_info_txt_sex, my_info_txt_phone, my_info_txt_weixin, my_info_txt_email;
    private static int NickNameCode = 1;//昵称返回标识
    private boolean updateimages = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        view = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        init();
        customHelper = CustomHelper.of(view);
        my_info_text_back.setOnClickListener(this);
        my_info_image.setOnClickListener(this);
        my_info_name.setOnClickListener(this);
        my_info_sex.setOnClickListener(this);


        pwd = sharedPreferences.getString(Contants.PASS_KEY, "0");
        if (pwd.equals("0")) {
        } else {
            gesture.setChecked(true);
        }
        gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pwd = sharedPreferences.getString(Contants.PASS_KEY, "0");
                if (isChecked) {
                    editor.putBoolean("isstartgesture", true);
                    editor.commit();
                    if (pwd.equals("0")) {
                        actionSecondActivity(LockMode.SETTING_PASSWORD);
                    } else {
                    }
                } else {
                    editor.putBoolean("isstartgesture", false);
                    editor.commit();
                    if (pwd.equals("0")) {
                    } else {
                        actionSecondActivity(LockMode.VERIFY_PASSWORD);
                    }
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
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        my_info_text_back = (TextView) findViewById(R.id.my_info_text_back);
        my_inf_txt_loginName = (TextView) findViewById(R.id.my_inf_txt_loginName);
        my_inf_txt_nicheng = (TextView) findViewById(R.id.my_inf_txt_nicheng);
        my_info_txt_sex = (TextView) findViewById(R.id.my_info_txt_sex);
        my_info_txt_phone = (TextView) findViewById(R.id.my_info_txt_phone);
        my_info_txt_email = (TextView) findViewById(R.id.my_info_txt_email);
        my_info_txt_weixin = (TextView) findViewById(R.id.my_info_txt_weixin);
        my_info_touxiang = (CircleImageView) findViewById(R.id.my_info_touxiang);
        my_info_image = (RelativeLayout) findViewById(R.id.my_info_image);
        my_info_name = (RelativeLayout) findViewById(R.id.my_info_name);
        my_info_sex = (RelativeLayout) findViewById(R.id.my_info_sex);
        gesture = (Switch) findViewById(R.id.gesture);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyInfoAcitvity.this);
        editor = sharedPreferences.edit();
        GetQiNiuToken();
        InitData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_text_back:
                this.finish();
                break;
            case R.id.my_info_image:
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                final Uri imageUri = Uri.fromFile(file);
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(20 * 1024).setMaxPixel(150).create();
//                CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setApectY(1).setWithOwnCrop(true).create();
                getTakePhoto().onEnableCompress(compressConfig, true);
                builder = new AlertDialog.Builder(MyInfoAcitvity.this);
                //    指定下拉列表的显示数据
                final String[] cities = {"相册", "拍照"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                getTakePhoto().onPickFromDocuments();
                                break;
                            case 1:
                                getTakePhoto().onPickFromCapture(imageUri);
                                break;
                            //dialog.dismiss();
                        }
                        //Toast.makeText(MyInfoAcitvity.this, "选择的城市为：" + cities[which], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
//                getTakePhoto().onPickFromGallery();
                //customHelper.onClick(view, getTakePhoto());
                break;
            case R.id.my_info_name: //修改昵称
                Intent intent = new Intent(MyInfoAcitvity.this, UpdateMyInfoActivity.class);
                intent.putExtra("old", myInfoBean.getResult().getUserNickName() == null ? "" : myInfoBean.getResult()
                        .getUserNickName().toString());
                startActivityForResult(intent, NickNameCode);
                break;
            case R.id.my_info_sex:
                builder = new AlertDialog.Builder(MyInfoAcitvity.this);
                final String[] sex = {"女", "男"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MyInfoAcitvity.this, "性别为：" + sex[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        myInfoBean.getResult().setMemberSex(which);
                        updateimages = false;
                        UpdateInfo();
                    }
                }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("from", "setting");
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (mode == LockMode.VERIFY_PASSWORD) {

            intent.putExtra("myinfo", true);
            intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
            startActivityForResult(intent, 1);

        } else {
            intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        } else {
            int result = data.getIntExtra("startgesture", 3);
            if (result == 0) {
                gesture.setChecked(false);
            }
            switch (requestCode) {
                case 1:
                    myInfoBean.getResult().setUserNickName(data.getStringExtra("nickname"));
                    updateimages = false;
                    UpdateInfo();
                    break;
            }
        }
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        //showImg(result.getImages());
        String filepath = result.getImages().get(0).getCompressPath().toString();
        String key = "memberLogo/android/" + UUID.randomUUID().toString();
        if (qiNiuToken == null) {
        } else {
            File file = new File(filepath);
            picasso.load(file)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.imageview_error).into(my_info_touxiang);
            String token = qiNiuToken.getFileUploadToken();
            UploadImage(filepath, key, token);
        }
    }


    private void UploadImage(String filepath, String key, String token) {
        //pdialog.setTitleText("上传中......");
        Configuration config = new Configuration.Builder().zone(Zone.zone0).build();
//  重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(filepath, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        if (info.isOK()) {
                            pdialog.dismiss();
                            Toast.makeText(MyInfoAcitvity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            myInfoBean.getResult().setMemberLogoUrl(key);
                            updateimages = true;
                            UpdateInfo();
                        }
                    }
                }, null);
    }

    private void GetQiNiuToken() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ActiveApi/ReciveQiNiuToken", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                qiNiuToken = gson.fromJson(response, QiNiuToken.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                String s = new String(bytecode);
                Toast.makeText(MyInfoAcitvity.this, "Token" + s, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    private void InitData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/ReceiveUserInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                myInfoBean = gson.fromJson(response, MyInfoBean.class);
                if (myInfoBean.isIsSuccess()) {
                    LoadInfo(myInfoBean);
                } else {
                    Toast.makeText(MyInfoAcitvity.this, "个人信息获取失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                String s = new String(bytecode);
                Toast.makeText(MyInfoAcitvity.this, s, Toast.LENGTH_SHORT).show();
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

    private void LoadInfo(MyInfoBean myInfoBean) {
        my_inf_txt_loginName.setText(myInfoBean.getResult().getMemberLoginNumber() == null ? "未填写" :
                myInfoBean.getResult().getMemberLoginNumber().toString());
        my_inf_txt_nicheng.setText(myInfoBean.getResult().getUserNickName() == null ? "未填写"
                : myInfoBean.getResult().getUserNickName().toString());
        if (myInfoBean.getResult().getMemberSex() == 0) {
            my_info_txt_sex.setText("男");
        } else {
            my_info_txt_sex.setText("女");
        }
        my_info_txt_phone.setText(myInfoBean.getResult().getMemberMobile() == null ? "未填写"
                : myInfoBean.getResult().getMemberMobile().toString());
        my_info_txt_email.setText(myInfoBean.getResult().getMemberEmail() == null ? "未填写"
                : myInfoBean.getResult().getMemberEmail().toString());
        Log.i("MyInfoAcitivity", myInfoBean.getResult().getMemberLogoUrl());
        String url = CommonUtils.GetLoginImageURl(this);
        if (url.equals("") || url.equals("0")) {
            picasso.load(R.drawable.appstart)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.imageview_error).into(my_info_touxiang);
        } else {
            picasso.load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.imageview_error).into(my_info_touxiang);
        }
    }

    private void UpdateInfo() {
        pdialog.setTitleText("修改中");
        pdialog.show();
        final String newnickname = myInfoBean.getResult().getUserNickName() == null ? ""
                : myInfoBean.getResult().getUserNickName().toString();
        String images = "";
//        int i = myInfoBean.getResult().getMemberLogoUrl().indexOf("http");
//        if(i>0)
//        {}
        if (updateimages) {
            images = myInfoBean.getResult().getMemberLogoUrl();
        } else {

        }

        final String sex = String.valueOf(myInfoBean.getResult().getMemberSex());
        final String finalImages = images;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/EditUserInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                myInfoBean = gson.fromJson(response, MyInfoBean.class);
                if (myInfoBean.isIsSuccess()) {
                    Toast.makeText(MyInfoAcitvity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    if (updateimages) {
                        Log.i("UploadImage", myInfoBean.getResult().getMemberLogoUrl());
                        CommonUtils.LoginImageURl(MyInfoAcitvity.this,
                                myInfoBean.getResult().getMemberLogoUrl());
                    } else {
                    }
                    LoadInfo(myInfoBean);
                } else {
                    Toast.makeText(MyInfoAcitvity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                String s = new String(bytecode);
                Toast.makeText(MyInfoAcitvity.this, s, Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                map.put("WechatLogoUrl", finalImages);
                map.put("WechatNickName", newnickname);
                map.put("MemberSex", sex);
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        boolean isstartgesture = sharedPreferences.getBoolean("isstartgesture", false);
        String pwd = PasswordUtil.getPin(MyInfoAcitvity.this);
        if (isstartgesture) {
            if (pwd != null) {
                gesture.setChecked(true);
            } else {
                gesture.setChecked(false);
            }
        } else {
            gesture.setChecked(false);
        }

    }
}
