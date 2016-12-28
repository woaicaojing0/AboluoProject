package com.aboluo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.com.AddressActivity;
import com.aboluo.com.CreditInfoActivity;
import com.aboluo.com.FavorActivity;
import com.aboluo.com.LoginActivity;
import com.aboluo.com.MainActivity;
import com.aboluo.com.MyInfoAcitvity;
import com.aboluo.com.OrderActivity;
import com.aboluo.com.R;
import com.aboluo.com.ReFundActivity;
import com.aboluo.com.WebActivity.FeedBackActivity;
import com.aboluo.com.WebActivity.InvitationActivity;
import com.aboluo.model.MyInfoBean;
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
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn, my_out;
    private ScrollView my_scrollview;
    private LinearLayout linLayout_my_info, my_nopay, my_nosend, my_noreceive, my_assessment,
            feedbackInfo, my_favor, my_refund, creditinfodetail,my_invitation;
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
        feedbackInfo.setOnClickListener(this);
        my_favor.setOnClickListener(this);
        my_refund.setOnClickListener(this);
        btn.setOnClickListener(this);
        my_out.setOnClickListener(this);
        my_invitation.setOnClickListener(this);
        creditinfodetail.setOnClickListener(this);
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
        feedbackInfo = (LinearLayout) view.findViewById(R.id.feedbackInfo);
        creditinfodetail = (LinearLayout) view.findViewById(R.id.creditinfodetail);
        my_invitation = (LinearLayout) view.findViewById(R.id.my_invitation);
        sharedPreferences = MyFragment.this.getContext().getSharedPreferences("aboluo", Context.MODE_PRIVATE);
        my_fragment_imageview = (CircleImageView) view.findViewById(R.id.my_fragment_imageview);
        InitData();
    }

    private void InitData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/MemberApi/ReceiveUserInfo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                myInfoBean = gson.fromJson(response, MyInfoBean.class);
                if (myInfoBean.isIsSuccess()) {
                    String url = myInfoBean.getResult().getMemberLogoUrl();
                    picasso.load(CommonUtils.GetLoginImageURl(MyFragment.this.getContext()))
                            .placeholder(R.drawable.image_placeholder)
                            .error(R.drawable.imageview_error).into(my_fragment_imageview);
                } else {
                    Toast.makeText(MyFragment.this.getContext(), "个人信息获取失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                String s = new String(bytecode);
                Toast.makeText(MyFragment.this.getContext(), s, Toast.LENGTH_SHORT).show();
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
            case R.id.feedbackInfo:
                Intent intent12 = new Intent(MyFragment.this.getActivity(), FeedBackActivity.class);
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
                startActivity(intent11);
                break;
            case  R.id.my_invitation:
                Intent intent13 = new Intent(MyFragment.this.getActivity(), InvitationActivity.class);
                startActivity(intent13);
                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonUtils.IsLogin(MyFragment.this.getContext())) {
            btn.setVisibility(View.GONE);
            my_out.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.VISIBLE);
            my_out.setVisibility(View.GONE);
        }
        InitData();
    }
}
