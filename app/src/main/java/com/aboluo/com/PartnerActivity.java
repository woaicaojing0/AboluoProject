package com.aboluo.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.FullyLinearLayoutManager;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.RBCallbkRecyclerView;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.PartnerAdpater;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.model.GoodsListInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/12/20.
 * 合伙人acitivity
 */

public class PartnerActivity extends Activity implements PartnerAdpater.OnRecyclerViewItemClickListener, View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private RBCallbkRecyclerView parnter_RecyclerView;
    private int currentpages = 1;
    private int pagesize = 10;
    private GoodsListInfo listBean;
    private List<GoodsListInfo.ResultBean.GoodsListBean> goodsListBean;
    private PartnerAdpater partnerAdpater;
    private BaseConfigBean PartnerBannerBean;
    private RollPagerView partner_view_pager;
    private BannerAdapter bannerAdapter;
    private Button parnter_goods_detail_price, btn_default;
    private boolean ispricesort;
    private boolean isdefault = true;
    private EditText parnter_top_editsearch;
    private String GoodsName;
    private ImageView iv_partner_back, parnter_price_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parnter);
        init();
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        parnter_RecyclerView.setLayoutManager(linearLayoutManager);
        parnter_RecyclerView.setReachBottomRow(4);
        parnter_RecyclerView.setOnReachBottomListener(new RBCallbkRecyclerView.OnReachBottomListener() {
            @Override
            public void onReachBottom() {
                //即将到达几部，进行加载更多操作
                currentpages++;
                parnter_RecyclerView.setInTheBottom(false);
                initData(currentpages);
            }
        });
        int screenWidth = ScreenUtils.getScreenWidth(this);
        //设置顶部banner 的高度 高度是宽度的1/3；
        partner_view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 3));
        partner_view_pager.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
        parnter_goods_detail_price.setOnClickListener(this);
        btn_default.setOnClickListener(this);
        iv_partner_back.setOnClickListener(this);
        initData(1);
        parnter_top_editsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏软键盘
                    InputMethodManager inm = (InputMethodManager) PartnerActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inm.hideSoftInputFromWindow(parnter_top_editsearch.getWindowToken(), 0);
                    GoodsName = parnter_top_editsearch.getText().toString();
                    initData(1);
                    return true;
                } else {
                    return false;
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
        parnter_RecyclerView = (RBCallbkRecyclerView) findViewById(R.id.parnter_RecyclerView);
        partner_view_pager = (RollPagerView) findViewById(R.id.partner_view_pager);
        parnter_goods_detail_price = (Button) findViewById(R.id.parnter_goods_detail_price);
        btn_default = (Button) findViewById(R.id.btn_default);
        parnter_top_editsearch = (EditText) findViewById(R.id.parnter_top_editsearch);
        iv_partner_back = (ImageView) findViewById(R.id.iv_partner_back);
        parnter_price_img = (ImageView) findViewById(R.id.parnter_price_img);
        InitBanner();
    }

    /**
     * 加载recycleview 的数据
     *
     * @param currentpage 当前的页数，分页使用
     */
    private void initData(final int currentpage) {
        pdialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Goods/ReceiveGoodsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                listBean = gson.fromJson(response, GoodsListInfo.class);
                if (currentpage == 1) {
                    goodsListBean = listBean.getResult().getGoodsList();
                    parnter_RecyclerView.setInTheBottom(false);
                    partnerAdpater = new PartnerAdpater(goodsListBean, PartnerActivity.this);
                    parnter_RecyclerView.setAdapter(partnerAdpater);
                    partnerAdpater.setOnItemClickListener(PartnerActivity.this);
                } else {
                    if (goodsListBean == null) {
                        goodsListBean = listBean.getResult().getGoodsList();
                        partnerAdpater = new PartnerAdpater(goodsListBean, PartnerActivity.this);
                        parnter_RecyclerView.setAdapter(partnerAdpater);
                        partnerAdpater.setOnItemClickListener(PartnerActivity.this);
                    } else {
                        List<GoodsListInfo.ResultBean.GoodsListBean> goodsListBean2 = listBean.getResult().getGoodsList();
                        goodsListBean.addAll(goodsListBean2);
//                        partnerAdpater.notifyDataSetChanged();
                        if (goodsListBean2.size() == 0) {
                            //清除下拉滑动事件
                            parnter_RecyclerView.setHasdata(true);
                        } else {
                            partnerAdpater = new PartnerAdpater(goodsListBean, PartnerActivity.this);
                            parnter_RecyclerView.setAdapter(partnerAdpater);
                            partnerAdpater.setOnItemClickListener(PartnerActivity.this);
                        }
                    }

                }
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
                map.put("APPToken", APPToken);
                map.put("CurrentPage", String.valueOf(currentpage));
                if (isdefault) {
                } else {
                    if (ispricesort) {
                        map.put("IsPriceSort", "true");
                    } else {
                        map.put("IsPriceSort", "false");
                    }
                }
                if (GoodsName == null) {
                } else if (GoodsName.length() == 0) {
                } else {
                    map.put("GoodsName", GoodsName);
                }
                map.put("PageSize", String.valueOf(pagesize));
                map.put("GoodsTypeId", "238"); //合伙人商品的id
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_default:
                currentpages = 1;
                btn_default.setTextColor(getResources().getColor(R.color.btn_color));
                parnter_goods_detail_price.setTextColor(Color.BLACK);
                parnter_price_img.setImageResource(R.drawable.triangle_down_fill);
                isdefault = true;
                parnter_RecyclerView.setHasdata(false);
                initData(1);
                break;
            case R.id.parnter_goods_detail_price:
                currentpages = 1;
                btn_default.setTextColor(Color.BLACK);
                parnter_goods_detail_price.setTextColor(getResources().getColor(R.color.btn_color));
                ispricesort = !ispricesort;
                if (ispricesort) {
                    parnter_price_img.setImageResource(R.drawable.triangle_up_fill_color);
                } else {
                    parnter_price_img.setImageResource(R.drawable.triangle_down_fill_color);

                }
                parnter_RecyclerView.setHasdata(false);
                isdefault = false;
                initData(1);
                break;
            case R.id.iv_partner_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //  Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        int goods_id = goodsListBean.get(position).getGoodsId();
        Intent intent = new Intent(this, PartnerGoodsDetailActivity.class);
        intent.putExtra("goods_id", goods_id);
        intent.putExtra("goods_type_id", "商品类型也就是合伙人");
        String transitionName = "detail";
        ActivityOptionsCompat transitionActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(PartnerActivity.this, view.findViewById(R.id.parnter_goods_image), transitionName);
        startActivity(intent, transitionActivityOptionsCompat.toBundle());
    }

    /**
     * 加载合伙人首部的banner
     */
    private void InitBanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                PartnerBannerBean = gson.fromJson(response, BaseConfigBean.class);
                if (PartnerBannerBean.isIsSuccess()) {
                    String[] arrString = new String[PartnerBannerBean.getAppConfigList().size()];
                    for (int i = 0; i < PartnerBannerBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + PartnerBannerBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length == 0) {
                        partner_view_pager.setVisibility(View.GONE);
                    } else {
                        //头部滚动banner
                        partner_view_pager.setVisibility(View.VISIBLE);
                        bannerAdapter = new BannerAdapter(PartnerActivity.this, arrString, partner_view_pager);
                        partner_view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                        partner_view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                        partner_view_pager.setFocusable(false);
                    }
                } else {
                    Toast.makeText(PartnerActivity.this, PartnerBannerBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] data = error.networkResponse.data;
                Toast.makeText(PartnerActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "9");
                map.put("APPToken", APPToken);
                return map;
            }

        };
        requestQueue.add(stringRequest);
    }

    /**
     * 获取合伙人对应的商品的id
     */
    private void GetPartnerTypeId() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Toast.makeText(PartnerActivity.this, new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "4");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
