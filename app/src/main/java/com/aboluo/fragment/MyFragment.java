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

import com.aboluo.com.LoginActivity;
import com.aboluo.com.R;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class MyFragment extends Fragment {
    private View view;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        return view;
    }
    private void  init()
    {
            btn  = (Button) view.findViewById(R.id.my_btn);
    }
}
