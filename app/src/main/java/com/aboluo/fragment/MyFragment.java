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

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.com.AddressActivity;
import com.aboluo.com.FavorActivity;
import com.aboluo.com.LoginActivity;
import com.aboluo.com.MyInfoAcitvity;
import com.aboluo.com.OrderActivity;
import com.aboluo.com.R;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn, my_out;
    private ScrollView my_scrollview;
    private LinearLayout linLayout_my_info, my_nopay, my_nosend, my_noreceive, my_assessment,
            feedbackInfo, my_favor;
    private RelativeLayout my_allorder, my_addressinfo;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my, null);
        } else {
        }
        init();

        OverScrollDecoratorHelper.setUpStaticOverScroll(my_scrollview, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        linLayout_my_info.setOnClickListener(this);
        my_allorder.setOnClickListener(this);
        my_nopay.setOnClickListener(this);
        my_nosend.setOnClickListener(this);
        my_noreceive.setOnClickListener(this);
        my_assessment.setOnClickListener(this);
        my_addressinfo.setOnClickListener(this);
        feedbackInfo.setOnClickListener(this);
        my_favor.setOnClickListener(this);
        btn.setOnClickListener(this);
        my_out.setOnClickListener(this);
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
        feedbackInfo = (LinearLayout) view.findViewById(R.id.feedbackInfo);
        sharedPreferences = MyFragment.this.getContext().getSharedPreferences("aboluo", Context.MODE_PRIVATE);
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
                break;
            case R.id.my_out:
                CommonUtils.LoginOut(MyFragment.this.getContext());
                Intent intent7 = new Intent(MyFragment.this.getActivity(), LoginActivity.class);
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
    }
}
