package com.aboluo.com;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.RBCallbkRecyclerView;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.UnaryAdapter;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.model.ShopCarBean;
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
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJ on 2016/12/2.
 */

public class UnaryActivity extends FragmentActivity implements UnaryAdapter.OnRecyclerViewItemClickListener, View.OnClickListener {
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
    private UnaryListBean unaryListBean;
    private TextView unary_popularity, unary_new, unary_introduce;
    private int currentpage = 1;
    private String sort = null;
    private String sorttype = null;
    private WebView webview_introduce;
    private LinearLayout unary_publish;
    private ArrayList<ShopCarBean.ResultBean.GoodsShoppingCartListBean> goodsShoppingCartListBean; //传入下订单的信息

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
                if (sort == null || sorttype == null) {
                } else {
                    currentpage++;
                    GetUnaryListBean("2", currentpage, sort, sorttype);
                }

            }
        });
        unary_popularity.setOnClickListener(this);
        unary_new.setOnClickListener(this);
        unary_introduce.setOnClickListener(this);
        unary_publish.setOnClickListener(this);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        unary__view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (screenWidth)*2 / 5));
        unary__view_pager.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
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
        unary_popularity = (TextView) findViewById(R.id.unary_popularity);
        unary_new = (TextView) findViewById(R.id.unary_new);
        unary_introduce = (TextView) findViewById(R.id.unary_introduce);
        unary_recyclerView = (RBCallbkRecyclerView) findViewById(R.id.unary_recyclerView);
        webview_introduce = (WebView) findViewById(R.id.webview_introduce);
        unary_publish = (LinearLayout) findViewById(R.id.unary_publish);
        unary_popularity.setTextColor(UnaryActivity.this.getResources().getColor(R.color.btn_color));
        initBannerImage();
        initNewOpen();
        InitUnaryListBean();
        initWebview();
        goodsShoppingCartListBean = new ArrayList<>();
    }

    private void initWebview() {
        webview_introduce.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview_introduce.setVerticalScrollBarEnabled(false);
        webview_introduce.setVerticalScrollbarOverlay(false);
        webview_introduce.setHorizontalScrollBarEnabled(false);
        webview_introduce.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = webview_introduce.getSettings();
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        webview_introduce.loadUrl("www.ly.com");
        webview_introduce.setWebViewClient(new WebViewClient());
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

    /**
     * 加载显示的正在揭晓的一元购
     */
    private void initNewOpen() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
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
                //最先开奖的放在前面
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
        UnaryListBean.ListResultBean listResultBean = unaryListBean.getListResult().get(position);
        Intent intent = new Intent(UnaryActivity.this, UnaryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", listResultBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int key = v.getId();
        switch (v.getId()) {
            case R.id.unary_popularity: // 人气
                CleanTxtView();
                unary_popularity.setTextColor(this.getResources().getColor(R.color.btn_color));
                currentpage = 1;
                sort = "joinCount";
                sorttype = "desc";
                GetUnaryListBean("1", currentpage, sort, sorttype);
                break;
            case R.id.unary_new: //最新
                CleanTxtView();
                unary_new.setTextColor(this.getResources().getColor(R.color.btn_color));
                currentpage = 1;
                sort = "StartTime";
                sorttype = "desc";
                GetUnaryListBean("1", currentpage, sort, sorttype);
                break;
            case R.id.unary_introduce: //玩法介绍
                CleanTxtView();
                webview_introduce.setVisibility(View.VISIBLE);
                unary_recyclerView.setVisibility(View.GONE);
                unary_introduce.setTextColor(this.getResources().getColor(R.color.btn_color));
                break;
            case R.id.unary_publish:
                Intent intent = new Intent(UnaryActivity.this, UnaryPublishActivity.class);
                startActivity(intent);
                break;
            case R.id.unary_begin:
                if (CommonUtils.IsLogin(UnaryActivity.this)) {
                    goodsShoppingCartListBean.clear();
                    if (tag != null && tag instanceof Integer) {
                        final int position = (Integer) tag;
                        UnaryListBean.ListResultBean listResultBean = unaryListBean.getListResult().get(position);
                        ShopCarBean.ResultBean.GoodsShoppingCartListBean goodsShoppingCartListBean2 = new ShopCarBean.ResultBean.GoodsShoppingCartListBean(
                                listResultBean.getGoodsId(), listResultBean.getGoodsColorId(),
                                listResultBean.getColorName() == null ? "0" : listResultBean.getColorName().toString(),
                                listResultBean.getGoodsStandId(), listResultBean.getColorName() == null ? "0" : listResultBean.getColorName().toString(),
                                1, 0, listResultBean.getGoodsName(), listResultBean.getGoodsLogo().toString(), 1
                        );
                        goodsShoppingCartListBean.add(goodsShoppingCartListBean2);
                        Intent intent1 = new Intent(UnaryActivity.this, MakeOrderActivity.class);
                        intent1.putExtra("allmoney", 1);
                        intent1.putExtra("data", goodsShoppingCartListBean);
                        intent1.putExtra("payfrom", "4"); //代表从一元购结算的
                        startActivity(intent1);
                    }
                } else {
                    Intent intent2 = new Intent(UnaryActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    Toast.makeText(UnaryActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
    }

}

    /**
     * @param status   四种状态：1代表正在开抢 2等待开奖 3已开奖 0 暂未开始
     * @param page     页数，默认是每页20条数据
     * @param sort     排序根据
     * @param sorttype 排序方式 desc或者asc
     */
    private void GetUnaryListBean(final String status, final int page, final String sort, final String sorttype) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    unary_recyclerView.setLayoutManager(new FullyGridLayoutManager(UnaryActivity.this, 2));
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(UnaryActivity.this);
                    adapter.setOnBeginClickListener(UnaryActivity.this);
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
                //最先开奖的放在前面
                Map<String, String> map = new HashMap<>();
                map.put("State", status);
                map.put("Current", String.valueOf(page));
                map.put("PageSize", "20");
                map.put("SortValue", sort);
                map.put("SortType", sorttype);
                map.put("TopCount", "0");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 初始化一元购 默认是选择人气
     */
    private void InitUnaryListBean() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    unary_recyclerView.setLayoutManager(new FullyGridLayoutManager(UnaryActivity.this, 2));
                    adapter = new UnaryAdapter(unaryListBean.getListResult(), UnaryActivity.this);
                    unary_recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(UnaryActivity.this);
                    adapter.setOnBeginClickListener(UnaryActivity.this);
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
                //最先开奖的放在前面
                Map<String, String> map = new HashMap<>();
                map.put("State", "1");
                map.put("Current", "1");
                map.put("PageSize", "20");
                map.put("SortValue", "joinCount");
                map.put("SortType", "desc");
                map.put("TopCount", "0");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CleanTxtView() {
        webview_introduce.setVisibility(View.GONE);
        unary_recyclerView.setVisibility(View.VISIBLE);
        unary_popularity.setTextColor(Color.parseColor("#737373"));
        unary_new.setTextColor(Color.parseColor("#737373"));
        unary_introduce.setTextColor(Color.parseColor("#737373"));
    }
}
