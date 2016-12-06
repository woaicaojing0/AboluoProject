package com.aboluo.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.RBCallbkRecyclerView;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.UnaryAdapter;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.model.UnaryListBean;
import com.aboluo.widget.FullyGridLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJ on 2016/12/2.
 */

public class UnaryActivity extends FragmentActivity implements UnaryAdapter.OnRecyclerViewItemClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Context mcontext;
    private Picasso picasso;
    private Gson gson;
    private BaseConfigBean unaryConfigBean;
    private BannerAdapter bannerAdapter;
    private RollPagerView unary__view_pager;
    private ImageView unary_image_03, unary_image_02, unary_image_01;
    private TextView unary_txt_02, unary_txt_01, unary_txt_03;
    private RBCallbkRecyclerView unary_recyclerView;
    private UnaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unary_index);
        init();
        unary_recyclerView.setReachBottomRow(4);
        unary_recyclerView.setOnReachBottomListener(new RBCallbkRecyclerView.OnReachBottomListener() {
            @Override
            public void onReachBottom() {
                //即将到达几部，进行加载更多操作
                Toast.makeText(mcontext, "sssssssssssssssssssssssss", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        mcontext = this;
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
        URL = CommonUtils.GetValueByKey(mcontext, "apiurl");
        APPToken = CommonUtils.GetValueByKey(mcontext, "APPToken");
        gson = new Gson();
        picasso = Picasso.with(mcontext);
        unary__view_pager = (RollPagerView) findViewById(R.id.unary__view_pager);
        unary_image_03 = (ImageView) findViewById(R.id.unary_image_03);
        unary_image_02 = (ImageView) findViewById(R.id.unary_image_02);
        unary_image_01 = (ImageView) findViewById(R.id.unary_image_01);
        unary_txt_02 = (TextView) findViewById(R.id.unary_txt_02);
        unary_txt_01 = (TextView) findViewById(R.id.unary_txt_01);
        unary_txt_03 = (TextView) findViewById(R.id.unary_txt_03);
        unary_recyclerView = (RBCallbkRecyclerView) findViewById(R.id.unary_recyclerView);
        initBannerImage();
        initNewOpen();
    }


    private void initBannerImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (unaryConfigBean.isIsSuccess()) {
                    String[] arrString = new String[unaryConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < unaryConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + unaryConfigBean.getAppConfigList().get(i).getImage();
                    }
                    //头部滚动banner
                    bannerAdapter = new BannerAdapter(mcontext, arrString, unary__view_pager);
                    unary__view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                    unary__view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                    unary__view_pager.setFocusable(false);
                } else {
                    Toast.makeText(mcontext, unaryConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                map.put("ConfigModule", "8");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void initNewOpen() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                UnaryListBean unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    List<UnaryListBean.ListResultBean> listResult = unaryListBean.getListResult();
                    if (listResult.size() >= 3) {
                        picasso.load(ImageUrl + listResult.get(0).getGoodsLogo())
                                .placeholder(UnaryActivity.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(unary_image_01);
                        unary_txt_01.setText(listResult.get(0).getGoodsName());
                        picasso.load(ImageUrl + listResult.get(1).getGoodsLogo())
                                .placeholder(UnaryActivity.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(unary_image_02);
                        unary_txt_02.setText(listResult.get(1).getGoodsName());
                        picasso.load(ImageUrl + listResult.get(2).getGoodsLogo())
                                .placeholder(UnaryActivity.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(unary_image_03);
                        unary_txt_03.setText(listResult.get(2).getGoodsName());
                        unary_recyclerView.setLayoutManager(new FullyGridLayoutManager(UnaryActivity.this, 2));
                        adapter = new UnaryAdapter(unaryListBean.getListResult(), UnaryActivity.this);
                        unary_recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(UnaryActivity.this);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String result = new String(error.networkResponse.data);
                Toast.makeText(mcontext, result, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("State", "2");
                map.put("IsPaging", "true");
                map.put("CurrentPage", "1");
                map.put("PageSize", "5");
                map.put("SortValue", "finishTime");
                map.put("SortType", "asc");
                map.put("TopCount", "3");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(mcontext, "当前点击项是：" + position, Toast.LENGTH_SHORT).show();
        Intent intent =new Intent();
    }
}
