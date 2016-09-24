package com.aboluo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.aboluo.com.LoginActivity;
import com.aboluo.com.MyInfoAcitvity;
import com.aboluo.com.R;
import com.handmark.pulltorefresh.library.OverscrollHelper;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn;
    private ScrollView my_scrollview;
    private LinearLayout linLayout_my_info;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my, null);
        } else {
        }
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        OverScrollDecoratorHelper.setUpStaticOverScroll(my_scrollview,OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        linLayout_my_info.setOnClickListener(this);
        return view;
    }
    private void  init()
    {
            btn  = (Button) view.findViewById(R.id.my_btn);
        my_scrollview = (ScrollView) view.findViewById(R.id.my_scrollview);
        linLayout_my_info = (LinearLayout) view.findViewById(R.id.my_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_info:
                Intent intent = new Intent(MyFragment.this.getActivity(), MyInfoAcitvity.class);
                startActivity(intent);
            break;
        }

    }
}
