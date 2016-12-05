package com.aboluo.fragment.UnaryFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.widget.CustomViewPager1;

/**
 * Created by CJ on 2016/12/2.
 */

public class NewFragment extends Fragment {
    private TextView txt_fragment_unary;
    private CustomViewPager1 vp;
//    public NewFragment(CustomViewPager1 viewPager1)
//    {
//        this.vp = viewPager1;
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_unary, null);
        init(view);
//        vp.setObjectForPosition(view,2);
        txt_fragment_unary.setText("2");
        return view;
    }

    private void init(View view) {
        txt_fragment_unary = (TextView) view.findViewById(R.id.txt_fragment_unary);
    }
}
