package com.aboluo.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.com.AddressActivity;
import com.aboluo.com.CouponsActivity;
import com.aboluo.com.CreditInfoActivity;
import com.aboluo.com.FastPartnerActivity;
import com.aboluo.com.FavorActivity;
import com.aboluo.com.HelpCenterActivity;
import com.aboluo.com.InvitationCodeActivity;
import com.aboluo.com.InvitationInfoActivity;
import com.aboluo.com.LoginActivity;
import com.aboluo.com.MainActivity;
import com.aboluo.com.MyAgentActivity;
import com.aboluo.com.MyInfoAcitvity;
import com.aboluo.com.OrderActivity;
import com.aboluo.com.R;
import com.aboluo.com.ReFundActivity;
import com.aboluo.com.SettingActivity;
import com.aboluo.com.WebActivity.InvitationActivity;
import com.aboluo.model.CreditInfoBean;
import com.aboluo.model.ExpressNumBean;
import com.aboluo.model.MyInfoBean;
import com.aboluo.model.QiNiuToken;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TResult;
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
 * Created by cj34920 on 2016/9/8.
 */
public class MyFragment extends TakePhotoFragment implements View.OnClickListener {
    private View view;
    private Button btn, my_out;
    private ScrollView my_scrollview;
    private LinearLayout linLayout_my_info, my_nopay, my_nosend, my_noreceive, my_assessment,
            helpcenter, my_favor, my_refund, creditinfodetail, my_invitation,
            invitation_code, my_agent, my_coupons_center, ll_fast_parnter;
    private RelativeLayout my_allorder, my_addressinfo;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private MyInfoBean myInfoBean;
    private CircleImageView my_fragment_imageview;
    private TextView tv_my_huiyuan, my_01_num, my_02_num, my_03_num, my_04_num, tv_user_id;
    private ImageView iv_my_setting;
    private AlertDialog.Builder builder;
    private QiNiuToken qiNiuToken;
    private CreditInfoBean creditInfoBean;
    private TextView my_allmoney, tv_findReferrer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my, null);
        } else {
        }
        init();

//        OverScrollDecoratorHelper.setUpStaticOverScroll(my_scrollview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        linLayout_my_info.setOnClickListener(this);
        my_allorder.setOnClickListener(this);
        my_nopay.setOnClickListener(this);
        my_nosend.setOnClickListener(this);
        my_noreceive.setOnClickListener(this);
        my_assessment.setOnClickListener(this);
        my_addressinfo.setOnClickListener(this);
        helpcenter.setOnClickListener(this);
        my_favor.setOnClickListener(this);
        my_refund.setOnClickListener(this);
        btn.setOnClickListener(this);
        my_out.setOnClickListener(this);
        my_invitation.setOnClickListener(this);
        creditinfodetail.setOnClickListener(this);
        invitation_code.setOnClickListener(this);
        my_agent.setOnClickListener(this);
        my_coupons_center.setOnClickListener(this);
        iv_my_setting.setOnClickListener(this);
        my_fragment_imageview.setOnClickListener(this);
        ll_fast_parnter.setOnClickListener(this);
        if (CommonUtils.IsLogin(MyFragment.this.getContext())) {
            btn.setVisibility(View.GONE);
            my_out.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.VISIBLE);
            my_out.setVisibility(View.GONE);
        }
        return view;
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(MyFragment.this.getContext());
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(MyFragment.this.getContext(), "ImgUrl");
        URL = CommonUtils.GetValueByKey(MyFragment.this.getContext(), "apiurl");
        APPToken = CommonUtils.GetValueByKey(MyFragment.this.getContext(), "APPToken");
        picasso = Picasso.with(MyFragment.this.getContext());
        gson = new Gson();
        btn = (Button) view.findViewById(R.id.my_btn);
        my_out = (Button) view.findViewById(R.id.my_out);
        my_scrollview = (ScrollView) view.findViewById(R.id.my_scrollview);
        linLayout_my_info = (LinearLayout) view.findViewById(R.id.my_info);
        my_allorder = (RelativeLayout) view.findViewById(R.id.my_allorder);
        my_nopay = (LinearLayout) view.findViewById(R.id.my_nopay);
        my_nosend = (LinearLayout) view.findViewById(R.id.my_nosend);
        my_noreceive = (LinearLayout) view.findViewById(R.id.my_noreceive);
        my_assessment = (LinearLayout) view.findViewById(R.id.my_assessment);
        my_favor = (LinearLayout) view.findViewById(R.id.my_favor);
        my_addressinfo = (RelativeLayout) view.findViewById(R.id.my_addressinfo);
        my_refund = (LinearLayout) view.findViewById(R.id.my_refund);
        helpcenter = (LinearLayout) view.findViewById(R.id.helpcenter);
        creditinfodetail = (LinearLayout) view.findViewById(R.id.creditinfodetail);
        my_invitation = (LinearLayout) view.findViewById(R.id.my_invitation);
        invitation_code = (LinearLayout) view.findViewById(R.id.invitation_code);
        my_agent = (LinearLayout) view.findViewById(R.id.my_agent);
        my_coupons_center = (LinearLayout) view.findViewById(R.id.my_coupons_center);
        ll_fast_parnter = (LinearLayout) view.findViewById(R.id.ll_fast_parnter);
        sharedPreferences = MyFragment.this.getContext().getSharedPreferences("aboluo", Context.MODE_PRIVATE);
        my_fragment_imageview = (CircleImageView) view.findViewById(R.id.my_fragment_imageview);
        tv_my_huiyuan = (TextView) view.findViewById(R.id.tv_my_huiyuan);
        iv_my_setting = (ImageView) view.findViewById(R.id.iv_my_setting);
        my_01_num = (TextView) view.findViewById(R.id.my_01_num);
        my_02_num = (TextView) view.findViewById(R.id.my_02_num);
        my_03_num = (TextView) view.findViewById(R.id.my_03_num);
        my_04_num = (TextView) view.findViewById(R.id.my_04_num);
        tv_user_id = (TextView) view.findViewById(R.id.tv_user_id);
        my_allmoney = (TextView) view.findViewById(R.id.my_allmoney);
        tv_findReferrer = (TextView) view.findViewById(R.id.tv_findReferrer);
        pdialog = new SweetAlertDialog(MyFragment.this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        InitData();
        GetQiNiuToken();
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
//                byte[] bytecode = error.networkResponse.data;
//                String s = new String(bytecode);
                Toast.makeText(MyFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
        MemberId = CommonUtils.GetMemberId(MyFragment.this.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/ReceiveUserInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                myInfoBean = gson.fromJson(response, MyInfoBean.class);
                if (myInfoBean.isIsSuccess()) {
                    String url = CommonUtils.GetLoginImageURl(MyFragment.this.getContext());
                    if (url.equals("") || url.equals("0")) {
                        picasso.load(R.drawable.appstart)
                                .placeholder(R.drawable.image_placeholder)
                                .error(R.drawable.imageview_error).into(my_fragment_imageview);
                    } else {
                        picasso.load(CommonUtils.GetLoginImageURl(MyFragment.this.getContext()))
                                .placeholder(R.drawable.image_placeholder)
                                .error(R.drawable.imageview_error).into(my_fragment_imageview);
                    }
                    //更新用户的身份
                    CommonUtils.SetisDealer(MyFragment.this.getContext(), String.valueOf(myInfoBean.getResult()
                            .getIsLeader()));
                    switch (myInfoBean.getResult().getIsLeader()) {
                        case 0:
                            tv_my_huiyuan.setText("普通会员");
                            break;
                        case 1:
                            tv_my_huiyuan.setText("合伙人");
                            break;
                        case 2:
                            tv_my_huiyuan.setText("金牌合伙人");
                            break;
                        default:
                            tv_my_huiyuan.setText("普通会员");
                            break;
                    }
                    tv_user_id.setText("用户编号：10" + String.valueOf(myInfoBean.getResult().getMemberId()));
                } else {
                    Toast.makeText(MyFragment.this.getContext(), "个人信息获取失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] bytecode = error.networkResponse.data;
//                String s = new String(bytecode);
//                Toast.makeText(MyFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();
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
        ExpressNum();
        getMoneyData();
        if (0 == CommonUtils.GetReferrer1Id(MyFragment.this.getContext())) {
            tv_findReferrer.setText("寻找推荐人");
            return;
        }
        tv_findReferrer.setText("我的上级");
    }

    private void ExpressNum() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveOrderCountByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                my_01_num.setVisibility(View.GONE);
                my_02_num.setVisibility(View.GONE);
                my_03_num.setVisibility(View.GONE);
                my_04_num.setVisibility(View.GONE);
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                ExpressNumBean bean = gson.fromJson(response, ExpressNumBean.class);
                if (bean.getResult().getWaitPayCount() != 0) {
                    my_01_num.setVisibility(View.VISIBLE);
                }
                if (bean.getResult().getWaitSendCount() != 0) {
                    my_02_num.setVisibility(View.VISIBLE);
                }
                if (bean.getResult().getWaitReceiptCont() != 0) {
                    my_03_num.setVisibility(View.VISIBLE);
                }
                if (bean.getResult().getWaitEvaluateCount() != 0) {
                    my_04_num.setVisibility(View.VISIBLE);
                }
                my_01_num.setText(bean.getResult().getWaitPayCount() + "");
                my_02_num.setText(bean.getResult().getWaitSendCount() + "");
                my_03_num.setText(bean.getResult().getWaitReceiptCont() + "");
                my_04_num.setText(bean.getResult().getWaitEvaluateCount() + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] bytecode = error.networkResponse.data;
//                String s = new String(bytecode);
//                Toast.makeText(MyFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
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
            case R.id.my_info:
                Intent intent = new Intent(MyFragment.this.getActivity(), MyInfoAcitvity.class);
                startActivity(intent);
                break;
            case R.id.my_allorder:
                Intent intent1 = new Intent(MyFragment.this.getActivity(), OrderActivity.class);
                intent1.putExtra("TAG", 0);
                intent1.putExtra("status", 10);
                startActivity(intent1);
                break;
            case R.id.my_nopay:
                Intent intent2 = new Intent(MyFragment.this.getActivity(), OrderActivity.class);
                intent2.putExtra("TAG", 1);
                intent2.putExtra("status", 20);
                startActivity(intent2);
                break;
            case R.id.my_nosend:
                Intent intent3 = new Intent(MyFragment.this.getActivity(), OrderActivity.class);
                intent3.putExtra("TAG", 2);
                intent3.putExtra("status", 30);
                startActivity(intent3);
                break;
            case R.id.my_noreceive:
                Intent intent4 = new Intent(MyFragment.this.getActivity(), OrderActivity.class);
                intent4.putExtra("TAG", 3);
                startActivity(intent4);
                break;
            case R.id.my_assessment:
                Intent intent5 = new Intent(MyFragment.this.getActivity(), OrderActivity.class);
                intent5.putExtra("TAG", 4);
                startActivity(intent5);
                break;
            case R.id.my_addressinfo:
                Intent intent6 = new Intent(MyFragment.this.getActivity(), AddressActivity.class);
                intent6.putExtra("TAG", 5);
                startActivity(intent6);
                break;
            case R.id.helpcenter:
                Intent intent12 = new Intent(MyFragment.this.getActivity(), HelpCenterActivity.class);
                startActivity(intent12);
                break;
            case R.id.my_out:
                CommonUtils.LoginOut(MyFragment.this.getContext());
                Intent intent7 = new Intent(MyFragment.this.getActivity(), MainActivity.class);
                intent7.putExtra("id", 1);
                startActivity(intent7);
                break;
            case R.id.my_btn:
                Intent intent8 = new Intent(MyFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent8);
                break;
            case R.id.my_favor:
                Intent intent9 = new Intent(MyFragment.this.getActivity(), FavorActivity.class);
                startActivity(intent9);
                break;
            case R.id.my_refund:
                Intent intent10 = new Intent(MyFragment.this.getActivity(), ReFundActivity.class);
                startActivity(intent10);
                break;
            case R.id.creditinfodetail:
                Intent intent11 = new Intent(MyFragment.this.getActivity(), CreditInfoActivity.class);
                Bundle bundle = new Bundle();
                if (myInfoBean.getResult().getMemberEmail() == null || myInfoBean.getResult().getMemberEmail() == "") {
                    bundle.putString("email", "0");
                } else {
                    bundle.putString("email", myInfoBean.getResult().getMemberEmail().toString());
                }
                if (myInfoBean.getResult().getMemberMobile() == null || myInfoBean.getResult().getMemberMobile() == "") {
                    bundle.putString("phone", "0");
                } else {
                    bundle.putString("phone", myInfoBean.getResult().getMemberMobile().toString());
                }
                bundle.putDouble("money", creditInfoBean.getCanUseMoney());
                intent11.putExtras(bundle);
                startActivity(intent11);
                break;
            case R.id.my_invitation:
                Intent intent13 = new Intent(MyFragment.this.getActivity(), InvitationActivity.class);
                startActivity(intent13);
                break;
            case R.id.invitation_code:
                if (0 == CommonUtils.GetReferrer1Id(MyFragment.this.getContext())) {
                    Intent intent14 = new Intent(MyFragment.this.getActivity(), InvitationCodeActivity.class);
                    startActivity(intent14);
                    return;
                }
                Intent intent19 = new Intent(MyFragment.this.getActivity(), InvitationInfoActivity.class);
                startActivity(intent19);
                break;
            case R.id.my_agent:
                Intent intent15 = new Intent(MyFragment.this.getActivity(), MyAgentActivity.class);
                startActivity(intent15);
                break;
            case R.id.my_coupons_center:
                Intent intent16 = new Intent(MyFragment.this.getActivity(), CouponsActivity.class);
                intent16.putExtra("allmoney", "0");
                startActivity(intent16);
                break;
            case R.id.iv_my_setting:
                Intent intent17 = new Intent(MyFragment.this.getActivity(), SettingActivity.class);
                startActivity(intent17);
                break;
            case R.id.my_fragment_imageview:
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                final Uri imageUri = Uri.fromFile(file);
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(20 * 1024).setMaxPixel(150).create();
//                CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setApectY(1).setWithOwnCrop(true).create();
                getTakePhoto().onEnableCompress(compressConfig, true);
                builder = new AlertDialog.Builder(MyFragment.this.getContext());
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
                break;
            case R.id.ll_fast_parnter:
                Intent intent18 = new Intent(MyFragment.this.getActivity(), FastPartnerActivity.class);
                startActivity(intent18);
                break;

        }

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String filepath = result.getImages().get(0).getCompressPath().toString();
        String key = "memberLogo/android/" + UUID.randomUUID().toString();
        if (qiNiuToken == null) {
        } else {
            File file = new File(filepath);
            picasso.load(file)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.imageview_error).into(my_fragment_imageview);
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
                            Toast.makeText(MyFragment.this.getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                            myInfoBean.getResult().setMemberLogoUrl(key);
                            UpdateInfo();
                        }
                    }
                }, null);
    }

    private void UpdateInfo() {
        pdialog.setTitleText("修改中");
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/EditUserInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                myInfoBean = gson.fromJson(response, MyInfoBean.class);
                if (myInfoBean.isIsSuccess()) {
                    Toast.makeText(MyFragment.this.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    Log.i("UploadImage", myInfoBean.getResult().getMemberLogoUrl());
                    CommonUtils.LoginImageURl(MyFragment.this.getContext(),
                            myInfoBean.getResult().getMemberLogoUrl());
                } else {
                    Toast.makeText(MyFragment.this.getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                }
                picasso.load(myInfoBean.getResult().getMemberLogoUrl())
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.imageview_error).into(my_fragment_imageview);
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
                map.put("WechatLogoUrl", myInfoBean.getResult().getMemberLogoUrl());
                map.put("WechatNickName", myInfoBean.getResult().getUserNickName().toString());
                map.put("MemberSex", String.valueOf(myInfoBean.getResult().getMemberSex()));
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonUtils.IsLogin(MyFragment.this.getContext())) {
            btn.setVisibility(View.GONE);
            my_out.setVisibility(View.VISIBLE);
            InitData();
        } else {
            btn.setVisibility(View.VISIBLE);
            my_out.setVisibility(View.GONE);
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
            InitData();
        }
    }

    private void getMoneyData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/ReceiveMemberAccount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                creditInfoBean = gson.fromJson(response, CreditInfoBean.class);
                if (creditInfoBean.isIsSuccess()) {
                    my_allmoney.setText("￥" + String.valueOf(creditInfoBean.getTotalMoney()));
                } else {
                    Toast.makeText(MyFragment.this.getContext(), "获取金额错误", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
}
