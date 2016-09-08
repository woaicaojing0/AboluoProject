package com.aboluo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboluo.com.R;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class ShopCarFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shopcar, null);
        } else {
        }
        return view;
    }
}
