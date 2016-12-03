package com.aboluo.fragment.UnaryFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.com.R;
import com.aboluo.fragment.IndexFragment;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.widget.CustomViewPager1;
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

/**
 * Created by CJ on 2016/12/2.
 */

public class MoodsFragment extends Fragment {
    private TextView txt_fragment_unary;
    private CustomViewPager1 vp;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Context mcontext;
    private Picasso picasso;
    private Gson gson;
    private BaseConfigBean unaryConfigBean;
    public MoodsFragment(CustomViewPager1 viewPager1)
    {
        this.vp = viewPager1;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_unary, null);
        init(view);
        vp.setObjectForPosition(view,0);
        txt_fragment_unary.setText("0");
        return view;
    }

    private void init(View view) {
        mcontext= MoodsFragment.this.getActivity();
        txt_fragment_unary = (TextView) view.findViewById(R.id.txt_fragment_unary);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
        URL = CommonUtils.GetValueByKey(mcontext, "apiurl");
        APPToken = CommonUtils.GetValueByKey(mcontext, "APPToken");
        picasso = Picasso.with(mcontext);
    }
    private  void initDate()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                 return map;
            }
        };
        requestQueue.add(stringRequest);
    }


}
